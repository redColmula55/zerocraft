package rc55.mc.zerocraft.world;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftGameRules {
    //游戏规则
    //赤潮污染原版水的几率（百分比），0为禁用，100为必定感染
    //注意此项设置过大会导致在试图感染大面积水域时崩溃
    public static final GameRules.Key<GameRules.IntRule> SCARLET_WATER_INFEST_CHANCE = GameRuleRegistry.register("scarletTideSpreadRate", GameRules.Category.UPDATES, GameRuleFactory.createIntRule(20, 0, 100));
    //赤潮污染水是否侵蚀无全套赤晶装备的生物
    public static final GameRules.Key<GameRules.BooleanRule> SCARLET_WATER_DAMAGE = GameRuleRegistry.register("doScarletWaterDamage", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
    //流体管道是否在其接受过高温度的流体时损坏掉落
    public static final GameRules.Key<GameRules.BooleanRule> FLUID_PIPE_MELTS = GameRuleRegistry.register("fluidPipeMelts", GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(true));
    //初始化注册
    public static void regGameRule(){
        ZeroCraft.LOGGER.info("ZeroCraft game rules registered.");
    }
}
