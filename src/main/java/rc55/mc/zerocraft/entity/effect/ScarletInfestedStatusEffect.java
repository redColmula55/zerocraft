package rc55.mc.zerocraft.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import rc55.mc.zerocraft.entity.damage.ZeroCraftDamageSources;

public class ScarletInfestedStatusEffect extends StatusEffect {
    protected ScarletInfestedStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xff0000);
    }
    //每秒一次
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 10 == 1;
    }
    //效果
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(ZeroCraftDamageSources.scarletInfected(entity.getWorld()), 1.0f + (amplifier * 0.5f) );
    }
}
