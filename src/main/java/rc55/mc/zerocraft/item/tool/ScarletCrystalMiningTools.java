package rc55.mc.zerocraft.item.tool;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OperatorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import rc55.mc.zerocraft.api.Utils;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;

import java.util.List;
import java.util.Objects;

public class ScarletCrystalMiningTools {

    //通用翻译键
    public static final String toolsTranslationKey = "item.zerocraft.scarlet_crystal_mining_tools";

    //设置模式
    private static String setMode(ItemStack stack){
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        NbtList enchantments = stack.getEnchantments();
        byte mode = nbtCompound.getByte("mode");//当前模式 幸运1 精准2
        //移除现有附魔
        for(int i = 0; i < stack.getEnchantments().size(); ++i) {
            NbtCompound enchantmentEntry = enchantments.getCompound(i);
            String id = enchantmentEntry.getString("id");
            if (Objects.equals(Registries.ENCHANTMENT.get(new Identifier(id)), Enchantments.SILK_TOUCH) || Objects.equals(Registries.ENCHANTMENT.get(new Identifier(id)), Enchantments.FORTUNE)){
                enchantments.remove(enchantmentEntry);
            }
        }
        return switch (mode) {
            case 1 -> {
                nbtCompound.putByte("mode", (byte) 2);
                yield Enchantments.SILK_TOUCH.getTranslationKey();
            }
            case 2 -> {
                nbtCompound.putByte("mode", (byte) 1);
                yield Enchantments.FORTUNE.getTranslationKey();
            }
            default -> {
                //重置无效状态
                nbtCompound.putByte("mode", (byte) 1);
                yield Enchantments.FORTUNE.getTranslationKey();
            }
        };
    }
    //物品lore
    public static void appendTooltip(ItemStack stack, List<Text> tooltip) {
        tooltip.add(Text.translatable(toolsTranslationKey +".mode.hint", ZeroCraftKeyBinds.TOOL_MODE_SWITCH_KEY.getBoundKeyLocalizedText()).formatted(Formatting.GRAY));

        byte mode = stack.getOrCreateNbt().getByte("mode");
        switch (mode){
            case 1:
                tooltip.add(Text.translatable(toolsTranslationKey +".mode.current", Text.translatable(Enchantments.FORTUNE.getTranslationKey())));
                break;
            case 2:
                tooltip.add(Text.translatable(toolsTranslationKey +".mode.current", Text.translatable(Enchantments.SILK_TOUCH.getTranslationKey())));
                break;
            case 0://没有模式
                break;
            default://无效模式
                tooltip.add(Text.translatable(toolsTranslationKey +".mode.current", Text.translatable(toolsTranslationKey +".mode.unknown")));
        }
    }
    //每刻执行
    //检测是否按下按键
    public static void inventoryTick(Item item, World world, Entity entity, Identifier packId) {
        //仅服务端
        if (!world.isClient){
            //服务端接收包后处理
            //收到包说明按下按键，执行动作
            ServerPlayNetworking.registerGlobalReceiver(packId, (server, serverPlayer, handler, buf, responseSender) -> {
                server.execute(() -> {
                    //是否手持
                    if (serverPlayer.isHolding(item)){
                        ItemStack serverStack = serverPlayer.getStackInHand(serverPlayer.getActiveHand());
                        //是否在主手
                        if (serverStack.isOf(item)){
                            //模式切换
                            Utils.sendMessage(serverPlayer, Text.translatable(toolsTranslationKey +".mode.switch", Text.translatable(setMode(serverStack))));
                        }
                    }
                });
            });
        }
    }
    //挖掘+更新状态
    public static boolean updateMine(World world, BlockPos pos, BlockState state, PlayerEntity miner) {
        if (world.isClient) {
            Block block = state.getBlock();
            if (block instanceof OperatorBlock && !miner.isCreativeLevelTwoOp()) {
                return false;
            } else if (state.isAir()) {
                return false;
            } else {
                block.onBreak(world, pos, state, miner);
                FluidState fluidState = world.getFluidState(pos);
                boolean bl = world.setBlockState(pos, fluidState.getBlockState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                if (bl) {
                    block.onBroken(world, pos, state);
                }

                return bl;
            }
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            Block block = state.getBlock();
            if (block instanceof OperatorBlock && !miner.isCreativeLevelTwoOp()) {
                world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                return false;
            } else if (miner.isBlockBreakingRestricted(world, pos, miner.isSpectator() ? GameMode.SPECTATOR : GameMode.CREATIVE)) {
                return false;
            } else {
                block.onBreak(world, pos, state, miner);
                boolean bl = world.removeBlock(pos, false);
                if (bl) {
                    block.onBroken(world, pos, state);
                }

                if (miner.isCreative()) {
                    return true;
                } else {
                    ItemStack itemStack = miner.getMainHandStack();
                    ItemStack tool = itemStack.copy();
                    NbtCompound nbtCompound = tool.getOrCreateNbt();
                    byte mode = nbtCompound.getByte("mode");//当前模式 幸运1 精准2
                    //模式
                    switch (mode){
                        case 1:
                        default:
                            tool.addEnchantment(Enchantments.FORTUNE, 3);
                            break;
                        case 2:
                            tool.addEnchantment(Enchantments.SILK_TOUCH, 1);
                            break;
                    }
                    boolean bl2 = miner.canHarvest(state);
                    itemStack.postMine(world, state, pos, miner);
                    if (bl && bl2) {
                        block.afterBreak(world, miner, pos, state, blockEntity, tool);//挖掘后掉落物品
                    }
                    return true;
                }
            }
        }
    }
}
