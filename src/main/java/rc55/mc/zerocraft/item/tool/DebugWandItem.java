package rc55.mc.zerocraft.item.tool;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
}
