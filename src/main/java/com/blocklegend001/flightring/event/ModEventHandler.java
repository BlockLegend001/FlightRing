package com.blocklegend001.flightring.event;

import com.blocklegend001.flightring.item.custom.FlightRingItem;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber
public class ModEventHandler {

    private static final ConcurrentHashMap<UUID, Boolean> previousMayfly = new ConcurrentHashMap<>();
    private static boolean preventFallDamageOnce = false;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Level level = event.getEntity().level();
        Entity entity = event.getEntity();
        if (!level.isClientSide() && entity instanceof Player player) {
            boolean hasRing = checkRingFlight(player);
            UUID playerUUID = player.getUUID();

            if (hasRing) {
                previousMayfly.putIfAbsent(playerUUID, player.getAbilities().mayfly);

                player.getAbilities().mayfly = true;
                preventFallDamageOnce = true;
                player.onUpdateAbilities();

            } else if (previousMayfly.containsKey(playerUUID)) {
                player.getAbilities().mayfly = previousMayfly.get(playerUUID);

                if (!player.getAbilities().mayfly) {
                    player.getAbilities().flying = false;
                }

                previousMayfly.remove(playerUUID);
                player.onUpdateAbilities();
            }
        }
    }

    @SubscribeEvent
    public static void cancelPlayerFallDamage(LivingFallEvent event) {
        Level level = event.getEntity().level();
        Entity entity = event.getEntity();
        if (!level.isClientSide() && entity instanceof Player) {
            if (preventFallDamageOnce) {
                event.setDistance(0);
                preventFallDamageOnce = false;
            }
        }
    }

    public static boolean checkRingFlight(Player player) {
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        if (mainHand.getItem() instanceof FlightRingItem || offHand.getItem() instanceof FlightRingItem) {
            return true;
        }

        boolean curiosHasRing = CuriosApi.getCuriosInventory(player).map(handler -> {
            for (ICurioStacksHandler stacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);
                    if (stack.getItem() instanceof FlightRingItem) {
                        return true;
                    }
                }
            }
            return false;
        }).orElse(false);
        if (curiosHasRing) return true;

        AccessoriesCapability cap = AccessoriesCapability.get(player);
        if (cap != null) {
            for (SlotEntryReference ref : cap.getAllEquipped()) {
                ItemStack stack = ref.stack();
                if (stack.getItem() instanceof FlightRingItem) {
                    return true;
                }
            }
        }

        return false;
    }
}