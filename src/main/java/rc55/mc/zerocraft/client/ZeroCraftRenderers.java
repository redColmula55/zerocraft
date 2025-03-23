package rc55.mc.zerocraft.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.client.renderer.ChestplateElytraFeatureRenderer;

@Environment(EnvType.CLIENT)
public class ZeroCraftRenderers {
    //初始化注册
    public static void addRenderer(){
        addFeatureRenderer();
        ZeroCraft.LOGGER.info("Added renderers.");
    }
    //渲染额外属性
    private static void addFeatureRenderer(){
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(((entityType, livingEntityRenderer, registrationHelper, context) -> {
            if (livingEntityRenderer instanceof PlayerEntityRenderer){
                //盔甲鞘翅
                registrationHelper.register(new ChestplateElytraFeatureRenderer<>(livingEntityRenderer, context.getModelLoader()));
            }
        }));
    }
}
