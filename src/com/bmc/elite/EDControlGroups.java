package com.bmc.elite;

import com.logitech.gaming.LogiLED;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EDControlGroups {
    public static HashMap<Integer[], String[]> MAIN_CONTROLS = new HashMap<Integer[], String[]>() {
        {
            put(EDColors.MOVEMENT_SPEED, new String[] {
                EDControls.IncreaseEnginesPower, EDControls.ForwardKey, EDControls.BackwardKey,
                EDControls.UseBoostJuice, EDControls.SetSpeedMinus100, EDControls.SetSpeedMinus75,
                EDControls.SetSpeedMinus50, EDControls.SetSpeedMinus25, EDControls.SetSpeedZero, EDControls.SetSpeed25,
                EDControls.SetSpeed50, EDControls.SetSpeed75, EDControls.SetSpeed100, EDControls.OrderHoldPosition
            });
            put(EDColors.MOVEMENT_SECONDARY, new String[] {
                EDControls.RollLeftButton, EDControls.RollRightButton, EDControls.PitchUpButton,
                EDControls.PitchDownButton, EDControls.LeftThrustButton,  EDControls.RightThrustButton,
                EDControls.UpThrustButton, EDControls.DownThrustButton, EDControls.ForwardThrustButton,
                EDControls.BackwardThrustButton, EDControls.OrderFollow
            });
            put(EDColors.HUD, new String[] {
                EDControls.FocusLeftPanel, EDControls.FocusCommsPanel, EDControls.QuickCommsPanel,
                EDControls.FocusRadarPanel, EDControls.FocusRightPanel, EDControls.OrderAggressiveBehaviour
            });
            put(EDColors.NAVIGATION, new String[] {
                EDControls.GalaxyMapOpen, EDControls.SystemMapOpen, EDControls.TargetNextRouteSystem,
                EDControls.HyperSuperCombination, EDControls.Supercruise, EDControls.Hyperspace,
                EDControls.OrderRequestDock
            });
            put(EDColors.SHIP_STUFF, new String[] {
                EDControls.ToggleFlightAssist, EDControls.ToggleCargoScoop, EDControls.LandingGearToggle,
                EDControls.ShipSpotLightToggle, EDControls.MODIFIER
            });
            put(EDColors.DEFENCE, new String[] {
                EDControls.IncreaseSystemsPower, EDControls.OrderDefensiveBehaviour, EDControls.FireChaffLauncher,
                EDControls.UseShieldCell, EDControls.DeployHeatSink, EDControls.ChargeECM
            });
            put(EDColors.OFFENCE, new String[] {
                EDControls.DeployHardpointToggle, EDControls.SelectHighestThreat, EDControls.CycleFireGroupPrevious,
                EDControls.CycleFireGroupNext, EDControls.IncreaseWeaponsPower, EDControls.OrderFocusTarget,
                EDControls.CycleNextSubsystem, EDControls.CyclePreviousSubsystem, EDControls.CycleNextHostileTarget,
                EDControls.CyclePreviousHostileTarget
            });
            put(EDColors.WING, new String[] {
                EDControls.OrderHoldFire, EDControls.TargetWingman0, EDControls.TargetWingman1,
                EDControls.TargetWingman2, EDControls.SelectTargetsTarget, EDControls.WingNavLock
            });
            put(EDColors.MODE_ENABLE, new String[] {
                EDControls.PlayerHUDModeToggle, EDControls.ExplorationFSSEnter
            });
            put(EDColors.MODE_DISABLE, new String[] {});
            put(EDColors.CAMERA, new String[] {});
        }
    };

    public static final HashMap<Integer, HashMap<Integer[], String[]>> UI_MODE_CONTROLS =
        new HashMap<Integer, HashMap<Integer[], String[]>>() {
            {
                put(EDGuiFocus.NONE, MAIN_CONTROLS);
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
