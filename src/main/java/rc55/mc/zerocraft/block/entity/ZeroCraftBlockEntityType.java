package rc55.mc.zerocraft.block.entity;

import com.mojang.datafixers.types.Type;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;

public class ZeroCraftBlockEntityType {
    //科技线
    //储罐
    public static final BlockEntityType<FluidTankBlockEntity> FLUID_TANK = register("fluid_tank", FabricBlockEntityTypeBuilder.create(FluidTankBlockEntity::new, ZeroCraftBlocks.FLUID_TANK).build());//BlockEntityType.Builder.create(FluidTankBlockEntity::new, ZeroCraftBlocks.FLUID_TANK)
    //注册用
    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType.Builder<T> builder) {
        Identifier identifier = new Identifier(ZeroCraft.MODID, id);
        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, String.valueOf(identifier));
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, builder.build(type));
    }
    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> type) {
        Identifier identifier = new Identifier(ZeroCraft.MODID, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, type);
    }
    //初始化注册
    public static void regBlockEntity(){
        ZeroCraft.LOGGER.info("ZeroCraft block entities loaded.");
    }
}
