package rc55.mc.zerocraft.block.fluid;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import rc55.mc.zerocraft.fluid.ZeroCraftFluidTags;
import rc55.mc.zerocraft.fluid.ZeroCraftFluids;

public class ScarletWaterFluidBlock extends FluidBlock {
    public ScarletWaterFluidBlock() {
        super(ZeroCraftFluids.SCARLET_WATER, Settings.create()
                .mapColor(MapColor.RED)
                .replaceable()
                .noCollision()
                .ticksRandomly()
                .strength(100.0F)
                .luminance(state -> 2)
                .pistonBehavior(PistonBehavior.DESTROY)
                .dropsNothing()
                .liquid()
                .sounds(BlockSoundGroup.INTENTIONALLY_EMPTY));
    }
    //放置后
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (this.receiveNeighborFluids(world, pos, state)) {
            world.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
    }
    //更新
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        if (this.receiveNeighborFluids(world, pos, state)) {
            world.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
    }
    //接触其他流体时
    private boolean receiveNeighborFluids(World world, BlockPos pos, BlockState state) {
        if (this.fluid.isIn(ZeroCraftFluidTags.SCARLET_WATER)) {
            for (Direction direction : FLOW_DIRECTIONS) {
                BlockPos newPos = pos.offset(direction.getOpposite());
                if (world.getFluidState(newPos).isIn(FluidTags.LAVA)) {//遇岩浆表现与水相同
                    Block block = world.getFluidState(newPos).isStill() ? Blocks.OBSIDIAN : Blocks.COBBLESTONE;
                    world.setBlockState(newPos, block.getDefaultState());
                    return false;
                } else if (world.getFluidState(newPos).isIn(FluidTags.WATER)) {//遇水污染
                    if (Random.create().nextBetween(0, 5) == 0) {// 1/5机率，防崩
                        BlockState newState = ZeroCraftFluids.SCARLET_WATER.getDefaultState().getBlockState();//静态水
                        if (!world.getFluidState(newPos).isStill()) {//流动水
                            newState = ZeroCraftFluids.FLOWING_SCARLET_WATER.getDefaultState().getBlockState();
                            for (Property<?> property : world.getFluidState(newPos).getBlockState().getProperties()) {
                                with(newState, property, getPropertyValue(newState, property));//设置水流动属性
                            }
                        }
                        world.setBlockState(newPos, newState);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //设置属性用
    private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
        return (BlockState)property.parse(name).map(value -> state.with(property, value)).orElse(state);
    }
    //提供属性值
    private <T extends Comparable<T>> String getPropertyValue(BlockState state, Property<T> property){
        return property.name(state.get(property));
    }
}
