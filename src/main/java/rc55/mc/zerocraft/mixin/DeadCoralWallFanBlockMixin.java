package rc55.mc.zerocraft.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralParentBlock;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DeadCoralWallFanBlock.class)
public abstract class DeadCoralWallFanBlockMixin {
    //设置仅能放置在水中或空气中
    @Inject(at = @At("RETURN"), method = "canPlaceAt", cancellable = true)
    public void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Fluid fluid = world.getFluidState(pos).getFluid();
        cir.setReturnValue((fluid.matchesType(Fluids.WATER) || fluid.matchesType(Fluids.EMPTY)) && cir.getReturnValueZ());
    }
}
