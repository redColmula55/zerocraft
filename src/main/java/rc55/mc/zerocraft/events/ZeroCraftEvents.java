package rc55.mc.zerocraft.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import rc55.mc.zerocraft.enchantment.BeheadingEnchantment;

public class ZeroCraftEvents {
    //初始化注册
    public static void regEvents(){
        //杀死实体
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(BeheadingEnchantment::postKill);//斩首附魔掉落头颅
    }
}
