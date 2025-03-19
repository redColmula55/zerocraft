package rc55.mc.zerocraft.block;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftBlockTags {

    public static final TagKey<Block> WRENCH_ADJUSTABLE = register("wrench_adjustable");

    private static TagKey<Block> register(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(ZeroCraft.MODID, id));
    }
}
