package rc55.mc.zerocraft.item.armor;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;

import java.util.List;
import java.util.stream.Collectors;

public class ScarletCrystalLeggingsItem extends ArmorItem {
    public ScarletCrystalLeggingsItem() {
        super(ZeroCraftArmorMaterials.SCARLET_CRYSTAL, Type.LEGGINGS, new Settings().rarity(Rarity.RARE).fireproof());
    }
    //发包用
    public static final Identifier PACK_ID = new Identifier(ZeroCraft.MODID, "key_inventory_leggings");

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (!world.isClient){

            /*ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
                while (ZeroCraftKeyBinds.LEGGINGS_INVENTORY_OPEN_KEY.wasPressed()){
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeItemStack(stack);
                    if (entity instanceof PlayerEntity player && player.getInventory().getArmorStack(1).isOf(this)) {
                        ClientPlayNetworking.send(ScarletCrystalLeggingsItem.PACK_ID, buf);
                        //player.sendMessage(Text.of("[debug/client] Opened."));//调试信息
                    }
                }
            });*/

            if (entity instanceof PlayerEntity player) {
                ItemStack leggings = player.getInventory().getArmorStack(1);
                //存储物品用
                //LeggingsInventory leggingsInventory = new LeggingsInventory(leggings);
                ServerPlayNetworking.registerGlobalReceiver(PACK_ID, ((server, serverPlayer, handler, buf, responseSender) -> {
                    //leggingsInventory.readNbtList(leggings.getOrCreateNbt().getList("Inventory", NbtElement.COMPOUND_TYPE));
                    //leggingsInventory.readNbt(leggings);
                    server.execute(() -> {
                        //仅玩家触发效果
                        //判断是否穿上
                        //ItemStack wearingStack = buf.readItemStack();
                        if (leggings.isOf(this)){

                            //NamedScreenHandlerFactory factory = new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player1) -> GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, leggingsInventory), leggings.getName());//
                            NamedScreenHandlerFactory factory = new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player1) -> GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, serverPlayer.getEnderChestInventory()), Text.translatable("container.enderchest"));
                            serverPlayer.openHandledScreen(factory);
                            if (factory.shouldCloseCurrentScreen()){
                                //leggings.getOrCreateNbt().put("Inventory", leggingsInventory.toNbtList());
                                //leggingsInventory.writeNbt(leggings);
                            }
                        }
                        //sendMessage(serverPlayer, Text.of("[debug/server] Received pack."));
                    });
                }));

            }

        }
    }
    //物品栏
    @Deprecated
    private Inventory getInventory(ItemStack stack){
        return new LeggingsInventory(stack);
    }
    @Deprecated
    public static class LeggingsInventory implements Inventory{

        private final int size = 27;
        public final DefaultedList<ItemStack> stacks;
        @Nullable
        private List<InventoryChangedListener> listeners;
        private final ItemStack stack;

        public LeggingsInventory(ItemStack stack) {
            this.stacks = DefaultedList.ofSize(this.size, ItemStack.EMPTY);
            this.stack = stack;
        }

        public void addListener(InventoryChangedListener listener) {
            if (this.listeners == null) {
                this.listeners = Lists.<InventoryChangedListener>newArrayList();
            }
            this.listeners.add(listener);
        }

        public void removeListener(InventoryChangedListener listener) {
            if (this.listeners != null) {
                this.listeners.remove(listener);
            }
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public boolean isEmpty() {
            for (ItemStack itemStack : this.stacks) {
                if (!itemStack.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public ItemStack getStack(int slot) {
            return stacks.get(slot);
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            this.markDirty();
            return Inventories.splitStack(stacks, slot, amount);
        }

        @Override
        public ItemStack removeStack(int slot) {
            this.markDirty();
            return Inventories.removeStack(stacks, slot);
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            this.stacks.set(slot, stack);
            if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
                stack.setCount(this.getMaxCountPerStack());
            }
            this.markDirty();
        }

        @Override
        public void markDirty() {
            if (this.listeners != null) {
                for (InventoryChangedListener inventoryChangedListener : this.listeners) {
                    inventoryChangedListener.onInventoryChanged(this);
                }
            }
        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return true;
        }

        @Override
        public void clear() {
            this.stacks.clear();
            this.markDirty();
        }

        public String toString() {
            return ((List)this.stacks.stream().filter(stack -> !stack.isEmpty()).collect(Collectors.toList())).toString();
        }

        private void addToNewSlot(ItemStack stack) {
            for (int i = 0; i < this.size; i++) {
                ItemStack itemStack = this.getStack(i);
                if (itemStack.isEmpty()) {
                    this.setStack(i, stack.copyAndEmpty());
                    return;
                }
            }
        }

        private void addToExistingSlot(ItemStack stack) {
            for (int i = 0; i < this.size; i++) {
                ItemStack itemStack = this.getStack(i);
                if (ItemStack.canCombine(itemStack, stack)) {
                    this.transfer(stack, itemStack);
                    if (stack.isEmpty()) {
                        return;
                    }
                }
            }
        }

        private void transfer(ItemStack source, ItemStack target) {
            int i = Math.min(this.getMaxCountPerStack(), target.getMaxCount());
            int j = Math.min(source.getCount(), i - target.getCount());
            if (j > 0) {
                target.increment(j);
                source.decrement(j);
                this.markDirty();
            }
        }

        public ItemStack addStack(ItemStack stack) {
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                ItemStack itemStack = stack.copy();
                this.addToExistingSlot(itemStack);
                if (itemStack.isEmpty()) {
                    return ItemStack.EMPTY;
                } else {
                    this.addToNewSlot(itemStack);
                    return itemStack.isEmpty() ? ItemStack.EMPTY : itemStack;
                }
            }
        }

        public void readNbtList(NbtList nbtList) {
            this.clear();

            for (int i = 0; i < nbtList.size(); i++) {
                ItemStack itemStack = ItemStack.fromNbt(nbtList.getCompound(i));
                if (!itemStack.isEmpty()) {
                    this.addStack(itemStack);
                }
            }
        }

        public NbtList toNbtList() {
            NbtList nbtList = new NbtList();

            for (int i = 0; i < this.size(); i++) {
                ItemStack itemStack = this.getStack(i);
                if (!itemStack.isEmpty()) {
                    nbtList.add(itemStack.writeNbt(new NbtCompound()));
                }
            }

            return nbtList;
        }

        public void readNbt(ItemStack stack){
            if (stack.isOf(this.stack.getItem())){
                this.readNbtList(stack.getOrCreateNbt().getList("Inventory", NbtElement.COMPOUND_TYPE));
            }
        }

        public void writeNbt(ItemStack stack){
            if (stack.isOf(this.stack.getItem())){
                stack.getOrCreateNbt().put("Inventory", this.toNbtList());
            }
        }
    }
}
