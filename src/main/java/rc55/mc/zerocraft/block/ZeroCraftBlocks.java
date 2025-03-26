package rc55.mc.zerocraft.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.block.fluid.ScarletWaterFluidBlock;
import rc55.mc.zerocraft.fluid.ScarletWaterFluid;
import rc55.mc.zerocraft.fluid.ZeroCraftFluids;

public class ZeroCraftBlocks {
    //一般方块
    public static final Block SCARLET_CRYSTAL_BLOCK = register("scarlet_crystal_block", new RedstoneBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)), new Item.Settings().fireproof());
    //流体
    public static final Block SCARLET_WATER = registerNoItem("scarlet_water", new ScarletWaterFluidBlock());
    //矿石
    public static final Block SCARLET_CRYSTAL_ORE = register("scarlet_crystal_ore", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
    public static final Block DEEPSLATE_SCARLET_CRYSTAL_ORE = register("deepslate_scarlet_crystal_ore", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)));
    //科技线
    //储罐
    public static final Block FLUID_TANK = register("fluid_tank", new FluidTankBlock(AbstractBlock.Settings.create().solid().hardness(1.0f).requiresTool()));
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
