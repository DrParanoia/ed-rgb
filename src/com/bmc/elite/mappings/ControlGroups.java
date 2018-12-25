package com.bmc.elite.mappings;

import com.bmc.elite.models.ControlGroup;
import com.bmc.elite.status.GuiFocus;

import java.util.*;

public class ControlGroups {

    public static List<ControlGroup> MAIN_CONTROLS = new ArrayList<>(Arrays.asList(
            new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
                Controls.IncreaseEnginesPower, Controls.ForwardKey, Controls.BackwardKey,
                Controls.UseBoostJuice, Controls.SetSpeedMinus100, Controls.SetSpeedMinus75,
                Controls.SetSpeedMinus50, Controls.SetSpeedMinus25, Controls.SetSpeedZero, Controls.SetSpeed25,
                Controls.SetSpeed50, Controls.SetSpeed75, Controls.SetSpeed100,
                Controls.OrderHoldPosition, Controls.AutoBreakBuggyButton
            ))),
            new ControlGroup(Colors.MOVEMENT_SECONDARY, new ArrayList<>(Arrays.asList(
                Controls.RollLeftButton, Controls.RollRightButton, Controls.PitchUpButton,
                Controls.PitchDownButton, Controls.LeftThrustButton,  Controls.RightThrustButton,
                Controls.UpThrustButton, Controls.DownThrustButton, Controls.ForwardThrustButton,
                Controls.BackwardThrustButton, Controls.OrderFollow
            ))),
            new ControlGroup(Colors.UI, new ArrayList<>(Arrays.asList(
                Controls.FocusLeftPanel, Controls.FocusCommsPanel, Controls.QuickCommsPanel,
                Controls.FocusRadarPanel, Controls.FocusRightPanel, Controls.OrderAggressiveBehaviour,
                Controls.UI_Select, Controls.PlayerHUDModeToggle
             ))),
            new ControlGroup(Colors.NAVIGATION, new ArrayList<>(Arrays.asList(
                Controls.GalaxyMapOpen, Controls.SystemMapOpen, Controls.TargetNextRouteSystem,
                Controls.HyperSuperCombination, Controls.Supercruise, Controls.Hyperspace,
                Controls.OrderRequestDock
             ))),
            new ControlGroup(Colors.SHIP_STUFF, new ArrayList<>(Arrays.asList(
                Controls.ToggleFlightAssist, Controls.ToggleCargoScoop, Controls.ToggleCargoScoop_Buggy,
                Controls.LandingGearToggle, Controls.ShipSpotLightToggle, Controls.HeadlightsBuggyButton,
                Controls.MODIFIER, Controls.NightVisionToggle
             ))),
            new ControlGroup(Colors.DEFENCE, new ArrayList<>(Arrays.asList(
                Controls.IncreaseSystemsPower, Controls.OrderDefensiveBehaviour, Controls.FireChaffLauncher,
                Controls.UseShieldCell, Controls.DeployHeatSink, Controls.ChargeECM
             ))),
            new ControlGroup(Colors.OFFENCE, new ArrayList<>(Arrays.asList(
                Controls.DeployHardpointToggle, Controls.SelectHighestThreat, Controls.CycleFireGroupPrevious,
                Controls.CycleFireGroupNext, Controls.IncreaseWeaponsPower, Controls.OrderFocusTarget,
                Controls.CycleNextSubsystem, Controls.CyclePreviousSubsystem, Controls.CycleNextHostileTarget,
                Controls.CyclePreviousHostileTarget
             ))),
            new ControlGroup(Colors.WING, new ArrayList<>(Arrays.asList(
                Controls.OrderHoldFire, Controls.TargetWingman0, Controls.TargetWingman1,
                Controls.TargetWingman2, Controls.SelectTargetsTarget, Controls.WingNavLock
             ))),
            new ControlGroup(Colors.MODE_ENABLE, new ArrayList<>(Arrays.asList(
                Controls.ExplorationFSSEnter
            ))),
            new ControlGroup(Colors.MODE_DISABLE, new ArrayList<>(Arrays.asList())),
            new ControlGroup(Colors.CAMERA, new ArrayList<>(Arrays.asList()))
    ));

    public static List<ControlGroup> SYSTEM_MAP = new ArrayList<>(Arrays.asList(
            new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
                Controls.CamTranslateForward, Controls.CamTranslateBackward, Controls.CamTranslateLeft,
                Controls.CamTranslateRight
            ))),
            new ControlGroup(Colors.MOVEMENT_SECONDARY, new ArrayList<>(Arrays.asList(
                Controls.CamZoomIn, Controls.CamZoomOut
            ))),
            new ControlGroup(Colors.UI, new ArrayList<>(Arrays.asList(
                Controls.UI_Back, Controls.UI_Select
            ))),
            new ControlGroup(Colors.NAVIGATION, new ArrayList<>(Arrays.asList(
                Controls.GalaxyMapOpen, Controls.SystemMapOpen
            )))
    ));

    public static List<ControlGroup> GALAXY_MAP = new ArrayList<ControlGroup>(SYSTEM_MAP) {
        {
            add(new ControlGroup(Colors.MOVEMENT_SECONDARY, new ArrayList<>(Arrays.asList(
                Controls.CamPitchUp, Controls.CamPitchDown, Controls.CamTranslateUp, Controls.CamTranslateDown,
                Controls.CamYawLeft, Controls.CamYawRight
            ))));
        }
    };

    public static List<ControlGroup> UI_PANELS = new ArrayList<ControlGroup>(MAIN_CONTROLS) {
        {
            add(new ControlGroup(Colors.UI, new ArrayList<>(Arrays.asList(
                Controls.UI_Left, Controls.UI_Right, Controls.UI_Up, Controls.UI_Down,
                Controls.UI_Select, Controls.UI_Back
            ))));
            add(new ControlGroup(Colors.UI_ALT, new ArrayList<>(Arrays.asList(
                Controls.CyclePreviousPanel, Controls.CycleNextPanel
            ))));
        }
    };

    public static final HashMap<Integer, LinkedHashMap<String, Integer[]>> UI_MODE_CONTROLS =
        new HashMap<Integer, LinkedHashMap<String, Integer[]>>() {
            {
                put(GuiFocus.NONE, getControlToColorMap(MAIN_CONTROLS));
                put(GuiFocus.MAP_SYSTEM, getControlToColorMap(SYSTEM_MAP));
                put(GuiFocus.MAP_GALAXY, getControlToColorMap(GALAXY_MAP));
                put(GuiFocus.MAP_ORRERY, get(GuiFocus.MAP_GALAXY));

                put(GuiFocus.PANEL_SYSTEMS, getControlToColorMap(UI_PANELS));
                put(GuiFocus.PANEL_NAV, get(GuiFocus.PANEL_SYSTEMS));
                put(GuiFocus.PANEL_COMS, get(GuiFocus.PANEL_SYSTEMS));
                put(GuiFocus.PANEL_ROLE, get(GuiFocus.PANEL_SYSTEMS));
                put(GuiFocus.STATION_SERVICES, get(GuiFocus.PANEL_SYSTEMS));
            }
        };

    public static LinkedHashMap<String, Integer[]> getControlToColorMap(List<ControlGroup> controlGroups) {
        LinkedHashMap<String, Integer[]> result = new LinkedHashMap<>();

        for(ControlGroup controlGroup : controlGroups) {
            for(String control : controlGroup.controls) {
                result.put(control, controlGroup.color);
            }
        }

        return result;
    }
}
