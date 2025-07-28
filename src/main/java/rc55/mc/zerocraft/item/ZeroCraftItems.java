package rc55.mc.zerocraft.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
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
    public static final Item RAW_TIN = register("raw_tin");
    public static final Item RAW_ZINC = register("raw_zinc");
    public static final Item TIN_INGOT = register("tin_ingot");
    public static final Item ZINC_INGOT = register("zinc_ingot");
    public static final Item BRONZE_INGOT = register("bronze_ingot");
    public static final Item BRASS_INGOT = register("brass_ingot");
    public static final Item TIN_NUGGET = register("tin_nugget");
    public static final Item ZINC_NUGGET = register("zinc_nugget");
    public static final Item BRONZE_NUGGET = register("bronze_nugget");
    public static final Item BRASS_NUGGET = register("brass_nugget");
    public static final Item COPPER_DUST = register("copper_dust");
    public static final Item TIN_DUST = register("tin_dust");
    public static final Item ZINC_DUST = register("zinc_dust");
    public static final Item BRONZE_DUST = register("bronze_dust");
    public static final Item BRASS_DUST = register("brass_dust");
    public static final Item SCARLET_WATER_BUCKET = register("scarlet_water_bucket", new ZeroCraftBucketItem(ZeroCraftFluids.SCARLET_WATER, new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));
    public static final Item STEAM_BUCKET = register("steam_bucket", new BucketItem(ZeroCraftFluids.STEAM, new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));
    //工具
    public static final Item SCARLET_CRYSTAL_SWORD = register("scarlet_crystal_sword", new ScarletCrystalSwordItem());
    public static final Item SCARLET_CRYSTAL_PICKAXE = register("scarlet_crystal_pickaxe", new ScarletCrystalPickaxeItem());
    public static final Item SCARLET_CRYSTAL_AXE = register("scarlet_crystal_axe", new ScarletCrystalAxeItem());
    public static final Item SCARLET_CRYSTAL_SHOVEL = register("scarlet_crystal_shovel", new ScarletCrystalShovelItem());
    public static final Item SCARLET_CRYSTAL_HOE = register("scarlet_crystal_hoe", new ScarletCrystalHoeItem());
    public static final Item WRENCH = register("wrench", new WrenchItem());
    public static final Item BLOCK_TRANSPORTER = register("block_transporter", new BlockTransporterItem());
    public static final Item ORE_FINDER = register("ore_finder", new OreFinderItem());

    public static final Item IRON_KNIFE = register("iron_knife", new KnifeItem(ToolMaterials.IRON, new FabricItemSettings().maxDamage(250)));
    //盔甲
    public static final Item SCARLET_CRYSTAL_HELMET = register("scarlet_crystal_helmet", new ScarletCrystalHelmetItem());
    public static final Item SCARLET_CRYSTAL_CHESTPLATE = register("scarlet_crystal_chestplate", new ScarletCrystalChestplateItem());
    public static final Item SCARLET_CRYSTAL_LEGGINGS = register("scarlet_crystal_leggings", new ScarletCrystalLeggingsItem());
    public static final Item SCARLET_CRYSTAL_BOOTS = register("scarlet_crystal_boots", new ScarletCrystalBootsItem());
    //唱片
    public static final Item DISC_OST_RED_TIDE = register("music_disc_ost_red_tide", new MusicDiscItem(16, ZeroCraftSounds.DISC_OST_RED_TIDE, (new Item.Settings().rarity(Rarity.RARE).maxCount(1)), 116));
    public static final Item DISK_IMAGE_SEEK = register("music_disc_image_seek", new MusicDiscItem(17, ZeroCraftSounds.DISC_IMAGE_SEEK, new Item.Settings().rarity(Rarity.RARE).maxCount(1), 235));
    //食物
    public static final Item SANDWICH = register("sandwich", new Item(new Item.Settings().food(ZeroCraftFoodComponents.SANDWICH)));
    public static final Item CABBAGE = register("cabbage", new AliasedBlockItem(ZeroCraftBlocks.CABBAGE_CROP, new Item.Settings().food(ZeroCraftFoodComponents.CABBAGE)));
    public static final Item BREAD_SLICE = registerFood("bread_slice", 2, 0.2f);
    public static final Item CHEESE = registerFood("cheese", 2, 0.1f);
    //技术性
    public static final Item DEBUG_WAND = register("debug_wand", new DebugWandItem());
    //注册用
    private static Item register(String id, Item item){
        return Registry.register(Registries.ITEM,new Identifier(ZeroCraft.MODID,id),item);
    }
    private static Item register(String id){
        return register(id, new Item(new Item.Settings()));
    }
    private static Item registerFood(String id, FoodComponent foodComponent) {
        return register(id, new Item(new Item.Settings().food(foodComponent)));
    }
    private static Item registerFood(String id, int hunger, float saturation) {
        return registerFood(id, new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).build());
    }
    //初始化注册
    public static void regItem(){
        regDispenserBehaviors();
        ZeroCraft.LOGGER.info("ZeroCraft items loaded.");
    }
    //原版物品lore，仅客户端
    @Environment(EnvType.CLIENT)
    public static void regItemTooltip() {
        ItemTooltipCallback.EVENT.register(((stack, context, lines) -> {
            Item item = stack.getItem();
            if (item instanceof BucketItem bucketItem && !stack.isOf(Items.BUCKET)) {//水桶内流体的温度
                lines.add(Text.translatable("item.minecraft.buckets.desc", bucketItem.fluid.getTemperature(), bucketItem.fluid.getTemperature()-273));
            }
        }));
    }
    //发射器
    private static void regDispenserBehaviors() {
        DispenserBehavior placeFluid = new ItemDispenserBehavior() {
            private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                FluidModificationItem fluidModificationItem = (FluidModificationItem)stack.getItem();
                BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                World world = pointer.getWorld();
                if (fluidModificationItem.placeFluid(null, world, blockPos, null)) {
                    fluidModificationItem.onEmptied(null, world, stack, blockPos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.fallbackBehavior.dispense(pointer, stack);
                }
            }
        };
        DispenserBlock.registerBehavior(ZeroCraftItems.SCARLET_WATER_BUCKET, placeFluid);
        DispenserBlock.registerBehavior(ZeroCraftItems.STEAM_BUCKET, placeFluid);
    }
}
