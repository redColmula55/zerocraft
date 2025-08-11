package rc55.mc.zerocraft.dataGen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import rc55.mc.zerocraft.block.ZeroCraftBlockTags;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;

import java.util.concurrent.CompletableFuture;

public class BlockTagDataGen extends FabricTagProvider.BlockTagProvider {

    public BlockTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        getOrCreateTagBuilder(ZeroCraftBlockTags.WRENCH_ADJUSTABLE)
                .add(Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.OBSERVER, Blocks.DISPENSER, Blocks.DROPPER)
                .add(Blocks.REPEATER, Blocks.COMPARATOR, Blocks.REDSTONE_TORCH, Blocks.REDSTONE_WALL_TORCH)
                .add(Blocks.TRAPPED_CHEST, Blocks.HOPPER).forceAddTag(BlockTags.SHULKER_BOXES)
                .forceAddTag(BlockTags.RAILS)
                .add(Blocks.SCULK_SENSOR, Blocks.CALIBRATED_SCULK_SENSOR)
                .forceAddTag(BlockTags.TRAPDOORS).forceAddTag(BlockTags.DOORS).forceAddTag(BlockTags.FENCE_GATES)
                .add(ZeroCraftBlocks.FLUID_PIPE, ZeroCraftBlocks.VALVE);

        getOrCreateTagBuilder(ZeroCraftBlockTags.SCARLET_CRYSTAL_ORES).add(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE).add(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE);
        getOrCreateTagBuilder(ZeroCraftBlockTags.TIN_ORES).add(ZeroCraftBlocks.TIN_ORE).add(ZeroCraftBlocks.DEEPSLATE_TIN_ORE);
        getOrCreateTagBuilder(ZeroCraftBlockTags.ZINC_ORES).add(ZeroCraftBlocks.ZINC_ORE).add(ZeroCraftBlocks.DEEPSLATE_ZINC_ORE);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK).addTag(ZeroCraftBlockTags.SCARLET_CRYSTAL_ORES)
                .addTag(ZeroCraftBlockTags.TIN_ORES).addTag(ZeroCraftBlockTags.ZINC_ORES)
                .add(ZeroCraftBlocks.RAW_TIN_BLOCK, ZeroCraftBlocks.RAW_ZINC_BLOCK)
                .add(ZeroCraftBlocks.TIN_BLOCK, ZeroCraftBlocks.ZINC_BLOCK, ZeroCraftBlocks.BRONZE_BLOCK, ZeroCraftBlocks.BRASS_BLOCK)
                .add(ZeroCraftBlocks.FLUID_TANK);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .addTag(ZeroCraftBlockTags.TIN_ORES).addTag(ZeroCraftBlockTags.ZINC_ORES)
                .add(ZeroCraftBlocks.RAW_TIN_BLOCK, ZeroCraftBlocks.RAW_ZINC_BLOCK);
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ZeroCraftBlocks.TIN_BLOCK, ZeroCraftBlocks.ZINC_BLOCK, ZeroCraftBlocks.BRONZE_BLOCK, ZeroCraftBlocks.BRASS_BLOCK);
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK).addTag(ZeroCraftBlockTags.SCARLET_CRYSTAL_ORES);

        getOrCreateTagBuilder(ConventionalBlockTags.ORES)
                .addTag(ZeroCraftBlockTags.SCARLET_CRYSTAL_ORES).addTag(ZeroCraftBlockTags.TIN_ORES).addTag(ZeroCraftBlockTags.ZINC_ORES);
    }
}
