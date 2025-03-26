package rc55.mc.zerocraft.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import rc55.mc.zerocraft.enchantment.ZeroCraftEnchantments;

public class ZeroCraftEvents {
    //初始化注册
    public static void regEvents(){
        //杀死实体
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity instanceof PlayerEntity killer) {
                int lvl = EnchantmentHelper.getLevel(ZeroCraftEnchantments.BEHEADING, killer.getActiveItem());
                if (lvl > 0) {
                    EntityType<?> type = killedEntity.getType();
                    if (type == EntityType.ZOMBIE) {
                        killedEntity.dropItem(Items.ZOMBIE_HEAD);
                    } else if (type == EntityType.SKELETON) {
                        killedEntity.dropItem(Items.SKELETON_SKULL);
                    } else if (type == EntityType.CREEPER) {
                        killedEntity.dropItem(Items.CREEPER_HEAD);
                    } else if (type == EntityType.WITHER_SKELETON) {
                        killedEntity.dropItem(Items.WITHER_SKELETON_SKULL);
                    } else if (type == EntityType.ENDER_DRAGON) {
                        killedEntity.dropItem(Items.DRAGON_HEAD);
                    } else if (type == EntityType.PIGLIN || type == EntityType.PIGLIN_BRUTE) {
                        killedEntity.dropItem(Items.PIGLIN_HEAD);
                    } else if (killedEntity instanceof PlayerEntity player) {
                        String name = player.getEntityName();
                        ItemStack drop = new ItemStack(Items.PLAYER_HEAD, 1);
                        drop.getOrCreateNbt().putString("Owner", name);
                        killedEntity.dropStack(drop);
                    }
                }
            }
        });
    }
}
