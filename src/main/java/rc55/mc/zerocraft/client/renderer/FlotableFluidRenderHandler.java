package rc55.mc.zerocraft.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.block.*;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class FlotableFluidRenderHandler extends SimpleFluidRenderHandler {
    public FlotableFluidRenderHandler(Identifier stillTexture, Identifier flowingTexture, @Nullable Identifier overlayTexture, int tint) {
        super(stillTexture, flowingTexture, overlayTexture, tint);
    }
    public FlotableFluidRenderHandler(Identifier stillTexture, Identifier flowingTexture, int tint) {
        this(stillTexture, flowingTexture, null, tint);
    }
    public FlotableFluidRenderHandler(Identifier stillTexture, Identifier flowingTexture) {
        this(stillTexture, flowingTexture, -1);
    }
    @Override
    public void renderFluid(BlockPos pos, BlockRenderView world, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState) {
        this.render(world, pos, vertexConsumer, blockState, fluidState);
    }

    public void render(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState) {
        boolean isLava = fluidState.isIn(FluidTags.LAVA);
        int color = isLava ? 16777215 : this.tint;//color without shader&light affection
        float colorR = (color >> 16 & 0xFF) / 255.0F;
        float colorG = (color >> 8 & 0xFF) / 255.0F;
        float colorB = (color & 0xFF) / 255.0F;
        BlockState blockStateDown = world.getBlockState(pos.offset(Direction.DOWN));
        FluidState fluidStateDown = blockStateDown.getFluidState();
        BlockState blockStateUp = world.getBlockState(pos.offset(Direction.UP));
        FluidState fluidStateUp = blockStateUp.getFluidState();
        BlockState blockStateNorth = world.getBlockState(pos.offset(Direction.NORTH));
        FluidState fluidStateNorth = blockStateNorth.getFluidState();
        BlockState blockStateSouth = world.getBlockState(pos.offset(Direction.SOUTH));
        FluidState fluidStateSouth = blockStateSouth.getFluidState();
        BlockState blockStateWest = world.getBlockState(pos.offset(Direction.WEST));
        FluidState fluidStateWest = blockStateWest.getFluidState();
        BlockState blockStateEast = world.getBlockState(pos.offset(Direction.EAST));
        FluidState fluidStateEast = blockStateEast.getFluidState();
        boolean bl2 = !isSameFluid(fluidState, fluidStateUp);
        boolean hasSideDown = shouldRenderSide(world, pos, fluidState, blockState, Direction.DOWN, fluidStateDown)
                && !isSideCovered(world, pos, Direction.DOWN, 0.8888889F, blockStateDown);
        boolean hasSideUp = shouldRenderSide(world, pos, fluidState, blockState, Direction.UP, fluidStateDown)
                && !isSideCovered(world, pos, Direction.UP, 0.8888889F, blockStateDown);
        boolean hasSideN = shouldRenderSide(world, pos, fluidState, blockState, Direction.NORTH, fluidStateNorth);
        boolean hasSideS = shouldRenderSide(world, pos, fluidState, blockState, Direction.SOUTH, fluidStateSouth);
        boolean hasSideW = shouldRenderSide(world, pos, fluidState, blockState, Direction.WEST, fluidStateWest);
        boolean hasSideE = shouldRenderSide(world, pos, fluidState, blockState, Direction.EAST, fluidStateEast);
        if (bl2 || hasSideDown || hasSideE || hasSideW || hasSideN || hasSideS) {//visible
            float shaderDown = world.getBrightness(Direction.DOWN, true);
            float shaderUp = world.getBrightness(Direction.UP, true);
            float l = world.getBrightness(Direction.NORTH, true);
            float m = world.getBrightness(Direction.WEST, true);
            Fluid fluid = fluidState.getFluid();
            float thisHeight = this.getFluidHeight(world, fluid, pos, blockState, fluidState);
            float cornerHeightNE;
            float cornerHeightNW;
            float cornerHeightSE;
            float cornerHeightSW;
            if (thisHeight >= 1.0F) {
                cornerHeightNE = 1.0F;
                cornerHeightNW = 1.0F;
                cornerHeightSE = 1.0F;
                cornerHeightSW = 1.0F;
            } else {
                float heightN = this.getFluidHeight(world, fluid, pos.north(), blockStateNorth, fluidStateNorth);
                float heightS = this.getFluidHeight(world, fluid, pos.south(), blockStateSouth, fluidStateSouth);
                float heightE = this.getFluidHeight(world, fluid, pos.east(), blockStateEast, fluidStateEast);
                float heightW = this.getFluidHeight(world, fluid, pos.west(), blockStateWest, fluidStateWest);
                cornerHeightNE = this.calculateFluidHeight(world, fluid, thisHeight, heightN, heightE, pos.offset(Direction.NORTH).offset(Direction.EAST));
                cornerHeightNW = this.calculateFluidHeight(world, fluid, thisHeight, heightN, heightW, pos.offset(Direction.NORTH).offset(Direction.WEST));
                cornerHeightSE = this.calculateFluidHeight(world, fluid, thisHeight, heightS, heightE, pos.offset(Direction.SOUTH).offset(Direction.EAST));
                cornerHeightSW = this.calculateFluidHeight(world, fluid, thisHeight, heightS, heightW, pos.offset(Direction.SOUTH).offset(Direction.WEST));
            }

            double d = pos.getX() & 15;
            double e = pos.getY() & 15;
            double w = pos.getZ() & 15;
            float x = 0.001F;
            float y = hasSideDown ? 0.001F : 0.0F;
            if (bl2 && !isSideCovered(world, pos, Direction.DOWN, Math.min(Math.min(cornerHeightNW, cornerHeightSW), Math.min(cornerHeightSE, cornerHeightNE)), blockStateDown)) {
                cornerHeightNW -= 0.001F;
                cornerHeightSW -= 0.001F;
                cornerHeightSE -= 0.001F;
                cornerHeightNE -= 0.001F;
                Vec3d vec3d = fluidState.getVelocity(world, pos);
                float z;
                float ab;
                float ad;
                float af;
                float aa;
                float ac;
                float ae;
                float ag;
                if (vec3d.x == 0.0 && vec3d.z == 0.0) {//still&full
                    Sprite sprite = this.sprites[0];
                    z = sprite.getFrameU(0.0);
                    aa = sprite.getFrameV(0.0);
                    ab = z;
                    ac = sprite.getFrameV(16.0);
                    ad = sprite.getFrameU(16.0);
                    ae = ac;
                    af = ad;
                    ag = aa;
                } else {
                    Sprite sprite = this.sprites[1];
                    float ah = (float) MathHelper.atan2(vec3d.z, vec3d.x) - (float) (Math.PI / 2);
                    float ai = MathHelper.sin(ah) * 0.25F;
                    float aj = MathHelper.cos(ah) * 0.25F;
                    float ak = 8.0F;
                    z = sprite.getFrameU(8.0F + (-aj - ai) * 16.0F);
                    aa = sprite.getFrameV(8.0F + (-aj + ai) * 16.0F);
                    ab = sprite.getFrameU(8.0F + (-aj + ai) * 16.0F);
                    ac = sprite.getFrameV(8.0F + (aj + ai) * 16.0F);
                    ad = sprite.getFrameU(8.0F + (aj + ai) * 16.0F);
                    ae = sprite.getFrameV(8.0F + (aj - ai) * 16.0F);
                    af = sprite.getFrameU(8.0F + (aj - ai) * 16.0F);
                    ag = sprite.getFrameV(8.0F + (-aj - ai) * 16.0F);
                }

                float al = (z + ab + ad + af) / 4.0F;
                float ah = (aa + ac + ae + ag) / 4.0F;
                float ai = this.sprites[0].getAnimationFrameDelta();
                z = MathHelper.lerp(ai, z, al);
                ab = MathHelper.lerp(ai, ab, al);
                ad = MathHelper.lerp(ai, ad, al);
                af = MathHelper.lerp(ai, af, al);
                aa = MathHelper.lerp(ai, aa, ah);
                ac = MathHelper.lerp(ai, ac, ah);
                ae = MathHelper.lerp(ai, ae, ah);
                ag = MathHelper.lerp(ai, ag, ah);
                int light = this.getLight(world, pos);
                float r = shaderDown * colorR;
                float g = shaderDown * colorG;
                float b = shaderDown * colorB;
                this.vertex(vertexConsumer, d + 0.0, e + cornerHeightNW, w + 0.0, r, g, b, z, aa, light);
                this.vertex(vertexConsumer, d + 0.0, e + cornerHeightSW, w + 1.0, r, g, b, ab, ac, light);
                this.vertex(vertexConsumer, d + 1.0, e + cornerHeightSE, w + 1.0, r, g, b, ad, ae, light);
                this.vertex(vertexConsumer, d + 1.0, e + cornerHeightNE, w + 0.0, r, g, b, af, ag, light);
                if (fluidState.canFlowTo(world, pos.up())) {
                    this.vertex(vertexConsumer, d + 0.0, e + cornerHeightNW, w + 0.0, r, g, b, z, aa, light);
                    this.vertex(vertexConsumer, d + 1.0, e + cornerHeightNE, w + 0.0, r, g, b, af, ag, light);
                    this.vertex(vertexConsumer, d + 1.0, e + cornerHeightSE, w + 1.0, r, g, b, ad, ae, light);
                    this.vertex(vertexConsumer, d + 0.0, e + cornerHeightSW, w + 1.0, r, g, b, ab, ac, light);
                }
            }

            if (hasSideUp) {//up
                float minU = this.sprites[0].getMinU();
                float maxU = this.sprites[0].getMaxU();
                float minV = this.sprites[0].getMinV();
                float maxV = this.sprites[0].getMaxV();
                int light = this.getLight(world, pos.up());
                float r = shaderUp * colorR;
                float g = shaderUp * colorG;
                float b = shaderUp * colorB;
                this.vertex(vertexConsumer, d, e + y, w + 1.0, r, g, b, minU, maxV, light);
                this.vertex(vertexConsumer, d, e + y, w, r, g, b, minU, minV, light);
                this.vertex(vertexConsumer, d + 1.0, e + y, w, r, g, b, maxU, minV, light);
                this.vertex(vertexConsumer, d + 1.0, e + y, w + 1.0, r, g, b, maxU, maxV, light);
            }

            int aq = this.getLight(world, pos);

            for (Direction direction : Direction.Type.HORIZONTAL) {//side
                float afx;
                float aax;
                double ar;
                double at;
                double as;
                double au;
                boolean bl8;
                switch (direction) {
                    case NORTH:
                        afx = cornerHeightNW;
                        aax = cornerHeightNE;
                        ar = d;
                        as = d + 1.0;
                        at = w + 0.001F;
                        au = w + 0.001F;
                        bl8 = hasSideN;
                        break;
                    case SOUTH:
                        afx = cornerHeightSE;
                        aax = cornerHeightSW;
                        ar = d + 1.0;
                        as = d;
                        at = w + 1.0 - 0.001F;
                        au = w + 1.0 - 0.001F;
                        bl8 = hasSideS;
                        break;
                    case WEST:
                        afx = cornerHeightSW;
                        aax = cornerHeightNW;
                        ar = d + 0.001F;
                        as = d + 0.001F;
                        at = w + 1.0;
                        au = w;
                        bl8 = hasSideW;
                        break;
                    default:
                        afx = cornerHeightNE;
                        aax = cornerHeightSE;
                        ar = d + 1.0 - 0.001F;
                        as = d + 1.0 - 0.001F;
                        at = w;
                        au = w + 1.0;
                        bl8 = hasSideE;
                }

                if (bl8 && !isSideCovered(world, pos, direction, Math.max(afx, aax), world.getBlockState(pos.offset(direction)))) {
                    BlockPos blockPos = pos.offset(direction);
                    Sprite sprite2 = this.sprites[1];
                    if (!isLava && this.overlayTexture != null) {
                        Block block = world.getBlockState(blockPos).getBlock();
                        if (block instanceof TransparentBlock || block instanceof LeavesBlock) {
                            sprite2 = this.sprites[2];
                        }
                    }

                    float av = sprite2.getFrameU(0.0);
                    float aw = sprite2.getFrameU(8.0);
                    float ax = sprite2.getFrameV((1.0F - afx) * 16.0F * 0.5F);
                    float ay = sprite2.getFrameV((1.0F - aax) * 16.0F * 0.5F);
                    float az = sprite2.getFrameV(8.0);
                    float shaderSide = direction.getAxis() == Direction.Axis.Z ? l : m;
                    float r = shaderDown * shaderSide * colorR;
                    float g = shaderDown * shaderSide * colorG;
                    float b = shaderDown * shaderSide * colorB;
                    this.vertex(vertexConsumer, ar, e + afx, at, r, g, b, av, ax, aq);
                    this.vertex(vertexConsumer, as, e + aax, au, r, g, b, aw, ay, aq);
                    this.vertex(vertexConsumer, as, e + y, au, r, g, b, aw, az, aq);
                    this.vertex(vertexConsumer, ar, e + y, at, r, g, b, av, az, aq);
                    if (sprite2.getAtlasId() != this.overlayTexture) {
                        this.vertex(vertexConsumer, ar, e + y, at, r, g, b, av, az, aq);
                        this.vertex(vertexConsumer, as, e + y, au, r, g, b, aw, az, aq);
                        this.vertex(vertexConsumer, as, e + aax, au, r, g, b, aw, ay, aq);
                        this.vertex(vertexConsumer, ar, e + afx, at, r, g, b, av, ax, aq);
                    }
                }
            }
        }
    }

    private float calculateFluidHeight(BlockRenderView world, Fluid fluid, float originHeight, float northSouthHeight, float eastWestHeight, BlockPos pos) {
        if (!(eastWestHeight >= 1.0F) && !(northSouthHeight >= 1.0F)) {
            float[] fs = new float[2];
            if (eastWestHeight > 0.0F || northSouthHeight > 0.0F) {
                float f = this.getFluidHeight(world, fluid, pos);
                if (f >= 1.0F) {
                    return 1.0F;
                }

                this.addHeight(fs, f);
            }

            this.addHeight(fs, originHeight);
            this.addHeight(fs, eastWestHeight);
            this.addHeight(fs, northSouthHeight);
            return fs[0] / fs[1];
        } else {
            return 1.0F;
        }
    }

    private void addHeight(float[] weightedAverageHeight, float height) {
        if (height >= 0.8F) {
            weightedAverageHeight[0] += height * 10.0F;
            weightedAverageHeight[1] += 10.0F;
        } else if (height >= 0.0F) {
            weightedAverageHeight[0] += height;
            weightedAverageHeight[1]++;
        }
    }

    private float getFluidHeight(BlockRenderView world, Fluid fluid, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return this.getFluidHeight(world, fluid, pos, blockState, blockState.getFluidState());
    }

    private float getFluidHeight(BlockRenderView world, Fluid fluid, BlockPos pos, BlockState blockState, FluidState fluidState) {
        if (fluid.matchesType(fluidState.getFluid())) {
            BlockState blockState2 = world.getBlockState(pos.up());
            return fluid.matchesType(blockState2.getFluidState().getFluid()) ? 1.0F : fluidState.getHeight();
        } else {
            return !blockState.isSolid() ? 0.0F : -1.0F;
        }
    }

    private void vertex(VertexConsumer vertexConsumer, double x, double y, double z, float red, float green, float blue, float u, float v, int light) {
        vertexConsumer.vertex(x, y, z).color(red, green, blue, 1.0F).texture(u, v).light(light).normal(0.0F, 1.0F, 0.0F).next();
    }

    private int getLight(BlockRenderView world, BlockPos pos) {
        int i = WorldRenderer.getLightmapCoordinates(world, pos);
        int j = WorldRenderer.getLightmapCoordinates(world, pos.up());
        int k = i & (LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE | 15);
        int l = j & (LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE | 15);
        int m = i >> 16 & (LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE | 15);
        int n = j >> 16 & (LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE | 15);
        return (Math.max(k, l) | Math.max(m, n)) << 16;
    }

    private static boolean isSameFluid(FluidState a, FluidState b) {
        return b.getFluid().matchesType(a.getFluid());
    }

    private static boolean isSideCovered(BlockView world, Direction direction, float height, BlockPos pos, BlockState state) {
        if (state.isOpaque()) {
            VoxelShape voxelShape = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, height, 1.0);
            VoxelShape voxelShape2 = state.getCullingShape(world, pos);
            return VoxelShapes.isSideCovered(voxelShape, voxelShape2, direction);
        } else {
            return false;
        }
    }

    private static boolean isSideCovered(BlockView world, BlockPos pos, Direction direction, float maxDeviation, BlockState state) {
        return isSideCovered(world, direction, maxDeviation, pos.offset(direction), state);
    }

    private static boolean isOppositeSideCovered(BlockView world, BlockPos pos, BlockState state, Direction direction) {
        return isSideCovered(world, direction.getOpposite(), 1.0F, pos, state);
    }

    public static boolean shouldRenderSide(
            BlockRenderView world, BlockPos pos, FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState
    ) {
        return !isOppositeSideCovered(world, pos, blockState, direction) && !isSameFluid(fluidState, neighborFluidState);
    }
}
