package katajaLang.input;

import katajaLang.compiler.CompilerConfig;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Scanner;

public final class InputHandler {

    private final Scanner scanner;
    private ArgumentHandler argHandler;

    public InputHandler(String[] args){
        scanner = new Scanner(System.in);

        if(args.length == 0) {
            System.out.println("run '-h' for help");
            nextLine();
        }else{
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
        if(!argHandler.hasNext()){
            nextLine();
            return;
        }

        boolean quit = false;

        if(argHandler.hasNextParameter()){
            System.err.println("Expected command, got value '"+argHandler.advance().argument+"'");
        }else{
            String arg = argHandler.advance().argument;
            switch(arg){
                case "-q":
                    quit = true;
                    break;
                case "-t":
                    setTarget();
                    break;
                case "-d":
                    setDebug();
                    break;
                case "-o":
                    setOutFolder();
                    break;
                case "-h":
                    printHelp();
                    break;
                default:
                    System.err.println("Unknown Command '"+arg+"', run '-h' for a list of all valid commands");
                    break;
            }
        }

        if(!quit) executeNext();
    }

    private void setTarget(){
        if(argHandler.hasNextParameter()){
            String value = argHandler.advance().argument;
            CompilerConfig.TargetType type = CompilerConfig.TargetType.ofString(value);

            if(type == null) System.err.println("Type '"+value+"' is not supported");
            else CompilerConfig.targetType = type;
        }else System.err.println("Expected String Value for target option");
    }

    private void setDebug(){
        if(argHandler.hasNextParameter()){
            String value = argHandler.advance().argument;

            if(value.equals("true"))
                CompilerConfig.debug = true;
            else if(value.equals("false"))
                CompilerConfig.debug = false;
            else
                System.err.println("Expected boolean value, got '"+value+"'");
        }else System.err.println("Expected boolean Value for debug option");
    }

    private void setOutFolder(){
        if(argHandler.hasNextParameter()){
            String path = argHandler.advance().argument;

            try {
                CompilerConfig.outFolder = Paths.get(path);
            }catch (InvalidPathException ignored){
                System.err.println("Expected filepath got '"+path+"'");
            }
        }else System.err.println("Expected boolean Value for output option");
    }

    private void printHelp(){
        System.out.println("<----------Help---------->");
        System.out.println("General Info:");
        System.out.println("\nAvailable target types:");
        System.out.println("class\njar");
        System.out.println("\nAvailable commands:");
        System.out.println("-d <boolean> : enable debug");
        System.out.println("-h           : print help");
        System.out.println("-o <string>  : set out put folder");
        System.out.println("-q           : quit Compiler");
        System.out.println("-t <string>  : set target type");
        System.out.println("<------------------------>");
    }
}