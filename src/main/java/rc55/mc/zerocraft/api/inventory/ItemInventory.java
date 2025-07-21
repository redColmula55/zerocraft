package rc55.mc.zerocraft.api.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

/**
 * 可以存储物品的物品所用的物品栏（好绕）
 * @see rc55.mc.zerocraft.item.armor.ScarletCrystalLeggingsItem
 */
public class ItemInventory implements InventoryImpl {

    private final DefaultedList<ItemStack> inventory;
    private final ItemStack stack;

    public ItemInventory(int size, ItemStack stack) {
        this.inventory = DefaultedList.ofSize(size, ItemStack.EMPTY);
        this.stack = stack;
    }

    @Override
    public DefaultedList<ItemStack> getStacksList() {
        return this.inventory;
    }

    @Override
    public void onOpen(PlayerEntity player) {
        NbtCompound nbt = this.stack.getOrCreateNbt();
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    public void onClose(PlayerEntity player) {
        NbtCompound nbt = this.stack.getOrCreateNbt();
        Inventories.writeNbt(nbt, this.inventory);
        this.markDirty();
    }
}
