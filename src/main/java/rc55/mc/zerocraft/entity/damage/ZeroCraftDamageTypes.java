package rc55.mc.zerocraft.entity.damage;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftDamageTypes {

    public static final RegistryKey<DamageType> SCARLET_INFESTED = getKey("scarlet_infested");

    //数据生成
    public static void bootstrap(Registerable<DamageType> registerable) {
        registerable.register(SCARLET_INFESTED, new DamageType("scarletInfested", 0.0f));
    }
    //注册键提供
    private static RegistryKey<DamageType> getKey(String id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(ZeroCraft.MODID, id));
    }
}
