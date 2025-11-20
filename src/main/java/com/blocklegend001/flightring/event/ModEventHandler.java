package com.blocklegend001.flightring.event;

public class ModEventHandler {
    private static boolean preventFallDamage = false;

    public static void onRingRemoved() {
        preventFallDamage = true;
    }

    public static boolean shouldPreventFallDamage() {
        return preventFallDamage;
    }

    public static void resetFallDamagePrevention() {
        preventFallDamage = false;
    }
}