package rc55.mc.zerocraft.item;

import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.fluid.ZeroCraftFluids;
import rc55.mc.zerocraft.item.armor.ScarletCrystalBootsItem;
import rc55.mc.zerocraft.item.armor.ScarletCrystalChestplateItem;
import rc55.mc.zerocraft.item.armor.ScarletCrystalHelmetItem;
import rc55.mc.zerocraft.item.armor.ScarletCrystalLeggingsItem;
import rc55.mc.zerocraft.item.tool.*;
import rc55.mc.zerocraft.sound.ZeroCraftSounds;

public class ZeroCraftItems {
    //物品
    public static final Item SCARLET_CRYSTAL = register("scarlet_crystal", new ScarletCrystalItem());
    public static final Item SCARLET_CRYSTAL_INGOT = register("scarlet_crystal_ingot", new Item(new Item.Settings().fireproof().rarity(Rarity.RARE)));
    public static final Item SCARLET_WATER_BUCKET = register("scarlet_water_bucket", new BucketItem(ZeroCraftFluids.SCARLET_WATER, new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));
    //工具
    public static final Item SCARLET_CRYSTAL_SWORD = register("scarlet_crystal_sword", new ScarletCrystalSwordItem());
    public static final Item SCARLET_CRYSTAL_PICKAXE = register("scarlet_crystal_pickaxe", new ScarletCrystalPickaxeItem());
    public static final Item SCARLET_CRYSTAL_AXE = register("scarlet_crystal_axe", new ScarletCrystalAxeItem());
    public static final Item SCARLET_CRYSTAL_SHOVEL = register("scarlet_crystal_shovel", new ScarletCrystalShovelItem());
    public static final Item SCARLET_CRYSTAL_HOE = register("scarlet_crystal_hoe", new ScarletCrystalHoeItem());
    public static final Item WRENCH = register("wrench", new WrenchItem());
    public static final Item BLOCK_TRANSPORTER = register("block_transporter", new BlockTransporterItem());
    //盔甲
    public static final Item SCARLET_CRYSTAL_HELMET = register("scarlet_crystal_helmet", new ScarletCrystalHelmetItem());
    public static final Item SCARLET_CRYSTAL_CHESTPLATE = register("scarlet_crystal_chestplate", new ScarletCrystalChestplateItem());
    public static final Item SCARLET_CRYSTAL_LEGGINGS = register("scarlet_crystal_leggings", new ScarletCrystalLeggingsItem());
    public static final Item SCARLET_CRYSTAL_BOOTS = register("scarlet_crystal_boots", new ScarletCrystalBootsItem());
    //唱片
    public static final Item DISC_OST_RED_TIDE = register("music_disc_ost_red_tide", new MusicDiscItem(16, ZeroCraftSounds.DISC_OST_RED_TIDE, (new Item.Settings().rarity(Rarity.RARE).maxCount(1)), 116));
    public static final Item DISK_IMAGE_SEEK = register("music_disc_image_seek", new MusicDiscItem(17, ZeroCraftSounds.DISC_IMAGE_SEEK, new Item.Settings().rarity(Rarity.RARE).maxCount(1), 235));
    //技术性
    public static final Item DEBUG_WAND = register("debug_wand", new DebugWandItem());
    //注册用
    private static Item register(String id, Item item){
        return Registry.register(Registries.ITEM,new Identifier(ZeroCraft.MODID,id),item);
    }
    //初始化注册
    public static void regItem(){
        ZeroCraft.LOGGER.info("ZeroCraft items loaded.");
    }
}
