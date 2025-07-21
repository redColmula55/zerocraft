package rc55.mc.zerocraft.block.fluid;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import rc55.mc.zerocraft.api.Utils;
import rc55.mc.zerocraft.entity.effect.ZeroCraftStatusEffects;
import rc55.mc.zerocraft.fluid.ZeroCraftFluidTags;
import rc55.mc.zerocraft.fluid.ZeroCraftFluids;
import rc55.mc.zerocraft.world.ZeroCraftGameRules;
import rc55.mc.zerocraft.item.armor.ZeroCraftArmorMaterials;

public class ScarletWaterFluidBlock extends FluidBlock {
    public ScarletWaterFluidBlock() {
        super(ZeroCraftFluids.SCARLET_WATER, Settings.create()
                .mapColor(MapColor.RED)
                .replaceable()
                .noCollision()
                .ticksRandomly()
                .strength(100.0F)
                .luminance(state -> 2)
                .pistonBehavior(PistonBehavior.DESTROY)
                .dropsNothing()
                .liquid()
                .sounds(BlockSoundGroup.INTENTIONALLY_EMPTY));
    }
    //放置后
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (this.receiveNeighborFluids(world, pos, state)) {
            world.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
    }
    //更新
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        if (this.receiveNeighborFluids(world, pos, state)) {
            world.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
    }
    //寻路
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
    //侵蚀无全套赤晶装备的生物
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            if (!ZeroCraftArmorMaterials.hasFullSet(livingEntity, ZeroCraftArmorMaterials.SCARLET_CRYSTAL) && livingEntity.isTouchingWater() && world.getGameRules().getBoolean(ZeroCraftGameRules.SCARLET_WATER_DAMAGE)) {
                livingEntity.addStatusEffect(new StatusEffectInstance(ZeroCraftStatusEffects.SCARLET_INFESTED, 21, 0, true, true));
            }
        }
        super.onEntityCollision(state, world, pos, entity);
    }
    //接触其他流体时
    private boolean receiveNeighborFluids(World world, BlockPos pos, BlockState state) {
        if (this.fluid.isIn(ZeroCraftFluidTags.SCARLET_WATER)) {
            for (Direction direction : FLOW_DIRECTIONS) {
                BlockPos newPos = pos.offset(direction.getOpposite());
                int chance = world.getGameRules().getInt(ZeroCraftGameRules.SCARLET_WATER_INFEST_CHANCE);
                //遇水污染
                if (world.getFluidState(newPos).isIn(ZeroCraftFluidTags.SCARLET_WATER_POLLUTIABLE) && chance != 0) {
                    if (Utils.getRandomPercent(world.getRandom(), chance)) {//感染概率
                        if (world.getBlockState(newPos).getBlock() instanceof FluidBlock) {//排除含水方块
                            BlockState newState = ZeroCraftFluids.SCARLET_WATER.getDefaultState().getBlockState();//静态水
                            if (!world.getFluidState(newPos).isStill()) {//流动水
                                newState = ZeroCraftFluids.FLOWING_SCARLET_WATER.getDefaultState().getBlockState();
                                for (Property<?> property : world.getFluidState(newPos).getBlockState().getProperties()) {
                                    newState = Utils.withProperty(newState, property, Utils.getPropertyValue(state, property));//设置水流动属性
                                }
                            }
                            world.setBlockState(newPos, newState, 18);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
