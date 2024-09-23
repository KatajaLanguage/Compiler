/*
 * Copyright (C) 2024 Xaver Weste
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

package katajaLang.jvm.constpool;

import java.util.ArrayList;

public final class ConstPool {

    private final ArrayList<ConstantInfo> entries = new ArrayList<>();

    public int add(ConstantInfo info){
        entries.add(info);
        return entries.size();
    }

    public int addClassInfo(String clazz){
        return entries.stream().filter(ClassInfo.class::isInstance).map(ClassInfo.class::cast).filter(obj -> ((Utf8Info)entries.get(obj.name_index - 1)).value.equals(clazz)).findFirst().map(entries::indexOf).orElseGet(() -> add(new ClassInfo((short) addUtf8Info(clazz))));
    }

    public int addUtf8Info(String string){
        return entries.stream().filter(Utf8Info.class::isInstance).map(Utf8Info.class::cast).filter(obj -> obj.value.equals(string)).findFirst().map(entries::indexOf).orElseGet(() -> add(new Utf8Info(string)));
    }

    public ConstantInfo[] getEntries(){
        return entries.toArray(new ConstantInfo[0]);
    }

    public int getSize(){
        return entries.size() + 1;
    }
}
