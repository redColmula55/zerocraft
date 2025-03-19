package rc55.mc.zerocraft.worldGen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.gen.GenerationStep;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftOresGen {
    public static void addOres(){
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), GenerationStep.Feature.UNDERGROUND_ORES,ZeroCraftPlacedFeatures.ORE_SCARLET_CRYSTAL);
        ZeroCraft.LOGGER.info("Ores added.");
    }
}
