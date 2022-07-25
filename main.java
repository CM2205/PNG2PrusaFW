/**
 * 
 * PNG2PrusaFW v0.1
 * by Rad Projects 
 * 25 July 2022
 * 
 * 
 * Simple java script to convert all pngs to .c array files. 
 * 
 * Code for cmd execution is inspired by this example: 
 * https://stackoverflow.com/questions/15464111/run-cmd-commands-through-java
 * Question by Reham
 * Answer by Luke Woodward
 * 
 * The licensing terms for the code below is as follows: 
 * Anyone may use parts or all of the code below for personal or 
 * commercial uses as long as I am credited in the cooresponding 
 * heading comment and/or README. 
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class main {


    static String introPrompt= "Absolute path of the root directory of prusa mini source code: "; 

    static String commonPath;
    static String pngPath;
    static String ccPath;

    public static void main(String[] args) throws IOException, InterruptedException{
        
        introPrompt(); 
        commonPath = getPath();
        pngPath = commonPath + "\\src\\gui\\res\\png";
        ccPath = commonPath + "\\src\\gui\\res\\cc";
        convertFromPNG();

    }


    private static void introPrompt() {
        System.out.println(introPrompt);
    }

    private static String getPath() throws IOException{

        Scanner scnr = new Scanner(System.in);
        String path = scnr.nextLine();
        scnr.close();
        File testPathFile = new File(path);
        System.out.println(path);
        if (!path.contains("" + '\\') && !path.contains("/")) {
            System.err.println("Invalid absolute path. Path should be as folllows:" + '\n' + "C:\\Users\\Shrek\\Documents");
            throw new IOException();
        }        return path;
    }

    private static void convertFromPNG() {

        long startTime = System.nanoTime();
        long nanoToSec = 1000000000;

        File pngDir = new File(pngPath);
        File[] pngs = pngDir.listFiles();
        int count = 0;
        final String PREFIX_PNG = "png_";

        System.out.println("Converting files");
        String dots = "_ ";

        for (File pngElem : pngs) {
        
            System.out.println(dots);
            if (dots.length() == 16) {
                System.out.println('\n' + "Converting files");
                dots = "";
            }
            String pngName = pngElem.getName();
            pngName = pngName.substring(0, pngName.indexOf('.'));
            //pngName = pngName.split(".")[0];
            String convCMD = "python " + "./utils/bin2cc.py " + "./src/gui/res/png/" + pngName + ".png "  + "./src/gui/res/cc/" + PREFIX_PNG + pngName + ".c " + PREFIX_PNG + pngName;    
            ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe","/c", "cd " + commonPath + " && " + convCMD);
            builder.redirectErrorStream(true);
            try {
                Process p = builder.start();
                p.waitFor();
            } catch(IOException e) {
                System.err.println("Builder could not start.");
            } catch (InterruptedException ie) {
                System.err.println("Process did not execute.");
            }            
            count++;
            dots = dots + "_ ";
        }

        System.out.println('\n' + "Converted " + count + " files!" + '\n' + '[' + ((System.nanoTime() - startTime)/nanoToSec) + " seconds]" + '\n');
    }

}
