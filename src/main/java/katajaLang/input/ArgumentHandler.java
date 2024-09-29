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

package katajaLang.input;

import java.util.ArrayList;

/**
 * Representation of an array of Arguments
 */
final class ArgumentHandler{

    /**
     * Representing an argument, that can be a command or parameter
     */
    static final class Argument{
        /**
         * Whether the argument is a command
         */
        final boolean isCommand;
        /**
         * Value of the Argument
         */
        final String argument;

        /**
         * creates a new Argument, that represents the given value
         */
        Argument(String argument){
            this.argument = argument;
            isCommand = argument.startsWith("-");
        }
    }

    private final Argument[] args;
    private int index;

    /**
     * Creates a new ArgumentHandler that represents the given array of arguments
     */
    ArgumentHandler(String[] arguments){
        ArrayList<Argument> args = new ArrayList<>();

        for(String arg:arguments)
            if(!arg.trim().isEmpty())
                args.add(new Argument(arg.trim()));

        this.args = args.toArray(new Argument[0]);
        index = -1;
    }

    /**
     * Advances to the next argument and returns it
     * @throws RuntimeException if no next argument is available
     */
    Argument advance() throws RuntimeException{
        if(!hasNext())
            throw new RuntimeException("No next Argument available");

        return args[++index];
    }

    /**
     * Returns whether it has a next argument
     */
    boolean hasNext(){
        return index < args.length - 1;
    }

    /**
     * Returns whether it has a next argument, that is a parameter
     */
    boolean hasNextParameter(){
        if(!hasNext())
            return false;

        return !args[index + 1].isCommand;
    }
}
