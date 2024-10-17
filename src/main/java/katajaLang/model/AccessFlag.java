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

public enum AccessFlag {
    PUBLIC,
    PROTECTED,
    PRIVATE,
    PACKAGE;

    public static boolean canAccess(String clazz1, String clazz2, AccessFlag flag){
        if(flag == PUBLIC) return true;
        if(flag == PRIVATE) return clazz1.equals(clazz2);
        if(flag == PROTECTED) return false;

        String package1 = clazz1.contains("/") ? clazz1.substring(0, clazz1.lastIndexOf('/')) : "";
        String package2 = clazz2.contains("/") ? clazz2.substring(0, clazz2.lastIndexOf('/')) : "";
        return package1.equals(package2);
    }
}
