package rc55.mc.zerocraft.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.item.ZeroCraftItems;
import rc55.mc.zerocraft.item.armor.ScarletCrystalChestplateItem;
import rc55.mc.zerocraft.item.armor.ScarletCrystalHelmetItem;
import rc55.mc.zerocraft.item.armor.ScarletCrystalLeggingsItem;
import rc55.mc.zerocraft.item.tool.*;

@Environment(EnvType.CLIENT)
public class ZeroCraftKeyBinds {

    //按键绑定分组
    public static final String CATEGORY = "key.category.zerocraft";
    //按键
    public static final KeyBinding TOOL_MODE_SWITCH_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.zerocraft.tool_mode_switch", InputUtil.GLFW_KEY_G, CATEGORY));
    public static final KeyBinding HELMET_MODE_SWITCH_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.zerocraft.helmet_mode_switch", InputUtil.GLFW_KEY_H, CATEGORY));
    public static final KeyBinding CHESTPLATE_MODE_SWITCH_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.zerocraft.chestplate_mode_switch", InputUtil.GLFW_KEY_K, CATEGORY));
    public static final KeyBinding LEGGINGS_INVENTORY_OPEN_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.zerocraft.leggings_inventory", InputUtil.GLFW_KEY_J, CATEGORY));

    //注册&监听按键
    //仅客户端
    public static void regKeyBinds(){
        ZeroCraft.LOGGER.info("ZeroCraft key bindings loaded.");

        //监听按键是否按下
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (ZeroCraftKeyBinds.TOOL_MODE_SWITCH_KEY.wasPressed()){
                PlayerEntity player = minecraftClient.player;
                if (player != null) {
                    //发包
                    if (player.isHolding(ZeroCraftItems.SCARLET_CRYSTAL_PICKAXE)){
                        ClientPlayNetworking.send(ScarletCrystalPickaxeItem.PACK_ID, PacketByteBufs.create());//稿
                    } else if (player.isHolding(ZeroCraftItems.SCARLET_CRYSTAL_SWORD)) {
                        ClientPlayNetworking.send(ScarletCrystalSwordItem.PACK_ID, PacketByteBufs.create());//剑
                    } else if (player.isHolding(ZeroCraftItems.SCARLET_CRYSTAL_AXE)) {
                        ClientPlayNetworking.send(ScarletCrystalAxeItem.PACK_ID, PacketByteBufs.create());//斧
                    } else if (player.isHolding(ZeroCraftItems.SCARLET_CRYSTAL_SHOVEL)) {
                        ClientPlayNetworking.send(ScarletCrystalShovelItem.PACK_ID, PacketByteBufs.create());//铲子
                    } else if (player.isHolding(ZeroCraftItems.SCARLET_CRYSTAL_HOE)) {
                        ClientPlayNetworking.send(ScarletCrystalHoeItem.PACK_ID, PacketByteBufs.create());//锄头
                    }
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (ZeroCraftKeyBinds.HELMET_MODE_SWITCH_KEY.wasPressed()){
                if (minecraftClient.player != null && minecraftClient.player.getInventory().getArmorStack(3).isOf(ZeroCraftItems.SCARLET_CRYSTAL_HELMET)) {
                    ClientPlayNetworking.send(ScarletCrystalHelmetItem.PACK_ID, PacketByteBufs.create());//头盔
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (ZeroCraftKeyBinds.LEGGINGS_INVENTORY_OPEN_KEY.wasPressed()){
                PacketByteBuf buf = PacketByteBufs.create();
                if (minecraftClient.player != null && minecraftClient.player.getInventory().getArmorStack(1).isOf(ZeroCraftItems.SCARLET_CRYSTAL_LEGGINGS)) {
                    ClientPlayNetworking.send(ScarletCrystalLeggingsItem.PACK_ID, buf);
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (ZeroCraftKeyBinds.CHESTPLATE_MODE_SWITCH_KEY.wasPressed()){
                PacketByteBuf buf = PacketByteBufs.create();
                if (minecraftClient.player != null && minecraftClient.player.getInventory().getArmorStack(2).isOf(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE)) {
                    ClientPlayNetworking.send(ScarletCrystalChestplateItem.PACK_ID, buf);
                }
            }
        });

        ZeroCraft.LOGGER.info("ZeroCraft is now listening to keyboard input.");
    }
}
