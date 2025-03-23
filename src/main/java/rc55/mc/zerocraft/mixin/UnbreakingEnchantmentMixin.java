package rc55.mc.zerocraft.mixin;

import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rc55.mc.zerocraft.item.ZeroCraftItemTags;

@Mixin(UnbreakingEnchantment.class)
public abstract class UnbreakingEnchantmentMixin {
    //禁止部分工具附魔耐久
    //耐久override了isAcceptableItem所以需要单独设置
    @Inject(at = @At("RETURN"), method = "isAcceptableItem", cancellable = true)
    public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(cir.getReturnValueZ() && !stack.isIn(ZeroCraftItemTags.UNENCHANTABLES));
    }
}
