package rc55.mc.zerocraft.mixin;

import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import rc55.mc.zerocraft.fluid.FluidTemperature;

@Mixin(Fluid.class)
public abstract class FluidMixin implements FluidTemperature {
}
