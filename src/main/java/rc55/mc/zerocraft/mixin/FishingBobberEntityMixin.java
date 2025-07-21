package rc55.mc.zerocraft.mixin;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @Shadow protected abstract void tickFishingLogic(BlockPos pos);
    //设置仅在水中钓到鱼
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;tickFishingLogic(Lnet/minecraft/util/math/BlockPos;)V"), method = "tick")
    public void tick(FishingBobberEntity entity, BlockPos pos) {
        World world = entity.getWorld();
        if (world.getFluidState(pos).getFluid().matchesType(Fluids.WATER)) {
            tickFishingLogic(pos);
        }
    }
}
