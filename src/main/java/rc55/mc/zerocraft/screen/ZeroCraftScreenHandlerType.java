package rc55.mc.zerocraft.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftScreenHandlerType {

    //通用
    public static final ScreenHandlerType<Generic1x1ContainerScreenHandler> GENERIC_1x1_CONTAINER = register("generic_1x1", Generic1x1ContainerScreenHandler::new);
    //科技线
    //储罐
    //public static final ScreenHandlerType<FluidTankScreenHandler> FLUID_TANK_SCREEN = ScreenHandlerRegistry.registerExtended(new Identifier(ZeroCraft.MODID, "fluid_tank_screen"), FluidTankScreenHandler::new);
    public static final ScreenHandlerType<FluidTankScreenHandler> FLUID_TANK_SCREEN = register("fluid_tank", FluidTankScreenHandler::new);
    //蒸汽
    public static final ScreenHandlerType<BoilerScreenHandler> BOILER_SCREEN = register("boiler", BoilerScreenHandler::new);
    //注册用
    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(ZeroCraft.MODID, id), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }
    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory, FeatureFlag... requiredFeatures) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(ZeroCraft.MODID, id), new ScreenHandlerType<>(factory, FeatureFlags.FEATURE_MANAGER.featureSetOf(requiredFeatures)));
    }
    //初始化注册
    public static void addScreenHandler(){
        ZeroCraft.LOGGER.info("ZeroCraft screen handlers added.");
    }
}
