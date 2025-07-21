package rc55.mc.zerocraft.api.fluid;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Tank {

    private final int capacity;
    private final int maxTemperature;
    private final NbtCompound nbt = new NbtCompound();

    public Tank(int capacity, int maxTemperature) {
        this.capacity = capacity;
        this.maxTemperature = maxTemperature;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getMaxTemperature() {
        return this.maxTemperature;
    }

    public NbtCompound getNbt() {
        return this.nbt;
    }

    public abstract int getUsedCapacity();

    public NbtCompound toNbt() {
        NbtCompound nbt = this.getNbt();
        nbt.putInt("Capacity", this.getCapacity());
        nbt.putInt("MaxTemperature", this.getMaxTemperature());
        return nbt;
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("Capacity", this.getCapacity());
        nbt.putInt("MaxTemperature", this.getMaxTemperature());
        return nbt;
    }

    @Nullable
    public abstract FluidType getCarriedFluid(int index);

    public abstract boolean isEmpty();

    /**
     * 向相邻容器插入流体
     * @param type 插入的流体种类和数量
     * @return 是否成功
     */
    public abstract boolean insert(FluidType type, Faces face);

    /**
     * 从相邻容器取出流体
     * @param amount 要取出的数量
     * @return 取出的流体种类和数量
     */
    public abstract FluidType extract(int index, int amount, Faces face);

    //不同面的功能
    public enum Faces {
        INSERT(true, false),
        EXTRACT(false, true),
        BOTH(true, true),
        NONE(false, false);

        private final boolean canInsert;
        private final boolean canExtract;

        Faces(boolean canInsert, boolean canExtract) {
            this.canInsert = canInsert;
            this.canExtract = canExtract;
        }
        //是否可以插入流体
        public boolean canInsert() {
            return this.canInsert;
        }
        //是否可以取出流体
        public boolean canExtract() {
            return this.canExtract;
        }
    }
}
