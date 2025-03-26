package rc55.mc.zerocraft.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rc55.mc.zerocraft.item.ZeroCraftItemTags;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    //禁止部分工具附魔
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentTarget;isAcceptableItem(Lnet/minecraft/item/Item;)Z"), method = "isAcceptableItem")
    public boolean isAcceptableItem(EnchantmentTarget instance, Item item){
        return !item.getDefaultStack().isIn(ZeroCraftItemTags.UNENCHANTABLES) && instance.isAcceptableItem(item);
    }
}
