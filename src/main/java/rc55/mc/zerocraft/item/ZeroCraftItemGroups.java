package rc55.mc.zerocraft.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
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
    public static final RegistryKey<ItemGroup> ITEMS = getKey("group_items");
    public static final RegistryKey<ItemGroup> TOOLS = getKey("group_tools");
    public static final RegistryKey<ItemGroup> MACHINES = getKey("group_machines");
    //注册键提供
    private static RegistryKey<ItemGroup> getKey(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(ZeroCraft.MODID, id));
    }
    //注册+初始化
    public static void regItemGroup(){

        Registry.register(Registries.ITEM_GROUP, ITEMS, FabricItemGroup.builder()
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

        Registry.register(Registries.ITEM_GROUP, TOOLS, FabricItemGroup.builder()
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

        Registry.register(Registries.ITEM_GROUP, MACHINES, FabricItemGroup.builder()
                .displayName(Text.translatable("zerocraft.group.machines"))
                .icon(() -> new ItemStack(ZeroCraftBlocks.FLUID_TANK))
                .entries(((displayContext, entries) -> {
                    entries.add(ZeroCraftBlocks.FLUID_TANK);
                })).build());

        ZeroCraft.LOGGER.info("ZeroCraft item group loaded.");
    }
}
//ItemGroup.create(ItemGroup.Row.TOP, 7)