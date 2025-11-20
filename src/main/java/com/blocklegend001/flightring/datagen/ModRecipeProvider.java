package com.blocklegend001.flightring.datagen;

import com.blocklegend001.flightring.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    protected ModRecipeProvider(HolderLookup.Provider p_361709_, RecipeOutput p_365321_) {
        super(p_361709_, p_365321_);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> providerCompletableFuture) {
            super(output, providerCompletableFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        shaped(RecipeCategory.COMBAT, ModItems.FLIGHT_RING)
                .define('F', Items.FEATHER)
                .define('G', Items.GOLD_BLOCK)
                .define('B', Items.BLAZE_ROD)
                .define('N', Items.NETHER_STAR)
                .pattern("FGF")
                .pattern("BNB")
                .pattern("FGF")
                .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
                .save(this.output);
    }
}
