package rc55.mc.zerocraft.dataGen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import rc55.mc.zerocraft.api.Utils;
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

public class LangZhCnDataGen extends FabricLanguageProvider {
    public LangZhCnDataGen(FabricDataOutput dataOutput) {
        super(dataOutput, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("modmenu.nameTranslation.zerocraft", "齐零工艺");
        translationBuilder.add("modmenu.descriptionTranslation.zerocraft", "齐零科技 荣誉出品 \n （开玩笑的，本mod和赤潮官方没有关系，仅为粉丝二创，任何内容均不代表官方立场 \n 齐零工艺是一个以赤潮ZeroERA为背景制作的mod，引入了诸多赤潮ZeroERA的特性以及一些小工具。");

        translationBuilder.add("item.minecraft.buckets.desc", "温度: %sK (%s℃)");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL, "赤晶");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL.getTranslationKey()+".desc", "将它握在掌心，你似乎充满力量……");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_INGOT,"赤晶锭");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK,"赤晶块");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE,"赤晶矿");
        translationBuilder.add(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE,"深板岩赤晶矿");
        translationBuilder.add(ZeroCraftItems.COPPER_DUST, "铜粉");
        translationBuilder.add(ZeroCraftBlocks.TIN_ORE, "锡矿石");
        translationBuilder.add(ZeroCraftBlocks.DEEPSLATE_TIN_ORE, "深板岩锡矿石");
        translationBuilder.add(ZeroCraftItems.RAW_TIN, "粗锡");
        translationBuilder.add(ZeroCraftBlocks.RAW_TIN_BLOCK, "粗锡块");
        translationBuilder.add(ZeroCraftBlocks.TIN_BLOCK, "锡块");
        translationBuilder.add(ZeroCraftItems.TIN_INGOT, "锡锭");
        translationBuilder.add(ZeroCraftItems.TIN_DUST, "锡粉");
        translationBuilder.add(ZeroCraftItems.TIN_NUGGET, "锡粒");
        translationBuilder.add(ZeroCraftBlocks.ZINC_ORE, "锌矿石");
        translationBuilder.add(ZeroCraftBlocks.DEEPSLATE_ZINC_ORE, "深板岩锌矿石");
        translationBuilder.add(ZeroCraftItems.RAW_ZINC, "粗锌");
        translationBuilder.add(ZeroCraftBlocks.RAW_ZINC_BLOCK, "粗锌块");
        translationBuilder.add(ZeroCraftBlocks.ZINC_BLOCK, "锌块");
        translationBuilder.add(ZeroCraftItems.ZINC_INGOT, "锌锭");
        translationBuilder.add(ZeroCraftItems.ZINC_DUST, "锌粉");
        translationBuilder.add(ZeroCraftItems.ZINC_NUGGET, "锌粒");
        translationBuilder.add(ZeroCraftBlocks.BRONZE_BLOCK, "青铜块");
        translationBuilder.add(ZeroCraftItems.BRONZE_INGOT, "青铜锭");
        translationBuilder.add(ZeroCraftItems.BRONZE_DUST, "青铜粉");
        translationBuilder.add(ZeroCraftItems.BRONZE_NUGGET, "青铜粒");
        translationBuilder.add(ZeroCraftBlocks.BRASS_BLOCK, "黄铜块");
        translationBuilder.add(ZeroCraftItems.BRASS_INGOT, "黄铜锭");
        translationBuilder.add(ZeroCraftItems.BRASS_DUST, "黄铜粉");
        translationBuilder.add(ZeroCraftItems.BRASS_NUGGET, "黄铜粒");
        translationBuilder.add(ZeroCraftBlocks.SCARLET_WATER, "赤潮污染水");
        translationBuilder.add(ZeroCraftBlocks.STEAM, "蒸汽");

        translationBuilder.add(ZeroCraftBlocks.FLUID_TANK, "流体储罐");
        translationBuilder.add(ZeroCraftBlocks.WOODEN_FLUID_TANK, "木制简易储罐");
        translationBuilder.add(FluidTankScreenHandler.TANK_TRANS_KEY, "流体储罐");
        translationBuilder.add(FluidTankScreenHandler.TANK_EMPTY_TRANS_KEY, "(空)");
        translationBuilder.add(FluidTankScreenHandler.TANK_CARRYING_TRANS_KEY, "当前装有 %s mB 的%s");
        translationBuilder.add(ZeroCraftBlocks.FLUID_PIPE, "流体管道");
        translationBuilder.add(ZeroCraftBlocks.VALVE, "阀门管道");
        translationBuilder.add("block.zerocraft.pipes.desc.speed", "速度: %s mB/t");
        translationBuilder.add("block.zerocraft.pipes.desc.max_temp", "最大温度: %sK (%s℃)");
        translationBuilder.add(ZeroCraftBlocks.BOILER, "锅炉");

        translationBuilder.add(ZeroCraftItems.DISC_OST_RED_TIDE,"音乐唱片(OST)");
        translationBuilder.add(ZeroCraftItems.DISC_OST_RED_TIDE.getTranslationKey()+".desc","YoMio-Music - Red Tide");
        translationBuilder.add(ZeroCraftItems.DISK_IMAGE_SEEK,"音乐唱片(印象曲)");
        translationBuilder.add(ZeroCraftItems.DISK_IMAGE_SEEK.getTranslationKey()+".desc","洛少爷 - 寻溯");

        translationBuilder.add(ZeroCraftItems.SCARLET_WATER_BUCKET,"赤潮污染水桶");
        translationBuilder.add(ZeroCraftItems.STEAM_BUCKET,"蒸汽桶");

        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_PICKAXE,"赤晶稿");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_AXE,"赤晶斧");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SHOVEL,"赤晶锹");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HOE,"赤晶锄");
        translationBuilder.add(ScarletCrystalMiningTools.toolsTranslationKey+".mode.switch","切换到 %s 模式.");
        translationBuilder.add(ScarletCrystalMiningTools.toolsTranslationKey+".mode.current","当前为 %s 模式");
        translationBuilder.add(ScarletCrystalMiningTools.toolsTranslationKey+".mode.hint","按%s切换模式");

        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD,"赤晶剑");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.switch","切换到 %s 模式.");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.current","当前为 %s 模式");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.hint","按%s切换模式");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.frost","火焰");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_SWORD.getTranslationKey()+".mode.flame","冰霜");

        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET, "赤晶头盔");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET.getTranslationKey()+".night_vision.off", "夜视模式：关");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET.getTranslationKey()+".night_vision.on", "夜视模式：开");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_HELMET.getTranslationKey()+".hint", "按%s开关夜视");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE, "赤晶胸甲");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE.getTranslationKey()+".mode.on", "鞘翅：开");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE.getTranslationKey()+".mode.off", "鞘翅：关");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE.getTranslationKey()+".mode.hint", "按%s开关鞘翅");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_LEGGINGS, "赤晶护腿");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_LEGGINGS.getTranslationKey()+".hint", "按%s打开护腿物品栏");
        translationBuilder.add(ZeroCraftItems.SCARLET_CRYSTAL_BOOTS, "赤晶靴子");

        translationBuilder.add(ZeroCraftItems.WRENCH,"扳手");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".hint","左键选择属性，右键进行调整");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".invalid","选择的方块不可用扳手调整！");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".damaged","扳手损坏！");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".empty","选择的方块没有属性！");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".select","选定属性%s（当前为 %s）");
        translationBuilder.add(ZeroCraftItems.WRENCH.getTranslationKey()+".update","调整属性%s为%s");

        translationBuilder.add(ZeroCraftItems.ORE_FINDER, "探矿器");
        translationBuilder.add(OreFinderItem.NO_TARGET_TRANS_KEY, "尚未设置想要寻找的矿石！");
        translationBuilder.add(OreFinderItem.DAMAGE_TRANS_KEY, "探矿器损坏！");
        translationBuilder.add(OreFinderItem.FOUND_ORE_TRANS_KEY, "在 %s 处找到了 %s");
        translationBuilder.add(OreFinderItem.NOT_FOUND_TRANS_KEY, "未找到 %s");
        translationBuilder.add(OreFinderItem.HINT_TRANS_KEY, "SHIFT+右键打开GUI并放入你想寻找的矿石，然后右键地面开始探测");

        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER,"方块搬运器");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".invalid", "选择的方块不可搬运！");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".damaged", "搬运器损坏！");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".hint", "右键拾取方块，再次右键放下");
        translationBuilder.add(ZeroCraftItems.BLOCK_TRANSPORTER.getTranslationKey()+".hint.carrying", "正在拾取：[%s]");

        translationBuilder.add(ZeroCraftItems.DEBUG_WAND, "调试法杖");

        translationBuilder.add(ZeroCraftEnchantments.VAMPIRE, "吸血");
        translationBuilder.add(ZeroCraftEnchantments.BEHEADING, "斩首");

        translationBuilder.add(ZeroCraftStatusEffects.SCARLET_INFESTED, "赤潮侵蚀");
        translationBuilder.add(Utils.getDamageTypeTransKey(ZeroCraftDamageTypes.SCARLET_INFESTED), "%s被赤潮侵蚀了");
        translationBuilder.add(Utils.getDamageTypeTransKey(ZeroCraftDamageTypes.SCARLET_INFESTED)+".player", "%s在试图逃离%s时被赤潮侵蚀了");

        translationBuilder.add(ZeroCraftItemGroups.ITEMS, "齐零工艺 | 物品");
        translationBuilder.add(ZeroCraftItemGroups.TOOLS,"齐零工艺 | 工具和武器");
        translationBuilder.add(ZeroCraftItemGroups.MACHINES, "齐零工艺 | 机器和设备");
        translationBuilder.add(ZeroCraftItemGroups.FOODS, "齐零工艺 | 食物和饮料");
        translationBuilder.add(ZeroCraftItemGroups.FURNITURES, "齐零工艺 | 家具和摆设");

        translationBuilder.add(ZeroCraftKeyBinds.CATEGORY, "齐零工艺");
        translationBuilder.add(ZeroCraftKeyBinds.TOOL_MODE_SWITCH_KEY.getTranslationKey(),"工具模式切换");
        translationBuilder.add(ZeroCraftKeyBinds.HELMET_MODE_SWITCH_KEY.getTranslationKey(),"头盔夜视切换");
        translationBuilder.add(ZeroCraftKeyBinds.CHESTPLATE_MODE_SWITCH_KEY.getTranslationKey(), "胸甲鞘翅开关");
        translationBuilder.add(ZeroCraftKeyBinds.LEGGINGS_INVENTORY_OPEN_KEY.getTranslationKey(),"护腿物品栏");
        translationBuilder.add("item.zerocraft.hint.shift", "按住SHIFT获取详细信息");

        translationBuilder.add(ZeroCraftGameRules.SCARLET_WATER_INFEST_CHANCE.getTranslationKey(), "赤潮扩散几率");
        translationBuilder.add(ZeroCraftGameRules.SCARLET_WATER_INFEST_CHANCE.getTranslationKey()+".description", "赤潮污染水感染附近水源的几率，将此项设置为0可禁用（注意：若此项设置过大可能导致Minecraft在试图感染大面积水域时崩溃）");
        translationBuilder.add(ZeroCraftGameRules.SCARLET_WATER_DAMAGE.getTranslationKey(), "赤潮侵蚀伤害");
        translationBuilder.add(ZeroCraftGameRules.SCARLET_WATER_DAMAGE.getTranslationKey()+".description", "赤潮污染水是否会对未穿着全套赤晶盔甲的生物造成伤害");
        translationBuilder.add(ZeroCraftGameRules.FLUID_PIPE_MELTS.getTranslationKey(), "管道熔毁");
        translationBuilder.add(ZeroCraftGameRules.FLUID_PIPE_MELTS.getTranslationKey()+".description", "流体管道和阀门管道是否会在试图传输温度过高的流体时熔毁");
    }
}
