package rc55.mc.zerocraft.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventories;
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
import rc55.mc.zerocraft.api.fluid.FluidTransportHelper;
import rc55.mc.zerocraft.api.fluid.SingleTypeTank;
import rc55.mc.zerocraft.api.fluid.Tank;
import rc55.mc.zerocraft.api.inventory.InventoryImpl;
import rc55.mc.zerocraft.screen.FluidTankScreenHandler;

import java.util.List;

public class FluidTankBlockEntity extends BlockEntity implements InventoryImpl, NamedScreenHandlerFactory, Nameable, FluidTransportHelper {

    private int maxTemperature;
    private int capacity;
    private SingleTypeTank tank;

    public FluidTankBlockEntity(BlockPos pos, BlockState state) {
        super(ZeroCraftBlockEntityType.FLUID_TANK, pos, state);
    }
    public FluidTankBlockEntity(BlockPos pos, BlockState state, int capacity, int maxTemperature) {
        this(pos, state);
        this.capacity = capacity;
        this.maxTemperature = maxTemperature;
        this.tank = new SingleTypeTank(capacity, maxTemperature);
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

    public static final int INPUT_SLOT_ID = 0;
    public static final int OUTPUT_SLOT_ID = 1;
    //同步储存的信息
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> getStoredFluidRawId(FluidTankBlockEntity.this);//数字id
                case 1 -> getStoredFluidAmount(FluidTankBlockEntity.this);//数量
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
    public static void tick(World world, BlockPos pos, BlockState state, FluidTankBlockEntity blockEntity){
        if (!world.isClient) {
            if (!blockEntity.getStack(INPUT_SLOT_ID).isEmpty()) {
                if (blockEntity.onInsertItem(blockEntity.getStack(INPUT_SLOT_ID))) {
                    markDirty(world, pos, state);
                }
            }
        }
    }
    //设置数据
    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        if (nbt.contains("CustomName", NbtElement.STRING_TYPE)) {
            this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
        }
        this.tank = SingleTypeTank.fromNbt(nbt);
    }
    //保存世界
    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        if (this.customName != null) {
            nbt.putString("CustomName", Text.Serializer.toJson(this.customName));
        }
        this.tank.writeNbt(nbt);
    }

    //物品栏
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    @Nullable private List<InventoryChangedListener> listeners;
    @Override
    public DefaultedList<ItemStack> getStacksList() {
        return this.inventory;
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
        return Text.translatable(FluidTankScreenHandler.TANK_TRANS_KEY);
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
        return blockEntity.tank.getCarriedFluid(0).getFluidId();
    }
    public static int getStoredFluidAmount(FluidTankBlockEntity blockEntity) {
        return blockEntity.tank.getUsedCapacity();
    }
    public static int getStoredFluidRawId(FluidTankBlockEntity blockEntity) {
        return blockEntity.tank.getCarriedFluid(0).getFluidRawId();//数字id,空罐返回-1
    }
    public static int getCapacity(FluidTankBlockEntity blockEntity) {
        return blockEntity.tank.getCapacity();
    }
    public static int getMaxTemperature(FluidTankBlockEntity blockEntity) {
        return blockEntity.tank.getMaxTemperature();
    }
    //设置nbt
    public static void setStoredFluid(FluidTankBlockEntity blockEntity, String id, int amount){
        NbtCompound nbt = blockEntity.createNbt();
        NbtCompound nbt2 = new NbtCompound();
        nbt2.putString("id", id);
        nbt2.putInt("amount", amount);
        nbt.put("Fluid", nbt2);
        blockEntity.readNbt(nbt);
    }
    public static void setStoredFluid(FluidTankBlockEntity blockEntity, Fluid fluid, int addAmount) {
        if (getStoredFluidAmount(blockEntity) + addAmount > getCapacity(blockEntity)) return;
        setStoredFluid(blockEntity, Registries.FLUID.getId(fluid).toString(), getStoredFluidAmount(blockEntity) + addAmount);
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
            return this.onTakeFluid(1000);
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
        if ((!id.isEmpty() && !fluid.matchesType(Registries.FLUID.get(new Identifier(id)))) || !this.getStack(INPUT_SLOT_ID).isOf(fluid.getBucketItem()) || amount+1000 > getCapacity(this) || fluid.getTemperature() > getMaxTemperature(this)){
            return false;
        }
        this.removeStack(INPUT_SLOT_ID, 1);
        this.setStack(OUTPUT_SLOT_ID, new ItemStack(Items.BUCKET, this.getStack(OUTPUT_SLOT_ID).getCount()+1));
        setStoredFluid(this, String.valueOf(Registries.FLUID.getId(fluid)), amount+1000);
        return true;
    }

    /**
     * 取出流体
     * @param takeAmount 取出的流体数量
     * @return 是否成功取出
     */
    private boolean onTakeFluid(int takeAmount){
        String id = getStoredFluidName(this);
        int amount = getStoredFluidAmount(this);
        Fluid fluid = Registries.FLUID.get(new Identifier(id));
        if (id.isEmpty() || amount < takeAmount || !this.isOutputAvailable(fluid.getBucketItem())){
            return false;
        }
        ItemStack result = new ItemStack(fluid.getBucketItem(), this.getStack(OUTPUT_SLOT_ID).getCount()+1);
        this.removeStack(INPUT_SLOT_ID, 1);
        this.setStack(OUTPUT_SLOT_ID, result);
        if (amount - takeAmount <= 0){//没了
            setStoredFluid(this, "", 0);//重新设置为空
            return true;
        }
        setStoredFluid(this, id, amount-takeAmount);
        return true;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public int getMaxTemperature() {
        return this.maxTemperature;
    }

    @Override
    public Tank getTank() {
        return this.tank;
    }

    @Override
    public Tank.Faces getFacesType(World world, BlockPos pos, Direction direction, BlockPos newPos, BlockEntity newBlockEntity) {
        boolean powered = world.getReceivedRedstonePower(pos) != 0;
        if (powered) return Tank.Faces.NONE;
        return switch (direction) {
            case UP -> Tank.Faces.INSERT;
            case DOWN -> Tank.Faces.EXTRACT;
            default -> Tank.Faces.BOTH;
        };
    }
}
