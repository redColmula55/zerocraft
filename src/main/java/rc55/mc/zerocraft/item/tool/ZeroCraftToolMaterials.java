package rc55.mc.zerocraft.item.tool;

import com.google.common.base.Suppliers;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import rc55.mc.zerocraft.item.ZeroCraftItemTags;
import rc55.mc.zerocraft.item.ZeroCraftItems;

import java.util.function.Supplier;

public enum ZeroCraftToolMaterials implements ToolMaterial {
    COPPER(MiningLevels.STONE, 133, 5.0f, 2.0f, 14, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
    BRONZE(MiningLevels.IRON, 216, 6.0f, 2.0f, 14, () -> Ingredient.fromTag(ZeroCraftItemTags.BRONZE_INGOTS)),
    SCARLET_CRYSTAL(5,2031,12.0f,4.0f,22,() -> Ingredient.ofItems(ZeroCraftItems.SCARLET_CRYSTAL_INGOT));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ZeroCraftToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
