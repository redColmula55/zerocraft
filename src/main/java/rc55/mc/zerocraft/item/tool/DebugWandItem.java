package rc55.mc.zerocraft.item.tool;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rc55.mc.zerocraft.api.inventory.ItemInventory;
import rc55.mc.zerocraft.screen.Generic1x1ContainerScreenHandler;

public class DebugWandItem extends Item {
    public DebugWandItem() {
        super(new Settings().rarity(Rarity.EPIC).fireproof().maxCount(1));
    }
    @Override
    public boolean hasGlint(ItemStack stack){
        return true;
    }
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (!world.isClient) {
            if (state.hasBlockEntity()) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity != null) {
                    NbtCompound nbt = blockEntity.createNbt();
                    if (miner != null) {
                        miner.sendMessage(Text.of(nbt.toString()));
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient) {
            if (attacker instanceof PlayerEntity player) {
                player.sendMessage(Text.of(target.writeNbt(new NbtCompound()).toString()));
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getWorld().isClient) {
            user.sendMessage(Text.of(entity.writeNbt(new NbtCompound()).toString()));
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return openSettingsScreen(context.getWorld(), context.getPlayer(), context.getStack());
    }

    private ActionResult openSettingsScreen(World world, PlayerEntity player, ItemStack stack) {
        if (!world.isClient) {
            if (stack.isOf(this)) {
                Inventory inventory = new SettingsInventory(1, stack);
                NamedScreenHandlerFactory factory = new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player1) ->
                        new Generic1x1ContainerScreenHandler(syncId, playerInventory, inventory), Text.of("放入物品以查看它的nbt数据"));
                player.openHandledScreen(factory);
            }
        }
        return ActionResult.success(world.isClient);
    }

    private static class SettingsInventory extends ItemInventory {

        public SettingsInventory(int size, ItemStack stack) {
            super(size, stack);
        }

        @Override
        public void onClose(PlayerEntity player) {
            player.sendMessage(Text.of(this.getStack(0).getOrCreateNbt().toString()));
            player.giveItemStack(this.getStack(0));
            this.setStack(0, ItemStack.EMPTY);
            super.onClose(player);
        }
    }
}
