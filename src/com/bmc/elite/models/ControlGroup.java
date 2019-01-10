package com.bmc.elite.models;

import com.bmc.elite.StatusState;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ControlGroup {
    public Integer[] color;
    public List<String> controls;
    public StatusState neededStatus;

    private LinkedHashMap<String, Integer[]> controlToColor = new LinkedHashMap<>();

    public ControlGroup(Integer[] color, List<String> controls) {
        this(color, controls, null);
    }
    public ControlGroup(Integer[] color, List<String> controls, StatusState neededStatusState) {
        this.color = color;
        this.controls = controls;
        this.neededStatus = neededStatusState;

        for(String control : controls) {
            controlToColor.put(control, color);
        }
    }

    public LinkedHashMap<String, Integer[]> getControlToColorMap() {
        return controlToColor;
    }
}
