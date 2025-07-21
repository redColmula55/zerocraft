package rc55.mc.zerocraft.mixin;

import net.minecraft.block.CoralBlockBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CoralBlockBlock.class)
public abstract class CoralBlockBlockMixin {
    //设置仅在水中保持活性
    @Inject(at = @At("RETURN"), method = "isInWater", cancellable = true)
    public void isInWater(BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        boolean returnValue = false;
        for (Direction direction : Direction.values()) {
            Fluid fluid = world.getFluidState(pos.offset(direction)).getFluid();
            if (fluid.matchesType(Fluids.WATER)) {
                returnValue = true;
            }
        }
        cir.setReturnValue(returnValue);
    }
}
