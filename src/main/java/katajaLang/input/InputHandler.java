package katajaLang.input;

import katajaLang.compiler.CompilerConfig;

import java.util.Scanner;

public final class InputHandler {

    private final Scanner scanner;
    private ArgumentHandler argHandler;

    public InputHandler(String[] args){
        scanner = new Scanner(System.in);

        if(args.length == 0)
            nextLine();
        else{
            argHandler = new ArgumentHandler(args);
            executeNext();
        }
    }

    private void nextLine(){
        System.out.print("> ");

        argHandler = new ArgumentHandler(scanner.nextLine().split(" "));

        executeNext();
    }

    private void executeNext(){
        if(!argHandler.hasNext()) nextLine();

        boolean quit = false;

        if(argHandler.hasNextParameter()){
            System.err.println("Expected command, got value '"+argHandler.advance().argument+"'");
        }else{
            switch(argHandler.advance().argument){
                case "-q":
                    quit = true;
                    break;
                case "-t":

                case "-d":
                    setDebug();
                    break;
            }
        }

        if(!quit) executeNext();
    }

    private void setTarget(){

    }

    private void setDebug(){
        if(argHandler.hasNextParameter()){
            String value = argHandler.advance().argument;

            if(value.equals("true"))
                CompilerConfig.debug = true;
            else if(value.equals("false"))
                CompilerConfig.debug = false;
            else
                System.err.println("Expected boolean value, got "+value);
        }else System.err.println("Expected boolean Value for debug option");
    }
}
