/*
 * Copyright (C) 2024 ni271828mand
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License 3.0 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package katajaLang.model;

import java.util.HashMap;

public final class Uses {
    private final HashMap<String, String> uses = new HashMap<>();
    private final HashMap<String, String> statics = new HashMap<>();
    private HashMap<String, String> typeValues = null;

    public boolean addUse(String alias, String path){
        if(containsAlias(path)) return true;

        uses.put(alias, path);
        return false;
    }

    public boolean addStatic(String alias, String path){
        if(containsAlias(path)) return true;

        statics.put(alias, path);
        return false;
    }

    public boolean containsValue(String path){
        for(String value: uses.values()) if(value.equals(path)) return true;
        for(String value: statics.values()) if(value.equals(path)) return true;
        return false;
    }

    public boolean containsAlias(String alias){
        for(String value: uses.keySet()) if(value.equals(alias)) return true;
        for(String value: statics.keySet()) if(value.equals(alias)) return true;
        return false;
    }

    public String get(String alias){
        if(uses.containsKey(alias)) return uses.get(alias);
        if(statics.containsKey(alias)) return statics.get(alias);
        return null;
    }

    public String[] getStatics(){
        return statics.keySet().toArray(new String[0]);
    }
}
