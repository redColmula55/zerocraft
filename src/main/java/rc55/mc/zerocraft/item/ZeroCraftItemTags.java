package rc55.mc.zerocraft.item;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftItemTags {
    //杂项
    public static final TagKey<Item> ZEROCRAFT_DISCS = register("zerocraft_music_discs");//唱片
    //工具
    public static final TagKey<Item> UNENCHANTABLES = register("unenchantables");//不可附魔的工具

    //注册用
    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(ZeroCraft.MODID, id));
    }
}
