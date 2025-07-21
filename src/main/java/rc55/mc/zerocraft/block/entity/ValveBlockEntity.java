package rc55.mc.zerocraft.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import rc55.mc.zerocraft.api.fluid.FluidTransportHelper;
import rc55.mc.zerocraft.api.fluid.SingleTypeTank;
import rc55.mc.zerocraft.api.fluid.Tank;
import rc55.mc.zerocraft.block.ValveBlock;

public class ValveBlockEntity extends BlockEntity implements FluidTransportHelper {

    private int capacity;
    private int maxTemperature;
    private int transferSpeed;
    private SingleTypeTank tank;

    public ValveBlockEntity(BlockPos pos, BlockState state) {
        super(ZeroCraftBlockEntityType.VALVE, pos, state);
    }
    public ValveBlockEntity(BlockPos pos, BlockState state, int capacity, int maxTemperature, int transferSpeed) {
        this(pos, state);
        this.capacity = capacity;
        this.maxTemperature = maxTemperature;
        this.transferSpeed = transferSpeed;
        this.tank = new SingleTypeTank(capacity, maxTemperature);
    }

    private int transferCooldown = -1;

    public static void tick(World world, BlockPos pos, BlockState state, ValveBlockEntity blockEntity) {
//        this.transferCooldown++;
//        if (this.transferCooldown == 4) {
//            this.transferCooldown = 0;
//            //tryExtract(world, state.get(Properties.FACING).getOpposite(), this.capacity);
//        }
        if (!world.isClient) {
            if (state.get(ValveBlock.OPENED)) {
                doInsertOrExtract(world, pos, state, blockEntity);
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.capacity = nbt.getInt("Capacity");
        this.maxTemperature = nbt.getInt("MaxTemperature");
//        this.transferCooldown = nbt.getInt("TransferCooldown");
        this.tank = SingleTypeTank.fromNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
//        nbt.putInt("Capacity", this.capacity);
//        nbt.putInt("MaxTemperature", this.maxTemperature);
//        nbt.putInt("TransferCooldown", this.transferCooldown);
        this.tank.writeNbt(nbt);
    }

    @Override
    public int getSpeed() {
        return this.transferSpeed;
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
        BlockState thisState = world.getBlockState(pos);
        if (thisState.get(Properties.FACING) == direction) return Tank.Faces.INSERT;
        if (thisState.get(Properties.FACING) == direction.getOpposite()) return Tank.Faces.EXTRACT;
        return Tank.Faces.NONE;
    }

    private static void doInsertOrExtract(World world, BlockPos pos, BlockState state, ValveBlockEntity blockEntity) {
        for (Direction direction : Direction.values()) {
            if (state.get(Properties.FACING) == direction || state.get(Properties.FACING) == direction.getOpposite()) {
                FluidTransportHelper.doInsertOrExtract(world, pos, blockEntity, direction);
            }
        }
    }
}
