package rc55.mc.zerocraft.item.tool;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.block.ZeroCraftBlockTags;
import rc55.mc.zerocraft.sound.ZeroCraftSounds;

import java.util.Collection;
import java.util.List;

public class WrenchItem extends Item {
    public WrenchItem() {
        super(new Settings().maxDamage(55).fireproof().rarity(Rarity.EPIC));
    }
    //附魔光效
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack){ return false; }

    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        tooltip.add(Text.translatable(this.getTranslationKey()+".hint").formatted(Formatting.GRAY));
    }
    //生存左键选择（伪）
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return 999.0f;
    }
    //选择属性（左键效果）
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (!world.isClient) {
            this.use(miner, state, world, pos, false, miner.getStackInHand(Hand.MAIN_HAND));
        }
        return false;
    }
    //调整属性（右键效果）
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        if (!world.isClient && playerEntity != null) {
            BlockPos blockPos = context.getBlockPos();
            //ItemStack stack = playerEntity.getActiveItem();
            ItemStack stack = playerEntity.getMainHandStack();
            if (!this.use(playerEntity, world.getBlockState(blockPos), world, blockPos, true, context.getStack())) {
                return ActionResult.FAIL;
            }
        }
        if (playerEntity != null && world.isClient){
            playerEntity.playSound(ZeroCraftSounds.WRENCH_USE, 1.0f, 1.0f);//音效
        }
        return ActionResult.success(world.isClient);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user){
        if (user instanceof PlayerEntity && !((PlayerEntity) user).isCreative()) {
            stack.damage(1, user, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return stack;
    }

    private boolean use(PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos, boolean update, ItemStack stack) {
        if (!state.isIn(ZeroCraftBlockTags.WRENCH_ADJUSTABLE)) {
            //用不了
            sendMessage(player, getMessage(state, ".invalid"));
            return false;
        } else if (stack.getDamage()+1 >= stack.getMaxDamage() && !player.isCreative()){
            //工具损坏且非创造
            sendMessage(player, getMessage(state, ".damaged"));
            return false;
        } else {
            Block block = state.getBlock();//选择的方块
            StateManager<Block, BlockState> stateManager = block.getStateManager();
            Collection<Property<?>> collection = stateManager.getProperties();
            String blockId = Registries.BLOCK.getId(block).toString();//选择的方块id
            if (collection.isEmpty()){
                //没有属性
                sendMessage(player, getMessage(state, ".empty"));
                return false;
            } else {
                NbtCompound nbtCompound = stack.getOrCreateSubNbt("DebugProperty");//nbt储存选择的属性
                Property<?> property = stateManager.getProperty(nbtCompound.getString(blockId));//选择的属性
                if (update){
                    //设置属性
                    if (property == null) {
                        property = (Property)collection.iterator().next();//未选择时直接设置需要初始值
                    }
                    BlockState newState = cycle(state, property, player.shouldCancelInteraction());//新的状态
                    world.setBlockState(pos, newState, 18);//设置属性
                    sendMessage(player, getMessage(newState, property,".update"));

                    //不是创造就消耗耐久
                    if (!player.isCreative()){
                        stack.damage(1, player, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                    }
                } else {
                    //选择属性
                    property = (Property)cycle((Iterable)collection, property, player.shouldCancelInteraction());
                    nbtCompound.putString(blockId, property.getName());//设置选择
                    sendMessage(player, getMessage(state,property,".select"));
                }
            }
            return true;
        }
    }

    private static void sendMessage(PlayerEntity player, Text message) {
        ((ServerPlayerEntity)player).sendMessageToClient(message, true);
    }

    private static <T extends Comparable<T>> BlockState cycle(BlockState state, Property<T> property, boolean inverse) {
        return (BlockState)state.with(property, cycle(property.getValues(), state.get(property), inverse));
    }

    private static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) {
        return (T)(inverse ? Util.previous(elements, current) : Util.next(elements, current));
    }

    private Text getMessage(BlockState state, Property property, String type){
        return Text.translatable(this.getTranslationKey()+type, new Object[]{property.getName(), property.name(state.get(property))});
    }
    private Text getMessage(BlockState state, String type){
        return Text.translatable(this.getTranslationKey()+type, new Object[]{Registries.BLOCK.getId(state.getBlock()).toString()});
    }

}
