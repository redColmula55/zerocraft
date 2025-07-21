package rc55.mc.zerocraft.item.armor;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.api.Utils;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;

import java.util.List;

public class ScarletCrystalChestplateItem extends ArmorItem implements FabricElytraItem {
    public ScarletCrystalChestplateItem() {
        super(ZeroCraftArmorMaterials.SCARLET_CRYSTAL, Type.CHESTPLATE, new Settings().rarity(Rarity.RARE).fireproof());
    }
    //发包用
    public static final Identifier PACK_ID = new Identifier(ZeroCraft.MODID, "key_mode_switch_chestplate");

    //每刻触发
    //给予状态效果&检测按键是否按下
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        //仅玩家触发效果
        if (!world.isClient && entity instanceof PlayerEntity player){
            ItemStack chestplate = player.getInventory().getArmorStack(2);
            //判断是否穿上
            if (chestplate.isOf(this)){
                player.removeStatusEffect(StatusEffects.MINING_FATIGUE);
                player.removeStatusEffect(StatusEffects.WEAKNESS);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 221, 1, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 221, 1, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 221, 1, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 221, 1, false, false, false));
            }
            //按键是否按下
            ServerPlayNetworking.registerGlobalReceiver(PACK_ID, (server, serverPlayer, handler, buf, responseSender) -> {
                server.execute(() -> {
                    ItemStack armorStack = serverPlayer.getInventory().getArmorStack(2);
                    NbtCompound nbt = armorStack.getOrCreateNbt();
                    boolean fly = nbt.getBoolean("Fly");
                    //切换内置鞘翅开关
                    if (fly){
                        nbt.putBoolean("Fly", false);
                        Utils.sendMessage(serverPlayer, Text.translatable(this.getTranslationKey()+".mode.off"));
                    } else {
                        nbt.putBoolean("Fly", true);
                        Utils.sendMessage(serverPlayer, Text.translatable(this.getTranslationKey()+".mode.on"));
                    }
                });
            });
        }
    }
    //触发飞行
    @Override
    public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
        if (isFlyable(chestStack)) {
            if (tickElytra) {
                this.doVanillaElytraTick(entity, chestStack);
            }
            return true;
        } else {
            return false;
        }
    }
    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        tooltip.add(Text.translatable(this.getTranslationKey()+".mode.hint", new Object[]{ZeroCraftKeyBinds.CHESTPLATE_MODE_SWITCH_KEY.getBoundKeyLocalizedText()}).formatted(Formatting.GRAY));
        boolean fly = stack.getOrCreateNbt().getBoolean("Fly");
        if (fly){
            tooltip.add(Text.translatable(this.getTranslationKey()+".mode.on"));
        } else {
            tooltip.add(Text.translatable(this.getTranslationKey()+".mode.off"));
        }
    }
    //判断是否可以飞行
    public static boolean isFlyable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1 && stack.getOrCreateNbt().getBoolean("Fly");
    }
    //是否允许披风渲染
    public static boolean allowCapeRender(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        return !abstractClientPlayerEntity.getInventory().getArmorStack(2).getOrCreateNbt().getBoolean("Fly");
    }
}
