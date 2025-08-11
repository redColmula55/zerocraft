package rc55.mc.zerocraft.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.block.ZeroCraftBlocks;
import rc55.mc.zerocraft.item.ZeroCraftItemTags;
import rc55.mc.zerocraft.item.ZeroCraftItems;

import java.util.List;
import java.util.function.Consumer;

public class RecipeDataGen extends FabricRecipeProvider {
    public RecipeDataGen(FabricDataOutput output) {
        super(output);
    }

    private static final List<ItemConvertible> SCARLET_CRYSTAL_ORES = List.of(ZeroCraftBlocks.SCARLET_CRYSTAL_ORE, ZeroCraftBlocks.DEEPSLATE_SCARLET_CRYSTAL_ORE);
    private static final List<ItemConvertible> TIN_INGOT_SMELT_MATERIAL = List.of(ZeroCraftBlocks.TIN_ORE, ZeroCraftBlocks.DEEPSLATE_TIN_ORE, ZeroCraftItems.RAW_TIN, ZeroCraftItems.TIN_DUST);
    private static final List<ItemConvertible> ZINC_INGOT_SMELT_MATERIAL = List.of(ZeroCraftBlocks.ZINC_ORE, ZeroCraftBlocks.DEEPSLATE_ZINC_ORE, ZeroCraftItems.RAW_ZINC, ZeroCraftItems.ZINC_DUST);

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        //工作台
        //赤晶类
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ZeroCraftItems.SCARLET_CRYSTAL_INGOT).input(ZeroCraftItems.SCARLET_CRYSTAL, 9).criterion(hasItem(ZeroCraftItems.SCARLET_CRYSTAL), conditionsFromItem(ZeroCraftItems.SCARLET_CRYSTAL)).offerTo(consumer);
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.SCARLET_CRYSTAL_INGOT, RecipeCategory.BUILDING_BLOCKS, ZeroCraftBlocks.SCARLET_CRYSTAL_BLOCK, "scarlet_crystal_ingot_from_block", "scarlet_crystal_ingot");
        //粗矿
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.RAW_TIN, RecipeCategory.BUILDING_BLOCKS, ZeroCraftBlocks.RAW_TIN_BLOCK, "raw_tin_from_block", "raw_tin");
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.RAW_ZINC, RecipeCategory.BUILDING_BLOCKS, ZeroCraftBlocks.RAW_ZINC_BLOCK, "raw_zinc_from_block", "raw_zinc");
        //矿物锭/粒/块
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.TIN_INGOT, RecipeCategory.BUILDING_BLOCKS, ZeroCraftBlocks.TIN_BLOCK, "tin_ingot_from_block", "tin_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.TIN_NUGGET, RecipeCategory.MISC, ZeroCraftItems.TIN_INGOT, "tin_ingot_from_nugget", "tin_ingot");
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.ZINC_INGOT, RecipeCategory.BUILDING_BLOCKS, ZeroCraftBlocks.ZINC_BLOCK, "zinc_ingot_from_block", "zinc_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.ZINC_NUGGET, RecipeCategory.MISC, ZeroCraftItems.ZINC_INGOT, "zinc_ingot_from_nugget", "zinc_ingot");
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.BRONZE_INGOT, RecipeCategory.BUILDING_BLOCKS, ZeroCraftBlocks.BRONZE_BLOCK, "bronze_ingot_from_block", "bronze_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.BRONZE_NUGGET, RecipeCategory.MISC, ZeroCraftItems.BRONZE_INGOT, "bronze_ingot_from_nugget", "bronze_ingot");
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.BRASS_INGOT, RecipeCategory.BUILDING_BLOCKS, ZeroCraftBlocks.BRASS_BLOCK, "brass_ingot_from_block", "brass_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(consumer, RecipeCategory.MISC, ZeroCraftItems.BRASS_NUGGET, RecipeCategory.MISC, ZeroCraftItems.BRASS_INGOT, "brass_ingot_from_nugget", "brass_ingot");
        //合金粉
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ZeroCraftItems.BRONZE_DUST, 4).input(Ingredient.fromTag(ZeroCraftItemTags.COPPER_DUSTS), 3).input(ZeroCraftItemTags.TIN_DUSTS).criterion(hasItem(ZeroCraftItems.TIN_DUST), conditionsFromItem(ZeroCraftItems.TIN_DUST)).offerTo(consumer);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ZeroCraftItems.BRASS_DUST, 4).input(Ingredient.fromTag(ZeroCraftItemTags.COPPER_DUSTS), 3).input(ZeroCraftItemTags.ZINC_DUSTS).criterion(hasItem(ZeroCraftItems.ZINC_DUST), conditionsFromItem(ZeroCraftItems.ZINC_DUST)).offerTo(consumer);
        //食物
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ZeroCraftItems.BREAD_SLICE, 3)
                .input(Ingredient.fromTag(ZeroCraftItemTags.KNIVES)).input(Ingredient.ofItems(Items.BREAD))
                .criterion(hasItem(Items.BREAD), conditionsFromItem(Items.BREAD)).offerTo(consumer);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ZeroCraftItems.SANDWICH)
                .input(Ingredient.ofItems(ZeroCraftItems.BREAD_SLICE), 2)
                .input(Ingredient.fromTag(ZeroCraftItemTags.COOKED_MEATS))
                .input(Ingredient.fromTag(ZeroCraftItemTags.VEGETABLES))
                .input(Ingredient.ofItems(ZeroCraftItems.CHEESE))
                .criterion(hasItem(ZeroCraftItems.CHEESE), conditionsFromItem(ZeroCraftItems.CHEESE)).offerTo(consumer);
        //工具
        createKnifeRecipe(ZeroCraftItems.IRON_KNIFE, Items.IRON_INGOT, "iron_knife", consumer);

        createWrenchRecipe(ZeroCraftItems.SCARLET_CRYSTAL_WRENCH, ZeroCraftItems.SCARLET_CRYSTAL, "scarlet_wrench", consumer);
        //熔炉
        offerSmelting(consumer, SCARLET_CRYSTAL_ORES, RecipeCategory.MISC, ZeroCraftItems.SCARLET_CRYSTAL, 0.7f, 200, "scarlet_crystal_from_ore");
        offerBlasting(consumer, SCARLET_CRYSTAL_ORES, RecipeCategory.MISC, ZeroCraftItems.SCARLET_CRYSTAL, 0.3f, 100, "scarlet_crystal_from_ore");
        offerSmelting(consumer, TIN_INGOT_SMELT_MATERIAL, RecipeCategory.MISC, ZeroCraftItems.TIN_INGOT, 0.7f, 200, "tin_ingot_smelt");
        offerBlasting(consumer, TIN_INGOT_SMELT_MATERIAL, RecipeCategory.MISC, ZeroCraftItems.TIN_INGOT, 0.3f, 100, "tin_ingot_smelt");
        offerSmelting(consumer, ZINC_INGOT_SMELT_MATERIAL, RecipeCategory.MISC, ZeroCraftItems.ZINC_INGOT, 0.7f, 200, "zinc_ingot_smelt");
        offerBlasting(consumer, ZINC_INGOT_SMELT_MATERIAL, RecipeCategory.MISC, ZeroCraftItems.ZINC_INGOT, 0.3f, 100, "zinc_ingot_smelt");
        offerSmelting(consumer, List.of(ZeroCraftItems.COPPER_DUST), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 200, "copper_ingot_from_dust");
        offerBlasting(consumer, List.of(ZeroCraftItems.COPPER_DUST), RecipeCategory.MISC, Items.COPPER_INGOT, 0.3f, 100, "copper_ingot_from_dust");
        offerSmelting(consumer, List.of(ZeroCraftItems.BRONZE_DUST), RecipeCategory.MISC, ZeroCraftItems.BRONZE_INGOT, 0.7f, 200, "bronze_ingot_from_dust");
        offerBlasting(consumer, List.of(ZeroCraftItems.BRONZE_DUST), RecipeCategory.MISC, ZeroCraftItems.BRONZE_INGOT, 0.3f, 100, "bronze_ingot_from_dust");
        offerSmelting(consumer, List.of(ZeroCraftItems.BRASS_DUST), RecipeCategory.MISC, ZeroCraftItems.BRASS_INGOT, 0.7f, 200, "brass_ingot_from_dust");
        offerBlasting(consumer, List.of(ZeroCraftItems.BRASS_DUST), RecipeCategory.MISC, ZeroCraftItems.BRASS_INGOT, 0.3f, 100, "brass_ingot_from_dust");
    }

    private void createKnifeRecipe(Item knife, Item ingredient, String group, Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, knife).group(group)
                .input('a', Items.STICK).input('b', ingredient)
                .pattern("b ").pattern(" a")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, knife).group(group)
                .input('a', Items.STICK).input('b', ingredient)
                .pattern(" b").pattern("a ")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(consumer, new Identifier(ZeroCraft.MODID, group + "2"));
    }

    private void createWrenchRecipe(Item wrench, Item ingredient, String group, Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, wrench).group(group)
                .input('a', ingredient)
                .pattern("a a")
                .pattern("aaa")
                .pattern(" a ")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(consumer, new Identifier(ZeroCraft.MODID, group + "1"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, wrench).group(group)
                .input('a', ingredient)
                .pattern(" aa")
                .pattern("aa ")
                .pattern(" aa")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(consumer, new Identifier(ZeroCraft.MODID, group + "2"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, wrench).group(group)
                .input('a', ingredient)
                .pattern("aa ")
                .pattern(" aa")
                .pattern("aa ")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(consumer, new Identifier(ZeroCraft.MODID, group + "3"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, wrench).group(group)
                .input('a', ingredient)
                .pattern(" a ")
                .pattern("aaa")
                .pattern("a a")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(consumer, new Identifier(ZeroCraft.MODID, group + "4"));
    }
}
