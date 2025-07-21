package rc55.mc.zerocraft.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftFluidTags {

    public static final TagKey<Fluid> SCARLET_WATER = register("scarlet_water");
    public static final TagKey<Fluid> SCARLET_WATER_POLLUTIABLE = register("scarlet_water_pollutiable");//可被赤潮污染的水

    //注册
    private static TagKey<Fluid> register(String id) {
        return TagKey.of(RegistryKeys.FLUID, new Identifier(ZeroCraft.MODID, id));
    }
}
