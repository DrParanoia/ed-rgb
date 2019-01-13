package com.bmc.elite.models;

import com.bmc.elite.listeners.EliteKeyListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EliteBind {
    private List<String> modifiers = new ArrayList<>();
    public String eliteKey;

    public EliteBind() {}

    public EliteBind(String eliteKey) {
        this.eliteKey = eliteKey;
    }
    public EliteBind(List<String> modifiers, String eliteKey) {
        this.modifiers = modifiers;
        this.eliteKey = eliteKey;
        Collections.sort(this.modifiers);
    }

    public List<String> getModifiers() {
        return this.modifiers;
    }
    public void addModifier(String modifier) {
        this.modifiers.add(modifier);
        Collections.sort(this.modifiers);
    }

    public void setKey(String eliteKey) {
        this.eliteKey = eliteKey;
    }

    public boolean hasModifiers() {
        return !this.modifiers.isEmpty();
    }
    public boolean allModifiersPressed() {
        if(!modifiers.isEmpty()) {
            for(String modifier : modifiers) {
                if(!EliteKeyListener.isEliteKeyPressed(modifier)) {
                    return false;
                }
            }
        }

        return true;
    }
    public boolean isActive() {
        if(hasModifiers()) {
            return EliteKeyListener.allowedModifierBinds.contains(toString());
        }

        return true;
    }

    public String getModifierString() {
        return String.join("+", modifiers);
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
