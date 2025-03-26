package rc55.mc.zerocraft.screen.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class ResultSlot extends Slot {
    private final Runnable onTake;
    public ResultSlot(Inventory inventory, int index, int x, int y) {
        this(inventory, index, x, y, () -> {});
    }
    public ResultSlot(Inventory inventory, int index, int x, int y, Runnable onTake) {
        super(inventory, index, x, y);
        this.onTake = onTake;
    }
    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }
    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        onTake.run();
    }
}
