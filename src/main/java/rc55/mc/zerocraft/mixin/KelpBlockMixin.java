package rc55.mc.zerocraft.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.KelpBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KelpBlock.class)
public abstract class KelpBlockMixin {
    @Inject(at = @At("RETURN"), method = "getPlacementState", cancellable = true)
    public void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        Fluid fluid = ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid();
        cir.setReturnValue(fluid.matchesType(Fluids.WATER) ? cir.getReturnValue() : null);
    }
}
