package rc55.mc.zerocraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;

public class VampireEnchantment extends Enchantment {
    protected VampireEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }
    //最大等级
    @Override
    public int getMaxLevel() {
        return 5;
    }
    //可附魔物品
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack) || stack.isIn(ItemTags.AXES);
    }
    //伤害
    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        return level;
    }
    //打人后执行
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity) {
            user.heal(level);
        }
    }
}
