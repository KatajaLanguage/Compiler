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

package katajaLang.jvm;

import katajaLang.jvm.bytecode.Flag;
import katajaLang.model.AccessFlag;
import katajaLang.model.Class;

public class JavaClass extends Class {

    public JavaClass(AccessFlag accessFlag) {
        super(accessFlag);
    }

    public int getAccessFlag(){
        int accessFlag = 0;

        switch(this.accessFlag){
            case PUBLIC:
                accessFlag += Flag.PUBLIC;
                break;
            case PROTECTED:
                accessFlag += Flag.PROTECTED;
                break;
            case PRIVATE:
                accessFlag += Flag.PRIVATE;
                break;
        }

        return accessFlag;
    }
}
