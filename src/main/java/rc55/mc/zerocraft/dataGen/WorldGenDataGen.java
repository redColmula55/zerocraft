package rc55.mc.zerocraft.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class WorldGenDataGen extends FabricDynamicRegistryProvider {
    public WorldGenDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        entries.addAll(wrapperLookup.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        entries.addAll(wrapperLookup.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));
    }

    @Override
    public String getName() {
        return "ZeroCraft World Generation";
    }
}
