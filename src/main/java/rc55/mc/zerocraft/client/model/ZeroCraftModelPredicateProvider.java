package rc55.mc.zerocraft.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.item.ZeroCraftItems;

@Environment(EnvType.CLIENT)
public class ZeroCraftModelPredicateProvider {
    //初始化注册
    public static void addModelPredicate(){
        //剑右键格挡
        ModelPredicateProviderRegistry.register(ZeroCraftItems.SCARLET_CRYSTAL_SWORD, new Identifier("blocking"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }
}
