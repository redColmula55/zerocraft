package rc55.mc.zerocraft.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;
import rc55.mc.zerocraft.fluid.ZeroCraftFluidTags;
import rc55.mc.zerocraft.fluid.ZeroCraftFluids;

import java.util.concurrent.CompletableFuture;

public class FluidTagDataGen extends FabricTagProvider.FluidTagProvider {
    public FluidTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ZeroCraftFluidTags.SCARLET_WATER).add(ZeroCraftFluids.SCARLET_WATER).add(ZeroCraftFluids.FLOWING_SCARLET_WATER);
        getOrCreateTagBuilder(FluidTags.WATER).addTag(ZeroCraftFluidTags.SCARLET_WATER);
    }
}
