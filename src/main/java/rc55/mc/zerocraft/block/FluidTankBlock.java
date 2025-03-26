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
import java.util.Objects;

public class FluidTankBlock extends BlockWithEntity {
    protected FluidTankBlock(Settings settings) {
        super(settings);
    }
    //渲染
    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }
    //方块实体
    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FluidTankBlockEntity(pos, state);
    }
    //右键效果
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit){
        if (!world.isClient){
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof BucketItem){//是否手持水桶
                BlockEntity blockEntity = Objects.requireNonNull(world.getBlockEntity(pos));
                if (blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity){
                    NbtCompound nbt = fluidTankBlockEntity.createNbt();
                    String storedFluidId = nbt.getString("fluid");
                    int storedFluidAmount = nbt.getInt("amount");
                    for (Fluid fluid : Registries.FLUID){
                        if (fluid.isStill(fluid.getDefaultState()) || fluid.getDefaultState().isEmpty()){//仅静止，避免重复
                            if (!fluid.matchesType(Fluids.EMPTY)){//倒入
                                if (stack.isOf(fluid.getBucketItem())){
                                    String id = String.valueOf(Registries.FLUID.getId(fluid));
                                    if (Objects.equals(id, storedFluidId) || storedFluidAmount == 0){
                                        if (storedFluidAmount <= 15000){
                                            stack.decrement(1);
                                            player.dropItem(Items.BUCKET);
                                            storedFluidAmount+=1000;
                                            nbt.putString("fluid", id);
                                            nbt.putInt("amount", storedFluidAmount);
                                            fluidTankBlockEntity.readNbt(nbt);
                                            fluidTankBlockEntity.markDirty();
                                        } else {//满了
                                            player.openHandledScreen(createScreenHandlerFactory(state, world, pos));
                                            return ActionResult.CONSUME;
                                        }
                                    } else {//已有其他液体
                                        player.openHandledScreen(createScreenHandlerFactory(state, world, pos));
                                        return ActionResult.CONSUME;
                                    }
                                }
                            } else {//取出
                                if (stack.isOf(Items.BUCKET)){
                                    if (storedFluidAmount >= 1000){
                                        stack.decrement(1);
                                        Fluid storedFluidType = Registries.FLUID.get(new Identifier(storedFluidId));
                                        player.dropItem(storedFluidType.getBucketItem());
                                        storedFluidAmount-=1000;
                                        if (storedFluidAmount == 0){
                                            storedFluidId = "";
                                        }
                                        nbt.putString("fluid", storedFluidId);
                                        nbt.putInt("amount", storedFluidAmount);
                                        fluidTankBlockEntity.readNbt(nbt);
                                        fluidTankBlockEntity.markDirty();
                                    } else {//没有了
                                        player.openHandledScreen(createScreenHandlerFactory(state, world, pos));
                                        return ActionResult.CONSUME;
                                    }
                                }
                            }
                        }
                    }
                } else {//数据错误
                    return ActionResult.FAIL;
                }
            } else {//打开gui
                player.openHandledScreen(createScreenHandlerFactory(state, world, pos));
            }
        }
        return ActionResult.SUCCESS;
    }
    //每刻执行
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeroCraftBlockEntityType.FLUID_TANK, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
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
        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FluidTankBlockEntity) {
                ((FluidTankBlockEntity)blockEntity).setCustomName(itemStack.getName());
            }
        }
    }
    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        NbtCompound nbt = BlockItem.getBlockEntityNbt(stack);
        if (nbt != null) {
            String id = nbt.getString("fluid");
            if (id.isEmpty()) {
                tooltip.add(Text.translatable(FluidTankScreenHandler.TANK_EMPTY_TRANS_KEY));
            } else {
                tooltip.add(Text.translatable(FluidTankScreenHandler.TANK_CARRYING_TRANS_KEY, new Object[]{nbt.getInt("amount"), Text.translatable("block." + new Identifier(id).toTranslationKey())}));
            }
        }
    }
}
