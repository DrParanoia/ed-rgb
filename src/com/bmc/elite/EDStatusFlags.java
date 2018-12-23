package com.bmc.elite;

import java.util.HashMap;

public class EDStatusFlags {
    public static final int HUD_DISCOVERY_MODE = 1 << 27;
    public static final int LANDING_GEAR = 1 << 2;
    public static final int SUPERCRUISE = 1 << 4;
    public static final int HARDPOINTS = 1 << 6;
    public static final int SHIP_LIGHTS = 1 << 8;
    public static final int CARGO_SCOOP = 1 << 9;

    public static final HashMap<Integer, String> STATUS_TO_CONTROL = new HashMap<Integer, String>() {
        {
            put(LANDING_GEAR, EDControls.LandingGearToggle);
            put(HARDPOINTS, EDControls.DeployHardpointToggle);
            put(SHIP_LIGHTS, EDControls.ShipSpotLightToggle);
            put(CARGO_SCOOP, EDControls.ToggleCargoScoop);
        }
    };
}
