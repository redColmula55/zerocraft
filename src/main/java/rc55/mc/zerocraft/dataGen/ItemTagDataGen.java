package rc55.mc.zerocraft.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import rc55.mc.zerocraft.item.ZeroCraftItemTags;
import rc55.mc.zerocraft.item.ZeroCraftItems;

import java.util.concurrent.CompletableFuture;

public class ItemTagDataGen extends FabricTagProvider.ItemTagProvider {

    public ItemTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).addTag(ZeroCraftItemTags.ZEROCRAFT_DISCS);

        getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(ZeroCraftItems.DISC_OST_RED_TIDE)
                .add(ZeroCraftItems.DISK_IMAGE_SEEK);

        getOrCreateTagBuilder(ZeroCraftItemTags.ZEROCRAFT_DISCS)
                .add(ZeroCraftItems.DISC_OST_RED_TIDE)
                .add(ZeroCraftItems.DISK_IMAGE_SEEK);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR).add(ZeroCraftItems.SCARLET_CRYSTAL_BOOTS);
    }
}
