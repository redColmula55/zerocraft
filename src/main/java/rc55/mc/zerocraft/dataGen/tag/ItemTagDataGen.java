package rc55.mc.zerocraft.dataGen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.item.ZeroCraftItemTags;
import rc55.mc.zerocraft.item.ZeroCraftItems;

import java.util.concurrent.CompletableFuture;

public class ItemTagDataGen extends FabricTagProvider.ItemTagProvider {

    public ItemTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).addTag(ZeroCraftItemTags.ZEROCRAFT_DISCS);

        getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(ZeroCraftItems.DISC_OST_RED_TIDE)
                .add(ZeroCraftItems.DISK_IMAGE_SEEK);

        getOrCreateTagBuilder(ZeroCraftItemTags.ZEROCRAFT_DISCS)
                .add(ZeroCraftItems.DISC_OST_RED_TIDE)
                .add(ZeroCraftItems.DISK_IMAGE_SEEK);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET)
                .add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE)
                .add(ZeroCraftItems.SCARLET_CRYSTAL_LEGGINGS)
                .add(ZeroCraftItems.SCARLET_CRYSTAL_BOOTS);

        getOrCreateTagBuilder(ItemTags.SWORDS).add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD);
        getOrCreateTagBuilder(ItemTags.PICKAXES).add(ZeroCraftItems.SCARLET_CRYSTAL_PICKAXE);
        getOrCreateTagBuilder(ItemTags.AXES).add(ZeroCraftItems.SCARLET_CRYSTAL_AXE);
        getOrCreateTagBuilder(ItemTags.SHOVELS).add(ZeroCraftItems.SCARLET_CRYSTAL_SHOVEL);
        getOrCreateTagBuilder(ItemTags.HOES).add(ZeroCraftItems.SCARLET_CRYSTAL_HOE);

        getOrCreateTagBuilder(ZeroCraftItemTags.UNENCHANTABLES)
                .add(ZeroCraftItems.WRENCH, ZeroCraftItems.BLOCK_TRANSPORTER, ZeroCraftItems.ORE_FINDER);

        addToTag(ConventionalItemTags.ORES, ZeroCraftBlocks.SCARLET_CRYSTAL_ORE, ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE,
                ZeroCraftBlocks.TIN_ORE, ZeroCraftBlocks.DEEPSLATE_TIN_ORE,
                ZeroCraftBlocks.ZINC_ORE, ZeroCraftBlocks.DEEPSLATE_ZINC_ORE);

        getOrCreateTagBuilder(ConventionalItemTags.RAW_ORES).addTag(ZeroCraftItemTags.RAW_TIN).addTag(ZeroCraftItemTags.RAW_ZINC);

        getOrCreateTagBuilder(ZeroCraftItemTags.RAW_TIN).add(ZeroCraftItems.RAW_TIN);
        getOrCreateTagBuilder(ZeroCraftItemTags.RAW_ZINC).add(ZeroCraftItems.RAW_ZINC);
        getOrCreateTagBuilder(ZeroCraftItemTags.TIN_INGOTS).add(ZeroCraftItems.TIN_INGOT);
        getOrCreateTagBuilder(ZeroCraftItemTags.ZINC_INGOTS).add(ZeroCraftItems.ZINC_INGOT);
        getOrCreateTagBuilder(ZeroCraftItemTags.BRONZE_INGOTS).add(ZeroCraftItems.BRONZE_INGOT);
        getOrCreateTagBuilder(ZeroCraftItemTags.BRASS_INGOTS).add(ZeroCraftItems.BRASS_INGOT);
        getOrCreateTagBuilder(ZeroCraftItemTags.TIN_NUGGETS).add(ZeroCraftItems.TIN_NUGGET);
        getOrCreateTagBuilder(ZeroCraftItemTags.ZINC_NUGGETS).add(ZeroCraftItems.ZINC_NUGGET);
        getOrCreateTagBuilder(ZeroCraftItemTags.BRONZE_NUGGETS).add(ZeroCraftItems.BRONZE_NUGGET);
        getOrCreateTagBuilder(ZeroCraftItemTags.BRASS_NUGGETS).add(ZeroCraftItems.BRASS_NUGGET);
        getOrCreateTagBuilder(ZeroCraftItemTags.COPPER_DUSTS).add(ZeroCraftItems.COPPER_DUST);
        getOrCreateTagBuilder(ZeroCraftItemTags.TIN_DUSTS).add(ZeroCraftItems.TIN_DUST);
        getOrCreateTagBuilder(ZeroCraftItemTags.ZINC_DUSTS).add(ZeroCraftItems.ZINC_DUST);
        getOrCreateTagBuilder(ZeroCraftItemTags.BRONZE_DUSTS).add(ZeroCraftItems.BRONZE_DUST);
        getOrCreateTagBuilder(ZeroCraftItemTags.BRASS_DUSTS).add(ZeroCraftItems.BRASS_DUST);

        getOrCreateTagBuilder(ConventionalItemTags.INGOTS).add(ZeroCraftItems.SCARLET_CRYSTAL_INGOT)
                .addTag(ZeroCraftItemTags.TIN_INGOTS).addTag(ZeroCraftItemTags.ZINC_INGOTS).addTag(ZeroCraftItemTags.BRONZE_INGOTS).addTag(ZeroCraftItemTags.BRASS_INGOTS);
        getOrCreateTagBuilder(ConventionalItemTags.NUGGETS)
                .addTag(ZeroCraftItemTags.TIN_NUGGETS).addTag(ZeroCraftItemTags.ZINC_NUGGETS).addTag(ZeroCraftItemTags.BRONZE_NUGGETS).addTag(ZeroCraftItemTags.BRASS_NUGGETS);
        getOrCreateTagBuilder(ConventionalItemTags.DUSTS).addTag(ZeroCraftItemTags.COPPER_DUSTS)
                .addTag(ZeroCraftItemTags.TIN_DUSTS).addTag(ZeroCraftItemTags.ZINC_DUSTS).addTag(ZeroCraftItemTags.BRONZE_DUSTS).addTag(ZeroCraftItemTags.BRASS_DUSTS);
    }

    private void addToTag(TagKey<Item> tag, ItemConvertible... items) {
        FabricTagBuilder builder = getOrCreateTagBuilder(tag);
        for (ItemConvertible item : items) {
            builder.add(item.asItem());
        }
    }
}
