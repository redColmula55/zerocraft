package rc55.mc.zerocraft.item;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftItemTags {

    public static final TagKey<Item> ZEROCRAFT_DISCS = register("zerocraft_music_discs");

    public static final TagKey<Item> ELYTRA_LIKE_ITEMS = register("elytra_like_items");

    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(ZeroCraft.MODID, id));
    }
}
