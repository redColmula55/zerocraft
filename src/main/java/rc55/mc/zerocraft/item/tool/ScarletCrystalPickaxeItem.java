package rc55.mc.zerocraft.item.tool;

import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.ZeroCraft;

import java.util.List;

public class ScarletCrystalPickaxeItem extends PickaxeItem {
    public ScarletCrystalPickaxeItem() {
        super(ZeroCraftToolMaterials.SCARLET_CRYSTAL, 2, -2.8f, new Item.Settings().fireproof().rarity(Rarity.RARE));
    }
    //发包用
    public static final Identifier PACK_ID = new Identifier(ZeroCraft.MODID, "tool_mode_switch_pickaxe");
    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        ScarletCrystalMiningTools.appendTooltip(stack, tooltip);
    }
    //每刻执行
    //检测是否按下按键
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        //调用检测&切换
        ScarletCrystalMiningTools.inventoryTick(this, world, entity, PACK_ID);
    }
    //掉落物品
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        ScarletCrystalMiningTools.updateMine(world, pos, state, miner);
        return miner.isCreative();
    }
}
