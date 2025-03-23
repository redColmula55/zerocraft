package rc55.mc.zerocraft.item.tool;

import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.ZeroCraft;

import java.util.List;

public class ScarletCrystalAxeItem extends AxeItem {
    public ScarletCrystalAxeItem() {
        super(ZeroCraftToolMaterials.SCARLET_CRYSTAL, 6.0f, -3.0f, new Item.Settings().fireproof().rarity(Rarity.RARE));
    }
    //发包用
    public static final Identifier PACK_ID = new Identifier(ZeroCraft.MODID, "tool_mode_switch_axe");
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
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        ScarletCrystalMiningTools.postMine(stack, world, state, pos, miner);
        return super.postMine(stack, world, state, pos, miner);
    }
    //防止重复掉落
    @Override
    public boolean isSuitableFor(BlockState state) {
        return false;
    }
}
