package rc55.mc.zerocraft.block.plant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import rc55.mc.zerocraft.item.ZeroCraftItems;

public class CabbageCropBlock extends CropBlock {

    //private final ItemConvertible seedItem;

    public CabbageCropBlock(Settings settings) {
        super(settings);
    }

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)
    };

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
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[this.getAge(state)];
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ZeroCraftItems.CABBAGE;
    }
}
