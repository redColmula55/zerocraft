package rc55.mc.zerocraft.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.client.ZeroCraftKeyBinds;
import rc55.mc.zerocraft.enchantment.ZeroCraftEnchantments;
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
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL, "Scarlet Crystal");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_INGOT,"Scarlet Crystal Ingot");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK,"Scarlet Crystal Block");
        translationBuilder.add(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE,"Deepslate Scarlet Crystal Ore");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE,"Scarlet Crystal Ore");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_WATER, "Scarlet Tide Polluted Water");

        translationBuilder.add(ZeroCraftBlocks.FLUID_TANK, "Fluid Tank");
        translationBuilder.add(FluidTankScreenHandler.TANK_TRANS_KEY, "Fluid Tank");
        translationBuilder.add(FluidTankScreenHandler.TANK_EMPTY_TRANS_KEY, "(Empty)");
        translationBuilder.add(FluidTankScreenHandler.TANK_CARRYING_TRANS_KEY, "Stored %s mB of %s");

        translationBuilder.add(ZeroCraftItems.DISC_OST_RED_TIDE,"Music Disc (OST)");
        translationBuilder.add(ZeroCraftItems.DISC_OST_RED_TIDE.getTranslationKey()+".desc","YoMio-Music - Red Tide");
        translationBuilder.add(ZeroCraftItems.DISK_IMAGE_SEEK,"Music Disc (Image Song)");
        translationBuilder.add(ZeroCraftItems.DISK_IMAGE_SEEK.getTranslationKey()+".desc","Luoshaoye - Seek (Not official translation, may change at any time)");

        translationBuilder.add(ZeroCraftItems.SCARLET_WATER_BUCKET,"Scarlet Tide Polluted Water Bucket");

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

        translationBuilder.add(ZeroCraftEnchantments.VAMPIRE, "Vampire");
        translationBuilder.add(ZeroCraftEnchantments.BEHEADING, "Beheading");

        translationBuilder.add(ZeroCraftItemGroups.ITEMS, "ZeroCraft - Items");
        translationBuilder.add(ZeroCraftItemGroups.TOOLS,"ZeroCraft - Tools & Weapons");
        translationBuilder.add(ZeroCraftItemGroups.MACHINES, "ZeroCraft - Machines");

        translationBuilder.add(ZeroCraftKeyBinds.CATEGORY, "ZeroCraft");
        translationBuilder.add(ZeroCraftKeyBinds.TOOL_MODE_SWITCH_KEY.getTranslationKey(),"Tool mode switch key");
        translationBuilder.add(ZeroCraftKeyBinds.HELMET_MODE_SWITCH_KEY.getTranslationKey(),"Toggle helmet night vision");
        translationBuilder.add(ZeroCraftKeyBinds.CHESTPLATE_MODE_SWITCH_KEY.getTranslationKey(), "Toggle chestplate elytra");
        translationBuilder.add(ZeroCraftKeyBinds.LEGGINGS_INVENTORY_OPEN_KEY.getTranslationKey(),"Leggings inventory");
    }
}
