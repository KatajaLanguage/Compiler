package katajaLang.input;

import java.util.ArrayList;

final class ArgumentHandler{

    static final class Argument{
        final boolean isCommand;
        final String argument;

        Argument(String argument){
            this.argument = argument;
            isCommand = argument.startsWith("-");
        }
    }

    private final Argument[] args;
    private int index;

    ArgumentHandler(String[] arguments){
        ArrayList<Argument> args = new ArrayList<>();

        for(String arg:arguments)
            if(!arg.trim().isEmpty())
                args.add(new Argument(arg.trim()));

        this.args = args.toArray(new Argument[0]);
        index = -1;
    }

    Argument advance(){
        if(!hasNext())
            throw new RuntimeException("No next Argument available");

        return args[++index];
    }

    boolean hasNext(){
        return index < args.length - 1;
    }

    boolean hasNextParameter(){
        if(!hasNext())
            return false;

        return !args[index + 1].isCommand;
    }
}
