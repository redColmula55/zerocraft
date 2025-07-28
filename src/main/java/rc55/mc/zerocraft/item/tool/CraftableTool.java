package rc55.mc.zerocraft.item.tool;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.item.ItemStack;

/**
 * 用于可在工作台中合成物品的工具
 */
public interface CraftableTool extends FabricItem {
    @Override
    default ItemStack getRecipeRemainder(ItemStack stack) {
        ItemStack damaged = stack.copy();
        damaged.setDamage(damaged.getDamage() + 1);
        return stack.getDamage() < stack.getMaxDamage() - 1 ? damaged : ItemStack.EMPTY;
    }
}
