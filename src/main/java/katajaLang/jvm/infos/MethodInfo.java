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

package katajaLang.jvm.infos;

import katajaLang.jvm.attribute.CodeAttribute;
import katajaLang.jvm.attribute.MethodParametersAttribute;

public final class MethodInfo implements Info{
    public final int access_flag;
    public final int name_index;
    public final int descriptor_index;
    public final MethodParametersAttribute methodParametersAttribute;
    public final CodeAttribute codeAttribute;

    public MethodInfo(int access_flag, int name_index, int descriptor_index, MethodParametersAttribute methodParametersAttribute, CodeAttribute codeAttribute){
        this.access_flag = access_flag;
        this.name_index = name_index;
        this.descriptor_index = descriptor_index;
        this.methodParametersAttribute = methodParametersAttribute;
        this.codeAttribute = codeAttribute;
    }
}
