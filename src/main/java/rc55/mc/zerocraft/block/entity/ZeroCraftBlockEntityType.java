package rc55.mc.zerocraft.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;

public class ZeroCraftBlockEntityType {
    //科技线
    //储罐&管道
    public static final BlockEntityType<FluidTankBlockEntity> FLUID_TANK = register("fluid_tank", FabricBlockEntityTypeBuilder.create(FluidTankBlockEntity::new, ZeroCraftBlocks.FLUID_TANK, ZeroCraftBlocks.WOODEN_FLUID_TANK));
    public static final BlockEntityType<ValveBlockEntity> VALVE = register("valve", FabricBlockEntityTypeBuilder.create(ValveBlockEntity::new, ZeroCraftBlocks.VALVE));
    public static final BlockEntityType<FluidPipeBlockEntity> FLUID_PIPE = register("fluid_pipe", FabricBlockEntityTypeBuilder.create(FluidPipeBlockEntity::new, ZeroCraftBlocks.FLUID_PIPE));
    //蒸汽
    public static final BlockEntityType<BoilerBlockEntity> BOILER = register("boiler", FabricBlockEntityTypeBuilder.create(BoilerBlockEntity::new, ZeroCraftBlocks.BOILER));
    //注册用
    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder<T> builder) {//fabric FabricBlockEntityTypeBuilder.create(para).build()
        Identifier identifier = new Identifier(ZeroCraft.MODID, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, builder.build());
    }
    //初始化注册
    public static void regBlockEntity(){
        ZeroCraft.LOGGER.info("ZeroCraft block entities loaded.");
    }
}
