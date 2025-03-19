package rc55.mc.zerocraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;

@Environment(EnvType.CLIENT)
public class ZeroCraftClient implements ClientModInitializer {
    //仅客户端
    @Override
    public void onInitializeClient() {
        ZeroCraft.LOGGER.info("ZeroCraft client side loading...");
        ZeroCraftKeyBinds.regKeyBinds();
        ZeroCraft.LOGGER.info("ZeroCraft client side loaded.");
    }
}
