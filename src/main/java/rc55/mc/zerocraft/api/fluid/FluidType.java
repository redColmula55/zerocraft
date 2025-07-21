package rc55.mc.zerocraft.api.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class FluidType {
    private final Fluid fluid;
    private int amount;

    public FluidType(@NotNull Fluid fluid, int amount) {
        this.fluid = fluid;
        this.amount = amount;
    }
    public FluidType(@NotNull String fluid, int amount) {
        this(Registries.FLUID.get(new Identifier(fluid)), amount);
    }
    public FluidType(@NotNull Identifier fluid, int amount) {
        this(Registries.FLUID.get(fluid), amount);
    }
    public FluidType(int fluid, int amount) {
        this(Registries.FLUID.get(fluid), amount);
    }
    //空的
    private FluidType() {
        this.fluid = null;
        this.amount = 0;
    }
    public static final FluidType EMPTY = new FluidType();

    //是否为空
    public boolean isEmpty() {
        return this.fluid == null || this.fluid == Fluids.EMPTY || this.amount <= 0;
    }
    //储存的流体
    public Fluid getFluid() {
        return this.isEmpty() ? Fluids.EMPTY : this.fluid;
    }
    //储存的流体id
    public String getFluidId() {
        return this.isEmpty() ? "" : Registries.FLUID.getId(this.getFluid()).toString();
    }
    public int getFluidRawId() {
        return this.isEmpty() ? -1 : Registries.FLUID.getRawId(this.getFluid());
    }
    //储存的流体数量（mB）
    public int getAmount() {
        return this.isEmpty() ? 0 : this.amount;
    }
    //判断储存的流体类型是否为参数给定流体
    public boolean isOf(Fluid fluid) {
        return this.getFluid().matchesType(fluid);
    }
    public boolean isOf(FluidType type) {
        return this.isOf(type.getFluid());
    }
    public boolean isIn(TagKey<Fluid> tag) {
        return this.getFluid().isIn(tag);
    }
    //nbt形式
    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        if (!this.isEmpty()) {
            nbt.putString("id", this.getFluidId());
            nbt.putInt("amount", this.amount);
        }
        return nbt;
    }
    //设置保存流体的数量
    //增加
    public FluidType add(int amount) {
        this.amount += amount;
        return this;
    }
    //减少
    public FluidType decrease(int amount) {
        if (this.amount - amount < 0) {
            this.amount = 0;
            return new FluidType(this.fluid, this.amount);
        } else this.amount -= amount;
        return new FluidType(this.fluid, amount);
    }
    //设为给定值
    public FluidType setAmount(int amount) {
        this.amount = amount;
        return this;
    }
    //读取nbt
    public static FluidType fromNbt(NbtCompound nbt) {
        String id = nbt.getString("id");
        int amount = nbt.getInt("amount");
        return nbt.isEmpty() || id.isEmpty() || amount <= 0 ? EMPTY : new FluidType(id, amount);
    }
}
