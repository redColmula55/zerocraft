package rc55.mc.zerocraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import rc55.mc.zerocraft.api.Utils;

import java.util.HashMap;
import java.util.Map;

public class BeheadingEnchantment extends Enchantment {
    protected BeheadingEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    //最大等级
    @Override
    public int getMaxLevel() {
        return 10;
    }
    //可附魔物品
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack) || stack.isIn(ItemTags.AXES);
    }

    /**
     * 设置对应怪物掉落的怪物头颅（不包括玩家）
     * @return 包含所有怪物对应头颅的 Map
     */
    public static Map<EntityType<?>, ItemStack> getMobSkull() {
        Map<EntityType<?>, ItemStack> map = new HashMap<>();

        map.put(EntityType.ZOMBIE, Items.ZOMBIE_HEAD.getDefaultStack());
        map.put(EntityType.SKELETON, Items.SKELETON_SKULL.getDefaultStack());
        map.put(EntityType.CREEPER, Items.CREEPER_HEAD.getDefaultStack());
        map.put(EntityType.WITHER_SKELETON, Items.WITHER_SKELETON_SKULL.getDefaultStack());
        map.put(EntityType.ENDER_DRAGON, Items.DRAGON_HEAD.getDefaultStack());
        map.put(EntityType.PIGLIN, Items.PIGLIN_HEAD.getDefaultStack());
        map.put(EntityType.PIGLIN_BRUTE, Items.PIGLIN_HEAD.getDefaultStack());
        map.put(EntityType.WITHER, new ItemStack(Items.WITHER_SKELETON_SKULL, Random.create().nextBetween(1,3)));//我为什么要做这个

        return map;
    }

    /**
     * 使用附魔斩首的武器杀死实体后掉落对应头颅
     * @param world 当前世界实例
     * @param entity 行凶实体（？
     * @param killedEntity 被杀死的实体
     */
    public static void postKill(World world, Entity entity, LivingEntity killedEntity) {
        if (entity instanceof PlayerEntity killer) {//仅玩家触发
            int lvl = EnchantmentHelper.getLevel(ZeroCraftEnchantments.BEHEADING, killer.getMainHandStack());
            if (lvl > 0) {
                if (Utils.getRandomPercent(world.getRandom(), lvl*10)) {//每级增加10%掉落几率
                    EntityType<?> type = killedEntity.getType();
                    Map<EntityType<?>, ItemStack> map = getMobSkull();
                    if (map.containsKey(type)) {//怪物头颅
                        killedEntity.dropStack(map.get(type));
                    } else if (killedEntity instanceof PlayerEntity killedPlayer) {//玩家头颅（保留玩家id）
                        NbtCompound nbt = new NbtCompound();
                        NbtHelper.writeGameProfile(nbt, killedPlayer.getGameProfile());
                        ItemStack drop = new ItemStack(Items.PLAYER_HEAD, 1);
                        drop.getOrCreateNbt().put("SkullOwner", nbt);
                        if (killedPlayer.getGameProfile().getName().equals("redColmula55")) {//彩蛋（？
                            drop.getOrCreateSubNbt("BlockEntityTag").putString("note_block_sound", SoundEvents.ENTITY_VILLAGER_DEATH.getId().toString());
                        }
                        killedEntity.dropStack(drop);
                    }
                }
            }
        }
    }
}
