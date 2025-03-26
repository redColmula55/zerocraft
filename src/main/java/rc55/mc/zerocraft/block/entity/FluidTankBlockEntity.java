package rc55.mc.zerocraft.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.screen.FluidTankScreenHandler;

import java.util.List;

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
    private int carriedFluidRawId = 0;
    private int carriedFluidAmount = 0;

    private final int INPUT_SLOT_ID = 0;
    private final int OUTPUT_SLOT_ID = 1;
    private final int MAX_AMOUNT = 16000;//最大存储，单位mb
    //同步储存的信息
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> FluidTankBlockEntity.this.carriedFluidRawId;//数字id
                case 1 -> FluidTankBlockEntity.this.carriedFluidAmount;//数量
                default -> -1;
            };
        }
        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> FluidTankBlockEntity.this.carriedFluidRawId = value;
                case 1 -> FluidTankBlockEntity.this.carriedFluidAmount = value;
            }
        }
        @Override
        public int size() {
            return 2;
        }
    };

    //每刻执行
    public void tick(World world, BlockPos pos, BlockState state){
        if (!world.isClient){
            if (!this.getStack(INPUT_SLOT_ID).isEmpty()){
                if (this.onInsertItem(this.getStack(INPUT_SLOT_ID))){
                    markDirty(world, pos, state);
                }
            }
            this.carriedFluidRawId = getStoredFluidRawId(this);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        if (nbt.contains("CustomName", NbtElement.STRING_TYPE)) {
            this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
        }
        this.carriedFluidName = nbt.getString("fluid");
        this.carriedFluidAmount = nbt.getInt("amount");
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putString("fluid", this.carriedFluidName);
        nbt.putInt("amount", this.carriedFluidAmount);
        if (this.customName != null) {
            nbt.putString("CustomName", Text.Serializer.toJson(this.customName));
        }
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
        //this.markDirty();
        ItemStack result = Inventories.splitStack(this.inventory, slot, amount);
        if (!result.isEmpty()) {
            this.markDirty();
        }
        return result;
    }
    //移除物品
    @Override
    public ItemStack removeStack(int slot) {
        this.markDirty();
        return Inventories.removeStack(this.inventory, slot);
        //return ItemStack.EMPTY;
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
        return new FluidTankScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
    //设置名字
    public void setCustomName(@Nullable Text name) {
        this.customName = name;
    }
    //获取数据
    public static String getStoredFluidName(FluidTankBlockEntity blockEntity) {
        NbtCompound nbt = blockEntity.createNbt();
        return nbt.getString("fluid");
    }
    public static int getStoredFluidAmount(FluidTankBlockEntity blockEntity) {
        NbtCompound nbt = blockEntity.createNbt();
        return nbt.getInt("amount");
    }
    public static int getStoredFluidRawId(FluidTankBlockEntity blockEntity) {
        String id = getStoredFluidName(blockEntity);
        return id.isEmpty() ? -1 : Registries.FLUID.getRawId(Registries.FLUID.get(new Identifier(id)));//数字id,空罐返回-1
    }
    //设置nbt
    public static void setStoredFluid(FluidTankBlockEntity blockEntity, String id, int amount){
        NbtCompound nbt = blockEntity.createNbt();
        nbt.putString("fluid", id);
        nbt.putInt("amount", amount);
        blockEntity.readNbt(nbt);
    }
    //是否能输出
    private boolean isOutputAvailable(){
        return this.getStack(OUTPUT_SLOT_ID).isEmpty() || this.getStack(OUTPUT_SLOT_ID).getCount() < this.getStack(OUTPUT_SLOT_ID).getMaxCount();
    }
    private boolean isOutputAvailable(Item item){
        return this.getStack(OUTPUT_SLOT_ID).isEmpty() || (this.getStack(OUTPUT_SLOT_ID).isOf(item) && this.getStack(OUTPUT_SLOT_ID).getCount() < this.getStack(OUTPUT_SLOT_ID).getMaxCount());
    }
    //放入物品
    private boolean onInsertItem(ItemStack stack){
        if (stack.isOf(Items.BUCKET)) {//取出
            return this.onTakeFluid();
        } else if (stack.getItem() instanceof BucketItem) {//放入
            for (Fluid fluid : Registries.FLUID){
                if (fluid.isStill(fluid.getDefaultState())){
                    if (this.onInsertFluid(fluid)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**放入流体
     * 遍历所有流体时调用
     * @param fluid 当前流体
     * @return 是否成功放入
     */
    private boolean onInsertFluid(Fluid fluid){
        if (!this.isOutputAvailable(Items.BUCKET)){
            return false;
        }
        String id = getStoredFluidName(this);
        int amount = getStoredFluidAmount(this);
        if ((!id.isEmpty() && !fluid.matchesType(Registries.FLUID.get(new Identifier(id)))) || !this.getStack(INPUT_SLOT_ID).isOf(fluid.getBucketItem()) || amount+1000 > MAX_AMOUNT){
            return false;
        }
        this.removeStack(INPUT_SLOT_ID, 1);
        this.setStack(OUTPUT_SLOT_ID, new ItemStack(Items.BUCKET, this.getStack(OUTPUT_SLOT_ID).getCount()+1));
        setStoredFluid(this, String.valueOf(Registries.FLUID.getId(fluid)), amount+1000);
        return true;
    }

    /**
     * 取出流体
     * @return 是否成功取出
     */
    private boolean onTakeFluid(){
        String id = getStoredFluidName(this);
        int amount = getStoredFluidAmount(this);
        Fluid fluid = Registries.FLUID.get(new Identifier(id));
        if (id.isEmpty() || amount < 1000 || !this.isOutputAvailable(fluid.getBucketItem())){
            return false;
        }
        ItemStack result = new ItemStack(fluid.getBucketItem(), this.getStack(OUTPUT_SLOT_ID).getCount()+1);
        this.removeStack(INPUT_SLOT_ID, 1);
        this.setStack(OUTPUT_SLOT_ID, result);
        if (amount - 1000 <= 0){//没了
            setStoredFluid(this, "", 0);//重新设置为空
            return true;
        }
        setStoredFluid(this, id, amount-1000);
        return true;
    }
}
