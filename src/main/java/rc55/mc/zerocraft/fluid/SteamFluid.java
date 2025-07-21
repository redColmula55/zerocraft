package rc55.mc.zerocraft.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.item.ZeroCraftItems;

public abstract class SteamFluid extends FloatableFluid {

    @Override
    public Fluid getFlowing() {
        return ZeroCraftFluids.FLOWING_STEAM;
    }

    @Override
    public Fluid getStill() {
        return ZeroCraftFluids.STEAM;
    }

    @Override
    public Item getBucketItem() {
        return ZeroCraftItems.STEAM_BUCKET;
    }

    @Override
    public int getTemperature() {
        return 400;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ZeroCraftBlocks.STEAM.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    public static class Flowing extends SteamFluid {
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

    public static class Still extends SteamFluid {
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
