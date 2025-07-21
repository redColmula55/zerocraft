package rc55.mc.zerocraft.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SeagrassBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SeagrassBlock.class)
public class SeagrassBlockMixin {
    //设置仅能种植在水中
    @Inject(at = @At("RETURN"), method = "canPlantOnTop", cancellable = true)
    public void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValueZ() && world.getFluidState(pos.up()).isOf(Fluids.WATER));
    }
    //修复骨粉右键生长行为
    @Inject(at = @At("HEAD"), method = "isFertilizable", cancellable = true)
    public void isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(world.getFluidState(pos.up()).isOf(Fluids.WATER));
    }
}
