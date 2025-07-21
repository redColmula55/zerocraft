package rc55.mc.zerocraft.dataGen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.item.ZeroCraftItems;

public class ModelDataGen extends FabricModelProvider {
    public ModelDataGen(FabricDataOutput output) {
        super(output);
    }
    //方块
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.TIN_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.DEEPSLATE_TIN_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.RAW_TIN_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.TIN_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.ZINC_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.DEEPSLATE_ZINC_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.RAW_ZINC_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.ZINC_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.BRONZE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.BRASS_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.FLUID_TANK);
        blockStateModelGenerator.registerSimpleCubeAll(ZeroCraftBlocks.WOODEN_FLUID_TANK);
    }
    //物品
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ZeroCraftItems.SCARLET_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.SCARLET_CRYSTAL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.RAW_TIN, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.RAW_ZINC, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.TIN_INGOT, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.ZINC_INGOT, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.BRONZE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.BRASS_INGOT, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.COPPER_DUST, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.TIN_DUST, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.ZINC_DUST, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.BRONZE_DUST, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.BRASS_DUST, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.TIN_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.ZINC_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.BRONZE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.BRASS_NUGGET, Models.GENERATED);

        itemModelGenerator.register(ZeroCraftItems.SCARLET_WATER_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.STEAM_BUCKET, Models.GENERATED);

        itemModelGenerator.register(ZeroCraftItems.SCARLET_CRYSTAL_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ZeroCraftItems.SCARLET_CRYSTAL_AXE, Models.HANDHELD);
        itemModelGenerator.register(ZeroCraftItems.SCARLET_CRYSTAL_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ZeroCraftItems.SCARLET_CRYSTAL_HOE, Models.HANDHELD);
        itemModelGenerator.register(ZeroCraftItems.WRENCH, Models.HANDHELD);
        itemModelGenerator.register(ZeroCraftItems.BLOCK_TRANSPORTER, Models.HANDHELD);
        itemModelGenerator.register(ZeroCraftItems.ORE_FINDER, Models.GENERATED);

        itemModelGenerator.registerArmor((ArmorItem) ZeroCraftItems.SCARLET_CRYSTAL_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ZeroCraftItems.SCARLET_CRYSTAL_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ZeroCraftItems.SCARLET_CRYSTAL_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ZeroCraftItems.SCARLET_CRYSTAL_BOOTS);

        itemModelGenerator.register(ZeroCraftItems.DISC_OST_RED_TIDE, Models.GENERATED);
        itemModelGenerator.register(ZeroCraftItems.DISK_IMAGE_SEEK, Models.GENERATED);

        itemModelGenerator.register(ZeroCraftItems.DEBUG_WAND, Models.HANDHELD);
    }
}
