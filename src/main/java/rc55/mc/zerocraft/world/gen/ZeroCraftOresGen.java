package rc55.mc.zerocraft.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.gen.GenerationStep;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftOresGen {
    public static void addOres(){
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), GenerationStep.Feature.UNDERGROUND_ORES,ZeroCraftPlacedFeatures.ORE_SCARLET_CRYSTAL);
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), GenerationStep.Feature.UNDERGROUND_ORES,ZeroCraftPlacedFeatures.ORE_TIN);
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), GenerationStep.Feature.UNDERGROUND_ORES,ZeroCraftPlacedFeatures.ORE_ZINC);
        ZeroCraft.LOGGER.info("Ores added.");
    }
}
