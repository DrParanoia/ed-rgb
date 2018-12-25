package com.bmc.elite.status;

import com.bmc.elite.mappings.Controls;

import java.util.HashMap;

public class Flags {
    public static final int DOCKED                          = 1;
    public static final int LANDED_PLANET                   = 1 << 1;
    public static final int LANDING_GEAR                    = 1 << 2;
    public static final int SHIELDS_UP                      = 1 << 3;
    public static final int SUPERCRUISE                     = 1 << 4;
    public static final int FA_OFF                          = 1 << 5;
    public static final int HARDPOINTS                      = 1 << 6;
    public static final int IN_WING                         = 1 << 7;
    public static final int SHIP_LIGHTS                     = 1 << 8;
    public static final int CARGO_SCOOP                     = 1 << 9;
    public static final int SILENT_RUNNING                  = 1 << 10;
    public static final int SCOOPING                        = 1 << 11;
    public static final int SRV_HANDBRAKE                   = 1 << 12;
    public static final int SRV_TURRET                      = 1 << 13;
    public static final int SRV_UNDER_SHIP                  = 1 << 14;
    public static final int SRV_DRIVE_ASSIST                = 1 << 15;
    public static final int MASS_LOCK                       = 1 << 16;
    public static final int FSD_CHARGING                    = 1 << 17;
    public static final int FSD_COOLDOWN                    = 1 << 18;
    public static final int LOW_FUEL                        = 1 << 19; // Less than 25%
    public static final int OVERHEATING                     = 1 << 20; // More than 100%
    public static final int HAS_LAT_LONG                    = 1 << 21;
    public static final int IN_DANGER                       = 1 << 22;
    public static final int INTERDICTION                    = 1 << 23;
    public static final int IN_SHIP                         = 1 << 24;
    public static final int IN_FIGHTER                      = 1 << 25;
    public static final int IN_SRV                          = 1 << 26;
    public static final int HUD_DISCOVERY_MODE              = 1 << 27;
    public static final int NIGHT_VISION                    = 1 << 28;

    public static final HashMap<Integer, String> STATUS_TO_CONTROL = new HashMap<Integer, String>() {
        {
            put(LANDING_GEAR, Controls.LandingGearToggle);
            put(HARDPOINTS, Controls.DeployHardpointToggle);
            put(SHIP_LIGHTS, Controls.ShipSpotLightToggle);
            put(CARGO_SCOOP, Controls.ToggleCargoScoop);
        }
    };
}
