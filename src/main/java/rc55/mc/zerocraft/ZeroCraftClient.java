package rc55.mc.zerocraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import rc55.mc.zerocraft.client.ZeroCraftHandledScreens;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;
import rc55.mc.zerocraft.client.ZeroCraftRenderers;
import rc55.mc.zerocraft.client.model.ZeroCraftModelPredicateProvider;

@Environment(EnvType.CLIENT)
public class ZeroCraftClient implements ClientModInitializer {
    //仅客户端
    @Override
    public void onInitializeClient() {
        ZeroCraft.LOGGER.info("ZeroCraft client side loading...");
        ZeroCraftKeyBinds.regKeyBinds();//按键绑定
        ZeroCraftRenderers.addRenderer();//渲染
        ZeroCraftModelPredicateProvider.addModelPredicate();//模型谓词-物品材质用
        ZeroCraftHandledScreens.addScreens();//客户端侧屏幕
        ZeroCraft.LOGGER.info("ZeroCraft client side loaded.");
    }
}
