package rc55.mc.zerocraft.worldGen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;
import rc55.mc.zerocraft.ZeroCraft;

import java.util.List;

public class ZeroCraftPlacedFeatures {
    public ZeroCraftPlacedFeatures(){}

    public static final RegistryKey<PlacedFeature> ORE_SCARLET_CRYSTAL = getKey("ore_scarlet_crystal");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        RegistryEntry<ConfiguredFeature<?, ?>> oreScarletRegEntry = registryEntryLookup.getOrThrow(ZeroCraftConfiguredFeatures.ORE_SCARLET_CRYSTAL);

        register(featureRegisterable, ORE_SCARLET_CRYSTAL, oreScarletRegEntry, modifiersWithCount(12, HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(31))));
    }
    //注册键
    public static RegistryKey<PlacedFeature> getKey(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(ZeroCraft.MODID, id));
    }
    //注册用
    private static void register(Registerable<PlacedFeature> featureRegisterable, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        featureRegisterable.register(key, new PlacedFeature(feature, List.copyOf(modifiers)));
    }
    //矿石生成方式
    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }
    //固定数量
    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }
    //稀有度
    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
