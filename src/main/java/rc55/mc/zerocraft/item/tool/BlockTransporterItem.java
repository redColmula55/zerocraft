package rc55.mc.zerocraft.item.tool;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class BlockTransporterItem extends Item {
    public BlockTransporterItem() {
        super(new Settings().fireproof().rarity(Rarity.EPIC).maxDamage(55));
    }
    //右键效果
    @Override
    public ActionResult useOnBlock(ItemUsageContext context){
        World world =context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();
        NbtCompound nbt = stack.getOrCreateSubNbt("CarriedBlock");
        if (!world.isClient){
            if (this.isCarryingBlock(stack)){
                //放置
                if (!this.place(new ItemPlacementContext(context), state, stack, nbt)){
                    //sendMessage(player, Text.of("fail!"));//调试信息
                    //放置失败
                    return ActionResult.FAIL;
                }
            } else {
                Block block = state.getBlock();//选择的方块
                //是否损坏
                if (this.isUseable(player, stack)){
                    //损坏
                    sendMessage(player, Text.translatable(this.getTranslationKey()+".damaged"));
                    return ActionResult.FAIL;
                } else if (state.isIn(BlockTags.WITHER_IMMUNE) && !(state.isIn(BlockTags.PORTALS) || state.isOf(Blocks.REINFORCED_DEEPSLATE))) {
                    //不可用
                    sendMessage(player, Text.translatable(this.getTranslationKey()+".invalid"));
                } else {
                    //选择方块
                    StateManager<Block, BlockState> stateManager = block.getStateManager();
                    Collection<Property<?>> collection = stateManager.getProperties();
                    String blockId = Registries.BLOCK.getId(block).toString();//选择的方块id
                    nbt.putString("id", blockId);
                    //储存方块属性
                    if (stateManager.getProperties() != null){
                        nbt.put("State", new NbtCompound());
                        //遍历属性并设置
                        for (Property<?> property : collection){
                            nbt.getCompound("State").putString(property.getName(), this.getPropertyValue(state, property));
                            //sendMessage(player, Text.of("Property set."));//调试信息
                        }
                    }
                    //储存方块实体
                    if (state.hasBlockEntity()){
                        BlockEntity entity = world.getBlockEntity(pos);
                        if (entity != null) {
                            nbt.put("BlockEntity", entity.createNbt());//设置nbt
                            entity.markRemoved();//移除原有数据防止意外
                        }
                    }
                    //搬走方块且不更新
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 18);
                    //不是创造就消耗耐久
                    if (player != null && !player.isCreative()) {
                        stack.damage(1, player, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                    }
                }
            }
        }
        return ActionResult.success(world.isClient);
    }

    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        tooltip.add(Text.translatable(this.getTranslationKey()+".hint").formatted(Formatting.GRAY));
        if (isCarryingBlock(stack)){
            String key = getBlock(stack).getTranslationKey();
            tooltip.add(Text.translatable(this.getTranslationKey()+".hint.carrying", new Object[]{Text.translatable(key)}));
        }
    }

    //附魔光效
    //正在搬运方块则有
    @Override
    public boolean hasGlint(ItemStack stack){
        return isCarryingBlock(stack);
    }
    @Override
    public boolean isEnchantable(ItemStack stack){ return false; }
    //是否正在搬运方块
    private boolean isCarryingBlock(ItemStack stack){
        return !stack.getOrCreateSubNbt("CarriedBlock").isEmpty();
    }
    //是否损坏
    private boolean isUseable(PlayerEntity player, ItemStack stack) {
        return stack.getDamage()+1 >= stack.getMaxDamage() && !player.isCreative();
    }
    //放置方块
    private boolean place(ItemPlacementContext context, BlockState state, ItemStack stack, NbtCompound nbt){
        if (!context.canPlace()){
            return false;
        }
        if (!this.place(context, state)){
            return false;
        }
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        BlockState blockState = getBlock(stack).getDefaultState();
        if (blockState.isOf(getBlock(stack))){
            //方块状态
            if (nbt.getCompound("State") != null){
                NbtCompound nbtCompound = nbt.getCompound("State");
                StateManager<Block, BlockState> stateManager = blockState.getBlock().getStateManager();
                for (String propertyId : nbtCompound.getKeys()){
                    Property<?> property = stateManager.getProperty(propertyId);
                    if (property != null){
                        String value = nbtCompound.getString(propertyId);
                        blockState = with(blockState, property, value);
                    }
                }
            }

            if (blockState != state) {
                world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            }

            //方块实体
            if (nbt.getCompound("BlockEntity") != null){
                if (world.getServer() != null){
                    BlockEntity blockEntity =  world.getBlockEntity(pos);
                    if (blockEntity != null){
                        //非创造+管理员不可放置的（e.g.命令方块）
                        if (!world.isClient && blockEntity.copyItemDataRequiresOperator() && (player == null || !player.isCreativeLevelTwoOp())) {
                            sendMessage(player, Text.translatable(this.getTranslationKey()+".invalid"));
                            //撤销更改
                            world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                            return false;
                        }
                        NbtCompound nbtCompound2 = blockEntity.createNbt();
                        NbtCompound nbtCompound3 = nbtCompound2.copy();
                        nbtCompound2.copyFrom(nbt.getCompound("BlockEntity"));
                        if (!nbtCompound2.equals(nbtCompound3)) {
                            blockEntity.readNbt(nbtCompound2);
                            blockEntity.markDirty();
                        }
                    }
                }
            }
            blockState.getBlock().onPlaced(world, pos, blockState, player, stack);
        }
        //声音
        BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
        world.playSound(
                player,
                pos,
                this.getPlaceSound(state),
                SoundCategory.BLOCKS,
                (blockSoundGroup.getVolume() + 1.0F) / 2.0F,
                blockSoundGroup.getPitch() * 0.8F
        );
        world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(player, state));//事件
        nbt.remove("id");
        nbt.remove("State");
        nbt.remove("BlockEntity");
        return true;
    }
    private boolean place(ItemPlacementContext context, BlockState state) {
        return context.getWorld().setBlockState(context.getBlockPos(), state, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
    }
    //设置属性用
    private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
        return (BlockState)property.parse(name).map(value -> state.with(property, value)).orElse(state);
    }
    //获取方块id
    private Block getBlock(ItemStack stack){
        String id = stack.getOrCreateSubNbt("CarriedBlock").getString("id");
        return Registries.BLOCK.get(new Identifier(id));
    }
    //放置音效
    protected SoundEvent getPlaceSound(BlockState state) {
        return state.getSoundGroup().getPlaceSound();
    }
    //提供属性值
    private <T extends Comparable<T>> String getPropertyValue(BlockState state, Property<T> property){
        return property.name(state.get(property));
    }
    //发送提示信息
    private static void sendMessage(PlayerEntity player, Text message) {
        ((ServerPlayerEntity)player).sendMessageToClient(message, true);
    }
}
