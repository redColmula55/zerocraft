package rc55.mc.zerocraft.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftStatusEffects {

    public static final StatusEffect SCARLET_INFESTED = register("scarlet_infested", new ScarletInfestedStatusEffect());

    //注册用
    private static StatusEffect register(String id, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(ZeroCraft.MODID, id), entry);
    }
    //初始化注册
    public static void regStatusEffect() {
        ZeroCraft.LOGGER.info("ZeroCraft Status effects registered.");
    }
}
