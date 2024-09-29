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

package katajaLang.jvm.bytecode;

import katajaLang.model.Modifier;

/**
 * List of all AccessFlags supported by the Java Virtual Machine
 */
public final class Flag {
    public static int PUBLIC       = 0x0001;
    public static int PRIVATE      = 0x0002;
    public static int PROTECTED    = 0x0004;
    public static int STATIC       = 0x0008;
    public static int FINAL        = 0x0010;
    public static int SUPER        = 0x0020;
    public static int SYNCHRONIZED = 0x0020;
    public static int VOLATILE     = 0x0040;
    public static int BRIDGE       = 0x0040;
    public static int TRANSIENT    = 0x0080;
    public static int VARARGS      = 0x0080;
    public static int NATIVE       = 0x0100;
    public static int INTERFACE    = 0x0200;
    public static int ABSTRACT     = 0x0400;
    public static int STRICT       = 0x0800;
    public static int SYNTHETIC    = 0x1000;
    public static int ANNOTATION   = 0x2000;
    public static int ENUM         = 0x4000;
    public static int MODULE       = 0x8000;

    public static int getAccessFlag(Modifier mod){
        int acc = 0;

        switch(mod.acc){
            case PUBLIC:
                acc += Flag.PUBLIC;
                break;
            case PRIVATE:
                acc += Flag.PRIVATE;
                break;
            case PROTECTED:
                acc += Flag.PROTECTED;
                break;
        }

        if(mod.abstrakt) acc += Flag.ABSTRACT;
        if(mod.finaly) acc += Flag.FINAL;

        return acc;
    }
}
