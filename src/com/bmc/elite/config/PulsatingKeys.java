package com.bmc.elite.config;

import com.bmc.elite.StatusState;
import com.bmc.elite.mappings.Controls;
import com.bmc.elite.status.Flags;

import java.util.HashMap;

public class PulsatingKeys extends HashMap<String, StatusState> {
    {
        put(Controls.LandingGearToggle, new StatusState(
                new int[] {Flags.LANDING_GEAR},
                new int[] {Flags.DOCKED, Flags.LANDED_PLANET}
        ));
        put(Controls.DeployHardpointToggle, new StatusState(
                new int[] {Flags.HARDPOINTS},
                new int[] {
                    Flags.DOCKED, Flags.LANDED_PLANET, Flags.SUPERCRUISE, Flags.IN_FIGHTER,
                    Flags.IN_SRV
                }
        ));
        put(Controls.ToggleCargoScoop, new StatusState(
                new int[] {Flags.CARGO_SCOOP},
                new int[] {Flags.DOCKED, Flags.LANDED_PLANET}
        ));
        put(Controls.ToggleCargoScoop_Buggy, new StatusState(
                new int[] {Flags.CARGO_SCOOP},
                new int[] {Flags.DOCKED, Flags.LANDED_PLANET}
        ));
        put(Controls.ShipSpotLightToggle, new StatusState(
                new int[] {Flags.SHIP_LIGHTS}
        ));
        put(Controls.HeadlightsBuggyButton, new StatusState(
                new int[] {Flags.SHIP_LIGHTS}
        ));
        put(Controls.NightVisionToggle, new StatusState(
                new int[] {Flags.NIGHT_VISION}
        ));
        put(Controls.AutoBreakBuggyButton, new StatusState(
                new int[] {Flags.IN_SRV, Flags.SRV_HANDBRAKE}
        ));
    }
}
