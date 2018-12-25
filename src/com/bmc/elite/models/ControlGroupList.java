package com.bmc.elite.models;

import java.util.ArrayList;
import java.util.List;

public class ControlGroupList extends ArrayList<ControlGroup> {
    public List<String> allControls = new ArrayList<>();

    public ControlGroupList(List<ControlGroup> controlGroupList) {
        super(controlGroupList);
        for(ControlGroup controlGroup : controlGroupList) {
            allControls.addAll(controlGroup.controls);
        }
    }
}
