package com.bmc.elite.models;

import com.bmc.elite.EliteKeyListener;

import java.util.ArrayList;
import java.util.List;

public class EliteBind {
    public List<String> modifiers = new ArrayList<>();
    public String eliteKey;

    public EliteBind() {}

    public EliteBind(String eliteKey) {
        this.eliteKey = eliteKey;
    }
    public EliteBind(List<String> modifiers, String eliteKey) {
        this.modifiers = modifiers;
        this.eliteKey = eliteKey;
    }

    public void addModifier(String modifier) {
        modifiers.add(modifier);
    }

    public void setKey(String eliteKey) {
        this.eliteKey = eliteKey;
    }

    public boolean isActive() {
        if(!modifiers.isEmpty()) {
            for(String modifier : modifiers) {
                if(!EliteKeyListener.isEliteKeyPressed(modifier)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder valueBuilder = new StringBuilder();

        String modifierString = String.join("+", modifiers);
        if(modifierString != null && modifierString != "") {
            valueBuilder
                .append(modifierString)
                .append("+");
        }

        valueBuilder.append(eliteKey);

        return valueBuilder.toString();
    }
}
