package com.bmc.elite;

import com.bmc.elite.animations.AnimatedKey;
import com.bmc.elite.animations.AnimationHelper;
import com.bmc.elite.config.Application;
import com.bmc.elite.config.PipPresets;
import com.bmc.elite.config.PulsatingKeys;
import com.bmc.elite.lists.HidKeysList;
import com.bmc.elite.mappings.Colors;
import com.bmc.elite.mappings.ControlGroups;
import com.bmc.elite.mappings.Controls;
import com.bmc.elite.lists.LogitechKeysList;
import com.bmc.elite.models.*;
import com.bmc.elite.status.Flags;
import com.bmc.elite.status.GuiFocus;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.util.*;

import static com.bmc.elite.KeyTools.dimEliteKeyFromColorArray;

public class KeyColorService {

	Gson gson;
	JsonReader jsonReader;

	private static volatile KeyColorService instance = null;

	public static synchronized KeyColorService getInstance() {
		if(instance == null) {
			instance = new KeyColorService();
		}
		return instance;
	}

	private KeyColorService() {
		gson = new Gson();
		initColors();
	}

	public static boolean isBitSet(long bitmask, int flag) {
		return (bitmask & flag) == flag;
	}

	public static HashMap<String, EliteBindList> eliteBindings;

	public static HashMap<String, Integer[]> MAIN_CONTROLS = ControlGroups.getControlToColorMap(ControlGroups.MAIN_CONTROLS);

	AnimationHelper animationHelper = AnimationHelper.getInstance();

	ControlGroupList currentControlGroups;
	Status currentStatus = null;

	public PulsatingKeys pulsatingKeys = new PulsatingKeys();

	public void initColors() {
		initColors(false);
	}
	public void initColors(boolean resetBindings) {
		eliteBindings = BindingParser.getBindings(resetBindings);
		updateStatus();
	}

	public void setColorsFromBindings(Status newStatus) {

		if(animationHelper.isAnimating()) {
			return;
		}

		if(ControlGroups.UI_MODE_CONTROLS.containsKey(newStatus.GuiFocus)) {
			currentControlGroups = ControlGroups.UI_MODE_CONTROLS.get(newStatus.GuiFocus);
		} else {
			currentControlGroups = ControlGroups.UI_MODE_CONTROLS.get(GuiFocus.NONE);
		}

		List<Integer> remainingLogitechKeys = new LogitechKeysList();
		List<Integer> remainingHidKeys = new HidKeysList();

		EliteBindList eliteBindList;
		List<Integer> setHidKeys;
		HashMap<Integer, AnimatedKey> animatedKeys = new HashMap<>();
		for(ControlGroup controlGroup : currentControlGroups) {
			if(controlGroup.neededStatus != null && !controlGroup.neededStatus.conditionSatisfied(newStatus)) {
				continue;
			}
			for(String control : controlGroup.controls) {
				eliteBindList = eliteBindings.get(control);
				if(eliteBindList != null) {
					if(Arrays.equals(controlGroup.color, Colors.OTHER)) {
						continue;
					}

					if(pulsatingKeys.containsKey(control)) {
						StatusState statusState = pulsatingKeys.get(control);
						if(
							currentControlGroups.allControls.contains(control)
							&& statusState.conditionSatisfied(newStatus)
						) {
							setHidKeys = KeyTools.setEliteKeysPulseFromColorArrays(
								eliteBindList.getActiveKeys(),
								Colors.OTHER,
								MAIN_CONTROLS.get(control),
								Application.PULSE_DURATION,
								true
							);
						} else {
							if(KeyTools.isColorCacheEmpty()) {
								setHidKeys = KeyTools.setEliteKeysFromColorArray(
									eliteBindList.getActiveKeys(),
									MAIN_CONTROLS.get(control)
								);
							} else {
								setHidKeys = KeyTools.getHidsFromEliteKeys(eliteBindList.getActiveKeys());
								for(Integer hidKey : setHidKeys) {
									Integer[] currentColor = KeyTools.getCurrentKeyHidColor(hidKey);
									if(currentColor != null) {
										animatedKeys.put(hidKey, new AnimatedKey(
											hidKey,
											Colors.percentageArrayToInteger(currentColor),
											Colors.percentageArrayToInteger(MAIN_CONTROLS.get(control))
										));
									} else {
										KeyTools.setKeyFromColorArray(hidKey, MAIN_CONTROLS.get(control));
									}
								}
							}
						}
					} else {
						if(KeyTools.isColorCacheEmpty()) {
							setHidKeys = KeyTools.setEliteKeysFromColorArray(eliteBindList.getActiveKeys(), controlGroup.color);
						} else {
							setHidKeys = KeyTools.getHidsFromEliteKeys(eliteBindList.getActiveKeys());
							for(Integer hidKey : setHidKeys) {
								Integer[] currentColor = KeyTools.getCurrentKeyHidColor(hidKey);
								if(currentColor != null) {
									animatedKeys.put(hidKey, new AnimatedKey(
										hidKey,
										Colors.percentageArrayToInteger(currentColor),
										Colors.percentageArrayToInteger(controlGroup.color)
									));
								} else {
									KeyTools.setKeyFromColorArray(hidKey, controlGroup.color);
								}
							}
						}
					}
					remainingHidKeys.removeAll(setHidKeys);
				}
			}
		}

		for(Integer hidKey : remainingHidKeys) {
			Integer[] currentColor = KeyTools.getCurrentKeyHidColor(hidKey);
			if(currentColor != null) {
				animatedKeys.put(hidKey, new AnimatedKey(
					hidKey,
					Colors.percentageArrayToInteger(currentColor),
					Colors.percentageArrayToInteger(Colors.OTHER)
				));
			} else {
				KeyTools.setKeyFromColorArray(hidKey, Colors.OTHER);
			}
		}
		for(Integer logitechKey : remainingLogitechKeys) {
			KeyTools.setKeyFromColorArray(logitechKey, Colors.OTHER, true);
		}

		if(!animatedKeys.isEmpty()) {
			animationHelper.pulseKeys(animatedKeys, 300, false);
		}

		setToggleKeyColors(newStatus);
	}

	public void updateStatus() {
		updateStatus(FileUtils.readFile(Application.STATUS_FILE_PATH));
	}

	public void updateStatus(String statusJson) {
		try {
			Status newStatus = gson.fromJson(statusJson, Status.class);
			if(newStatus == null) {
				if(Application.DEBUG) LogUtils.log("Cannot get status! " + statusJson);
				return;
			}
			setColorsFromBindings(newStatus);
			currentStatus = newStatus;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setToggleKeyColors(Status newStatus) {
		// Custom PIP presets for G keys
		for (Map.Entry<Integer, Integer[]> pipPreset : PipPresets.STATUS_TO_CONTROL.entrySet()) {
			if(Arrays.equals(pipPreset.getValue(), newStatus.Pips)) {
				KeyTools.setKeyFromColorArray(
					pipPreset.getKey(),
					PipPresets.PIP_PRESET_COLORS.get(pipPreset.getKey()),
					true
				);
			} else {
				KeyTools.setKeyFromColorArray(
					pipPreset.getKey(),
					PipPresets.PIP_PRESET_COLORS_DISABLED.get(pipPreset.getKey()),
					true
				);
			}
		}

		double m = 0.075;
		double c = 0.4;

		try {
			dimEliteKeyFromColorArray(eliteBindings.get(Controls.IncreaseSystemsPower).getActiveKeys().get(0), MAIN_CONTROLS.get(Controls.IncreaseSystemsPower), m * newStatus.Pips[0] + c);
			dimEliteKeyFromColorArray(eliteBindings.get(Controls.IncreaseEnginesPower).getActiveKeys().get(0), MAIN_CONTROLS.get(Controls.IncreaseEnginesPower), m * newStatus.Pips[1] + c);
			dimEliteKeyFromColorArray(eliteBindings.get(Controls.IncreaseWeaponsPower).getActiveKeys().get(0), MAIN_CONTROLS.get(Controls.IncreaseWeaponsPower), m * newStatus.Pips[2] + c);
		} catch (Exception ignored) {}

		// Change HUD Mode key color based on current HUD mode (Discovery/Combat)
		if (currentControlGroups.allControls.contains(Controls.PlayerHUDModeToggle)) {
			if (isBitSet(newStatus.Flags, Flags.HUD_DISCOVERY_MODE)) {
				KeyTools.setEliteKeysFromColorArray(
					eliteBindings.get(Controls.PlayerHUDModeToggle).getActiveKeys(),
					Colors.HUD_MODE_DISCOVERY
				);
			} else {
				KeyTools.setEliteKeysFromColorArray(
					eliteBindings.get(Controls.PlayerHUDModeToggle).getActiveKeys(),
					Colors.HUD_MODE_COMBAT
				);
			}
		}
	}
}
