package com.MapEditor.game;

import com.RPGproject.game.UserInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class textProcessor {
    String fileName;
    String tileName;
    boolean fileLoad;
    boolean fileSaving;

    public textProcessor(){
        fileName="";
    }

    public void fileSave(Map currentMap, UserInterface ui) {
        System.out.println("hi");
    	try {
            int tileCount = 0;
            String tileType = "!";
            if (currentMap.getFileName() == "") {
                ui.getMainText().input("Enter new map name");
            }
            else {
                FileWriter fileWriter = new FileWriter(currentMap.getFileName());
                for (ArrayList<String> A : currentMap.getMapContent()) {
                    for (String S : A) {
                        if (tileType.equals("!")) {
                            tileType = S;
                        } else if (tileType.equals(S)) {
                            tileCount++;
                        } else {
                            if (tileType.equals("")){
                                tileType="space";
                            }
                            fileWriter.write(tileCount + " " + tileType);
                            fileWriter.write("\n");
                            tileType = S;
                            tileCount = 1;
                        }
                    }
                    fileWriter.write("\n");
                }
                fileWriter.close();
            }
        }
    	catch (IOException e){
                e.printStackTrace();
            }
    	
    }
    
    public Map fileChoice(String newFileName,Map currentMap){
        newFileName=newFileName+".txt";
        System.out.println(newFileName);
        try{
            File file = new File(newFileName);
                Scanner lineReader = new Scanner(file);
                String line = "";
                boolean check = true;
                int row=0;
                System.out.println("File: "+file.getAbsoluteFile());
                currentMap = new Map(fileName);
                while (lineReader.hasNextLine() && (check)){
                	
                    line = lineReader.nextLine();
                    System.out.println(line);
                    if(line.equals("")){
                        currentMap.getMapContent().add(new ArrayList<String>());
                        row++;
                        System.out.println("new line");
                    }
                    else {
                        String tile = line.substring((line.indexOf(" ")+1));
                        if (tile.equals("space")){
                            tile = "";
                        }
                        int num = Integer.parseInt(line.substring(0, line.indexOf(" ")));
                        for (int i = 0; i < num; i++) {

                            currentMap.getMapContent().get(row).add(tile);
                            System.out.println(currentMap.getMapContent().get(row));
                        }
                    }
                }
                lineReader.close();
                System.out.println("It is working");
                currentMap.setFileName(this.fileName);
        }
        catch (Exception e){
            System.out.println("File unable to be read");
            e.printStackTrace();
            //try {
                //File file = new File(newFileName);
                //System.out.println(file.createNewFile());
                //if(file.createNewFile()) {
                //    System.out.println("file created");
                //}

            //}
            //catch(IOException e2){
                //e2.printStackTrace();
                //System.out.println("File can't be created");
            //}
        }
        return currentMap;
        //If file is not found, it will ask if you want a new file with that name.
    }

    public void processing(String text){
        text=text.toLowerCase();
        if (fileLoad){
            fileLoad = false;
            fileName = text;
            //fileChoice(text);// goes to separate method to ask for file name.
        }

        if(text.equals("save")){
        	fileSaving = true;
        	System.out.println("fileSaving is true");
            //Saves the file. Creates a backup of the old file;
        }
        else if (text.equals("load")){
            System.out.println("file loading!");
            fileLoad = true;//sets a boolean so the next entered text will load a file.

        }
        else if (text.equals("help")){
            //Lists the commands that can be typed in the console.
        }
        else if (text.equals("1")){
            this.tileName = "grass";
        }
        else if (text.equals("2")){
            this.tileName = "water";
        }
        else if (text.equals("3")){
            this.tileName = "stone";
        }
    }
}
