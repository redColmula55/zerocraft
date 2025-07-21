package rc55.mc.zerocraft.screen;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;
import rc55.mc.zerocraft.api.Utils;
import rc55.mc.zerocraft.block.entity.BoilerBlockEntity;
import rc55.mc.zerocraft.block.entity.ZeroCraftBlockEntityType;
import rc55.mc.zerocraft.screen.slot.InputSlot;

public class BoilerScreenHandler extends ScreenHandler {

    private static final int HOTBAR_START = 0;
    private static final int HOTBAR_END = 8;
    private static final int INVENTORY_START = 9;
    private static final int INVENTORY_END = 35;

    public static final String BOILER_TRANS_KEY = Utils.getContainerTransKey(ZeroCraftBlockEntityType.BOILER);

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    //方块实体用
    public BoilerScreenHandler(int syncId, PlayerInventory playerInventory, @NotNull Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ZeroCraftScreenHandlerType.BOILER_SCREEN, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.addPlayerInventorySlots(playerInventory);
        this.addInputSlot();
        this.addProperties(propertyDelegate);
    }
    //注册用
    public BoilerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1), new ArrayPropertyDelegate(3));
    }
    //获取数据
    public int getInputAmount() {
        return this.propertyDelegate.get(BoilerBlockEntity.INPUT_AMOUNT_INDEX);
    }
    public int getOutputAmount() {
        return this.propertyDelegate.get(BoilerBlockEntity.OUTPUT_AMOUNT_INDEX);
    }
    public int getBurnTime() {
        return this.propertyDelegate.get(BoilerBlockEntity.BURN_TIME_INDEX);
    }
    //快速移动物品
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
    //是否可以使用
    @Override
    public boolean canUse(PlayerEntity player) {
        return !player.isSpectator() && this.inventory instanceof BoilerBlockEntity;
    }
    //更新物品栏
    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
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
    //燃料
    private void addInputSlot(){
        this.addSlot(new InputSlot(this.inventory, 0, 15, 20, AbstractFurnaceBlockEntity::canUseAsFuel));
    }
}
