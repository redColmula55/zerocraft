package rc55.mc.zerocraft.fluid;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.item.ZeroCraftItems;

public abstract class ScarletWaterFluid extends WaterFluid {
    //流动
    @Override
    public Fluid getFlowing() {
        return ZeroCraftFluids.FLOWING_SCARLET_WATER;
    }
    //静止
    @Override
    public Fluid getStill() {
        return ZeroCraftFluids.SCARLET_WATER;
    }
    //桶物品
    @Override
    public Item getBucketItem() {
        return ZeroCraftItems.SCARLET_WATER_BUCKET;
    }
    //渲染
    @Override
    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        super.randomDisplayTick(world, pos, state, random);
    }
    //是否符合
    @Override
    public boolean matchesType(Fluid fluid){
        return fluid == this.getStill() || fluid == this.getFlowing();
    }
    //方块形式
    @Override
    public BlockState toBlockState(FluidState state) {
        return ZeroCraftBlocks.SCARLET_WATER.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }
    //可替换其他流体
    @Override
    public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }
    //流动
    @Override
    protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        if (direction == Direction.DOWN) {
            FluidState newFluidState = world.getFluidState(pos);
            if (this.isIn(ZeroCraftFluidTags.SCARLET_WATER) && newFluidState.isIn(FluidTags.WATER)) {
                if (state.getBlock() instanceof FluidBlock) {
                    world.setBlockState(pos, ZeroCraftFluids.SCARLET_WATER.getDefaultState().getBlockState(), Block.NOTIFY_ALL);
                }
                return;
            }
        }
        super.flow(world, pos, state, direction, fluidState);
    }
    //流动形式
    public static class Flowing extends ScarletWaterFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }
    }
    //静止形式
    public static class Still extends ScarletWaterFluid {
        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }
}
