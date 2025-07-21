package rc55.mc.zerocraft.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScarletCrystalItem extends Item {
    public ScarletCrystalItem() {
        super(new Settings().rarity(Rarity.RARE).fireproof());
    }

    //物品lore
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(this.getTranslationKey()+".desc").formatted(Formatting.GRAY));
    }
}
