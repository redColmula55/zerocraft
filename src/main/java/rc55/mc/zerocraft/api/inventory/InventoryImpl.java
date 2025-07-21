package rc55.mc.zerocraft.api.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface InventoryImpl extends Inventory {

    DefaultedList<ItemStack> getStacksList();

    //大小
    @Override
    default int size() {
        return getStacksList().size();
    }
    //是否可以使用
    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
    //是否为空
    @Override
    default boolean isEmpty() {
        return this.getStacksList().isEmpty();
    }
    //获取物品
    @Override
    default ItemStack getStack(int slot) {
        return this.getStacksList().get(slot);
    }
    //减少物品
    @Override
    default ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(this.getStacksList(), slot, amount);
        if (!result.isEmpty()) {
            this.markDirty();
        }
        return result;
    }
    //移除物品
    @Override
    default ItemStack removeStack(int slot) {
        this.markDirty();
        return Inventories.removeStack(this.getStacksList(), slot);
    }
    //设置物品
    @Override
    default void setStack(int slot, ItemStack stack) {
        this.getStacksList().set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > stack.getMaxCount()) {
            stack.setCount(stack.getMaxCount());
            this.markDirty();
        }
    }
    //清空
    @Override
    default void clear() {
        this.getStacksList().clear();
        this.markDirty();
    }
    //进行更改时执行
    @Override
    default void markDirty() {

    }
}
