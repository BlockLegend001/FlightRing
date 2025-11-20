package com.blocklegend001.flightring.render;

import com.blocklegend001.flightring.item.ModItems;
import com.blocklegend001.flightring.item.custom.FlightRingItem;
import dev.emi.trinkets.api.TrinketsApi;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;
import java.util.WeakHashMap;

public class WingsRenderLogic {

    private static final double FLAP_FREQUENCY = 0.5;
    private static final double MAX_ANGLE = 25.0;
    private static final double CLOSED_ANGLE = -50.0;

    private static final Map<PlayerEntity, Double> playerAngles = new WeakHashMap<>();
    private static final Map<PlayerEntity, Long> playerLastFlyingTime = new WeakHashMap<>();
    private static final Map<PlayerEntity, Double> playerPrevY = new WeakHashMap<>();

    public static void renderWings(PlayerEntity player, PlayerEntityModel<?> playerModel,
                                   MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                   int light) {

        boolean hasRing = checkRingFlight(player);
        if (!hasRing) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        BakedModel wingModel = mc.getItemRenderer().getModels().getModel(ModItems.FLIGHT_WINGS.getDefaultStack());

        double currentAngle = playerAngles.getOrDefault(player, CLOSED_ANGLE);
        long lastFlyingTime = playerLastFlyingTime.getOrDefault(player, 0L);
        double prevY = playerPrevY.getOrDefault(player, player.getY());

        boolean isFlying = player.getAbilities().flying || player.isFallFlying();
        boolean isGrounded = player.isOnGround();
        boolean isRising = (player.getY() - prevY) > 0.01;

        long now = System.currentTimeMillis();
        if (isFlying || isRising) {
            lastFlyingTime = now;
            playerLastFlyingTime.put(player, lastFlyingTime);
        }

        boolean recentlyFlying = (now - lastFlyingTime) < 300;
        boolean shouldOpenWings = isFlying || recentlyFlying || (!isGrounded && !isFlying);

        double targetAngle = shouldOpenWings
                ? MAX_ANGLE * Math.sin(2 * Math.PI * FLAP_FREQUENCY * (now / 1000.0))
                : CLOSED_ANGLE;

        currentAngle += (targetAngle - currentAngle) * 0.1;
        playerAngles.put(player, currentAngle);
        playerPrevY.put(player, player.getY());

        for (int i = -1; i <= 1; i += 2) {
            double dir = i;

            matrices.push();
            playerModel.body.rotate(matrices);

            matrices.translate(dir * -0.5, 0.1, 0.35);
            matrices.scale(0.9f, 0.9f, 0.9f);

            matrices.multiply(new Quaternionf().rotateXYZ(
                    0f,
                    (float) ((Math.PI / 2.f) - dir * (Math.PI / 2.f - Math.PI / 6.f)),
                    (float) Math.PI
            ));

            matrices.translate(-0.5, 0, 0);
            matrices.multiply(new Quaternionf().rotateY((float) Math.toRadians(dir * currentAngle)));
            matrices.translate(0.5, 0, 0);

            mc.getItemRenderer().renderItem(
                    ModItems.FLIGHT_WINGS.getDefaultStack(),
                    ModelTransformationMode.NONE,
                    false,
                    matrices,
                    vertexConsumers,
                    light,
                    0,
                    wingModel
            );

            matrices.pop();
        }
    }

    @Unique
    private static boolean checkRingFlight(PlayerEntity player) {
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
            for (var ref : capability.getAllEquipped()) {
                ItemStack stack = ref.stack();
                if (stack.getItem() instanceof FlightRingItem) return true;
            }
        }

        return false;
    }
}