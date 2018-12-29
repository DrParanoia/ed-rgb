package com.bmc.elite.mappings;

import com.bmc.elite.StatusState;
import com.bmc.elite.callbacks.StatusConditionCallback;
import com.bmc.elite.journal.JournalStatus;
import com.bmc.elite.journal.enums.FighterStatus;
import com.bmc.elite.models.ControlGroup;
import com.bmc.elite.models.ControlGroupList;
import com.bmc.elite.status.Flags;
import com.bmc.elite.status.GuiFocus;

import java.util.*;

public class ControlGroups {

    static JournalStatus journalStatus = JournalStatus.getInstance();

    public static ControlGroupList MAIN_CONTROLS = new ControlGroupList(Arrays.asList(
        new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
            Controls.ForwardKey, Controls.BackwardKey, Controls.IncreaseEnginesPower, Controls.SetSpeedZero,
            Controls.SetSpeed25, Controls.SetSpeed50, Controls.SetSpeed75, Controls.SetSpeed100
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET
        })),
        new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
            Controls.SetSpeedMinus100, Controls.SetSpeedMinus75, Controls.SetSpeedMinus50,
            Controls.SetSpeedMinus25, Controls.AutoBreakBuggyButton
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.SUPERCRUISE
        })),
        new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
            Controls.OrderHoldPosition
        )), new StatusState(() -> journalStatus.getFighterStatus() != FighterStatus.None)),

        new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
            Controls.UseBoostJuice
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.SUPERCRUISE, Flags.LANDING_GEAR, Flags.CARGO_SCOOP
        })),
        new ControlGroup(Colors.MOVEMENT_SECONDARY, new ArrayList<>(Arrays.asList(
            Controls.RollLeftButton, Controls.RollRightButton, Controls.PitchUpButton, Controls.PitchDownButton
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET
        })),
        new ControlGroup(Colors.MOVEMENT_SECONDARY, new ArrayList<>(Arrays.asList(
            Controls.LeftThrustButton,  Controls.RightThrustButton, Controls.UpThrustButton, Controls.DownThrustButton,
            Controls.ForwardThrustButton, Controls.BackwardThrustButton
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.SUPERCRUISE
        })),
        new ControlGroup(Colors.MOVEMENT_SECONDARY, new ArrayList<>(Arrays.asList(
            Controls.OrderFollow
        )), new StatusState(() -> journalStatus.getFighterStatus() != FighterStatus.None)),
        new ControlGroup(Colors.UI, new ArrayList<>(Arrays.asList(
            Controls.FocusLeftPanel, Controls.FocusCommsPanel, Controls.QuickCommsPanel,
            Controls.FocusRadarPanel, Controls.FocusRightPanel, Controls.UI_Select, Controls.PlayerHUDModeToggle
        ))),
        new ControlGroup(Colors.UI, new ArrayList<>(Arrays.asList(
            Controls.UI_Left, Controls.UI_Right, Controls.UI_Up, Controls.UI_Down
        )), new StatusState(new int[] {
            Flags.DOCKED
        })),
        new ControlGroup(Colors.UI, new ArrayList<>(Arrays.asList(
            Controls.UI_Left, Controls.UI_Right, Controls.UI_Up, Controls.UI_Down
        )), new StatusState(new int[] {
            Flags.LANDED_PLANET
        })),
        new ControlGroup(Colors.UI, new ArrayList<>(Arrays.asList(
            Controls.OrderAggressiveBehaviour
        )), new StatusState(() -> journalStatus.getFighterStatus() != FighterStatus.None)),

        new ControlGroup(Colors.NAVIGATION, new ArrayList<>(Arrays.asList(
            Controls.GalaxyMapOpen, Controls.SystemMapOpen, Controls.TargetNextRouteSystem
        ))),
        new ControlGroup(Colors.NAVIGATION, new ArrayList<>(Arrays.asList(
            Controls.HyperSuperCombination, Controls.Supercruise, Controls.Hyperspace
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.MASS_LOCK, Flags.LANDING_GEAR, Flags.HARDPOINTS, Flags.CARGO_SCOOP
        })),
        new ControlGroup(Colors.NAVIGATION, new ArrayList<>(Arrays.asList(
            Controls.OrderRequestDock
        )), new StatusState(() -> journalStatus.getFighterStatus() != FighterStatus.None)),
        new ControlGroup(Colors.SHIP_STUFF, new ArrayList<>(Arrays.asList(
            Controls.ShipSpotLightToggle, Controls.HeadlightsBuggyButton, Controls.MODIFIER, Controls.NightVisionToggle
        ))),
        new ControlGroup(Colors.SHIP_STUFF, new ArrayList<>(Arrays.asList(
            Controls.ToggleFlightAssist, Controls.ToggleCargoScoop, Controls.ToggleCargoScoop_Buggy,
            Controls.LandingGearToggle
        )), new StatusState(null, new int[] {
             Flags.DOCKED, Flags.LANDED_PLANET, Flags.SUPERCRUISE
        })),
        new ControlGroup(Colors.DEFENCE, new ArrayList<>(Arrays.asList(
            Controls.IncreaseSystemsPower, Controls.UseShieldCell, Controls.DeployHeatSink, Controls.ChargeECM
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET
        })),
        new ControlGroup(Colors.DEFENCE, new ArrayList<>(Arrays.asList(
            Controls.FireChaffLauncher
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.SUPERCRUISE
        })),
        new ControlGroup(Colors.DEFENCE, new ArrayList<>(Arrays.asList(
            Controls.OrderDefensiveBehaviour
        )), new StatusState(() -> journalStatus.getFighterStatus() != FighterStatus.None)),

        new ControlGroup(Colors.OFFENCE, new ArrayList<>(Arrays.asList(
            Controls.CycleFireGroupPrevious
        ))),
        new ControlGroup(Colors.OFFENCE, new ArrayList<>(Arrays.asList(
            Controls.IncreaseWeaponsPower, Controls.CycleFireGroupNext, Controls.SelectHighestThreat,
            Controls.CycleNextSubsystem, Controls.CyclePreviousSubsystem, Controls.CycleNextHostileTarget,
            Controls.CyclePreviousHostileTarget
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET
        })),
        new ControlGroup(Colors.OFFENCE, new ArrayList<>(Arrays.asList(
            Controls.DeployHardpointToggle
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.SUPERCRUISE
        })),
        new ControlGroup(Colors.OFFENCE, new ArrayList<>(Arrays.asList(
            Controls.OrderFocusTarget
        )), new StatusState(() -> journalStatus.getFighterStatus() != FighterStatus.None)),

        new ControlGroup(Colors.WING, new ArrayList<>(Arrays.asList(
            Controls.TargetWingman0, Controls.TargetWingman1,
            Controls.TargetWingman2, Controls.SelectTargetsTarget, Controls.WingNavLock
        )), new StatusState(new int[] {
            Flags.IN_WING
        })),
        new ControlGroup(Colors.WING, new ArrayList<>(Arrays.asList(
            Controls.OrderHoldFire
        )), new StatusState(() -> journalStatus.getFighterStatus() != FighterStatus.None)),

        new ControlGroup(Colors.MODE_ENABLE, new ArrayList<>(Arrays.asList(
            Controls.ExplorationFSSEnter
        )), new StatusState(
            new int[] {
                Flags.SUPERCRUISE, Flags.HUD_DISCOVERY_MODE
            },
            new int[] {
                Flags.DOCKED, Flags.LANDED_PLANET
            })
        )
    ));

    public static ControlGroupList SYSTEM_MAP = new ControlGroupList(Arrays.asList(
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

    public static ControlGroupList GALAXY_MAP = new ControlGroupList(SYSTEM_MAP) {
        {
            add(new ControlGroup(Colors.MOVEMENT_SECONDARY, new ArrayList<>(Arrays.asList(
                Controls.CamPitchUp, Controls.CamPitchDown, Controls.CamTranslateUp, Controls.CamTranslateDown,
                Controls.CamYawLeft, Controls.CamYawRight
            ))));
        }
    };

    public static ControlGroupList UI_PANELS = new ControlGroupList(MAIN_CONTROLS) {
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

    public static final HashMap<Integer, ControlGroupList> UI_MODE_CONTROLS =
        new HashMap<Integer, ControlGroupList>() {
            {
                put(GuiFocus.NONE, MAIN_CONTROLS);
                put(GuiFocus.MAP_SYSTEM, SYSTEM_MAP);
                put(GuiFocus.MAP_GALAXY, GALAXY_MAP);
                put(GuiFocus.MAP_ORRERY, get(GuiFocus.MAP_GALAXY));

                put(GuiFocus.PANEL_SYSTEMS, UI_PANELS);
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
