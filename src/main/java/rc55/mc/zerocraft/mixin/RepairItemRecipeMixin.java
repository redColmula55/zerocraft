package rc55.mc.zerocraft.mixin;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rc55.mc.zerocraft.item.tool.CraftableTool;

@Mixin(RepairItemRecipe.class)
public abstract class RepairItemRecipeMixin {
    //禁用在工作台中可消耗自身耐久合成物品的合成修复（要修用砂轮
    @Inject(at = @At("RETURN"), method = "matches(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/world/World;)Z", cancellable = true)
    public void matches(RecipeInputInventory recipeInputInventory, World world, CallbackInfoReturnable<Boolean> cir) {
        for (ItemStack stack : recipeInputInventory.getInputStacks()) {
            if (stack.getRecipeRemainder() != null && stack.getItem() instanceof CraftableTool) {
                cir.setReturnValue(false);
            }
        }
    }
}
