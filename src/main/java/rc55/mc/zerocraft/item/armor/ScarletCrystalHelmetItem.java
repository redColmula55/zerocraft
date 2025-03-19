package rc55.mc.zerocraft.item.armor;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;

import java.util.List;

public class ScarletCrystalHelmetItem extends ArmorItem {
    public ScarletCrystalHelmetItem() {
        super(ZeroCraftArmorMaterials.SCARLET_CRYSTAL, Type.HELMET, new Settings().rarity(Rarity.RARE).fireproof());
    }
    //发包用
    public static final Identifier PACK_ID = new Identifier(ZeroCraft.MODID, "key_night_vision_helmet");
    //是否启用
    boolean enabled = false;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        //仅玩家触发效果
        if (!world.isClient && entity instanceof PlayerEntity player){
            //判断是否穿上
            if (player.getInventory().getArmorStack(3).isOf(this)){
                if (enabled){
                    player.removeStatusEffect(StatusEffects.DARKNESS);
                    player.removeStatusEffect(StatusEffects.BLINDNESS);
                    player.removeStatusEffect(StatusEffects.NAUSEA);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 221, 0, false, false, false));
                } else {
                    player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                }
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 221, 0, false, false, false));
            }
            //服务端接收包
            ServerPlayNetworking.registerGlobalReceiver(PACK_ID, ((server, serverPlayer, handler, buf, responseSender) -> {
                if (serverPlayer.getInventory().getArmorStack(3).isOf(this)){
                    enabled = !enabled;
                    if (enabled){
                        sendMessage(serverPlayer, Text.translatable(this.getTranslationKey()+".night_vision.on"));
                    } else {
                        sendMessage(serverPlayer, Text.translatable(this.getTranslationKey()+".night_vision.off"));
                    }
                }
            }));
        }
    }
    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        tooltip.add(Text.translatable(this.getTranslationKey()+".hint", new Object[]{Text.translatable(ZeroCraftKeyBinds.HELMET_MODE_SWITCH_KEY.getBoundKeyTranslationKey())}).formatted(Formatting.GRAY));

        if (this.enabled){
            tooltip.add(Text.translatable(this.getTranslationKey()+".night_vision.on"));
        } else {
            tooltip.add(Text.translatable(this.getTranslationKey()+".night_vision.off"));
        }
    }
    private static void sendMessage(PlayerEntity player, Text message) {
        ((ServerPlayerEntity)player).sendMessageToClient(message, true);
    }
}
