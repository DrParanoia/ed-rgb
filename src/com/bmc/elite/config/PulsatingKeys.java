package com.bmc.elite.config;

import com.bmc.elite.StatusState;
import com.bmc.elite.mappings.ControlNames;
import com.bmc.elite.values.StatusFlags;

import java.util.HashMap;

public class PulsatingKeys extends HashMap<String, StatusState> {
    {
        put(ControlNames.LandingGearToggle, new StatusState(
                new int[] {StatusFlags.LANDING_GEAR},
                new int[] {StatusFlags.DOCKED, StatusFlags.LANDED_PLANET}
        ));
        put(ControlNames.DeployHardpointToggle, new StatusState(
                new int[] {StatusFlags.HARDPOINTS},
                new int[] {
                    StatusFlags.DOCKED, StatusFlags.LANDED_PLANET, StatusFlags.SUPERCRUISE, StatusFlags.IN_FIGHTER,
                    StatusFlags.IN_SRV
                }
        ));
        put(ControlNames.ToggleCargoScoop, new StatusState(
                new int[] {StatusFlags.CARGO_SCOOP},
                new int[] {StatusFlags.DOCKED, StatusFlags.LANDED_PLANET}
        ));
        put(ControlNames.ToggleCargoScoop_Buggy, new StatusState(
                new int[] {StatusFlags.CARGO_SCOOP},
                new int[] {StatusFlags.DOCKED, StatusFlags.LANDED_PLANET}
        ));
        put(ControlNames.ShipSpotLightToggle, new StatusState(
                new int[] {StatusFlags.SHIP_LIGHTS}
        ));
        put(ControlNames.HeadlightsBuggyButton, new StatusState(
                new int[] {StatusFlags.SHIP_LIGHTS}
        ));
        put(ControlNames.NightVisionToggle, new StatusState(
                new int[] {StatusFlags.NIGHT_VISION}
        ));
        put(ControlNames.AutoBreakBuggyButton, new StatusState(
                new int[] {StatusFlags.IN_SRV, StatusFlags.SRV_HANDBRAKE}
        ));
    }
}
