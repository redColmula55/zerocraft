package rc55.mc.zerocraft.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Function;

public class InputSlot extends Slot {

    private final Function<ItemStack, Boolean> insertCondition;

    public InputSlot(Inventory inventory, int index, int x, int y) {
        this(inventory, index, x, y, (stack -> true));
    }
    public InputSlot(Inventory inventory, int index, int x, int y, Function<ItemStack, Boolean> insertCondition) {
        super(inventory, index, x, y);
        this.insertCondition = insertCondition;
    }
    @Override
    public boolean canInsert(ItemStack stack) {
        return this.insertCondition.apply(stack);
    }
}
