package rc55.mc.zerocraft.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import rc55.mc.zerocraft.screen.slot.InputSlot;

import java.util.function.Function;

public class Generic1x1ContainerScreenHandler extends ScreenHandler {

    private static final int CONTAINER_SIZE = 1;
    private static final int INVENTORY_START = 9;
    private static final int INVENTORY_END = 35;
    private static final int HOTBAR_START = 0;
    private static final int HOTBAR_END = 8;

    private final Inventory inventory;
    private final Function<PlayerEntity, Boolean> openCondition;

    public Generic1x1ContainerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, Function<PlayerEntity, Boolean> openCondition, Function<ItemStack, Boolean> insertCondition) {
        super(ZeroCraftScreenHandlerType.GENERIC_1x1_CONTAINER, syncId);
        inventory.onOpen(playerInventory.player);
        this.openCondition = openCondition;
        this.inventory = inventory;
        this.addPlayerInventorySlots(playerInventory);
        this.addSlot(new InputSlot(inventory, 0, 80, 36, insertCondition));
    }
    public Generic1x1ContainerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, Function<PlayerEntity, Boolean> openCondition) {
        this(syncId, playerInventory, inventory, openCondition, stack -> true);
    }
    public Generic1x1ContainerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(syncId, playerInventory, inventory, player -> true);
    }
    public Generic1x1ContainerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotId) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotId);
        if (slot != null && slot.hasStack()) {
            ItemStack oldStack = slot.getStack();
            stack = oldStack.copy();

            if (slotId <= INVENTORY_END) {
                if (!this.insertItem(oldStack, 36, 37, false)){
                    return ItemStack.EMPTY;//放入
                }
                slot.onQuickTransfer(oldStack, stack);
            } else if (!this.insertItem(oldStack, HOTBAR_START, INVENTORY_END, true)) {
                return ItemStack.EMPTY;//取出
            }

            if (oldStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (oldStack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, oldStack);
        }

        return stack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.openCondition.apply(player);
    }
    //更新物品栏
    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.inventory) {
            this.inventory.setStack(0, ItemStack.EMPTY);
            this.sendContentUpdates();
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

    //玩家物品栏
    private void addPlayerInventorySlots(PlayerInventory playerInventory) {
        //快捷栏
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        //物品栏
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }
}
