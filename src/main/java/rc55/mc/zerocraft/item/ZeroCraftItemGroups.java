package rc55.mc.zerocraft.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;

public class ZeroCraftItemGroups {
    //注册键
    public static final RegistryKey<ItemGroup> ITEMS = register("group_items");
    public static final RegistryKey<ItemGroup> TOOLS = register("group_tools");
    //注册键提供
    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(ZeroCraft.MODID, id));
    }
    //注册+初始化
    public static void regItemGroup(){

        Registry.register(Registries.ITEM_GROUP, ITEMS, ItemGroup.create(ItemGroup.Row.TOP, 7)
                .displayName(Text.translatable("zerocraft.group.items"))
                .icon(() -> new ItemStack(ZeroCraftItems.SCARLET_CRYSTAL))
                .entries(((displayContext, entries) -> {
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_INGOT);
                    entries.add(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK);
                    entries.add(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE);
                    entries.add(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE);
                    entries.add(ZeroCraftItems.DISC_OST_RED_TIDE);
                    entries.add(ZeroCraftItems.DISK_IMAGE_SEEK);
        })).build());

        Registry.register(Registries.ITEM_GROUP, TOOLS, ItemGroup.create(ItemGroup.Row.TOP, 7)
                .displayName(Text.translatable("zerocraft.group.tools"))
                .icon(() -> new ItemStack(ZeroCraftItems.SCARLET_CRYSTAL_PICKAXE))
                .entries(((displayContext, entries) -> {
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_PICKAXE);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_AXE);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_SHOVEL);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_HOE);
                    entries.add(ZeroCraftItems.WRENCH);
                    entries.add(ZeroCraftItems.BLOCK_TRANSPORTER);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_LEGGINGS);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_BOOTS);
                })).build());

        ZeroCraft.LOGGER.info("ZeroCraft item group loaded.");
    }
}
