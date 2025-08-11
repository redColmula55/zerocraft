package rc55.mc.zerocraft.block.plant;

import com.google.common.base.Suppliers;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class Age3CropBlock extends CropBlock {

    private final Supplier<ItemConvertible> seedSupplier;

    public Age3CropBlock(Supplier<ItemConvertible> seedSupplier) {
        this(Settings.copy(Blocks.WHEAT), seedSupplier);
    }
    public Age3CropBlock(Settings settings, Supplier<ItemConvertible> seedSupplier) {
        super(settings);
        this.seedSupplier = Suppliers.memoize(seedSupplier::get);
    }

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)
    };

    //age属性
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(this.getAgeProperty());
    }
    @Override
    public IntProperty getAgeProperty() {
        return Properties.AGE_3;
    }
    @Override
    public int getMaxAge() {
        return 3;
    }
    //选框外形
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[this.getAge(state)];
    }
    //物品形式（种子）
    @Override
    protected ItemConvertible getSeedsItem() {
        return this.seedSupplier.get();
    }
}
