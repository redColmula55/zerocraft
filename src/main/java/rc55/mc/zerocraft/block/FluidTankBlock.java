package rc55.mc.zerocraft.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.block.entity.FluidTankBlockEntity;
import rc55.mc.zerocraft.block.entity.ZeroCraftBlockEntityType;

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
                                        player.sendMessage(Text.of("full"));
                                        return ActionResult.CONSUME;
                                    }
                                } else {//已有其他液体
                                    player.sendMessage(Text.of("other: "+storedFluidId));
                                    return ActionResult.CONSUME;
                                }
                            }
                        } else {//取出
                            if (stack.isOf(Items.BUCKET)){
                                if (storedFluidAmount >= 1000){
                                    stack.decrement(1);
                                    Fluid storedFluidType = Registries.FLUID.get(new Identifier(storedFluidId));
                                    player.dropItem(storedFluidType.getBucketItem());//new BucketItem(storedFluidType, new Item.Settings())
                                    player.sendMessage(Text.of(storedFluidId));
                                    storedFluidAmount-=1000;
                                    if (storedFluidAmount == 0){
                                        storedFluidId = "";
                                    }
                                    nbt.putString("fluid", storedFluidId);
                                    nbt.putInt("amount", storedFluidAmount);
                                    fluidTankBlockEntity.readNbt(nbt);
                                    fluidTankBlockEntity.markDirty();
                                } else {//没有了
                                    player.sendMessage(Text.of("empty or too few"));
                                    return ActionResult.CONSUME;
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
        //return checkType(type, ZeroCraftBlockEntityType.FLUID_TANK, ShulkerBoxBlockEntity::tick);
        return (world1, pos, state1, blockEntity) -> FluidTankBlockEntity.tick(world1, pos, state1, (FluidTankBlockEntity) blockEntity);
    }
    //被破坏时
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
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

}
