package rc55.mc.zerocraft.item.tool;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.api.Utils;
import rc55.mc.zerocraft.api.inventory.ItemInventory;
import rc55.mc.zerocraft.screen.Generic1x1ContainerScreenHandler;

import java.util.List;

public class OreFinderItem extends Item {

    public static final String NO_TARGET_TRANS_KEY = "item.zerocraft.ore_finder.no_target";
    public static final String DAMAGE_TRANS_KEY = "item.zerocraft.ore_finder.damage";
    public static final String FOUND_ORE_TRANS_KEY = "item.zerocraft.ore_finder.found";
    public static final String NOT_FOUND_TRANS_KEY = "item.zerocraft.ore_finder.not_found";
    public static final String HINT_TRANS_KEY = "item.zerocraft.ore_finder.hint";

    public OreFinderItem() {
        super(new Settings().maxDamage(55).rarity(Rarity.EPIC));
    }

    //附魔光效
    @Override
    public boolean hasGlint(ItemStack stack) {
        return this.getTargetBlock(stack) != null;
    }
    //禁止附魔
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        Hand hand = context.getHand();
        if (player != null) {
            if (player.isSneaking()) {
                return this.openSettingsScreen(world, player, hand, stack);
            } else {
                return this.search(world, context.getBlockPos(), player, hand, stack);
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(HINT_TRANS_KEY).formatted(Formatting.GRAY));
    }

    private ActionResult search(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        Block target = this.getTargetBlock(stack);
        if (target == null) {
            Utils.sendMessage(player, Text.translatable(NO_TARGET_TRANS_KEY));
            return ActionResult.PASS;
        } else if (stack.getDamage() + 1 >= stack.getMaxDamage() && !player.isCreative()) {
            Utils.sendMessage(player, Text.translatable(DAMAGE_TRANS_KEY));
            return ActionResult.PASS;
        }
        player.getItemCooldownManager().set(this, 100);
        final int range = 55;
        BlockPos blockPos = null;
        boolean shouldBreak = false;
        for (int x = pos.getX()-range; x < pos.getX()+range; x++) {
            for (int z = pos.getZ()-range; z < pos.getZ()+range; z++) {
                for (int y = pos.getY(); y >= world.getBottomY(); y--) {
                    blockPos = new BlockPos(x, y, z);
                    if (world.getBlockState(blockPos).isOf(target)) {
                        shouldBreak = true;
                        break;
                    }
                }
                if (shouldBreak) break;
            }
            if (shouldBreak) break;
        }
        stack.damage(1, player, e -> e.sendToolBreakStatus(hand));
        if (shouldBreak) {
            if (!world.isClient) player.sendMessage(Text.translatable(FOUND_ORE_TRANS_KEY, blockPos.toShortString(), Text.translatable(target.getTranslationKey())));
            world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS);
            return ActionResult.success(world.isClient);
        } else {
            if (!world.isClient) player.sendMessage(Text.translatable(NOT_FOUND_TRANS_KEY, Text.translatable(target.getTranslationKey())));
            return ActionResult.PASS;
        }
    }

    private ActionResult openSettingsScreen(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        if (!world.isClient) {
            if (stack.isOf(this)) {
                Inventory inventory = new ItemInventory(1, stack);
                NamedScreenHandlerFactory factory = new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player1) ->
                        new Generic1x1ContainerScreenHandler(syncId, playerInventory, inventory, player2 -> player2.getStackInHand(hand).isOf(OreFinderItem.this), stack1 -> stack1.isIn(ConventionalItemTags.ORES)), stack.getName());
                player.openHandledScreen(factory);
            }
        }
        return ActionResult.success(world.isClient);
    }

    @Nullable
    private Block getTargetBlock(ItemStack stack) {
        DefaultedList<ItemStack> list = DefaultedList.ofSize(1, ItemStack.EMPTY);
        Inventories.readNbt(stack.getOrCreateNbt(), list);
        if (list.get(0).getItem() instanceof BlockItem blockItem) {
            return list.get(0).isIn(ConventionalItemTags.ORES) ? blockItem.getBlock() : null;
        }
        return null;
    }
}
