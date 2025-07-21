package rc55.mc.zerocraft.api.fluid;

import net.minecraft.nbt.NbtCompound;

public class SingleTypeTank extends Tank {

    private FluidType type = FluidType.EMPTY;

    public SingleTypeTank(int capacity, int maxTemperature) {
        super(capacity, maxTemperature);
    }
    //NBT数据
    @Override
    public NbtCompound toNbt() {
        NbtCompound nbt = super.toNbt();
        nbt.put("Fluid", this.type.toNbt());
        return nbt;
    }
    //保存NBT数据
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("Fluid", this.type.toNbt());
        return nbt;
    }
    //从NBT中加载数据
    public static SingleTypeTank fromNbt(NbtCompound nbt) {
        int capacity = nbt.getInt("Capacity");
        int maxTemperature = nbt.getInt("MaxTemperature");
        SingleTypeTank tank = new SingleTypeTank(capacity, maxTemperature);
        tank.insert(FluidType.fromNbt(nbt.getCompound("Fluid")), Faces.BOTH);//new FluidType(nbt.getCompound("Fluid").getString("id"),nbt.getCompound("Fluid").getInt("amount"))
        return tank;
    }

    @Override
    public boolean isEmpty() {
        return this.type.isEmpty();
    }

    @Override
    public int getUsedCapacity() {
        return this.type.getAmount();
    }

    @Override
    public FluidType getCarriedFluid(int index) {
        return this.type;
    }

    @Override
    public boolean insert(FluidType type, Faces face) {
        if (!face.canInsert() || type.isEmpty()) return false;
        if (this.isEmpty()) {
            this.type = type;
            return true;
        }
        if (this.type.isOf(type) && this.getCapacity() >= this.getUsedCapacity() + type.getAmount()) {
            this.type.add(type.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public FluidType extract(int index, int amount, Faces face) {
        if (!face.canExtract() || this.isEmpty()) return FluidType.EMPTY;
        return this.type.decrease(amount);
    }
}
