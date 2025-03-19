package rc55.mc.zerocraft.item.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import rc55.mc.zerocraft.ZeroCraft;

public class ScarletCrystalBootsItem extends ArmorItem {
    public ScarletCrystalBootsItem() {
        super(ZeroCraftArmorMaterials.SCARLET_CRYSTAL, Type.BOOTS, new Settings().rarity(Rarity.RARE).fireproof());
    }

    public static final Identifier PACK_ID = new Identifier(ZeroCraft.MODID, "key_fly_boots");

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        //仅玩家触发效果
        if (!world.isClient && entity instanceof PlayerEntity player){
            //判断是否穿上
            if (player.getInventory().getArmorStack(0).isOf(this)){
                player.isInvulnerableTo(entity.getDamageSources().fall());
                player.removeStatusEffect(StatusEffects.SLOWNESS);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 221, 1, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 221, 1, false, false, false));
            }
        }
    }
}
