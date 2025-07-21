package rc55.mc.zerocraft.fluid;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.shorts.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public abstract class FloatableFluid extends FlowableFluid {
    //是否无限
    @Override
    protected boolean isInfinite(World world) {
        return false;
    }
    //冲毁方块前掉落物品
    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }
    //流动特性
    @Override
    public void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        super.flow(world, pos, state, direction, fluidState);
    }
    @Override
    public void onScheduledTick(World world, BlockPos pos, FluidState state) {
        if (!state.isStill()) {
            FluidState fluidState = this.getUpdatedState(world, pos, world.getBlockState(pos));
            int i = this.getTickRate(world);
            if (fluidState.isEmpty()) {
                state = fluidState;
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
            } else if (!fluidState.equals(state)) {
                state = fluidState;
                BlockState blockState = fluidState.getBlockState();
                world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
                world.scheduleFluidTick(pos, fluidState.getFluid(), i);
                world.updateNeighborsAlways(pos, blockState.getBlock());
            }
        }
        this.tryFlow(world, pos, state);
    }
    //流速
    @Override
    protected int getFlowSpeed(WorldView world) {
        return 4;
    }
    //流动距离
    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }
    //能否被其他流体替换
    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }
    //刻计算周期
    @Override
    public int getTickRate(WorldView world) {
        return 5;
    }
    //爆炸抗性
    @Override
    protected float getBlastResistance() {
        return 100.0f;
    }
    //种类
    @Override
    public boolean matchesType(Fluid fluid){
        return fluid == this.getStill() || fluid == this.getFlowing();
    }
    //外形
    @Override
    public VoxelShape getShape(FluidState state, BlockView world, BlockPos pos) {
        return state.getLevel() == 9 && state.getFluid().matchesType(world.getFluidState(pos.down()).getFluid())
                ? VoxelShapes.fullCube()
                : Maps.<FluidState, VoxelShape>newIdentityHashMap().computeIfAbsent(state, state2 -> VoxelShapes.cuboid(0.0, state2.getHeight(world, pos), 0.0, 1.0, 1.0, 1.0));
    }
    @Override
    public float getHeight(FluidState state, BlockView world, BlockPos pos) {
        return state.getFluid().matchesType(world.getFluidState(pos.down()).getFluid()) ? 1.0F : state.getHeight();
    }
    @Override
    public float getHeight(FluidState state) {
        return super.getHeight(state);
    }
    @Override
    public Vec3d getVelocity(BlockView world, BlockPos pos, FluidState state) {
        return super.getVelocity(world, pos, state);
    }
    //音效
    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
    }
    //流动
    //好复杂改了半天啊啊啊
    protected void tryFlow(World world, BlockPos fluidPos, FluidState state) {
        if (!state.isEmpty()) {
            BlockState blockState = world.getBlockState(fluidPos);
            BlockPos blockPos = fluidPos.up();
            BlockState blockState2 = world.getBlockState(blockPos);
            FluidState fluidState = this.getUpdatedState(world, blockPos, blockState2);
            if (this.canFlow(world, fluidPos, blockState, Direction.UP, blockPos, blockState2, world.getFluidState(blockPos), fluidState.getFluid())) {
                this.flow(world, blockPos, blockState2, Direction.UP, fluidState);
                if (this.countNeighboringSources(world, fluidPos) >= 3) {
                    this.flowToSides(world, fluidPos, state, blockState);
                }
            } else if (state.isStill() || !this.canFlowUpTo(world, fluidState.getFluid(), fluidPos, blockState, blockPos, blockState2)) {
                this.flowToSides(world, fluidPos, state, blockState);
            }
        }
    }
    protected boolean isFlowBlocked(BlockView world, BlockPos pos, Direction direction) {
        BlockState blockState = world.getBlockState(pos);
        FluidState fluidState = world.getFluidState(pos);
        if (fluidState.getFluid().matchesType(this)) {
            return false;
        } else if (direction == Direction.DOWN) {
            return true;
        } else {
            return blockState.getBlock() instanceof IceBlock ? false : blockState.isSideSolidFullSquare(world, pos, direction);
        }
    }
    private boolean canFlowUpTo(BlockView world, Fluid fluid, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState) {
        if (!this.receivesFlow(Direction.UP, world, pos, state, fromPos, fromState)) {
            return false;
        } else {
            return fromState.getFluidState().getFluid().matchesType(this) ? true : this.canFill(world, fromPos, fromState, fluid);
        }
    }
    private void flowToSides(World world, BlockPos pos, FluidState fluidState, BlockState blockState) {
        int i = fluidState.getLevel() - this.getLevelDecreasePerBlock(world);
        if (fluidState.get(FALLING)) {
            i = 7;
        }

        if (i > 0) {
            Map<Direction, FluidState> map = this.getSpread(world, pos, blockState);

            for (Map.Entry<Direction, FluidState> entry : map.entrySet()) {
                Direction direction = entry.getKey();
                FluidState fluidState2 = entry.getValue();
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState2 = world.getBlockState(blockPos);
                if (this.canFlow(world, pos, blockState, direction, blockPos, blockState2, world.getFluidState(blockPos), fluidState2.getFluid())) {
                    this.flow(world, blockPos, blockState2, direction, fluidState2);
                }
            }
        }
    }
    private int countNeighboringSources(WorldView world, BlockPos pos) {
        int i = 0;

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            FluidState fluidState = world.getFluidState(blockPos);
            if (this.matchesType(fluidState.getFluid()) && fluidState.isStill()) {
                i++;
            }
        }

        return i;
    }

    protected FluidState getUpdatedState(World world, BlockPos pos, BlockState state) {
        int i = 0;
        int j = 0;

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            FluidState fluidState = blockState.getFluidState();
            if (fluidState.getFluid().matchesType(this) && this.receivesFlow(direction, world, pos, state, blockPos, blockState)) {
                if (fluidState.isStill()) {
                    j++;
                }

                i = Math.max(i, fluidState.getLevel());
            }
        }

        if (this.isInfinite(world) && j >= 2) {
            BlockState blockState2 = world.getBlockState(pos.up());
            FluidState fluidState2 = blockState2.getFluidState();
            if (blockState2.isSolid() || (this.matchesType(fluidState2.getFluid()) && fluidState2.isStill())) {
                return this.getStill(false);
            }
        }

        BlockPos blockPos2 = pos.down();
        BlockState blockState3 = world.getBlockState(blockPos2);
        FluidState fluidState3 = blockState3.getFluidState();
        if (!fluidState3.isEmpty() && fluidState3.getFluid().matchesType(this) && this.receivesFlow(Direction.DOWN, world, pos, state, blockPos2, blockState3)) {
            return this.getFlowing(8, true);
        } else {
            int k = i - this.getLevelDecreasePerBlock(world);
            return k <= 0 ? Fluids.EMPTY.getDefaultState() : this.getFlowing(k, false);
        }
    }
    protected Map<Direction, FluidState> getSpread(World world, BlockPos pos, BlockState state) {
        int i = 1000;
        Map<Direction, FluidState> map = Maps.newEnumMap(Direction.class);
        Short2ObjectMap<Pair<BlockState, FluidState>> short2ObjectMap = new Short2ObjectOpenHashMap<>();
        Short2BooleanMap short2BooleanMap = new Short2BooleanOpenHashMap();

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            short s = packXZOffset(pos, blockPos);
            Pair<BlockState, FluidState> pair = short2ObjectMap.computeIfAbsent(s, (Short2ObjectFunction<? extends Pair<BlockState, FluidState>>)(sx -> {
                BlockState blockStatex = world.getBlockState(blockPos);
                return Pair.of(blockStatex, blockStatex.getFluidState());
            }));
            BlockState blockState = pair.getFirst();
            FluidState fluidState = pair.getSecond();
            FluidState fluidState2 = this.getUpdatedState(world, blockPos, blockState);
            if (this.canFlowThrough(world, fluidState2.getFluid(), pos, state, direction, blockPos, blockState, fluidState)) {
                BlockPos blockPos2 = blockPos.up();
                boolean bl = short2BooleanMap.computeIfAbsent(s, sx -> {
                    BlockState blockState2 = world.getBlockState(blockPos2);
                    return this.canFlowUpTo(world, this.getFlowing(), blockPos, blockState, blockPos2, blockState2);
                });
                int j;
                if (bl) {
                    j = 0;
                } else {
                    j = this.getFlowSpeedBetween(world, blockPos, 1, direction.getOpposite(), blockState, pos, short2ObjectMap, short2BooleanMap);
                }

                if (j < i) {
                    map.clear();
                }

                if (j <= i) {
                    map.put(direction, fluidState2);
                    i = j;
                }
            }
        }

        return map;
    }
    private boolean canFlowThrough(
            BlockView world, Fluid fluid, BlockPos pos, BlockState state, Direction face, BlockPos fromPos, BlockState fromState, FluidState fluidState
    ) {
        return !(this.matchesType(fluidState.getFluid()) && fluidState.isStill())
                && this.receivesFlow(face, world, pos, state, fromPos, fromState)
                && this.canFill(world, fromPos, fromState, fluid);
    }
    private static short packXZOffset(BlockPos from, BlockPos to) {
        int i = to.getX() - from.getX();
        int j = to.getZ() - from.getZ();
        return (short)((i + 128 & 0xFF) << 8 | j + 128 & 0xFF);
    }
}
