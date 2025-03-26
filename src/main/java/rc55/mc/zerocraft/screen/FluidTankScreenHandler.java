package rc55.mc.zerocraft.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;
import rc55.mc.zerocraft.block.entity.FluidTankBlockEntity;
import rc55.mc.zerocraft.screen.slot.FluidTankInputSlot;
import rc55.mc.zerocraft.screen.slot.ResultSlot;

public class FluidTankScreenHandler extends ScreenHandler {

    private static final int CONTAINER_SIZE = 2;
    private static final int INVENTORY_START = 9;
    private static final int INVENTORY_END = 36;
    private static final int HOTBAR_START = 0;
    private static final int HOTBAR_END = 8;
    private Inventory inventory;

    //方块实体调用
    public FluidTankScreenHandler(int syncId, PlayerInventory playerInventory, @NotNull Inventory inventory) {
        super(ZeroCraftScreenHandlerType.FLUID_TANK_SCREEN, syncId);
        this.inventory = inventory;
        this.addPlayerInventorySlots(playerInventory);
        this.addInputSlot();
        this.addOutputSlot();
    }
    //注册屏幕用
    public FluidTankScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2));
    }
    //shift放置物品
    @Override
    public ItemStack quickMove(PlayerEntity player, int slotId) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotId);
        if (slot != null && slot.hasStack()) {
            ItemStack oldStack = slot.getStack();
            stack = oldStack.copy();

            if (slotId < INVENTORY_END) {
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
    //是否可以打开界面
    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory instanceof FluidTankBlockEntity;
    }

    //玩家物品栏
    private void addPlayerInventorySlots(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        //快捷栏
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
    //放水用
    private void addInputSlot(){
        this.addSlot(new FluidTankInputSlot(this.inventory, 0, 0, 0));
    }
    private void addOutputSlot(){
        this.addSlot(new ResultSlot(this.inventory, 1, 20, 0));
    }
}
