package rc55.mc.zerocraft.item.tool;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rc55.mc.zerocraft.ZeroCraft;

import java.util.List;

public class ScarletCrystalShovelItem extends ShovelItem {
    public ScarletCrystalShovelItem() {
        super(ZeroCraftToolMaterials.SCARLET_CRYSTAL, 1.5f, -3.0f, new Item.Settings().fireproof().rarity(Rarity.RARE));
    }
    //发包用
    public static final Identifier PACK_ID = new Identifier(ZeroCraft.MODID, "tool_mode_switch_shovel");
    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
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
}
