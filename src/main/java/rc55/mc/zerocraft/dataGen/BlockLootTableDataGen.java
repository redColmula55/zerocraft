package rc55.mc.zerocraft.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.item.ZeroCraftItems;

public class BlockLootTableDataGen extends FabricBlockLootTableProvider {
    public BlockLootTableDataGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK);
        addDrop(ZeroCraftBlocks.TIN_BLOCK);
        addDrop(ZeroCraftBlocks.ZINC_BLOCK);
        addDrop(ZeroCraftBlocks.BRONZE_BLOCK);
        addDrop(ZeroCraftBlocks.BRASS_BLOCK);
        addDrop(ZeroCraftBlocks.RAW_TIN_BLOCK);
        addDrop(ZeroCraftBlocks.RAW_ZINC_BLOCK);

        addDrop(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE, copperLikeOreDrops(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE,ZeroCraftItems.SCARLET_CRYSTAL));
        addDrop(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE, copperLikeOreDrops(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE,ZeroCraftItems.SCARLET_CRYSTAL));

        addDrop(ZeroCraftBlocks.TIN_ORE, oreDrops(ZeroCraftBlocks.TIN_ORE, ZeroCraftItems.RAW_TIN));
        addDrop(ZeroCraftBlocks.DEEPSLATE_TIN_ORE, oreDrops(ZeroCraftBlocks.DEEPSLATE_TIN_ORE, ZeroCraftItems.RAW_TIN));
        addDrop(ZeroCraftBlocks.ZINC_ORE, oreDrops(ZeroCraftBlocks.ZINC_ORE, ZeroCraftItems.RAW_ZINC));
        addDrop(ZeroCraftBlocks.DEEPSLATE_ZINC_ORE, oreDrops(ZeroCraftBlocks.DEEPSLATE_ZINC_ORE, ZeroCraftItems.RAW_ZINC));

        addDrop(ZeroCraftBlocks.CABBAGE_CROP, cropDrops(ZeroCraftBlocks.CABBAGE_CROP, ZeroCraftItems.CABBAGE));

        addDrop(ZeroCraftBlocks.FLUID_TANK, preserveBlockEntityDataDrops(ZeroCraftBlocks.FLUID_TANK,
                ItemEntry.builder(ZeroCraftBlocks.FLUID_TANK).apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
                        .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY).withOperation("Fluid", "BlockEntityTag.Fluid"))));
        addDrop(ZeroCraftBlocks.WOODEN_FLUID_TANK, preserveBlockEntityDataDrops(ZeroCraftBlocks.WOODEN_FLUID_TANK,
                ItemEntry.builder(ZeroCraftBlocks.WOODEN_FLUID_TANK).apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
                        .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY).withOperation("Fluid", "BlockEntityTag.Fluid"))));
        addDrop(ZeroCraftBlocks.VALVE);
        addDrop(ZeroCraftBlocks.FLUID_PIPE);
    }

    private LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 5.0F))).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }

    private LootTable.Builder preserveBlockEntityDataDrops(Block drop, LeafEntry.Builder<?> builder) {
        return LootTable.builder().pool(this.addSurvivesExplosionCondition(drop, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(builder)));
    }

    private LootTable.Builder cropDrops(Block crop, Item seed) {
        if (crop instanceof CropBlock cropBlock) {
            //int max = cropBlock.getAgeProperty().stream().max((i1, i2) -> Math.max(i1.value(), i2.value())).orElseThrow().value();
            LootCondition.Builder condition = BlockStatePropertyLootCondition.builder(crop).properties(StatePredicate.Builder.create().exactMatch(cropBlock.getAgeProperty(), cropBlock.getMaxAge()));
            return this.applyExplosionDecay(crop, LootTable.builder()
                    .pool(LootPool.builder().with(ItemEntry.builder(seed)))
                    .pool(LootPool.builder().conditionally(condition).with(ItemEntry.builder(seed).apply(ApplyBonusLootFunction.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 3)))
                    ));
        }
        return LootTable.builder().pool(LootPool.builder().with(ItemEntry.builder(seed)));
    }
}
