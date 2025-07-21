package rc55.mc.zerocraft.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import rc55.mc.zerocraft.api.fluid.FluidTransportHelper;
import rc55.mc.zerocraft.api.fluid.SingleTypeTank;
import rc55.mc.zerocraft.api.fluid.Tank;
import rc55.mc.zerocraft.block.FluidPipeBlock;
import rc55.mc.zerocraft.block.PipeBlocks;

public class FluidPipeBlockEntity extends BlockEntity implements FluidTransportHelper {

    private int capacity;
    private int maxTemperature;
    private int transferSpeed;
    private SingleTypeTank tank;

    public FluidPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ZeroCraftBlockEntityType.FLUID_PIPE, pos, state);
    }
    public FluidPipeBlockEntity(BlockPos pos, BlockState state, int capacity, int maxTemperature, int transferSpeed) {
        this(pos, state);
        this.capacity = capacity;
        this.maxTemperature = maxTemperature;
        this.transferSpeed = transferSpeed;
        this.tank = new SingleTypeTank(capacity, maxTemperature);
    }

    private int transferCooldown = -1;

    public static void tick(World world, BlockPos pos, BlockState state, FluidPipeBlockEntity blockEntity) {
        if (!world.isClient) {
            for (Direction direction : Direction.values()) {
                if (state.get(FluidPipeBlock.FACINGS.get(direction))) {
                    FluidTransportHelper.doInsertOrExtract(world, pos, blockEntity, direction);
                }
            }
        }
    }
    //写入数据
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.capacity = nbt.getInt("Capacity");
        this.maxTemperature = nbt.getInt("MaxTemperature");
//        this.transferCooldown = nbt.getInt("TransferCooldown");
        this.tank = SingleTypeTank.fromNbt(nbt);
    }
    //保存世界
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
        BlockState state = world.getBlockState(pos);
        Block newBlock = world.getBlockState(newPos).getBlock();
        return state.get(FluidPipeBlock.FACINGS.get(direction)) && newBlock instanceof PipeBlocks ? Tank.Faces.BOTH : Tank.Faces.NONE;
    }
}
