package rc55.mc.zerocraft.mixin;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rc55.mc.zerocraft.api.Utils;
import rc55.mc.zerocraft.fluid.ZeroCraftFluidTags;
import rc55.mc.zerocraft.world.ZeroCraftGameRules;

@Mixin(WaterFluid.class)
public abstract class WaterFluidMixin {
    //使赤潮污染水可以流入
    @Inject(at = @At("RETURN"), method = "canBeReplacedWith", cancellable = true)
    public void canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        boolean bl = cir.getReturnValueZ();
        if (world instanceof ServerWorld serverWorld) {
            int chance = serverWorld.getGameRules().getInt(ZeroCraftGameRules.SCARLET_WATER_INFEST_CHANCE);
            if (Utils.getRandomPercent(serverWorld.getRandom(), chance)) {//感染几率
                cir.setReturnValue(bl || (fluid.isIn(ZeroCraftFluidTags.SCARLET_WATER) && direction == Direction.DOWN));
            }
        } else cir.setReturnValue(bl);
    }
}
