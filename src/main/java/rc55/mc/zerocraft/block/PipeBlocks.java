package rc55.mc.zerocraft.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public interface PipeBlocks {
    //获取信息
    int getMaxTemperature();
    int getTransferSpeed();
    //其他管道是否可以连接
    boolean isSideConnectable(BlockState state, Direction direction);
    //物品lore翻译键
    String MAX_TEMP_TRANS_KEY = "block.zerocraft.pipes.desc.max_temp";
    String SPEED_TRANS_KEY = "block.zerocraft.pipes.desc.speed";
}
