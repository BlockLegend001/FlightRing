package com.blocklegend001.flightring.datagen;

import com.blocklegend001.flightring.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.FLIGHT_RING)
                .input('F', Items.FEATHER)
                .input('G', Items.GOLD_BLOCK)
                .input('B', Items.BLAZE_ROD)
                .input('N', Items.NETHER_STAR)
                .pattern("FGF")
                .pattern("BNB")
                .pattern("FGF")
                .criterion("has_nether_star", conditionsFromItem(Items.NETHER_STAR))
                .offerTo(consumer);
    }
}
