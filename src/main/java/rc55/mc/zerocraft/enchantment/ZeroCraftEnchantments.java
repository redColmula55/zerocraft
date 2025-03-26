package rc55.mc.zerocraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftEnchantments {

    public static final Enchantment VAMPIRE = register("vampire", new VampireEnchantment());
    public static final Enchantment BEHEADING = register("beheading", new BeheadingEnchantment());

    //注册用
    private static Enchantment register(String id, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(ZeroCraft.MODID, id), enchantment);
    }
    //初始化注册
    public static void regEnchantment(){
        ZeroCraft.LOGGER.info("ZeroCraft Enchantments registered.");
    }
}
