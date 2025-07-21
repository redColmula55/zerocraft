package rc55.mc.zerocraft.item.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

public class ScarletCrystalBootsItem extends ArmorItem {
    public ScarletCrystalBootsItem() {
        super(ZeroCraftArmorMaterials.SCARLET_CRYSTAL, Type.BOOTS, new Settings().rarity(Rarity.RARE).fireproof());
    }

    //每刻执行
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        //仅玩家触发效果
        if (!world.isClient && entity instanceof PlayerEntity player){
            //判断是否穿上
            if (player.getInventory().getArmorStack(0).isOf(this)){
                player.removeStatusEffect(StatusEffects.SLOWNESS);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 221, 1, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 221, 1, false, false, false));
                //免疫摔伤
                if (!player.isCreative() && !player.isOnGround() && !player.isInsideWaterOrBubbleColumn() && !player.isInLava()){
                    NbtCompound nbt = new NbtCompound();
                    player.writeNbt(nbt);
                    nbt.putFloat("FallDistance", 0.0f);
                    player.readNbt(nbt);
                }
            }
        }
    }
}
