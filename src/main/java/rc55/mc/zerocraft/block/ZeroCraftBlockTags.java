package rc55.mc.zerocraft.block;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftBlockTags {
    //矿石
    public static final TagKey<Block> SCARLET_CRYSTAL_ORES = register("scarlet_crystal_ores");
    //其他
    public static final TagKey<Block> WRENCH_ADJUSTABLE = register("wrench_adjustable");
    //通用标签
    //矿石类
    public static final TagKey<Block> TIN_ORES = registerConventional("ores/tin");
    public static final TagKey<Block> ZINC_ORES = registerConventional("ores/zinc");
    //注册用
    private static TagKey<Block> register(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(ZeroCraft.MODID, id));
    }
    private static TagKey<Block> registerConventional(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier("c", id));
    }
}
