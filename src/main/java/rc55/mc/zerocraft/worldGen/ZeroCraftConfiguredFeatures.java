package rc55.mc.zerocraft.worldGen;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;

import java.util.List;

public class ZeroCraftConfiguredFeatures {
    public ZeroCraftConfiguredFeatures(){}

    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_SCARLET_CRYSTAL = getKey("ore_scarlet_crystal");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        RuleTest overworld = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest overworld_deep = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest nether = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest end = new BlockMatchRuleTest(Blocks.END_STONE);
        List<OreFeatureConfig.Target> scarletOres = List.of(OreFeatureConfig.createTarget(overworld, ZeroCraftBlocks.SCARLET_CRYSTAL_ORE.getDefaultState()), OreFeatureConfig.createTarget(overworld_deep, ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE.getDefaultState()));

        register(featureRegisterable, ORE_SCARLET_CRYSTAL, Feature.ORE, new OreFeatureConfig(scarletOres,10));
    }
    //注册键
    private static RegistryKey<ConfiguredFeature<?,?>> getKey(String id){
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(ZeroCraft.MODID, id));
    }
    //注册用
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> featureRegisterable, RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC config){
        featureRegisterable.register(key, new ConfiguredFeature<FC, F>(feature, config));
    }
}
