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
import rc55.mc.zerocraft.api.Utils;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;

public class ZeroCraftItemGroups {
    //注册键
    public static final RegistryKey<ItemGroup> ITEMS = getKey("group_items");
    public static final RegistryKey<ItemGroup> TOOLS = getKey("group_tools");
    public static final RegistryKey<ItemGroup> MACHINES = getKey("group_machines");
    public static final RegistryKey<ItemGroup> FOODS = getKey("group_foods");
    public static final RegistryKey<ItemGroup> FURNITURES = getKey("group_furnitures");
    //注册键提供
    private static RegistryKey<ItemGroup> getKey(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(ZeroCraft.MODID, id));
    }
    //注册+初始化
    public static void regItemGroup(){

        Registry.register(Registries.ITEM_GROUP, ITEMS, FabricItemGroup.builder()
                .displayName(Text.translatable(Utils.getItemGroupTransKey(ITEMS)))
                .icon(() -> new ItemStack(ZeroCraftItems.SCARLET_CRYSTAL))
                .entries(((displayContext, entries) -> {
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_INGOT);
                    entries.add(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK);
                    entries.add(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE);
                    entries.add(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE);
                    entries.add(ZeroCraftItems.COPPER_DUST);
                    entries.add(ZeroCraftItems.TIN_INGOT);
                    entries.add(ZeroCraftBlocks.TIN_BLOCK);
                    entries.add(ZeroCraftItems.TIN_DUST);
                    entries.add(ZeroCraftItems.TIN_NUGGET);
                    entries.add(ZeroCraftBlocks.TIN_ORE);
                    entries.add(ZeroCraftBlocks.DEEPSLATE_TIN_ORE);
                    entries.add(ZeroCraftItems.RAW_TIN);
                    entries.add(ZeroCraftBlocks.RAW_TIN_BLOCK);
                    entries.add(ZeroCraftItems.ZINC_INGOT);
                    entries.add(ZeroCraftBlocks.ZINC_BLOCK);
                    entries.add(ZeroCraftItems.ZINC_DUST);
                    entries.add(ZeroCraftItems.ZINC_NUGGET);
                    entries.add(ZeroCraftBlocks.ZINC_ORE);
                    entries.add(ZeroCraftBlocks.DEEPSLATE_ZINC_ORE);
                    entries.add(ZeroCraftItems.RAW_ZINC);
                    entries.add(ZeroCraftBlocks.RAW_ZINC_BLOCK);
                    entries.add(ZeroCraftItems.BRONZE_INGOT);
                    entries.add(ZeroCraftBlocks.BRONZE_BLOCK);
                    entries.add(ZeroCraftItems.BRONZE_DUST);
                    entries.add(ZeroCraftItems.BRONZE_NUGGET);
                    entries.add(ZeroCraftItems.BRASS_INGOT);
                    entries.add(ZeroCraftBlocks.BRASS_BLOCK);
                    entries.add(ZeroCraftItems.BRASS_DUST);
                    entries.add(ZeroCraftItems.BRASS_NUGGET);
                    entries.add(ZeroCraftItems.DISC_OST_RED_TIDE);
                    entries.add(ZeroCraftItems.DISK_IMAGE_SEEK);
                    entries.add(ZeroCraftItems.SCARLET_WATER_BUCKET);
                    entries.add(ZeroCraftItems.STEAM_BUCKET);
        })).build());

        Registry.register(Registries.ITEM_GROUP, TOOLS, FabricItemGroup.builder()
                .displayName(Text.translatable(Utils.getItemGroupTransKey(TOOLS)))
                .icon(() -> new ItemStack(ZeroCraftItems.SCARLET_CRYSTAL_PICKAXE))
                .entries(((displayContext, entries) -> {
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_PICKAXE);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_AXE);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_SHOVEL);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_HOE);
                    entries.add(ZeroCraftItems.WRENCH);
                    entries.add(ZeroCraftItems.BLOCK_TRANSPORTER);
                    entries.add(ZeroCraftItems.ORE_FINDER);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_LEGGINGS);
                    entries.add(ZeroCraftItems.SCARLET_CRYSTAL_BOOTS);
                })).build());

        Registry.register(Registries.ITEM_GROUP, MACHINES, FabricItemGroup.builder()
                .displayName(Text.translatable(Utils.getItemGroupTransKey(MACHINES)))
                .icon(() -> new ItemStack(ZeroCraftBlocks.FLUID_TANK))
                .entries(((displayContext, entries) -> {
                    entries.add(ZeroCraftBlocks.FLUID_TANK);
                    entries.add(ZeroCraftBlocks.WOODEN_FLUID_TANK);
                    entries.add(ZeroCraftBlocks.VALVE);
                    entries.add(ZeroCraftBlocks.FLUID_PIPE);
                    entries.add(ZeroCraftBlocks.BOILER);
                })).build());

        Registry.register(Registries.ITEM_GROUP, FOODS, FabricItemGroup.builder()
                .displayName(Text.translatable(Utils.getItemGroupTransKey(FOODS)))
                .icon(() -> new ItemStack(ZeroCraftItems.WRENCH))
                .entries(((displayContext, entries) -> {
                })).build());

        Registry.register(Registries.ITEM_GROUP, FURNITURES, FabricItemGroup.builder()
                .displayName(Text.translatable(Utils.getItemGroupTransKey(FURNITURES)))
                .icon(() -> new ItemStack(ZeroCraftItems.WRENCH))
                .entries(((displayContext, entries) -> {
                })).build());

        ZeroCraft.LOGGER.info("ZeroCraft item group loaded.");
    }
}