package rc55.mc.zerocraft.dataGen.tag;

import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.vanilla.VanillaDamageTypeTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import rc55.mc.zerocraft.entity.damage.ZeroCraftDamageTypes;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagDataGen extends VanillaDamageTypeTagProvider {
    public DamageTypeTagDataGen(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> maxChainedNeighborUpdates) {
        super(output, maxChainedNeighborUpdates);
    }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).add(ZeroCraftDamageTypes.SCARLET_INFESTED);
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS).add(ZeroCraftDamageTypes.SCARLET_INFESTED);
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(ZeroCraftDamageTypes.SCARLET_INFESTED);
    }
}
