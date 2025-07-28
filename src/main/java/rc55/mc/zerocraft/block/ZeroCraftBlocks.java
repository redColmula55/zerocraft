package rc55.mc.zerocraft.block;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.block.fluid.ScarletWaterFluidBlock;
import rc55.mc.zerocraft.block.plant.CabbageCropBlock;
import rc55.mc.zerocraft.fluid.ZeroCraftFluids;

public class ZeroCraftBlocks {
    //一般方块
    public static final Block SCARLET_CRYSTAL_BLOCK = register("scarlet_crystal_block", new RedstoneBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)), new Item.Settings().fireproof());
    public static final Block TIN_BLOCK = register("tin_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block ZINC_BLOCK = register("zinc_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block BRONZE_BLOCK = register("bronze_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block BRASS_BLOCK = register("brass_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    //流体
    public static final Block SCARLET_WATER = registerNoItem("scarlet_water", new ScarletWaterFluidBlock());
    public static final Block STEAM = registerNoItem("steam", new FluidBlock(ZeroCraftFluids.STEAM, AbstractBlock.Settings.copy(Blocks.WATER)));
    //矿石
    public static final Block SCARLET_CRYSTAL_ORE = register("scarlet_crystal_ore", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
    public static final Block DEEPSLATE_SCARLET_CRYSTAL_ORE = register("deepslate_scarlet_crystal_ore", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)));
    public static final Block TIN_ORE = register("tin_ore", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
    public static final Block DEEPSLATE_TIN_ORE = register("deepslate_tin_ore", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)));
    public static final Block RAW_TIN_BLOCK = register("raw_tin_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block ZINC_ORE = register("zinc_ore", new Block(AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)));
    public static final Block DEEPSLATE_ZINC_ORE = register("deepslate_zinc_ore", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)));
    public static final Block RAW_ZINC_BLOCK = register("raw_zinc_block", new Block(AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)));
    //植物
    public static final Block CABBAGE_CROP = registerNoItem("cabbage_crop", new CabbageCropBlock(AbstractBlock.Settings.copy(Blocks.WHEAT)));
    //科技线
    //储罐
    public static final Block FLUID_TANK = register("fluid_tank", new FluidTankBlock(16000, 2000, AbstractBlock.Settings.create().solid().hardness(1.0f).requiresTool()));
    public static final Block WOODEN_FLUID_TANK = register("wooden_fluid_tank", new FluidTankBlock(4000, 400, AbstractBlock.Settings.copy(FLUID_TANK)));
    //管道
    public static final Block VALVE = register("valve", new ValveBlock(40, 2000, 40, AbstractBlock.Settings.create()));
    public static final Block FLUID_PIPE = register("fluid_pipe", new FluidPipeBlock(40, 2000, 40, AbstractBlock.Settings.create()));
    //蒸汽
    public static final Block BOILER = register("boiler", new BoilerBlock(AbstractBlock.Settings.create()));
    //注册用
    private static Block register(String id, Block block){
        Registry.register(Registries.ITEM, new Identifier(ZeroCraft.MODID, id), new BlockItem(block, new Item.Settings()));
        return registerNoItem(id, block);
    }
    //方块物品特殊设置
    private static Block register(String id, Block block, Item.Settings settings){
        Registry.register(Registries.ITEM, new Identifier(ZeroCraft.MODID, id), new BlockItem(block, settings));
        return registerNoItem(id, block);
    }
    //技术性方块
    private static Block registerNoItem(String id, Block block){
        return Registry.register(Registries.BLOCK, new Identifier(ZeroCraft.MODID, id), block);
    }
    //初始化注册
    public static void regBlock(){
        ZeroCraft.LOGGER.info("ZeroCraft blocks loaded.");
    }
}
