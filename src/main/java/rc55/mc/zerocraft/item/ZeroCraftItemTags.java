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
    //通用标签
    //粗矿类
    public static final TagKey<Item> RAW_TIN = registerConventional("raw_materials/tin");
    public static final TagKey<Item> RAW_ZINC = registerConventional("raw_materials/zinc");
    //锭类
    public static final TagKey<Item> TIN_INGOTS = registerConventional("ingots/tin");
    public static final TagKey<Item> ZINC_INGOTS = registerConventional("ingots/zinc");
    public static final TagKey<Item> BRONZE_INGOTS = registerConventional("ingots/bronze");
    public static final TagKey<Item> BRASS_INGOTS = registerConventional("ingots/brass");
    //粒类
    public static final TagKey<Item> TIN_NUGGETS = registerConventional("nuggets/tin");
    public static final TagKey<Item> ZINC_NUGGETS = registerConventional("nuggets/zinc");
    public static final TagKey<Item> BRONZE_NUGGETS = registerConventional("nuggets/bronze");
    public static final TagKey<Item> BRASS_NUGGETS = registerConventional("nuggets/brass");
    //粉末类
    public static final TagKey<Item> COPPER_DUSTS = registerConventional("dusts/copper");
    public static final TagKey<Item> TIN_DUSTS = registerConventional("dusts/tin");
    public static final TagKey<Item> ZINC_DUSTS = registerConventional("dusts/zinc");
    public static final TagKey<Item> BRONZE_DUSTS = registerConventional("dusts/bronze");
    public static final TagKey<Item> BRASS_DUSTS = registerConventional("dusts/brass");
    //注册用
    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(ZeroCraft.MODID, id));
    }
    private static TagKey<Item> registerConventional(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("c", id));
    }
}
