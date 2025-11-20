package com.blocklegend001.flightring.mixin;

import com.blocklegend001.flightring.FlightRing;
import com.blocklegend001.flightring.event.ModEventHandler;
import com.blocklegend001.flightring.item.custom.FlightRingItem;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    private static final Map<UUID, Boolean> FlyByRing = new HashMap<>();
    private static final AbilitySource RING_SOURCE = Pal.getAbilitySource(FlightRing.MOD_ID, "flight_ring");

    @Inject(method = "tick", at = @At("HEAD"))
    public void onPlayerTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        UUID uuid = player.getUuid();

        if (player.getWorld().isClient) return;
        if (player.isCreative() || player.isSpectator()) return;

        boolean hasRingFlight = checkRingFlight(player);
        boolean hadRingFlight = FlyByRing.getOrDefault(uuid, false);

        if (hasRingFlight) {
            FlyByRing.put(uuid, true);

            if (!VanillaAbilities.ALLOW_FLYING.getTracker(player).isGrantedBy(RING_SOURCE)) {
                VanillaAbilities.ALLOW_FLYING.getTracker(player).addSource(RING_SOURCE);
            }

            player.fallDistance = 0.0f;
            return;
        }

        if (!hasRingFlight && hadRingFlight) {
            FlyByRing.put(uuid, false);

            if (VanillaAbilities.ALLOW_FLYING.getTracker(player).isGrantedBy(RING_SOURCE)) {
                VanillaAbilities.ALLOW_FLYING.getTracker(player).removeSource(RING_SOURCE);
            }

            if (!player.isOnGround()) {
                player.getAbilities().flying = true;
                player.fallDistance = 0.0f;
                ModEventHandler.resetFallDamagePrevention();
            }

            ModEventHandler.onRingRemoved();
        }

        if (player.isOnGround() && ModEventHandler.shouldPreventFallDamage()) {
            player.getAbilities().flying = false;
            player.fallDistance = 0.0f;
            ModEventHandler.resetFallDamagePrevention();
        }

    }

    @Unique
    private boolean checkRingFlight(PlayerEntity player) {
        ItemStack mainHand = player.getMainHandStack();
        ItemStack offHand = player.getOffHandStack();

        if (mainHand.getItem() instanceof FlightRingItem || offHand.getItem() instanceof FlightRingItem) {
            return true;
        }

        boolean hasTrinket = TrinketsApi.getTrinketComponent(player)
                .map(component -> component.getAllEquipped().stream()
                        .anyMatch(pair -> pair.getRight().getItem() instanceof FlightRingItem))
                .orElse(false);
        if (hasTrinket) return true;

        var capability = player.accessoriesCapability();
        if (capability != null) {
            for (SlotEntryReference ref : capability.getAllEquipped()) {
                ItemStack stack = ref.stack();
                if (stack.getItem() instanceof FlightRingItem) return true;
            }
        }

        return false;
    }
}