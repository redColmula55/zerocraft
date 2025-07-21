package rc55.mc.zerocraft.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.block.entity.FluidTankBlockEntity;
import rc55.mc.zerocraft.block.entity.ZeroCraftBlockEntityType;
import rc55.mc.zerocraft.screen.FluidTankScreenHandler;

import java.util.List;

public class FluidTankBlock extends BlockWithEntity {

    private final int capacity;
    private final int maxTemperature;

    protected FluidTankBlock(int capacity, int maxTemperature, Settings settings) {
        super(settings);
        this.capacity = capacity;
        this.maxTemperature = maxTemperature;
    }

    //渲染
    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }
    //方块实体
    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FluidTankBlockEntity(pos, state, this.capacity, this.maxTemperature);
    }
    //右键效果
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit){
        ItemStack stack = player.getStackInHand(hand);
        FluidTankBlockEntity tank = world.getBlockEntity(pos) != null ? (FluidTankBlockEntity) world.getBlockEntity(pos) : null;
        if (tank != null) {
            if (stack.isOf(Items.BUCKET)){//取出
                if (!this.onExtract(world, tank, player, stack)) {//未成功则打开gui
                    player.openHandledScreen(createScreenHandlerFactory(state, world, pos));
                }
            } else if (stack.getItem() instanceof BucketItem) {//放入
                if (!this.onInsert(world, tank, player, stack)) {
                    player.openHandledScreen(createScreenHandlerFactory(state, world, pos));
                }
            } else {//打开gui
                player.openHandledScreen(createScreenHandlerFactory(state, world, pos));
            }
            tank.markDirty();
        }
        return ActionResult.SUCCESS;
    }
    //每刻执行
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeroCraftBlockEntityType.FLUID_TANK, FluidTankBlockEntity::tick);
    }
    //被破坏时
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
    //放置效果
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity) {
            if (itemStack.hasCustomName()) {
                fluidTankBlockEntity.setCustomName(itemStack.getName());
            }
            //FluidTankBlockEntity.setTankProperty(fluidTankBlockEntity, this.capacity, this.maxTemperature);
        }
    }
    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        NbtCompound nbt = BlockItem.getBlockEntityNbt(stack);
        if (nbt == null || nbt.getCompound("Fluid").isEmpty()) {
            tooltip.add(Text.translatable(FluidTankScreenHandler.TANK_EMPTY_TRANS_KEY));
        } else {
            NbtCompound nbt2 = nbt.getCompound("Fluid");
            String id = nbt2.getString("id");
            tooltip.add(Text.translatable(FluidTankScreenHandler.TANK_CARRYING_TRANS_KEY, new Object[]{nbt2.getInt("amount"), Text.translatable("block." + new Identifier(id).toTranslationKey())}));
        }
    }

    /**
     * 放入流体
     * @param world 世界实例
     * @param blockEntity 方块实体实例
     * @param player 交互的玩家实例
     * @param stack 手持物品
     * @return 是否成功放入
     */
    private boolean onInsert(World world, FluidTankBlockEntity blockEntity, PlayerEntity player, ItemStack stack) {
        int storedFluidRawId = FluidTankBlockEntity.getStoredFluidRawId(blockEntity);
        Fluid storedFluid = storedFluidRawId == -1 ? Fluids.EMPTY : Registries.FLUID.get(storedFluidRawId);
        int storedFluidAmount = FluidTankBlockEntity.getStoredFluidAmount(blockEntity);
        for (Fluid fluid : Registries.FLUID) {
            if (fluid.isStill(fluid.getDefaultState())) {
                String id = Registries.FLUID.getId(fluid).toString();
                if ((fluid.matchesType(storedFluid) || storedFluid.matchesType(Fluids.EMPTY)) && stack.isOf(fluid.getBucketItem()) && storedFluidAmount+1000 <= this.capacity && fluid.getTemperature() <= this.maxTemperature) {
                    FluidTankBlockEntity.setStoredFluid(blockEntity, id, storedFluidAmount+1000);
                    if (!player.isCreative()) {
                        stack.decrement(1);
                        player.dropItem(Items.BUCKET);
                    }
                    world.playSound(player, blockEntity.getPos(), fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS);
                    blockEntity.markDirty();
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 取出流体
     * @param world 世界实例
     * @param blockEntity 方块实体实例
     * @param player 交互的玩家实例
     * @param stack 手持物品
     * @return 是否成功取出
     */
    private boolean onExtract(World world, FluidTankBlockEntity blockEntity, PlayerEntity player, ItemStack stack) {
        int storedFluidRawId = FluidTankBlockEntity.getStoredFluidRawId(blockEntity);
        Fluid storedFluid = Registries.FLUID.get(storedFluidRawId);
        int storedFluidAmount = FluidTankBlockEntity.getStoredFluidAmount(blockEntity);
        if (!storedFluid.matchesType(Fluids.EMPTY) && storedFluidAmount-1000 >= 0) {
            String id = Registries.FLUID.getId(storedFluid).toString();
            if (storedFluidAmount-1000 <= 0) {//没了就重新设置为空
                FluidTankBlockEntity.setStoredFluid(blockEntity, "", 0);
            } else {//正常减少
                FluidTankBlockEntity.setStoredFluid(blockEntity, id, storedFluidAmount-1000);
            }
            if (!player.isCreative()) {//创造不减少不返还
                stack.decrement(1);
                player.dropItem(storedFluid.getBucketItem());
            }
            world.playSound(player, blockEntity.getPos(), storedFluid.getBucketFillSound().orElse(SoundEvents.ITEM_BUCKET_FILL), SoundCategory.BLOCKS);
            blockEntity.markDirty();
            return true;
        }
        return false;
    }
}
