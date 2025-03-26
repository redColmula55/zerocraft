package rc55.mc.zerocraft.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftFluidTags {

    public static final TagKey<Fluid> SCARLET_WATER = register("scarlet_water");
    //注册
    private static TagKey<Fluid> register(String id) {
        return TagKey.of(RegistryKeys.FLUID, new Identifier(ZeroCraft.MODID, id));
    }
}
