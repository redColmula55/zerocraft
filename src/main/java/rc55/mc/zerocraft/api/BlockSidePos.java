package rc55.mc.zerocraft.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Set;

public class BlockSidePos {
    private final Direction side;
    private Vec3d startPos;
    private Vec3d endPos;
    private final Vec3d hitPos;

    private final PlayerEntity player;

    public BlockSidePos(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        Vec3d blockCenter = pos.toCenterPos();
        //Vec3d vec3d = context.getHitPos().subtract(pos.toCenterPos());//.offset(hitSide, 0.5)
        this.side = context.getSide();
        this.hitPos = context.getHitPos();
        switch (context.getSide()) {
            case NORTH -> {
                this.startPos = blockCenter.add(-0.5, -0.5, -0.5);
                this.endPos = blockCenter.add(0.5, 0.5, -0.5);
            }
            case SOUTH -> {
                this.startPos = blockCenter.add(-0.5, -0.5, 0.5);
                this.endPos = blockCenter.add(0.5, 0.5, 0.5);
            }
            case WEST -> {
                this.startPos = blockCenter.add(-0.5, -0.5, -0.5);
                this.endPos = blockCenter.add(-0.5, 0.5, 0.5);
            }
            case EAST -> {
                this.startPos = blockCenter.add(0.5, -0.5, -0.5);
                this.endPos = blockCenter.add(0.5, 0.5, 0.5);
            }
            case UP -> {
                this.startPos = blockCenter.add(-0.5, 0.5, -0.5);
                this.endPos = blockCenter.add(0.5, 0.5, 0.5);
            }
            case DOWN -> {
                this.startPos = blockCenter.add(-0.5, -0.5, -0.5);
                this.endPos = blockCenter.add(0.5, -0.5, 0.5);
            }
        }

        this.player = context.getPlayer();
    }

    //平面坐标
    public double getSideX() {
        Vec3d vec3d = this.endPos.subtract(this.hitPos);
        return vec3d.x == 0.0 ? vec3d.y : (vec3d.z == 0.0 ? vec3d.y : vec3d.x);
    }
    public double getSideY() {
        Vec3d vec3d = this.endPos.subtract(this.hitPos);
        return vec3d.z == 0.0 ? (vec3d.y == this.getSideX() ? vec3d.x : vec3d.y) : vec3d.z;
    }

    //点击方块的位置（9向）
    public Area getHitArea() {
        //TODO: 上下面跟随玩家视角改变
        if (this.getSideY() < 0.3) {
            if (this.side.getAxis() == Direction.Axis.X) {
                return this.side.getDirection() == Direction.AxisDirection.POSITIVE ? this.getHitAreaX(Area.LEFT) : this.getHitAreaX(Area.RIGHT);
            } else {
                return this.side.getDirection() == Direction.AxisDirection.POSITIVE ? this.getHitAreaX(Area.RIGHT) : this.getHitAreaX(Area.LEFT);
            }
        } else if (this.getSideY() > 0.7) {
            if (this.side.getAxis() == Direction.Axis.X) {
                return this.side.getDirection() == Direction.AxisDirection.NEGATIVE ? this.getHitAreaX(Area.LEFT) : this.getHitAreaX(Area.RIGHT);
            } else {
                return this.side.getDirection() == Direction.AxisDirection.NEGATIVE ? this.getHitAreaX(Area.RIGHT) : this.getHitAreaX(Area.LEFT);
            }
        } else if (this.getSideX() < 0.3) {
            return Area.TOP;
        } else if (this.getSideX() > 0.7) {
            return Area.BOTTOM;
        } else return Area.MIDDLE;
    }

    //9向简化为5向（调整方块朝向用）
    public Area getSimpleHitArea() {
        Area area = this.getHitArea();
        return switch (area) {
            case TOP_RIGHT -> Area.TOP;
            case BOTTOM_RIGHT -> Area.RIGHT;
            case BOTTOM_LEFT -> Area.BOTTOM;
            case TOP_LEFT -> Area.LEFT;
            default -> area;
        };
    }

    private Area getHitAreaX(Area area) {
        if (area == Area.LEFT) {
            if (this.getSideX() < 0.3) {
                return Area.TOP_LEFT;
            } else if (this.getSideX() > 0.7) {
                return Area.BOTTOM_LEFT;
            } else return Area.LEFT;
        } else {
            if (this.getSideX() < 0.3) {
                return Area.TOP_RIGHT;
            } else if (this.getSideX() > 0.7) {
                return Area.BOTTOM_RIGHT;
            } else return Area.RIGHT;
        }
    }

    //朝向方块转向
    public Direction getSimpleRotatedDirection(Direction original) {
        Direction playerPerspective = this.player.getHorizontalFacing();
        return switch (this.getSimpleHitArea()) {
            case RIGHT -> original.rotateCounterclockwise(playerPerspective.rotateClockwise(playerPerspective.getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X).getAxis());
            case TOP -> original.rotateClockwise(playerPerspective.rotateYClockwise().getAxis());
            case LEFT -> original.rotateClockwise(playerPerspective.rotateClockwise(playerPerspective.getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X).getAxis());
            case BOTTOM -> original.rotateCounterclockwise(playerPerspective.rotateYClockwise().getAxis());
            case MIDDLE -> playerPerspective == original ? playerPerspective.getOpposite() : playerPerspective;
            default -> original;
        };
    }

    public Vec3d getHitPos2() {
        return this.endPos.subtract(this.hitPos);
    }

    @Override
    public String toString() {
        return String.format("BlockSidePos[side=%s, startPos=%s, endPos=%s, hitPos=%s]", this.side, this.startPos, this.endPos, this.hitPos);
    }

    public enum Area {
        TOP_LEFT, TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, MIDDLE;

        public Set<Direction> getDirections() {
            return switch (this) {
                case TOP_LEFT -> Set.of(Direction.EAST, Direction.NORTH);
                case TOP -> Set.of(Direction.EAST);
                case TOP_RIGHT -> Set.of(Direction.EAST, Direction.SOUTH);
                case RIGHT -> Set.of(Direction.SOUTH);
                case BOTTOM_RIGHT -> Set.of(Direction.WEST, Direction.SOUTH);
                case BOTTOM -> Set.of(Direction.WEST);
                case BOTTOM_LEFT -> Set.of(Direction.WEST, Direction.NORTH);
                case LEFT -> Set.of(Direction.NORTH);
                case MIDDLE -> Set.of();
            };
        }
    }
}
