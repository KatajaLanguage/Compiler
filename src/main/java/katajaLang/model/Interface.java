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

import java.util.ArrayList;
import java.util.HashMap;

public final class Interface extends Compilable{

    public final HashMap<Method.MethodDesc, Method> methods = new HashMap<>();

    public Interface(Uses uses, String src, Modifier mod, ArrayList<String> interfaces){
        super(uses, src, mod, interfaces);
    }

    @Override
    public void validateTypes(String className) {
        super.validateTypes(className);
    }
}
