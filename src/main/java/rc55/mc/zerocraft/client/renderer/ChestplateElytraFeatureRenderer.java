package rc55.mc.zerocraft.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.item.ZeroCraftItems;
import rc55.mc.zerocraft.item.armor.ScarletCrystalChestplateItem;

@Environment(EnvType.CLIENT)
public class ChestplateElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier SKIN = new Identifier("textures/entity/elytra.png");
    private final ElytraEntityModel<T> elytra;

    public ChestplateElytraFeatureRenderer(FeatureRendererContext context, EntityModelLoader loader) {
        super(context);
        this.elytra = new ElytraEntityModel<>(loader.getModelPart(EntityModelLayers.ELYTRA));
    }

    public void render(
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l
    ) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (itemStack.isOf(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE) && ScarletCrystalChestplateItem.isFlyable(itemStack)) {
            Identifier identifier;
            if (livingEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                //关闭披风渲染
                LivingEntityFeatureRenderEvents.ALLOW_CAPE_RENDER.register(ScarletCrystalChestplateItem::allowCapeRender);
                //鞘翅特殊材质
                if (abstractClientPlayerEntity.canRenderElytraTexture() && abstractClientPlayerEntity.getElytraTexture() != null) {
                    identifier = abstractClientPlayerEntity.getElytraTexture();
                } else if (abstractClientPlayerEntity.canRenderCapeTexture()
                        && abstractClientPlayerEntity.getCapeTexture() != null
                        && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE)) {
                    identifier = abstractClientPlayerEntity.getCapeTexture();
                } else {
                    identifier = SKIN;
                }
            } else {
                identifier = SKIN;
            }

            matrixStack.push();
            matrixStack.translate(0.0F, 0.0F, 0.125F);
            this.getContextModel().copyStateTo(this.elytra);
            this.elytra.setAngles(livingEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(
                    vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(identifier), false, itemStack.hasGlint()
            );
            this.elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }
}
