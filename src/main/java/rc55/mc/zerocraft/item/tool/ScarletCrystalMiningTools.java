package rc55.mc.zerocraft.item.tool;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;

import java.util.List;
import java.util.Objects;

public class ScarletCrystalMiningTools {
    //设置模式
    private static String setMode(ItemStack stack){
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        NbtList enchantments = stack.getEnchantments();
        byte mode = nbtCompound.getByte("mode");//当前模式 幸运1 精准2
        //移除现有附魔
        for(int i = 0; i < stack.getEnchantments().size(); ++i) {
            NbtCompound enchantmentEntry = enchantments.getCompound(i);
            String id = enchantmentEntry.getString("id");
            if (Objects.equals(id, "minecraft:silk_touch") || Objects.equals(id, "minecraft:fortune")){
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
        tooltip.add(Text.translatable(universalTranslationKey+".mode.hint", new Object[]{Text.translatable(ZeroCraftKeyBinds.TOOL_MODE_SWITCH_KEY.getBoundKeyTranslationKey())}).formatted(Formatting.GRAY));

        byte mode = stack.getOrCreateNbt().getByte("mode");
        switch (mode){
            case 1:
                tooltip.add(Text.translatable(universalTranslationKey+".mode.current", new Object[]{Text.translatable(Enchantments.FORTUNE.getTranslationKey())}));
                break;
            case 2:
                tooltip.add(Text.translatable(universalTranslationKey+".mode.current", new Object[]{Text.translatable(Enchantments.SILK_TOUCH.getTranslationKey())}));
                break;
            case 0://没有模式
                break;
            default://无效模式
                tooltip.add(Text.translatable(universalTranslationKey+".mode.current", new Object[]{Text.translatable(universalTranslationKey+".mode.unknown")}));
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
                            sendMessage(serverPlayer, Text.translatable(universalTranslationKey+".mode.switch", new Object[]{Text.translatable(setMode(serverStack))}));
                        }
                    }
                });
            });
        }
    }
    //挖掘后掉落物品
    public static void postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient){
            NbtCompound nbtCompound = stack.getOrCreateNbt();
            byte mode = nbtCompound.getByte("mode");//当前模式 幸运1 精准2
            LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld) world)
                    .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
                    .add(LootContextParameters.BLOCK_STATE, state)
                    .add(LootContextParameters.THIS_ENTITY, miner);
            ItemStack tool = stack.getItem().getDefaultStack();
            //模式
            switch (mode){
                case 1:
                    tool.addEnchantment(Enchantments.FORTUNE, 3);
                    builder.add(LootContextParameters.TOOL, tool);
                    break;
                case 2:
                    tool.addEnchantment(Enchantments.SILK_TOUCH, 1);
                    builder.add(LootContextParameters.TOOL, tool);
                    break;
            }
            //掉落
            for (ItemStack drop : state.getDroppedStacks(builder)){
                miner.dropStack(drop);
            }
        }
    }
    //通用翻译键
    private static final String universalTranslationKey = "item.zerocraft.scarlet_crystal_mining_tools";
    //发送消息
    //仅服务端
    private static void sendMessage(PlayerEntity player, Text message) {
        ((ServerPlayerEntity)player).sendMessageToClient(message, true);
    }
}
