package rc55.mc.zerocraft.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    //剑右键格挡
    @Inject(at = @At("HEAD"), method = "damageShield(F)V", cancellable = true)
    public void damageShield(float amount, CallbackInfo ci){
        PlayerEntity player = ((PlayerEntity)(Object)this);
        ItemStack stack = player.getActiveItem();
        //是否可以格挡
        if (stack.isOf(Items.SHIELD) || stack.isIn(ItemTags.SWORDS)) {
            //格挡后放手
            if (!player.getWorld().isClient) {
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            }
            //消耗耐久
            if (amount >= 3.0F) {
                int i = 1 + MathHelper.floor(amount);
                Hand hand = player.getActiveHand();
                stack.damage(i, player, player1 -> player1.sendToolBreakStatus(hand));
                //损坏
                if (stack.isEmpty()) {
                    if (hand == Hand.MAIN_HAND) {
                        player.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        player.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    player.clearActiveItem();
                    //损坏音效
                    player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.getWorld().random.nextFloat() * 0.4F);
                }
            }
        }
        ci.cancel();
    }
}
