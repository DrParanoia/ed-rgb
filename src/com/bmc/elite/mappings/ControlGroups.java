package com.bmc.elite.mappings;

import com.bmc.elite.values.StatusGuiFocus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ControlGroups {
    public static HashMap<Integer[], String[]> MAIN_CONTROLS = new HashMap<Integer[], String[]>() {
        {
            put(ColorGroups.MOVEMENT_SPEED, new String[] {
                ControlNames.IncreaseEnginesPower, ControlNames.ForwardKey, ControlNames.BackwardKey,
                ControlNames.UseBoostJuice, ControlNames.SetSpeedMinus100, ControlNames.SetSpeedMinus75,
                ControlNames.SetSpeedMinus50, ControlNames.SetSpeedMinus25, ControlNames.SetSpeedZero, ControlNames.SetSpeed25,
                ControlNames.SetSpeed50, ControlNames.SetSpeed75, ControlNames.SetSpeed100, ControlNames.OrderHoldPosition
            });
            put(ColorGroups.MOVEMENT_SECONDARY, new String[] {
                ControlNames.RollLeftButton, ControlNames.RollRightButton, ControlNames.PitchUpButton,
                ControlNames.PitchDownButton, ControlNames.LeftThrustButton,  ControlNames.RightThrustButton,
                ControlNames.UpThrustButton, ControlNames.DownThrustButton, ControlNames.ForwardThrustButton,
                ControlNames.BackwardThrustButton, ControlNames.OrderFollow
            });
            put(ColorGroups.HUD, new String[] {
                ControlNames.FocusLeftPanel, ControlNames.FocusCommsPanel, ControlNames.QuickCommsPanel,
                ControlNames.FocusRadarPanel, ControlNames.FocusRightPanel, ControlNames.OrderAggressiveBehaviour
            });
            put(ColorGroups.NAVIGATION, new String[] {
                ControlNames.GalaxyMapOpen, ControlNames.SystemMapOpen, ControlNames.TargetNextRouteSystem,
                ControlNames.HyperSuperCombination, ControlNames.Supercruise, ControlNames.Hyperspace,
                ControlNames.OrderRequestDock
            });
            put(ColorGroups.SHIP_STUFF, new String[] {
                ControlNames.ToggleFlightAssist, ControlNames.ToggleCargoScoop, ControlNames.LandingGearToggle,
                ControlNames.ShipSpotLightToggle, ControlNames.MODIFIER
            });
            put(ColorGroups.DEFENCE, new String[] {
                ControlNames.IncreaseSystemsPower, ControlNames.OrderDefensiveBehaviour, ControlNames.FireChaffLauncher,
                ControlNames.UseShieldCell, ControlNames.DeployHeatSink, ControlNames.ChargeECM
            });
            put(ColorGroups.OFFENCE, new String[] {
                ControlNames.DeployHardpointToggle, ControlNames.SelectHighestThreat, ControlNames.CycleFireGroupPrevious,
                ControlNames.CycleFireGroupNext, ControlNames.IncreaseWeaponsPower, ControlNames.OrderFocusTarget,
                ControlNames.CycleNextSubsystem, ControlNames.CyclePreviousSubsystem, ControlNames.CycleNextHostileTarget,
                ControlNames.CyclePreviousHostileTarget
            });
            put(ColorGroups.WING, new String[] {
                ControlNames.OrderHoldFire, ControlNames.TargetWingman0, ControlNames.TargetWingman1,
                ControlNames.TargetWingman2, ControlNames.SelectTargetsTarget, ControlNames.WingNavLock
            });
            put(ColorGroups.MODE_ENABLE, new String[] {
                ControlNames.PlayerHUDModeToggle, ControlNames.ExplorationFSSEnter
            });
            put(ColorGroups.MODE_DISABLE, new String[] {});
            put(ColorGroups.CAMERA, new String[] {});
        }
    };

    public static final HashMap<Integer, HashMap<Integer[], String[]>> UI_MODE_CONTROLS =
        new HashMap<Integer, HashMap<Integer[], String[]>>() {
            {
                put(StatusGuiFocus.NONE, MAIN_CONTROLS);
            }
        };

    public static HashMap<String, Integer[]> getControlToColorMap() {
        HashMap<String, Integer[]> result = new HashMap<>();

        Iterator it = MAIN_CONTROLS.entrySet().iterator();
        Map.Entry pair;
        while (it.hasNext()) {
            pair = (Map.Entry) it.next();
            for(String control : (String[])pair.getValue()) {
                result.put(control, (Integer[])pair.getKey());
            }
        }

        return result;
    }
}
