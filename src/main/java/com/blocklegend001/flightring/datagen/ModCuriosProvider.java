package com.blocklegend001.flightring.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class ModCuriosProvider extends CuriosDataProvider {

    public ModCuriosProvider(String modId, PackOutput output,
                              CompletableFuture<HolderLookup.Provider> registries) {
        super(modId, output, registries);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        this.createSlot("ring")
                .size(1)
                .icon(ResourceLocation.parse("curios:slot/empty_ring_slot"));

        this.createEntities("flight_ring")
                .addPlayer()
                .addSlots("ring", "hands");
    }
}