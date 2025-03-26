package rc55.mc.zerocraft.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
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
                .add(Blocks.PISTON).add(Blocks.STICKY_PISTON)
                .add(Blocks.OBSERVER)
                .add(Blocks.REDSTONE_TORCH).add(Blocks.REDSTONE_WALL_TORCH)
                .add(Blocks.REPEATER).add(Blocks.COMPARATOR)
                .add(Blocks.DAYLIGHT_DETECTOR)
                .add(Blocks.DISPENSER).add(Blocks.DROPPER)
                .add(Blocks.NOTE_BLOCK)
                .add(Blocks.TRIPWIRE_HOOK).add(Blocks.TRIPWIRE)
                .add(Blocks.TRAPPED_CHEST).forceAddTag(BlockTags.SHULKER_BOXES).add(Blocks.HOPPER)
                .forceAddTag(BlockTags.RAILS)
                .add(Blocks.SCULK_SENSOR).add(Blocks.CALIBRATED_SCULK_SENSOR).add(Blocks.SCULK_SHRIEKER)
                .add(Blocks.REDSTONE_WIRE)
                .forceAddTag(BlockTags.TRAPDOORS).forceAddTag(BlockTags.DOORS).forceAddTag(BlockTags.FENCE_GATES);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK).add(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE).add(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE)
                .add(ZeroCraftBlocks.FLUID_TANK);
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK).add(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE).add(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE);
    }
}
