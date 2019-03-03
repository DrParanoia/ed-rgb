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
		/*---------------CAMERA---------------*/
		/*new ControlGroup(Colors.CAMERA, new ArrayList<>(Arrays.asList(
            Controls.PhotoCameraToggle, Controls.PhotoCameraToggle_Buggy, Controls.VanityCameraScrollLeft,
            Controls.VanityCameraScrollRight, Controls.ToggleFreeCam, Controls.FreeCamToggleHUD,
            Controls.FixCameraRelativeToggle, Controls.FixCameraWorldToggle, Controls.ToggleRotationLock,
            Controls.FreeCamSpeedInc, Controls.FreeCamSpeedDec
        ))),*/

		/*---------------MOVEMENT_SPEED---------------*/
		new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
			Controls.IncreaseEnginesPower
		))),
		new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
            Controls.ForwardKey, Controls.BackwardKey, Controls.SetSpeedZero,
            Controls.SetSpeed25, Controls.SetSpeed50, Controls.SetSpeed75, Controls.SetSpeed100
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET
        })),
        new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
            Controls.SetSpeedMinus100, Controls.SetSpeedMinus75, Controls.SetSpeedMinus50,
            Controls.SetSpeedMinus25
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
        new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
            Controls.IncreaseSpeedButtonMax, Controls.DecreaseSpeedButtonMax, Controls.AutoBreakBuggyButton
        )), new StatusState(new int[] {
            Flags.IN_SRV
        })),

		/*---------------MOVEMENT_SECONDARY---------------*/
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

		/*---------------UI---------------*/
		new ControlGroup(Colors.UI, new ArrayList<>(Arrays.asList(
            Controls.FocusLeftPanel, Controls.FocusCommsPanel, Controls.QuickCommsPanel,
            Controls.FocusRadarPanel, Controls.FocusRightPanel, Controls.UI_Select
        ))),
        new ControlGroup(Colors.UI, new ArrayList<>(Arrays.asList(
            Controls.UI_Left, Controls.UI_Right, Controls.UI_Up, Controls.UI_Down, Controls.UI_Select
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

		/*---------------NAVIGATION---------------*/
		new ControlGroup(Colors.NAVIGATION, new ArrayList<>(Arrays.asList(
            Controls.GalaxyMapOpen, Controls.SystemMapOpen, Controls.TargetNextRouteSystem
        ))),
        new ControlGroup(Colors.NAVIGATION, new ArrayList<>(Arrays.asList(
            Controls.HyperSuperCombination, Controls.Supercruise, Controls.Hyperspace
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.MASS_LOCK, Flags.LANDING_GEAR, Flags.HARDPOINTS, Flags.CARGO_SCOOP,
            Flags.IN_FIGHTER, Flags.IN_SRV, Flags.FSD_COOLDOWN
        })),
        new ControlGroup(Colors.NAVIGATION, new ArrayList<>(Arrays.asList(
            Controls.OrderRequestDock
        )), new StatusState(() -> journalStatus.getFighterStatus() != FighterStatus.None)),

		/*---------------SHIP_STUFF---------------*/
		new ControlGroup(Colors.SHIP_STUFF, new ArrayList<>(Arrays.asList(
			Controls.ResetPowerDistribution
		))),
        new ControlGroup(Colors.SHIP_STUFF, new ArrayList<>(Arrays.asList(
            Controls.ShipSpotLightToggle, Controls.HeadlightsBuggyButton, Controls.NightVisionToggle
        ))),
        new ControlGroup(Colors.SHIP_STUFF, new ArrayList<>(Arrays.asList(
            Controls.ToggleFlightAssist
        )), new StatusState(null, new int[] {
             Flags.DOCKED, Flags.LANDED_PLANET, Flags.SUPERCRUISE
        })),
        new ControlGroup(Colors.SHIP_STUFF, new ArrayList<>(Arrays.asList(
            Controls.ToggleCargoScoop_Buggy
        )), new StatusState(new int[] {
            Flags.IN_SRV
        }, null)),
        new ControlGroup(Colors.SHIP_STUFF, new ArrayList<>(Arrays.asList(
            Controls.ToggleCargoScoop, Controls.LandingGearToggle, Controls.ToggleButtonUpInput
        )), new StatusState(null, new int[] {
             Flags.IN_SRV, Flags.SUPERCRUISE, Flags.IN_FIGHTER, Flags.DOCKED, Flags.LANDED_PLANET
        })),

		/*---------------DEFENCE---------------*/
		new ControlGroup(Colors.DEFENCE, new ArrayList<>(Arrays.asList(
			Controls.IncreaseSystemsPower
		))),
        new ControlGroup(Colors.DEFENCE, new ArrayList<>(Arrays.asList(
            Controls.ChargeECM
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.IN_SRV
        })),
        new ControlGroup(Colors.DEFENCE, new ArrayList<>(Arrays.asList(
            Controls.FireChaffLauncher
        )), new StatusState(null, new int[]{
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.SUPERCRUISE, Flags.IN_SRV
        }, () -> journalStatus.hasChaff())),
        new ControlGroup(Colors.DEFENCE, new ArrayList<>(Arrays.asList(
            Controls.DeployHeatSink
        )), new StatusState(null, new int[]{
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.IN_SRV
        }, () -> journalStatus.hasHeatSink())),
        new ControlGroup(Colors.DEFENCE, new ArrayList<>(Arrays.asList(
            Controls.UseShieldCell
        )), new StatusState(null, new int[] {
            Flags.DOCKED, Flags.LANDED_PLANET, Flags.IN_SRV
        }, () -> journalStatus.hasShieldCellBank())),
        new ControlGroup(Colors.DEFENCE, new ArrayList<>(Arrays.asList(
            Controls.OrderDefensiveBehaviour
        )), new StatusState(() -> journalStatus.getFighterStatus() != FighterStatus.None)),

		/*---------------OFFENCE---------------*/
		new ControlGroup(Colors.OFFENCE, new ArrayList<>(Arrays.asList(
            Controls.IncreaseWeaponsPower
        ))),
        new ControlGroup(Colors.OFFENCE, new ArrayList<>(Arrays.asList(
            Controls.CycleFireGroupPrevious, Controls.CycleFireGroupNext,
            Controls.SelectHighestThreat, Controls.CycleNextSubsystem, Controls.CyclePreviousSubsystem,
            Controls.CycleNextHostileTarget, Controls.CyclePreviousHostileTarget
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

		/*---------------WING---------------*/
		new ControlGroup(Colors.WING, new ArrayList<>(Arrays.asList(
            Controls.TargetWingman0, Controls.TargetWingman1,
            Controls.TargetWingman2, Controls.SelectTargetsTarget, Controls.WingNavLock
        )), new StatusState(new int[] {
            Flags.IN_WING
        })),
        new ControlGroup(Colors.WING, new ArrayList<>(Arrays.asList(
            Controls.OrderHoldFire
        )), new StatusState(() -> journalStatus.getFighterStatus() != FighterStatus.None)),

		/*---------------MODE_ENABLE---------------*/
        new ControlGroup(Colors.MODE_ENABLE, new ArrayList<>(Arrays.asList(
            Controls.PlayerHUDModeToggle
        ))),
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

    public static ControlGroupList FSS = new ControlGroupList(Arrays.asList(
        new ControlGroup(Colors.OFFENCE, new ArrayList<>(Arrays.asList(
            Controls.ExplorationFSSTarget
        ))),
        new ControlGroup(Colors.MODE_ENABLE, new ArrayList<>(Arrays.asList(
            Controls.ExplorationFSSQuit
        ))),
        new ControlGroup(Colors.MOVEMENT_SPEED, new ArrayList<>(Arrays.asList(
            Controls.ExplorationFSSCameraPitchDecreaseButton,
            Controls.ExplorationFSSCameraPitchIncreaseButton,
            Controls.ExplorationFSSCameraYawDecreaseButton,
            Controls.ExplorationFSSCameraYawIncreaseButton
        ))),
        new ControlGroup(Colors.MOVEMENT_SECONDARY, new ArrayList<>(Arrays.asList(
            Controls.ExplorationFSSMiniZoomIn,
            Controls.ExplorationFSSMiniZoomOut,
            Controls.ExplorationFSSZoomIn,
            Controls.ExplorationFSSZoomOut
        ))),
        new ControlGroup(Colors.NAVIGATION, new ArrayList<>(Arrays.asList(
            Controls.ExplorationFSSRadioTuningX_Increase,
            Controls.ExplorationFSSRadioTuningX_Decrease
        ))),
        new ControlGroup(Colors.SHIP_STUFF, new ArrayList<>(Arrays.asList(
            Controls.ExplorationFSSDiscoveryScan
        )))
    ));
    public static ControlGroupList ADS = new ControlGroupList(Arrays.asList());

    public static final HashMap<Integer, ControlGroupList> UI_MODE_CONTROLS =
        new HashMap<Integer, ControlGroupList>() {
            {
                put(GuiFocus.NONE, MAIN_CONTROLS);
                put(GuiFocus.MAP_SYSTEM, SYSTEM_MAP);
                put(GuiFocus.MAP_GALAXY, GALAXY_MAP);
                put(GuiFocus.MAP_ORRERY, get(GuiFocus.MAP_GALAXY));

                put(GuiFocus.MODE_FSS, FSS);
                put(GuiFocus.MODE_ADS, ADS);

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
