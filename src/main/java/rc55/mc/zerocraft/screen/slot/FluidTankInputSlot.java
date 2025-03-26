package rc55.mc.zerocraft.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class FluidTankInputSlot extends Slot {
    public FluidTankInputSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }
    @Override
    public boolean canInsert(ItemStack stack) { return stack.getItem() instanceof BucketItem; }
}
