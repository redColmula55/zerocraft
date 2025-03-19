package rc55.mc.zerocraft.item;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class ScarletCrystalItem extends Item {
    public ScarletCrystalItem() {
        super(new Settings().rarity(Rarity.RARE).fireproof());
    }
}
