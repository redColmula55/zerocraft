package rc55.mc.zerocraft.enchantment;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public class BeheadingEnchantment extends Enchantment {
    protected BeheadingEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    //
    @Override
    public int getMinPower(int level) {
        return 1;
    }
    //最大等级
    @Override
    public int getMaxLevel() {
        return 10;
    }
    //可附魔物品
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack) || stack.isIn(ItemTags.AXES);
    }
    //打人后执行
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {

    }
}
