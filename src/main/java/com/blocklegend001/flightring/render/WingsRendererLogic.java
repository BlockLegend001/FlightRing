package com.blocklegend001.flightring.render;

import com.blocklegend001.flightring.item.ModItems;
import com.blocklegend001.flightring.item.custom.FlightRingItem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;
import java.util.WeakHashMap;

public class WingsRendererLogic {
    private static final double FLAP_FREQUENCY = 0.5;
    private static final double MAX_ANGLE = 25.0;
    private static final double CLOSED_ANGLE = -50.0;

    private static final Map<Player, Double> playerAngles = new WeakHashMap<>();
    private static final Map<Player, Long> playerLastFlyingTime = new WeakHashMap<>();
    private static final Map<Player, Double> playerPrevY = new WeakHashMap<>();

    public static void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight,
                              AbstractClientPlayer player, PlayerModel<?> playerModel) {

        if (!player.isAlive()) return;

        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        if (level == null) return;

        ItemStack wingStack = ModItems.FLIGHT_WINGS.get().getDefaultInstance();
        BakedModel wingModel = mc.getItemRenderer().getModel(wingStack, level, player, 1);

        if (!checkRingFlight(player)) return;

        double currentAngle = playerAngles.getOrDefault(player, CLOSED_ANGLE);
        long lastFlyingTime = playerLastFlyingTime.getOrDefault(player, 0L);
        double prevY = playerPrevY.getOrDefault(player, player.getY());

        boolean isFlying = player.isFallFlying() || player.getAbilities().flying;
        boolean isGrounded = player.onGround();
        boolean isRising = (player.getY() - prevY) > 0.05;

        long now = System.currentTimeMillis();
        if (isFlying || isRising) {
            lastFlyingTime = now;
            playerLastFlyingTime.put(player, now);
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

            matrixStack.pushPose();
            playerModel.body.translateAndRotate(matrixStack);

            matrixStack.translate(dir * -0.5, 0.1, 0.35);
            matrixStack.scale(0.9f, 0.9f, 0.9f);

            matrixStack.mulPose(new Quaternionf().rotateXYZ(
                    0f,
                    (float) ((Math.PI / 2.f) - dir * (Math.PI / 2.f - Math.PI / 6.f)),
                    (float) Math.PI
            ));

            matrixStack.translate(-0.5, 0, 0);
            matrixStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(dir * currentAngle)));
            matrixStack.translate(0.5, 0, 0);

            mc.getItemRenderer().render(
                    wingStack,
                    ItemDisplayContext.NONE,
                    false,
                    matrixStack,
                    buffer,
                    packedLight,
                    0,
                    wingModel
            );

            matrixStack.popPose();
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
