package rc55.mc.zerocraft.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.client.screen.FluidTankHandledScreen;
import rc55.mc.zerocraft.screen.ZeroCraftScreenHandlerType;

@Environment(EnvType.CLIENT)
public class ZeroCraftHandledScreens {
    public static void addScreens(){
        HandledScreens.register(ZeroCraftScreenHandlerType.FLUID_TANK_SCREEN, FluidTankHandledScreen::new);
        ZeroCraft.LOGGER.info("ZeroCraft screens added.");
    }
}
