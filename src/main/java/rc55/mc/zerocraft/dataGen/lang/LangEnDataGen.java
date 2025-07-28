package rc55.mc.zerocraft.dataGen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import rc55.mc.zerocraft.api.Utils;
import rc55.mc.zerocraft.block.PipeBlocks;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;
import rc55.mc.zerocraft.enchantment.ZeroCraftEnchantments;
import rc55.mc.zerocraft.entity.damage.ZeroCraftDamageTypes;
import rc55.mc.zerocraft.entity.effect.ZeroCraftStatusEffects;
import rc55.mc.zerocraft.item.tool.OreFinderItem;
import rc55.mc.zerocraft.world.ZeroCraftGameRules;
import rc55.mc.zerocraft.item.ZeroCraftItemGroups;
import rc55.mc.zerocraft.item.ZeroCraftItems;
import rc55.mc.zerocraft.item.tool.ScarletCrystalMiningTools;
import rc55.mc.zerocraft.screen.FluidTankScreenHandler;

public class LangEnDataGen extends FabricLanguageProvider {
    public LangEnDataGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("item.minecraft.buckets.desc", "Temperature: %sK (%s℃)");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL, "Scarlet Crystal");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL.getTranslationKey()+".desc", "You seems to be feeling powerful holding this...");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_INGOT,"Scarlet Crystal Ingot");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK,"Scarlet Crystal Block");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE,"Scarlet Crystal Ore");
        translationBuilder.add(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE,"Deepslate Scarlet Crystal Ore");
        translationBuilder.add(ZeroCraftItems.COPPER_DUST, "Copper Dust");
        translationBuilder.add(ZeroCraftBlocks.TIN_ORE, "Tin Ore");
        translationBuilder.add(ZeroCraftBlocks.DEEPSLATE_TIN_ORE, "Deepslate Tin Ore");
        translationBuilder.add(ZeroCraftItems.RAW_TIN, "Raw Tin");
        translationBuilder.add(ZeroCraftBlocks.RAW_TIN_BLOCK, "Raw Tin Block");
        translationBuilder.add(ZeroCraftBlocks.TIN_BLOCK, "Tin Block");
        translationBuilder.add(ZeroCraftItems.TIN_INGOT, "Tin Ingot");
        translationBuilder.add(ZeroCraftItems.TIN_DUST, "Tin Dust");
        translationBuilder.add(ZeroCraftItems.TIN_NUGGET, "Tin Nugget");
        translationBuilder.add(ZeroCraftBlocks.ZINC_ORE, "Zinc Ore");
        translationBuilder.add(ZeroCraftBlocks.DEEPSLATE_ZINC_ORE, "Deepslate Zinc Ore");
        translationBuilder.add(ZeroCraftItems.RAW_ZINC, "Raw Zinc");
        translationBuilder.add(ZeroCraftBlocks.RAW_ZINC_BLOCK, "Raw Zinc Block");
        translationBuilder.add(ZeroCraftBlocks.ZINC_BLOCK, "Zinc Block");
        translationBuilder.add(ZeroCraftItems.ZINC_INGOT, "Zinc Ingot");
        translationBuilder.add(ZeroCraftItems.ZINC_DUST, "Zinc Dust");
        translationBuilder.add(ZeroCraftItems.ZINC_NUGGET, "Zinc Nugget");
        translationBuilder.add(ZeroCraftBlocks.BRONZE_BLOCK, "Bronze Block");
        translationBuilder.add(ZeroCraftItems.BRONZE_INGOT, "Bronze Ingot");
        translationBuilder.add(ZeroCraftItems.BRONZE_DUST, "Bronze Dust");
        translationBuilder.add(ZeroCraftItems.BRONZE_NUGGET, "Bronze Nugget");
        translationBuilder.add(ZeroCraftBlocks.BRASS_BLOCK, "Brass Block");
        translationBuilder.add(ZeroCraftItems.BRASS_INGOT, "Brass Ingot");
        translationBuilder.add(ZeroCraftItems.BRASS_DUST, "Brass Dust");
        translationBuilder.add(ZeroCraftItems.BRASS_NUGGET, "Brass Nugget");

        translationBuilder.add(ZeroCraftItems.SANDWICH, "Sandwich");
        translationBuilder.add(ZeroCraftItems.CABBAGE, "Cabbage");
        translationBuilder.add(ZeroCraftItems.CHEESE, "Cheese");
        translationBuilder.add(ZeroCraftItems.BREAD_SLICE, "Bread Slice");

        translationBuilder.add(ZeroCraftBlocks.SCARLET_WATER, "Scarlet Tide Polluted Water");
        translationBuilder.add(ZeroCraftBlocks.STEAM, "Steam");
        translationBuilder.add(ZeroCraftBlocks.CABBAGE_CROP, "Cabbage");

        translationBuilder.add(ZeroCraftItems.IRON_KNIFE, "Iron Knife");

        translationBuilder.add(ZeroCraftBlocks.FLUID_TANK, "Copper Fluid Tank");
        translationBuilder.add(ZeroCraftBlocks.WOODEN_FLUID_TANK, "Wooden Fluid Tank");
        translationBuilder.add(FluidTankScreenHandler.TANK_TRANS_KEY, "Fluid Tank");
        translationBuilder.add(FluidTankScreenHandler.TANK_EMPTY_TRANS_KEY, "(Empty)");
        translationBuilder.add(FluidTankScreenHandler.TANK_CARRYING_TRANS_KEY, "Stored %s mB of %s");
        translationBuilder.add(ZeroCraftBlocks.FLUID_PIPE, "Fluid Pipe");
        translationBuilder.add(ZeroCraftBlocks.VALVE, "Fluid Pipe With Valve");
        translationBuilder.add(PipeBlocks.SPEED_TRANS_KEY, "Speed: %s mB/t");
        translationBuilder.add(PipeBlocks.MAX_TEMP_TRANS_KEY, "Max Temperature: %sK (%s℃)");
        translationBuilder.add(ZeroCraftBlocks.BOILER, "Boiler");

        translationBuilder.add(ZeroCraftItems.DISC_OST_RED_TIDE,"Music Disc (OST)");
        translationBuilder.add(ZeroCraftItems.DISC_OST_RED_TIDE.getTranslationKey()+".desc","YoMio-Music - Red Tide");
        translationBuilder.add(ZeroCraftItems.DISK_IMAGE_SEEK,"Music Disc (Image Song)");
        translationBuilder.add(ZeroCraftItems.DISK_IMAGE_SEEK.getTranslationKey()+".desc","Luoshaoye - Seek (Not official translation, may change at any time)");

        translationBuilder.add(ZeroCraftItems.SCARLET_WATER_BUCKET,"Scarlet Tide Polluted Water Bucket");
        translationBuilder.add(ZeroCraftItems.STEAM_BUCKET,"Steam Bucket");

        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_PICKAXE,"Scarlet Pickaxe");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_AXE,"Scarlet Axe");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SHOVEL,"Scarlet Shovel");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HOE,"Scarlet Hoe");
        translationBuilder.add(ScarletCrystalMiningTools.toolsTranslationKey+".mode.switch","Switched to %s mode.");
        translationBuilder.add(ScarletCrystalMiningTools.toolsTranslationKey+".mode.current","Currently %s mode");
        translationBuilder.add(ScarletCrystalMiningTools.toolsTranslationKey+".mode.hint","Press %s to switch mode");

        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD,"Scarlet Sword");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.switch","Switched to %s mode.");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.current","Currently %s mode");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.hint","Press %s to switch mode");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.frost","Flame");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.flame","Frost");

        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET, "Scarlet Helmet");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET.getTranslationKey()+".night_vision.off", "Night Vision turned off.");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET.getTranslationKey()+".night_vision.on", "Night Vision turned on.");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET.getTranslationKey()+".hint", "Press %s to toggle Night Vision.");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE, "Scarlet Chestplate");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE.getTranslationKey()+".mode.on", "Elytra turned on.");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE.getTranslationKey()+".mode.off", "Elytra turned off.");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE.getTranslationKey()+".mode.hint", "Press %s to toggle elytra.");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_LEGGINGS, "Scarlet Leggings");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_LEGGINGS.getTranslationKey()+".hint", "Press %s to open leggings inventory.");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_BOOTS, "Scarlet Boots");

        translationBuilder.add(ZeroCraftItems.WRENCH,"Wrench");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".hint","Left click to select property, right click to adjust.");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".invalid","Selected block cannot be adjusted by wrench!");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".damaged","Wrench damaged!");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".empty", "Selected block doesn't have any property!");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".select","Selected property %s（Currently %s）");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".update","Set property %s to %s");

        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER,"Block Transporter");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".invalid", "Selected block cannot be transported！");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".damaged", "Transporter damaged!");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".hint", "Right click to pick up block, right click again to place down.");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".hint.carrying", "Currently carrying: [%s]");

        translationBuilder.add(ZeroCraftItems.ORE_FINDER, "Ore Finder");
        translationBuilder.add(OreFinderItem.NO_TARGET_TRANS_KEY, "You haven't set any ore to find yet!");
        translationBuilder.add(OreFinderItem.DAMAGE_TRANS_KEY, "Ore finder damaged!");
        translationBuilder.add(OreFinderItem.FOUND_ORE_TRANS_KEY, "Found %2$s at %1$s");
        translationBuilder.add(OreFinderItem.NOT_FOUND_TRANS_KEY, "Didn't find %s");
        translationBuilder.add(OreFinderItem.HINT_TRANS_KEY, "Shift+Right click to open GUI and put in the ore you want to find, and then right click on a block to start finding.");

        translationBuilder.add(ZeroCraftItems.DEBUG_WAND, "Debugging Wand");

        translationBuilder.add(ZeroCraftEnchantments.VAMPIRE, "Vampire");
        translationBuilder.add(ZeroCraftEnchantments.BEHEADING, "Beheading");

        translationBuilder.add(ZeroCraftStatusEffects.SCARLET_INFESTED, "Scarlet Tide Infested");
        translationBuilder.add(Utils.getDamageTypeTransKey(ZeroCraftDamageTypes.SCARLET_INFESTED), "%s was infested by scarlet tide");
        translationBuilder.add(Utils.getDamageTypeTransKey(ZeroCraftDamageTypes.SCARLET_INFESTED)+".player", "%s was infested by scarlet tide while trying to escape %s");

        translationBuilder.add(ZeroCraftItemGroups.ITEMS, "ZeroCraft - Items");
        translationBuilder.add(ZeroCraftItemGroups.TOOLS,"ZeroCraft - Tools & Weapons");
        translationBuilder.add(ZeroCraftItemGroups.MACHINES, "ZeroCraft - Machines");
        translationBuilder.add(ZeroCraftItemGroups.FOODS, "ZeroCraft - Foods & Drinks");
        translationBuilder.add(ZeroCraftItemGroups.FURNITURES, "ZeroCraft - Furnitures");

        translationBuilder.add(ZeroCraftKeyBinds.CATEGORY, "ZeroCraft");
        translationBuilder.add(ZeroCraftKeyBinds.TOOL_MODE_SWITCH_KEY.getTranslationKey(),"Tool mode switch key");
        translationBuilder.add(ZeroCraftKeyBinds.HELMET_MODE_SWITCH_KEY.getTranslationKey(),"Toggle helmet night vision");
        translationBuilder.add(ZeroCraftKeyBinds.CHESTPLATE_MODE_SWITCH_KEY.getTranslationKey(), "Toggle chestplate elytra");
        translationBuilder.add(ZeroCraftKeyBinds.LEGGINGS_INVENTORY_OPEN_KEY.getTranslationKey(),"Leggings inventory");
        translationBuilder.add("item.zerocraft.hint.shift", "Hold SHIFT for more info");

        translationBuilder.add(ZeroCraftGameRules.SCARLET_WATER_INFEST_CHANCE.getTranslationKey(), "Scarlet tide spread chance");
        translationBuilder.add(ZeroCraftGameRules.SCARLET_WATER_INFEST_CHANCE.getTranslationKey()+".description", "The chance of scarlet tide spreads, set to 0 to disable (Notice: big values may crash the game while trying to infest large amount of water)");
        translationBuilder.add(ZeroCraftGameRules.SCARLET_WATER_DAMAGE.getTranslationKey(), "Scarlet tide infests entity");
        translationBuilder.add(ZeroCraftGameRules.SCARLET_WATER_DAMAGE.getTranslationKey()+".description", "Whether scarlet water damage entities without full set of scarlet armor.");
        translationBuilder.add(ZeroCraftGameRules.FLUID_PIPE_MELTS.getTranslationKey(), "Fluid pipe melts");
        translationBuilder.add(ZeroCraftGameRules.FLUID_PIPE_MELTS.getTranslationKey()+".description", "Whether fluid pipes and valves melt down while trying to transport fluid hotter than its limit.");
    }
}
