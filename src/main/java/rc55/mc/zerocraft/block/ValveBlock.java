package rc55.mc.zerocraft.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.api.fluid.SingleTypeTank;
import rc55.mc.zerocraft.block.entity.ValveBlockEntity;
import rc55.mc.zerocraft.block.entity.ZeroCraftBlockEntityType;

import java.util.List;

public class ValveBlock extends BlockWithEntity implements Waterloggable, PipeBlocks {

    private final int capacity;
    private final int maxTemperature;
    private final int transferSpeed;
    private final SingleTypeTank tank;

    public ValveBlock(int capacity, int maxTemperature, int transferSpeed, Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.FACING, Direction.NORTH).with(OPENED, false).with(Properties.WATERLOGGED, false));
        this.capacity = capacity;
        this.maxTemperature = maxTemperature;
        this.transferSpeed = transferSpeed;
        this.tank = new SingleTypeTank(capacity, maxTemperature);
    }
    //渲染
    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }
    //方块实体
    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ValveBlockEntity(pos, state, this.capacity, this.maxTemperature, this.transferSpeed);
    }
    //属性
    //阀门开启状态
    public static final BooleanProperty OPENED = BooleanProperty.of("opened");
    //设置属性
    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.FACING, OPENED);
        builder.add(Properties.WATERLOGGED);
    }
    //放置后朝向
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Fluid fluid = context.getWorld().getFluidState(context.getBlockPos()).getFluid();
        return super.getPlacementState(context).with(Properties.FACING, context.getSide().getOpposite()).with(Properties.WATERLOGGED, fluid.matchesType(Fluids.WATER));
    }
    //选框
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape vertical = Block.createCuboidShape(4,0,4,12,16,12);//垂直
        VoxelShape northern = Block.createCuboidShape(4,4,0,12,12,16);//南北向
        VoxelShape eastern = Block.createCuboidShape(0,4,4,16,12,12);//东西向
        return switch (state.get(Properties.FACING)) {
            case DOWN, UP -> vertical;
            case NORTH, SOUTH -> northern;
            case WEST, EAST -> eastern;
        };
    }
    //含水
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    //方块更新
    @Override
    public BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        if (state.get(Properties.WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));//含水
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }
    //右键效果
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        world.setBlockState(pos, state.with(OPENED, !state.get(OPENED)));//开关阀门
        return ActionResult.SUCCESS;
    }
    //每刻执行
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeroCraftBlockEntityType.VALVE, ValveBlockEntity::tick);
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
        return state.get(Properties.FACING).getAxis() == direction.getAxis();
    }
}
