package com.bmc.elite.models;

import java.util.ArrayList;
import java.util.List;

public class EliteBindList extends ArrayList<EliteBind> {
    public List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        for(EliteBind bind : this) {
            keys.add(bind.eliteKey);
        }

        return keys;
    }

    public List<String> getActiveKeys() {
        List<String> keys = new ArrayList<>();
        for(EliteBind bind : this) {
            if(bind.isActive()) {
                keys.add(bind.eliteKey);
            }
        }

        return keys;
    }
}
