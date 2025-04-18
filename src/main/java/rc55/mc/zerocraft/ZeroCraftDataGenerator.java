package rc55.mc.zerocraft;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import rc55.mc.zerocraft.dataGen.*;
import rc55.mc.zerocraft.worldGen.ZeroCraftConfiguredFeatures;
import rc55.mc.zerocraft.worldGen.ZeroCraftPlacedFeatures;

public class ZeroCraftDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelDataGen::new);//模型
		pack.addProvider(BlockTagDataGen::new);//方块tag
		pack.addProvider(ItemTagDataGen::new);//物品tag
		pack.addProvider(FluidTagDataGen::new);//流体tag
		pack.addProvider(BlockLootTableDataGen::new);//方块掉落
		pack.addProvider(LangEnDataGen::new);//英文语言
		pack.addProvider(LangZhCnDataGen::new);//中文
		pack.addProvider(WorldGenDataGen::new);//世界生成
		pack.addProvider(RecipeDataGen::new);//合成配方
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder){
		//世界生成数据
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ZeroCraftPlacedFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ZeroCraftConfiguredFeatures::bootstrap);
	}
}
