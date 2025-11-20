package com.blocklegend001.flightring.datagen;

import com.blocklegend001.flightring.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        simpleItem(itemModelGenerator, ModItems.FLIGHT_RING);
        simpleItem(itemModelGenerator, ModItems.FLIGHT_WINGS);
    }

    private void simpleItem(ItemModelGenerator generator, Item item) {
        generator.register(item, Models.GENERATED);
    }
}
