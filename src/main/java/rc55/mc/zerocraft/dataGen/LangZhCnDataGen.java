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

public class LangZhCnDataGen extends FabricLanguageProvider {
    public LangZhCnDataGen(FabricDataOutput dataOutput) {
        super(dataOutput, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL, "赤晶");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_INGOT,"赤晶锭");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK,"赤晶块");
        translationBuilder.add(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE,"深板岩赤晶矿");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE,"赤晶矿");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_WATER, "赤潮污染水");

        translationBuilder.add(ZeroCraftBlocks.FLUID_TANK, "流体储罐");
        translationBuilder.add(FluidTankScreenHandler.TANK_TRANS_KEY, "流体储罐");
        translationBuilder.add(FluidTankScreenHandler.TANK_EMPTY_TRANS_KEY, "(空)");
        translationBuilder.add(FluidTankScreenHandler.TANK_CARRYING_TRANS_KEY, "当前装有 %s mB 的%s");

        translationBuilder.add(ZeroCraftItems.DISC_OST_RED_TIDE,"音乐唱片(OST)");
        translationBuilder.add(ZeroCraftItems.DISC_OST_RED_TIDE.getTranslationKey()+".desc","YoMio-Music - Red Tide");
        translationBuilder.add(ZeroCraftItems.DISK_IMAGE_SEEK,"音乐唱片(印象曲)");
        translationBuilder.add(ZeroCraftItems.DISK_IMAGE_SEEK.getTranslationKey()+".desc","洛少爷 - 寻溯");

        translationBuilder.add(ZeroCraftItems.SCARLET_WATER_BUCKET,"赤潮污染水桶");

        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_PICKAXE,"赤晶稿");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_AXE,"赤晶斧");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SHOVEL,"赤晶锹");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HOE,"赤晶锄");
        translationBuilder.add(ScarletCrystalMiningTools.toolsTranslationKey+".mode.switch","切换到 %s 模式.");
        translationBuilder.add(ScarletCrystalMiningTools.toolsTranslationKey+".mode.current","当前为 %s 模式");
        translationBuilder.add(ScarletCrystalMiningTools.toolsTranslationKey+".mode.hint","按 %s 切换模式");

        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD,"赤晶剑");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.switch","切换到 %s 模式.");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.current","当前为 %s 模式");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.hint","按 %s 切换模式");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.frost","火焰");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.flame","冰霜");

        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET, "赤晶头盔");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET.getTranslationKey()+".night_vision.off", "夜视模式：关");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET.getTranslationKey()+".night_vision.on", "夜视模式：开");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET.getTranslationKey()+".hint", "按 %s 开关夜视");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE, "赤晶胸甲");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE.getTranslationKey()+".mode.on", "鞘翅：开");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE.getTranslationKey()+".mode.off", "鞘翅：关");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE.getTranslationKey()+".mode.hint", "按 %s 开关鞘翅");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_LEGGINGS, "赤晶护腿");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_BOOTS, "赤晶靴子");

        translationBuilder.add(ZeroCraftItems.WRENCH,"扳手");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".hint","左键选择属性，右键进行调整");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".invalid","选择的方块不可用扳手调整！");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".damaged","扳手损坏！");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".empty","选择的方块没有属性！");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".select","选定属性%s（当前为 %s）");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".update","调整属性%s为%s");

        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER,"方块搬运器");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".invalid", "选择的方块不可搬运！");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".damaged", "搬运器损坏！");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".hint", "右键拾取方块，再次右键放下");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".hint.carrying", "正在拾取：[%s]");

        translationBuilder.add(ZeroCraftEnchantments.VAMPIRE, "吸血");
        translationBuilder.add(ZeroCraftEnchantments.BEHEADING, "斩首");

        translationBuilder.add(ZeroCraftItemGroups.ITEMS, "齐零工艺 | 物品");
        translationBuilder.add(ZeroCraftItemGroups.TOOLS,"齐零工艺 | 工具和武器");
        translationBuilder.add(ZeroCraftItemGroups.MACHINES, "齐零工艺 | 机器和设备");

        translationBuilder.add(ZeroCraftKeyBinds.CATEGORY, "齐零工艺");
        translationBuilder.add(ZeroCraftKeyBinds.TOOL_MODE_SWITCH_KEY.getTranslationKey(),"工具模式切换");
        translationBuilder.add(ZeroCraftKeyBinds.HELMET_MODE_SWITCH_KEY.getTranslationKey(),"头盔夜视切换");
        translationBuilder.add(ZeroCraftKeyBinds.CHESTPLATE_MODE_SWITCH_KEY.getTranslationKey(), "胸甲鞘翅开关");
        translationBuilder.add(ZeroCraftKeyBinds.LEGGINGS_INVENTORY_OPEN_KEY.getTranslationKey(),"护腿物品栏");
    }
}
