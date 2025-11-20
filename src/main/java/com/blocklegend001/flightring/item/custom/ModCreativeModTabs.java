package com.blocklegend001.flightring.item.custom;

import com.blocklegend001.flightring.FlightRing;
import com.blocklegend001.flightring.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FlightRing.MODID);

    public static final Supplier<CreativeModeTab> FLIGHTRING = CREATIVE_MODE_TABS.register("flightring",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FLIGHT_RING.get()))
                    .title(Component.translatable("itemGroup.flightring").withStyle(ChatFormatting.DARK_AQUA))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.FLIGHT_RING);
                    }))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
