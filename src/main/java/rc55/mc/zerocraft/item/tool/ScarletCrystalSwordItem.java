package rc55.mc.zerocraft.item.tool;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.api.Utils;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;

import java.util.List;

public class ScarletCrystalSwordItem extends SwordItem implements Vanishable {
    public ScarletCrystalSwordItem() {
        super(ZeroCraftToolMaterials.SCARLET_CRYSTAL, 3, -1.8f, new Item.Settings().fireproof().rarity(Rarity.RARE));
    }

    //模式本地化键
    private final String FLAME_MODE_TRANS_KEY = "item.zerocraft.scarlet_crystal_sword.mode.flame";
    private final String FROST_MODE_TRANS_KEY = "item.zerocraft.scarlet_crystal_sword.mode.frost";
    //发包用
    public static final Identifier PACK_ID = new Identifier(ZeroCraft.MODID, "tool_mode_switch_sword");
    //设置模式
    private String setMode(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        byte mode = nbtCompound.getByte("mode");//模式 1火焰 2冰冻
        if (mode == 1){
            nbtCompound.putByte("mode", (byte) 2);
            return FLAME_MODE_TRANS_KEY;
        } else {
            nbtCompound.putByte("mode", (byte) 1);
            return FROST_MODE_TRANS_KEY;
        }
    }
    //打人
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        byte mode = stack.getOrCreateNbt().getByte("mode");
        if (attacker instanceof PlayerEntity){
            switch (mode){
                case 1:
                    target.setFireTicks(300);
                    break;
                case 2:
                    target.setFireTicks(0);
                    target.setFrozenTicks(300);
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 5, 0, false, false, true), attacker);
                    break;
            }
        }
        stack.damage(1, attacker, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }
    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.translatable(this.getTranslationKey()+".mode.hint", ZeroCraftKeyBinds.TOOL_MODE_SWITCH_KEY.getBoundKeyLocalizedText()).formatted(Formatting.GRAY));

        byte mode = stack.getOrCreateNbt().getByte("mode");
        switch (mode){
            case 1:
                tooltip.add(Text.translatable(this.getTranslationKey()+".mode.current", Text.translatable(FROST_MODE_TRANS_KEY)));
                break;
            case 2:
                tooltip.add(Text.translatable(this.getTranslationKey()+".mode.current", Text.translatable(FLAME_MODE_TRANS_KEY)));
                break;
        }
    }
    //每刻执行
    //检测是否按下按键
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        //仅服务端
        if (!world.isClient){
            //服务端接收包后处理
            //收到包说明按下按键，执行动作
            ServerPlayNetworking.registerGlobalReceiver(PACK_ID, (server, serverPlayer, handler, buf, responseSender) -> {
                server.execute(() -> {
                    //是否手持
                    if (serverPlayer.isHolding(this)){
                        ItemStack serverStack = serverPlayer.getStackInHand(serverPlayer.getActiveHand());
                        //是否在主手
                        if (serverStack.isOf(this)){
                            //模式切换
                            Utils.sendMessage(serverPlayer, Text.translatable(this.getTranslationKey()+".mode.switch", Text.translatable(setMode(serverStack))));
                        }
                    }
                });
            });
        }
    }
    //格挡
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    //副手有盾牌就不格挡
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (user.getStackInHand(Hand.OFF_HAND).isOf(Items.SHIELD)){
            return TypedActionResult.fail(itemStack);
        }
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }
}
