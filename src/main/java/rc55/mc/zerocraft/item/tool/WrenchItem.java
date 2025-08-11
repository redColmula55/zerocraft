package rc55.mc.zerocraft.item.tool;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.api.BlockSidePos;
import rc55.mc.zerocraft.api.Utils;
import rc55.mc.zerocraft.block.ZeroCraftBlockTags;
import rc55.mc.zerocraft.sound.ZeroCraftSounds;

import java.util.List;

public class WrenchItem extends ToolItem {

    public static final String HINT_TRANS_KEY = "item." + ZeroCraft.MODID + ".wrench.hint";//lore
    public static final String INVALID_TRANS_KEY = "item." + ZeroCraft.MODID + ".wrench.invalid";//无法调整
    public static final String EMPTY_TRANS_KEY = "item." + ZeroCraft.MODID + ".wrench.empty";//无可调整属性
    public static final String UPDATE_TRANS_KEY = "item." + ZeroCraft.MODID + ".wrench.update";//调整

    public WrenchItem(ToolMaterial material, Settings settings) {
        super(material, settings.maxDamageIfAbsent(material.getDurability()));
    }

    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(HINT_TRANS_KEY).formatted(Formatting.GRAY));
    }

    //调整属性（右键效果）
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player != null && player.isSneaking()) {//仅按住shift时生效
            World world = context.getWorld();
            BlockPos pos = context.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (!state.isIn(ZeroCraftBlockTags.WRENCH_ADJUSTABLE)) {//无法调整
                Utils.sendMessage(player, Text.translatable(INVALID_TRANS_KEY));
                return ActionResult.PASS;
            }
            ItemStack stack = context.getStack();
            BlockSidePos sidePos = new BlockSidePos(context);
            Property<?> updatedProperty;
            BlockState newState;
            if (state.getProperties().contains(Properties.FACING)) {//一般带朝向方块
                updatedProperty = Properties.FACING;
                newState = state.with(Properties.FACING, sidePos.getSimpleRotatedDirection(state.get(Properties.FACING)));
            } else if (state.getProperties().contains(Properties.HOPPER_FACING)) {//漏斗
                updatedProperty = Properties.HOPPER_FACING;
                Direction newDirection = sidePos.getSimpleRotatedDirection(state.get(Properties.HOPPER_FACING));
                if (newDirection == Direction.UP) newDirection = Direction.DOWN;
                newState = state.with(Properties.HOPPER_FACING, newDirection);
            } else {//无可调整属性
                Utils.sendMessage(player, Text.translatable(EMPTY_TRANS_KEY));
                return ActionResult.PASS;
            }
            if (state.equals(newState)) return ActionResult.PASS;//调整后与原方块相同，不更新
            world.setBlockState(pos, newState, Block.NOTIFY_NEIGHBORS | Block.REDRAW_ON_MAIN_THREAD);
            stack.damage(1, player, e -> e.sendToolBreakStatus(context.getHand()));
            Utils.sendMessage(player, this.getMessage(newState, updatedProperty));
            player.playSound(ZeroCraftSounds.WRENCH_USE, SoundCategory.PLAYERS, 1.0f, world.random.nextFloat() + 0.5f);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(2, attacker, e -> e.sendToolBreakStatus(attacker.getActiveHand()));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0F) {
            stack.damage(1, miner, e -> e.sendToolBreakStatus(miner.getActiveHand()));
        }
        return true;
    }

    //消息
    private <T extends Comparable<T>> Text getMessage(BlockState state, Property<T> property) {
        return Text.translatable(UPDATE_TRANS_KEY, property.getName(), property.name(state.get(property)));
    }
}
