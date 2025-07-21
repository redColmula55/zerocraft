package rc55.mc.zerocraft.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.api.fluid.FluidTransportHelper;
import rc55.mc.zerocraft.api.fluid.MachineTank;
import rc55.mc.zerocraft.api.fluid.Tank;
import rc55.mc.zerocraft.api.inventory.InventoryImpl;
import rc55.mc.zerocraft.fluid.ZeroCraftFluids;
import rc55.mc.zerocraft.screen.BoilerScreenHandler;

public class BoilerBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, InventoryImpl, FluidTransportHelper {

    private MachineTank tank;
    private int burnTime = 0;

    public static final int BURN_TIME_INDEX = 0;
    public static final int INPUT_AMOUNT_INDEX = 1;
    public static final int OUTPUT_AMOUNT_INDEX = 2;

    public BoilerBlockEntity(BlockPos pos, BlockState state) {
        super(ZeroCraftBlockEntityType.BOILER, pos, state);
        this.tank = new MachineTank(16000, 2000);
    }

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case BURN_TIME_INDEX -> BoilerBlockEntity.this.burnTime;
                case INPUT_AMOUNT_INDEX -> getInputAmount();
                case OUTPUT_AMOUNT_INDEX -> getOutputAmount();
                default -> -1;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case BURN_TIME_INDEX -> BoilerBlockEntity.this.burnTime = value;
                case INPUT_AMOUNT_INDEX -> BoilerBlockEntity.this.getTank().getUsedCapacity();
                case OUTPUT_AMOUNT_INDEX -> ((MachineTank)(BoilerBlockEntity.this.getTank())).getOutputAmount();
            };
        }

        @Override
        public int size() {
            return 3;
        }
    };
    //每刻执行
    public static void tick(World world, BlockPos pos, BlockState state, BoilerBlockEntity blockEntity) {
        if (!world.isClient) {
            ItemStack fuelStack = blockEntity.getStack(0);
            if (AbstractFurnaceBlockEntity.canUseAsFuel(fuelStack) && blockEntity.burnTime <= 0 && blockEntity.getInputAmount() > 0) {
                blockEntity.burnTime = AbstractFurnaceBlockEntity.createFuelTimeMap().get(fuelStack.getItem());
                blockEntity.removeStack(0, 1);
                blockEntity.markDirty();
            }
            if (blockEntity.burnTime > 0) {
                blockEntity.tank.process(Fluids.WATER, ZeroCraftFluids.STEAM, 10);
                blockEntity.burnTime--;
            }
        }
    }
    //gui
    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BoilerScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
    //显示名字
    @Override
    public Text getDisplayName() {
        return Text.translatable(BoilerScreenHandler.BOILER_TRANS_KEY);
    }
    DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    //物品栏
    @Override
    public DefaultedList<ItemStack> getStacksList() {
        return this.inventory;
    }
    //nbt
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        this.tank = MachineTank.fromNbt(nbt);
        this.burnTime = nbt.getInt("BurnTime");
    }
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        this.tank.writeNbt(nbt);
        nbt.putInt("BurnTime", this.burnTime);
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public int getMaxTemperature() {
        return 2000;
    }

    @Override
    public Tank getTank() {
        return this.tank;
    }
    public int getBurnTime() {
        return this.burnTime;
    }
    public int getInputAmount(){
        return this.tank.getUsedCapacity();
    }
    public int getOutputAmount() {
        return this.tank.getOutputAmount();
    }

    @Override
    public Tank.Faces getFacesType(World world, BlockPos pos, Direction direction, BlockPos newPos, BlockEntity newBlockEntity) {
        if (world.getReceivedRedstonePower(pos) != 0) return Tank.Faces.NONE;
        return direction == Direction.UP ? Tank.Faces.EXTRACT : Tank.Faces.INSERT;
    }
}
