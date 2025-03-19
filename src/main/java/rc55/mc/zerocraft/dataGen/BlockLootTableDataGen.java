package rc55.mc.zerocraft.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.item.ZeroCraftItems;

public class BlockLootTableDataGen extends FabricBlockLootTableProvider {
    public BlockLootTableDataGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK);
        addDrop(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE, copperLikeOreDrops(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE,ZeroCraftItems.SCARLET_CRYSTAL));
        addDrop(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE, copperLikeOreDrops(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE,ZeroCraftItems.SCARLET_CRYSTAL));
    }
    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return dropsWithSilkTouch(drop, (LootPoolEntry.Builder)this.applyExplosionDecay(drop, ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 5.0F))).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
}
