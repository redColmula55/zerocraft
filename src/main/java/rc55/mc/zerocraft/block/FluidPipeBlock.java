package rc55.mc.zerocraft.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.api.fluid.FluidTransportHelper;
import rc55.mc.zerocraft.block.entity.FluidPipeBlockEntity;
import rc55.mc.zerocraft.block.entity.ZeroCraftBlockEntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluidPipeBlock extends BlockWithEntity implements Waterloggable, PipeBlocks {

    private final int capacity;
    private final int maxTemperature;
    private final int transferSpeed;

    protected FluidPipeBlock(int capacity, int maxTemperature, int transferSpeed, Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.WATERLOGGED, false));
        for (Direction direction : DIRECTIONS) {
            setDefaultState(getDefaultState().with(FACINGS.get(direction), false));
        }
        this.capacity = capacity;
        this.maxTemperature = maxTemperature;
        this.transferSpeed = transferSpeed;
    }
    //方块状态对应朝向
    public static final Map<Direction, BooleanProperty> FACINGS = new HashMap<>(ImmutableMap.of(Direction.NORTH, Properties.NORTH, Direction.SOUTH, Properties.SOUTH, Direction.WEST, Properties.WEST, Direction.EAST, Properties.EAST, Direction.UP, Properties.UP, Direction.DOWN, Properties.DOWN));
    //渲染
    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }
    //方块实体
    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FluidPipeBlockEntity(pos, state, this.capacity, this.maxTemperature, this.transferSpeed);
    }
    //设置属性
    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        for (Direction direction : DIRECTIONS) {
            builder.add(FACINGS.get(direction));
        }
        builder.add(Properties.WATERLOGGED);
    }
    //放置后朝向
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        World world = context.getWorld();
        Fluid fluid = context.getWorld().getFluidState(context.getBlockPos()).getFluid();
        BlockState state = super.getPlacementState(context).with(Properties.WATERLOGGED, fluid.matchesType(Fluids.WATER));
        for (Direction direction : DIRECTIONS) {
            BlockPos neighborPos = context.getBlockPos().offset(direction);
            BlockState neighborState = world.getBlockState(neighborPos);
            if (world.getBlockEntity(neighborPos) instanceof FluidTransportHelper) {//可连接的
                if (neighborState.getBlock() instanceof PipeBlocks pipe) {//与其他管道
                    state = state.with(FACINGS.get(direction), pipe.isSideConnectable(neighborState, direction));
                } else {
                    state = state.with(FACINGS.get(direction), true);
                }
            } else {
                state = state.with(FACINGS.get(direction), false);
            }
        }
        return state;
    }
    //选框
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape base = Block.createCuboidShape(4,4,4,12,12,12);

        Map<Direction, VoxelShape> shapeMap = new HashMap<>(ImmutableMap.of(
                Direction.NORTH, Block.createCuboidShape(4,4,0,12,12,4),
                Direction.SOUTH, Block.createCuboidShape(4,4,12,12,12,16),
                Direction.WEST, Block.createCuboidShape(0,4,4,4,12,12),
                Direction.EAST, Block.createCuboidShape(12,4,4,16,12,12),
                Direction.UP, Block.createCuboidShape(4,12,4,12,16,12),
                Direction.DOWN, Block.createCuboidShape(4,0,4,12,4,12)));
        for (Direction direction : DIRECTIONS) {
            if (state.get(FACINGS.get(direction))) {
                base = VoxelShapes.union(base, shapeMap.get(direction));
            }
        }
        return base;
    }
    //含水
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    //含水&管道朝向
    @Override
    public BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        if (state.get(Properties.WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));//含水
        if (world.getBlockEntity(neighborPos) instanceof FluidTransportHelper) {//可连接的
            if (neighborState.getBlock() instanceof PipeBlocks pipe) {//与其他管道
                return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(FACINGS.get(direction), pipe.isSideConnectable(neighborState, direction));
            }
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(FACINGS.get(direction), true);
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(FACINGS.get(direction), false);
        }
    }
    //方块状态更新
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }
    //每刻执行
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeroCraftBlockEntityType.FLUID_PIPE, FluidPipeBlockEntity::tick);
    }
    //管道属性
    public int getMaxTemperature() {
        return this.maxTemperature;
    }
    public int getTransferSpeed() {
        return this.transferSpeed;
    }
    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {//按shift显示
            tooltip.add(Text.translatable(PipeBlocks.SPEED_TRANS_KEY, this.transferSpeed));
            tooltip.add(Text.translatable(PipeBlocks.MAX_TEMP_TRANS_KEY, this.maxTemperature, this.maxTemperature-273));
        } else {
            tooltip.add(Text.translatable("item.zerocraft.hint.shift"));
        }
    }
    @Override
    public boolean isSideConnectable(BlockState state, Direction direction) {
        return true;
    }
}
