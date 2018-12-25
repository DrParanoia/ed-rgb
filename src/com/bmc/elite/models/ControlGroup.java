package com.bmc.elite.models;

import java.util.List;

public class ControlGroup {
    public Integer[] color;
    public List<String> controls;

    public ControlGroup(Integer[] color, List<String> controls) {
        this.color = color;
        this.controls = controls;
    }
}
