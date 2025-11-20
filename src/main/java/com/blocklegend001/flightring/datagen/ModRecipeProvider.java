package com.blocklegend001.flightring.datagen;

import com.blocklegend001.flightring.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(packOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.FLIGHT_RING)
                .define('F', Items.FEATHER)
                .define('G', Items.GOLD_BLOCK)
                .define('B', Items.BLAZE_ROD)
                .define('N', Items.NETHER_STAR)
                .pattern("FGF")
                .pattern("BNB")
                .pattern("FGF")
                .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
                .save(recipeOutput);
    }
}
