package rc55.mc.zerocraft.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.FluidTags;

public interface FluidTemperature {
    /**流体温度（单位K）
     * 默认返回300，岩浆类则为1300
     * @return 温度，单位：开（K）
     */
    default int getTemperature() {
        Fluid fluid = (Fluid) this;
        return fluid.isIn(FluidTags.LAVA) ? 1300 : 300;
    }
}
