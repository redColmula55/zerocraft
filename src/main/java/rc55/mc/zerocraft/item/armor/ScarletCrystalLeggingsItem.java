package rc55.mc.zerocraft.item.armor;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.api.inventory.ItemInventory;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;

import java.util.List;

public class ScarletCrystalLeggingsItem extends ArmorItem {
    public ScarletCrystalLeggingsItem() {
        super(ZeroCraftArmorMaterials.SCARLET_CRYSTAL, Type.LEGGINGS, new Settings().rarity(Rarity.RARE).fireproof());
    }
    //发包用
    public static final Identifier PACK_ID = new Identifier(ZeroCraft.MODID, "key_inventory_leggings");

    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(this.getTranslationKey() + ".hint", ZeroCraftKeyBinds.LEGGINGS_INVENTORY_OPEN_KEY.getBoundKeyLocalizedText()).formatted(Formatting.GRAY));
    }

    //每刻执行
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isClient){
            if (entity instanceof PlayerEntity) {//仅玩家触发效果
                ServerPlayNetworking.registerGlobalReceiver(PACK_ID, ((server, serverPlayer, handler, buf, responseSender) -> {
                    server.execute(() -> {
                        //判断是否穿上
                        ItemStack leggings = serverPlayer.getInventory().getArmorStack(1);
                        if (leggings.isOf(this)) {
                            Inventory inventory = new ItemInventory(27, leggings);//护腿物品栏
                            //屏幕
                            NamedScreenHandlerFactory factory = new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player) ->
                                    GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, inventory), leggings.getName());
                            serverPlayer.openHandledScreen(factory);
                        }
                    });
                }));
            }
        }
    }
}
