package rc55.mc.zerocraft.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.client.renderer.ChestplateElytraFeatureRenderer;
import rc55.mc.zerocraft.fluid.ZeroCraftFluids;

@Environment(EnvType.CLIENT)
public class ZeroCraftRenderers {
    //初始化注册
    public static void addRenderer(){
        addFeatureRenderer();
        addFluidRenderer();
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
    //渲染流体
    private static void addFluidRenderer() {
        //透明
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ZeroCraftFluids.SCARLET_WATER, ZeroCraftFluids.FLOWING_SCARLET_WATER);
        //材质
        FluidRenderHandlerRegistry.INSTANCE.register(ZeroCraftFluids.SCARLET_WATER, ZeroCraftFluids.FLOWING_SCARLET_WATER, new SimpleFluidRenderHandler(new Identifier("minecraft:block/water_still"), new Identifier("minecraft:block/water_flow"), 0xff0000));
    }
}
