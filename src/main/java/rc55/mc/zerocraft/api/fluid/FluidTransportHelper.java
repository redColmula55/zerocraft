package rc55.mc.zerocraft.api.fluid;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import rc55.mc.zerocraft.world.ZeroCraftGameRules;

public interface FluidTransportHelper {
    int getSpeed();

    int getMaxTemperature();

    Tank getTank();

    Tank.Faces getFacesType(World world, BlockPos pos, Direction direction, BlockPos newPos, BlockEntity newBlockEntity);

    /**
     * 管道每刻会调用<br><br>
     * 先尝试从其他容器中取出流体
     * 失败后会尝试插入至该容器
     * <pre>{@code
     * for (Direction direction : Direction.values()) {
     *     //custom conditions here if needed
     *     boolean condition;
     *     if (condition) FluidTransportHelper.doInsertOrExtract(world, pos, blockEntity, direction);
     * }
     * }</pre>
     * @param world 世界实例
     * @param pos 管道的位置
     * @param blockEntity 方块实体
     * @param direction 当前遍历到的方向
     */
    static void doInsertOrExtract(World world, BlockPos pos, FluidTransportHelper blockEntity, Direction direction) {
        BlockPos newPos = pos.offset(direction);
        BlockEntity newBlockEntity = world.getBlockEntity(newPos);
        if (newBlockEntity instanceof FluidTransportHelper helper) {
            FluidType extractedType = helper.getTank().extract(0, blockEntity.getSpeed(), helper.getFacesType(world, newPos, direction.getOpposite(), pos, (BlockEntity) blockEntity));
            if (extractedType.getFluid().getTemperature() > blockEntity.getMaxTemperature()) {//超过温度限制
                if (world.getGameRules().getBoolean(ZeroCraftGameRules.FLUID_PIPE_MELTS)) {//游戏规则是否烧毁
                    world.removeBlock(pos, false);//烧毁
                    world.removeBlockEntity(pos);
                    world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);//音效
                }
                return;
            }
            if (blockEntity.getTank().insert(extractedType, blockEntity.getFacesType(world, pos, direction, newPos, newBlockEntity))) {//成功就保存
                ((BlockEntity)blockEntity).markDirty();
                newBlockEntity.markDirty();
            } else {//取出失败，尝试插入
                helper.getTank().insert(extractedType, Tank.Faces.BOTH);//取出失败，还原
                FluidType insertedType = blockEntity.getTank().extract(0, blockEntity.getSpeed(), blockEntity.getFacesType(world, pos, direction, newPos, newBlockEntity));
                if (helper.getTank().insert(insertedType, helper.getFacesType(world, newPos, direction.getOpposite(), pos, (BlockEntity) blockEntity))) {//成功就保存
                    ((BlockEntity)blockEntity).markDirty();
                    newBlockEntity.markDirty();
                } else {//失败则还原
                    blockEntity.getTank().insert(insertedType, Tank.Faces.BOTH);
                }
            }
        }
    }
}
