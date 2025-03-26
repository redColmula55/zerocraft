package rc55.mc.zerocraft.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.item.ZeroCraftItems;

import java.util.List;
import java.util.function.Consumer;

public class RecipeDataGen extends FabricRecipeProvider {
    public RecipeDataGen(FabricDataOutput output) {
        super(output);
    }

    private static final List<ItemConvertible> SCARLET_CRYSTAL_ORES = List.of(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE, ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE);

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        offerReversibleCompactingRecipes(consumer, RecipeCategory.MISC, ZeroCraftItems.SCARLET_CRYSTAL_INGOT, RecipeCategory.MISC, ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ZeroCraftItems.SCARLET_CRYSTAL_INGOT).input(ZeroCraftItems.SCARLET_CRYSTAL, 9).criterion(hasItem(ZeroCraftItems.SCARLET_CRYSTAL), conditionsFromItem(ZeroCraftItems.SCARLET_CRYSTAL)).offerTo(consumer, new Identifier(ZeroCraft.MODID,"scarlet_crystal_ingot_from_crystals"));

        offerSmelting(consumer, SCARLET_CRYSTAL_ORES, RecipeCategory.MISC, ZeroCraftItems.SCARLET_CRYSTAL, 0.7f, 200, "scarlet_crystal_from_ore");
        offerBlasting(consumer, SCARLET_CRYSTAL_ORES, RecipeCategory.MISC, ZeroCraftItems.SCARLET_CRYSTAL, 0.3f, 100, "scarlet_crystal_from_ore");
    }
}
