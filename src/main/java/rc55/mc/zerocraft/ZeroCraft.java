package rc55.mc.zerocraft;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.block.entity.ZeroCraftBlockEntityType;
import rc55.mc.zerocraft.screen.ZeroCraftScreenHandlerType;
import rc55.mc.zerocraft.sound.ZeroCraftSounds;
import rc55.mc.zerocraft.item.ZeroCraftItemGroups;
import rc55.mc.zerocraft.item.ZeroCraftItems;
import rc55.mc.zerocraft.worldGen.ZeroCraftOresGen;

public class ZeroCraft implements ModInitializer {
	//mod id
	public static final String MODID = "zerocraft";
	//输出log用
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	//初始化
	@Override
	public void onInitialize() {
		LOGGER.info("Started to load ZeroCraft.");
		ZeroCraftItems.regItem();//物品
		ZeroCraftBlocks.regBlock();//方块
		ZeroCraftBlockEntityType.regBlockEntity();//方块实体
		ZeroCraftItemGroups.regItemGroup();//物品组
		ZeroCraftSounds.regSounds();//声音
		ZeroCraftScreenHandlerType.addScreenHandler();//屏幕

		ZeroCraftOresGen.addOres();//矿石生成
		LOGGER.info("ZeroCraft load completed.");
	}
}