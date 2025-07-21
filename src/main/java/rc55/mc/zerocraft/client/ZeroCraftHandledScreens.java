package rc55.mc.zerocraft.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.client.screen.BoilerHandledScreen;
import rc55.mc.zerocraft.client.screen.FluidTankHandledScreen;
import rc55.mc.zerocraft.client.screen.Generic1x1ContainerHandledScreen;
import rc55.mc.zerocraft.screen.ZeroCraftScreenHandlerType;

@Environment(EnvType.CLIENT)
public class ZeroCraftHandledScreens {
    public static void addScreens() {
        //通用
        HandledScreens.register(ZeroCraftScreenHandlerType.GENERIC_1x1_CONTAINER, Generic1x1ContainerHandledScreen::new);
        //科技线
        //储罐
        HandledScreens.register(ZeroCraftScreenHandlerType.FLUID_TANK_SCREEN, FluidTankHandledScreen::new);
        //蒸汽
        HandledScreens.register(ZeroCraftScreenHandlerType.BOILER_SCREEN, BoilerHandledScreen::new);
        ZeroCraft.LOGGER.info("ZeroCraft screens added.");
    }
}
