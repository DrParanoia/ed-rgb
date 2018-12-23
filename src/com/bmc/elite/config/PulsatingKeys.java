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
                new int[] {StatusFlags.DOCKED, StatusFlags.LANDED_PLANET}
        ));
        put(ControlNames.ToggleCargoScoop, new StatusState(
                new int[] {StatusFlags.CARGO_SCOOP},
                new int[] {StatusFlags.DOCKED, StatusFlags.LANDED_PLANET}
        ));
        put(ControlNames.EjectAllCargo_Buggy, new StatusState(
                new int[] {StatusFlags.CARGO_SCOOP},
                new int[] {StatusFlags.DOCKED, StatusFlags.LANDED_PLANET}
        ));
        put(ControlNames.ShipSpotLightToggle, new StatusState(
                new int[] {StatusFlags.SHIP_LIGHTS}
        ));
        put(ControlNames.HeadlightsBuggyButton, new StatusState(
                new int[] {StatusFlags.SHIP_LIGHTS}
        ));
    }
}
