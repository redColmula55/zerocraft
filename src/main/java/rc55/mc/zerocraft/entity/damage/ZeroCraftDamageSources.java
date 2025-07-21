package rc55.mc.zerocraft.entity.damage;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class ZeroCraftDamageSources {
    public static DamageSource get(World world, RegistryKey<DamageType> key){
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }

    public static DamageSource scarletInfected(World world){
        return get(world, ZeroCraftDamageTypes.SCARLET_INFESTED);
    }
}
