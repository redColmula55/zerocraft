package rc55.mc.zerocraft.fluid;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftFluids {
    //液体
    public static final FlowableFluid SCARLET_WATER = register("scarlet_water", new ScarletWaterFluid.Still());
    public static final FlowableFluid FLOWING_SCARLET_WATER = register("flowing_scarlet_water", new ScarletWaterFluid.Flowing());
    //气体
    public static final FloatableFluid STEAM = register("steam", new SteamFluid.Still());
    public static final FloatableFluid FLOWING_STEAM = register("flowing_steam", new SteamFluid.Flowing());

    //注册用
    private static <T extends Fluid> T register(String id, T value) {
        return Registry.register(Registries.FLUID, new Identifier(ZeroCraft.MODID, id), value);
    }
    static {
        for (Fluid fluid : Registries.FLUID) {
            for (FluidState fluidState : fluid.getStateManager().getStates()) {
                Fluid.STATE_IDS.add(fluidState);
            }
        }
    }
    //初始化注册
    public static void regFluids(){
        ZeroCraft.LOGGER.info("ZeroCraft fluids registered.");
    }
}
