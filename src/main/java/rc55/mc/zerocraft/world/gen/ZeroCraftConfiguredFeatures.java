package rc55.mc.zerocraft.world.gen;

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
    //注册键
    //矿石
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_SCARLET_CRYSTAL = getKey("ore_scarlet_crystal");
    public static final RegistryKey<ConfiguredFeature<?,?>> ORE_TIN = getKey("ore_tin");
    public static final RegistryKey<ConfiguredFeature<?,?>> ORE_ZINC = getKey("ore_zinc");
    //数据生成
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        //替换方块规则
        RuleTest overworld = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest overworld_deep = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest nether = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest end = new BlockMatchRuleTest(Blocks.END_STONE);
        //生成规则对应矿石方块
        List<OreFeatureConfig.Target> scarletOres = List.of(OreFeatureConfig.createTarget(overworld, ZeroCraftBlocks.SCARLET_CRYSTAL_ORE.getDefaultState()), OreFeatureConfig.createTarget(overworld_deep, ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> tinOres = List.of(OreFeatureConfig.createTarget(overworld, ZeroCraftBlocks.TIN_ORE.getDefaultState()), OreFeatureConfig.createTarget(overworld_deep, ZeroCraftBlocks.DEEPSLATE_TIN_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> zincOres = List.of(OreFeatureConfig.createTarget(overworld, ZeroCraftBlocks.ZINC_ORE.getDefaultState()), OreFeatureConfig.createTarget(overworld_deep, ZeroCraftBlocks.DEEPSLATE_ZINC_ORE.getDefaultState()));
        //注册
        register(featureRegisterable, ORE_SCARLET_CRYSTAL, Feature.ORE, new OreFeatureConfig(scarletOres,10));
        register(featureRegisterable, ORE_TIN, Feature.ORE, new OreFeatureConfig(tinOres, 12));
        register(featureRegisterable, ORE_ZINC, Feature.ORE, new OreFeatureConfig(zincOres, 12));
    }
    //注册键提供
    private static RegistryKey<ConfiguredFeature<?,?>> getKey(String id){
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(ZeroCraft.MODID, id));
    }
    //注册用
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> featureRegisterable, RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC config){
        featureRegisterable.register(key, new ConfiguredFeature<>(feature, config));
    }
}
