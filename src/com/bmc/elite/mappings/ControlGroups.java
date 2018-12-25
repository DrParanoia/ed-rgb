package com.bmc.elite.mappings;

import com.bmc.elite.status.GuiFocus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ControlGroups {
    public static HashMap<Integer[], String[]> MAIN_CONTROLS = new HashMap<Integer[], String[]>() {
        {
            put(ColorGroups.MOVEMENT_SPEED, new String[] {
                Controls.IncreaseEnginesPower, Controls.ForwardKey, Controls.BackwardKey,
                Controls.UseBoostJuice, Controls.SetSpeedMinus100, Controls.SetSpeedMinus75,
                Controls.SetSpeedMinus50, Controls.SetSpeedMinus25, Controls.SetSpeedZero, Controls.SetSpeed25,
                Controls.SetSpeed50, Controls.SetSpeed75, Controls.SetSpeed100,
                Controls.OrderHoldPosition, Controls.AutoBreakBuggyButton
            });
            put(ColorGroups.MOVEMENT_SECONDARY, new String[] {
                Controls.RollLeftButton, Controls.RollRightButton, Controls.PitchUpButton,
                Controls.PitchDownButton, Controls.LeftThrustButton,  Controls.RightThrustButton,
                Controls.UpThrustButton, Controls.DownThrustButton, Controls.ForwardThrustButton,
                Controls.BackwardThrustButton, Controls.OrderFollow
            });
            put(ColorGroups.HUD, new String[] {
                Controls.FocusLeftPanel, Controls.FocusCommsPanel, Controls.QuickCommsPanel,
                Controls.FocusRadarPanel, Controls.FocusRightPanel, Controls.OrderAggressiveBehaviour,
                Controls.UI_Select, Controls.PlayerHUDModeToggle
            });
            put(ColorGroups.NAVIGATION, new String[] {
                Controls.GalaxyMapOpen, Controls.SystemMapOpen, Controls.TargetNextRouteSystem,
                Controls.HyperSuperCombination, Controls.Supercruise, Controls.Hyperspace,
                Controls.OrderRequestDock
            });
            put(ColorGroups.SHIP_STUFF, new String[] {
                Controls.ToggleFlightAssist, Controls.ToggleCargoScoop, Controls.ToggleCargoScoop_Buggy,
                Controls.LandingGearToggle, Controls.ShipSpotLightToggle, Controls.HeadlightsBuggyButton,
                Controls.MODIFIER, Controls.NightVisionToggle
            });
            put(ColorGroups.DEFENCE, new String[] {
                Controls.IncreaseSystemsPower, Controls.OrderDefensiveBehaviour, Controls.FireChaffLauncher,
                Controls.UseShieldCell, Controls.DeployHeatSink, Controls.ChargeECM
            });
            put(ColorGroups.OFFENCE, new String[] {
                Controls.DeployHardpointToggle, Controls.SelectHighestThreat, Controls.CycleFireGroupPrevious,
                Controls.CycleFireGroupNext, Controls.IncreaseWeaponsPower, Controls.OrderFocusTarget,
                Controls.CycleNextSubsystem, Controls.CyclePreviousSubsystem, Controls.CycleNextHostileTarget,
                Controls.CyclePreviousHostileTarget
            });
            put(ColorGroups.WING, new String[] {
                Controls.OrderHoldFire, Controls.TargetWingman0, Controls.TargetWingman1,
                Controls.TargetWingman2, Controls.SelectTargetsTarget, Controls.WingNavLock
            });
            put(ColorGroups.MODE_ENABLE, new String[] {
                Controls.ExplorationFSSEnter
            });
            put(ColorGroups.MODE_DISABLE, new String[] {});
            put(ColorGroups.CAMERA, new String[] {});
        }
    };
    public static HashMap<Integer[], String[]> GALAXY_MAP = new HashMap<Integer[], String[]>() {
        {
            put(ColorGroups.MOVEMENT_SPEED, new String[] {
                Controls.CamTranslateForward, Controls.CamTranslateBackward, Controls.CamTranslateLeft,
                Controls.CamTranslateRight
            });
            put(ColorGroups.MOVEMENT_SECONDARY, new String[] {
                Controls.CamPitchUp, Controls.CamPitchDown, Controls.CamTranslateUp, Controls.CamTranslateDown,
                Controls.CamYawLeft, Controls.CamYawRight, Controls.CamZoomIn, Controls.CamZoomOut
            });
            put(ColorGroups.HUD, new String[] {
                Controls.UI_Back, Controls.UI_Select
            });
            put(ColorGroups.NAVIGATION, new String[] {
                Controls.GalaxyMapOpen, Controls.SystemMapOpen
            });
        }
    };
    public static HashMap<Integer[], String[]> SYSTEM_MAP = new HashMap<Integer[], String[]>() {
        {
            put(ColorGroups.MOVEMENT_SPEED, new String[] {
                Controls.CamTranslateForward, Controls.CamTranslateBackward, Controls.CamTranslateLeft,
                Controls.CamTranslateRight
            });
            put(ColorGroups.MOVEMENT_SECONDARY, new String[] {
                Controls.CamZoomIn, Controls.CamZoomOut
            });
            put(ColorGroups.HUD, new String[] {
                Controls.UI_Back, Controls.UI_Select
            });
            put(ColorGroups.NAVIGATION, new String[] {
                Controls.GalaxyMapOpen, Controls.SystemMapOpen
            });
        }
    };


    public static final HashMap<Integer, HashMap<String, Integer[]>> UI_MODE_CONTROLS =
        new HashMap<Integer, HashMap<String, Integer[]>>() {
            {
                put(GuiFocus.NONE, getControlToColorMap(MAIN_CONTROLS));
                put(GuiFocus.MAP_GALAXY, getControlToColorMap(GALAXY_MAP));
                put(GuiFocus.MAP_SYSTEM, getControlToColorMap(SYSTEM_MAP));
            }
        };

    public static HashMap<String, Integer[]> getControlToColorMap(HashMap<Integer[], String[]> controlMap) {
        HashMap<String, Integer[]> result = new HashMap<>();

        Iterator it = controlMap.entrySet().iterator();
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
