package rc55.mc.zerocraft.api.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

public class MachineTank extends Tank {

    private FluidType inputType;
    private FluidType outputType;

    public MachineTank(int capacity, int maxTemperature) {
        this(capacity, maxTemperature, FluidType.EMPTY, FluidType.EMPTY);
    }
    public MachineTank(int capacity, int maxTemperature, FluidType inputType, FluidType outputType) {
        super(capacity, maxTemperature);
        this.inputType = inputType;
        this.outputType = outputType;
    }

    @Override
    public int getUsedCapacity() {
        return this.inputType.getAmount();
    }
    public int getOutputAmount() {
        return this.outputType.getAmount();
    }

    @Override
    public boolean isEmpty() {
        return this.inputType.isEmpty() && this.outputType.isEmpty();
    }

    @Override
    public @Nullable FluidType getCarriedFluid(int index) {
        return this.outputType;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("Input", this.inputType.toNbt());
        nbt.put("Output", this.outputType.toNbt());
        return nbt;
    }

    public static MachineTank fromNbt(NbtCompound nbt) {
        int capacity = nbt.getInt("Capacity");
        int maxTemperature = nbt.getInt("MaxTemperature");
        FluidType inputType = FluidType.fromNbt(nbt.getCompound("Input"));
        FluidType outputType = FluidType.fromNbt(nbt.getCompound("Output"));
        return new MachineTank(capacity, maxTemperature, inputType, outputType);
    }

    @Override
    public NbtCompound toNbt() {
        NbtCompound nbt = super.toNbt();
        nbt.put("Input", this.inputType.toNbt());
        nbt.put("Output", this.outputType.toNbt());
        return nbt;
    }

    @Override
    public boolean insert(FluidType type, Faces face) {
        if (!face.canInsert() || type.isEmpty()) return false;
        if (this.inputType.isEmpty()) {
            this.inputType = type;
            return true;
        }
        if (this.inputType.isOf(type) && this.getCapacity() >= this.getUsedCapacity() + type.getAmount()) {
            this.inputType.add(type.getAmount());
            return true;
        }
        return false;
    }
    @Override
    public FluidType extract(int index, int amount, Faces face) {
        if (!face.canExtract() || this.outputType.isEmpty()) return FluidType.EMPTY;
        return this.outputType.decrease(amount);
    }
    //处理输入流体
    public void process(Fluid requiredFluid, Fluid producedFluid, int producedAmount) {
        if (!this.inputType.isOf(requiredFluid) || this.outputType.getAmount() + producedAmount > this.getCapacity()) return;
        if (this.outputType.isEmpty()) {
            this.outputType = new FluidType(producedFluid, producedAmount);
        } else if (this.outputType.isOf(producedFluid)) {
            this.outputType.add(producedAmount);
        } else return;
        this.inputType.decrease(1);
    }
    public void process(TagKey<Fluid> requiredFluid, Fluid producedFluid, int producedAmount) {
        if (!this.inputType.isIn(requiredFluid) || this.outputType.getAmount() + producedAmount > this.getCapacity()) return;
        if (this.outputType.isEmpty()) {
            this.outputType = new FluidType(producedFluid, producedAmount);
        } else if (this.outputType.isOf(producedFluid)) {
            this.outputType.add(producedAmount);
        } else return;
        this.inputType.decrease(1);
    }
    public void process(FluidType requiredFluid, Fluid producedFluid, int producedAmount) {
        if (!this.inputType.isOf(requiredFluid) || this.inputType.getAmount() - requiredFluid.getAmount() < 0 || this.outputType.getAmount() + producedAmount > this.getCapacity()) return;
        if (this.outputType.isEmpty()) {
            this.outputType = new FluidType(producedFluid, producedAmount);
        } else if (this.outputType.isOf(producedFluid)) {
            this.outputType.add(producedAmount);
        } else return;
        this.inputType.decrease(requiredFluid.getAmount());
    }
}
