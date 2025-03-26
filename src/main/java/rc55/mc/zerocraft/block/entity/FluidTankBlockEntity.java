package rc55.mc.zerocraft.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.screen.FluidTankScreenHandler;

import java.util.List;
import java.util.Objects;

public class FluidTankBlockEntity extends BlockEntity implements Inventory, NamedScreenHandlerFactory, Nameable {
    public FluidTankBlockEntity(BlockPos pos, BlockState state) {
        super(ZeroCraftBlockEntityType.FLUID_TANK, pos, state);
    }
    //
    private final ViewerCountManager stateManager = new ViewerCountManager() {

        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {

        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {

        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {

        }

        @Override
        protected boolean isPlayerViewing(PlayerEntity player) {
            return false;
        }
    };

    //自定义名字
    @Nullable private Text customName;
    //已有液体
    private String carriedFluidName = "";
    private int carriedFluidAmount = 0;

    public static void tick(World world, BlockPos pos, BlockState state, FluidTankBlockEntity blockEntity){
        NbtCompound nbt = blockEntity.createNbt();
        String storedFluidId = nbt.getString("fluid");
        int storedFluidAmount = nbt.getInt("amount");
        ItemStack stack = blockEntity.getStack(0);
        if (stack.getItem() instanceof BucketItem) {
            for (Fluid fluid : Registries.FLUID){
                if (!fluid.matchesType(Fluids.EMPTY)){//倒入
                    if (stack.isOf(fluid.getBucketItem())){
                        String id = String.valueOf(Registries.FLUID.getId(fluid));
                        if (Objects.equals(id, storedFluidId) || storedFluidAmount == 0){
                            if (storedFluidAmount <= 15000){
                                ItemStack outputStack = blockEntity.getStack(1);
                                //返还空桶
                                if (outputStack.isOf(Items.BUCKET) && (outputStack.getCount() < outputStack.getMaxCount())){
                                    outputStack.setCount(outputStack.getCount()+1);
                                    blockEntity.setStack(1, outputStack);
                                    blockEntity.removeStack(0, 1);
                                    storedFluidAmount+=1000;
                                    nbt.putString("fluid", id);
                                    nbt.putInt("amount", storedFluidAmount);
                                    blockEntity.readNbt(nbt);
                                    blockEntity.markDirty();
                                } else if (outputStack.isEmpty()) {
                                    blockEntity.setStack(1, Items.BUCKET.getDefaultStack());
                                    blockEntity.removeStack(0, 1);
                                    storedFluidAmount+=1000;
                                    nbt.putString("fluid", id);
                                    nbt.putInt("amount", storedFluidAmount);
                                    blockEntity.readNbt(nbt);
                                    blockEntity.markDirty();
                                }
                            } else {//满了
                                //return ActionResult.CONSUME;
                            }
                        } else {//已有其他液体
                            //player.sendMessage(Text.of("other: "+storedFluidId));
                            //return ActionResult.CONSUME;
                        }
                    }
                } else {//取出
                    if (stack.isOf(Items.BUCKET)){
                        if (storedFluidAmount >= 1000 && blockEntity.getStack(1).isEmpty()){
                            blockEntity.removeStack(0, 1);
                            Fluid storedFluidType = Registries.FLUID.get(new Identifier(storedFluidId));
                            blockEntity.setStack(1, storedFluidType.getBucketItem().getDefaultStack());
                            blockEntity.removeStack(0);
                            storedFluidAmount-=1000;
                            if (storedFluidAmount == 0){
                                storedFluidId = "";
                            }
                            nbt.putString("fluid", storedFluidId);
                            nbt.putInt("amount", storedFluidAmount);
                            blockEntity.readNbt(nbt);
                            blockEntity.markDirty();
                        } else {//没有了
                            //player.sendMessage(Text.of("empty or too few"));
                            //return ActionResult.CONSUME;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt){
        Inventories.readNbt(nbt, this.inventory);
        if (nbt.contains("CustomName", NbtElement.STRING_TYPE)) {
            this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
        }
        this.carriedFluidName = nbt.getString("fluid");
        this.carriedFluidAmount = nbt.getInt("amount");
        super.readNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putString("fluid", this.carriedFluidName);
        nbt.putInt("amount", this.carriedFluidAmount);
        if (this.customName != null) {
            nbt.putString("CustomName", Text.Serializer.toJson(this.customName));
        }
        super.writeNbt(nbt);
    }

    //物品栏
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
    @Nullable private List<InventoryChangedListener> listeners;
    //大小
    @Override
    public int size() { return 2; }
    //是否可以使用
    @Override
    public boolean canPlayerUse(PlayerEntity player) { return true; }
    //
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return stack.getItem() instanceof BucketItem && slot == 0;
    }
    //是否为空
    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }
    //获取物品
    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }
    //减少物品
    @Override
    public ItemStack removeStack(int slot, int amount) {
        this.markDirty();
        //return Inventories.splitStack(this.inventory, slot, amount);
        ItemStack stack = this.getStack(slot);
        stack.decrement(amount);
        return stack;
    }
    //移除物品
    @Override
    public ItemStack removeStack(int slot) {
        this.markDirty();
        return ItemStack.EMPTY;
    }
    //设置物品
    @Override
    public void setStack(int slot, ItemStack stack) {
        this.markDirty();
        this.inventory.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > stack.getMaxCount()) {
            stack.setCount(stack.getMaxCount());
        }
    }
    //清空
    @Override
    public void clear() {
        this.markDirty();
        this.inventory.clear();
    }
    //进行更改时执行
    @Override
    public void markDirty() {
        if (this.listeners != null) {
            for (InventoryChangedListener inventoryChangedListener : this.listeners) {
                inventoryChangedListener.onInventoryChanged(this);
            }
        }
        super.markDirty();
    }
    //容器名字
    @Override
    public Text getName() {
        return Text.translatable("container.zerocraft.fluid_tank");
    }
    //是否显示自定义名字
    @Override
    public Text getDisplayName() {
        return this.customName != null ? this.customName : this.getName();
    }
    //gui
    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FluidTankScreenHandler(syncId, playerInventory, this);
    }
    public Inventory getInventory(){
        return new SimpleInventory(this.inventory.size());
    }
    //设置名字
    public void setCustomName(@Nullable Text name) {
        this.customName = name;
    }
}
