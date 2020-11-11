package com.MapEditor.game;

import com.RPGproject.game.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class textProcessor {
    private String fileName;
    private String tileName;
    private boolean fileLoad;
    private boolean fileSaving;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    public boolean isFileLoad() {
        return fileLoad;
    }

    public void setFileLoad(boolean fileLoad) {
        this.fileLoad = fileLoad;
    }

    public boolean isFileSaving() {
        return fileSaving;
    }

    public boolean setFileSaving(boolean fileSaving) {
        this.fileSaving = fileSaving;
        return fileSaving;
    }

    public textProcessor(){
        fileName="";
    }

    public void fileSave(Map currentMap, UserInterface ui) {
        System.out.println("hi");
    	try {
            int tileCount = 1;
            String tileType = "!";
            System.out.println("FileName: "+currentMap.getFileName());
            if (currentMap.getFileName() == "") {
                ui.getMainText().input("Enter new map name");
            }
            else {
                FileWriter fileWriter = new FileWriter(currentMap.getFileName());
                for (ArrayList<String> A : currentMap.getMapContent()) {
                    for (String S : A) {
                    	if(S.equals("")) {
                    		S="space";
                    	}
                        if (tileType.equals("!")) {
                            tileType = S;
                        }
                        else if (tileType.equals(S)) {
                            tileCount++;
                        }
                        else {
                            fileWriter.write(tileCount + " " + tileType);
                            fileWriter.write("\n");
                            tileType = S;
                            tileCount = 1;
                            }
                        }
                    fileWriter.write(tileCount+" "+tileType +"\n"+"\n");
                    tileCount=1;
                    tileType="!";
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
                currentMap = new Map(newFileName);
                while (lineReader.hasNextLine() && (check)){
                    line = lineReader.nextLine();
                    if(line.equals("")){
                        currentMap.getMapContent().add(new ArrayList<String>());
                        row++;
                    }
                    else {
                        String tile = line.substring((line.indexOf(" ")+1));
                        if (tile.equals("space")){
                            tile = "";
                        }
                        int num = Integer.parseInt(line.substring(0, line.indexOf(" ")));
                        for (int i = 0; i < num; i++) {
                            currentMap.getMapContent().get(row).add(tile);
                        }
                    }
                }
                lineReader.close();
                System.out.println("It is working");
                currentMap.setFileName(newFileName);
        }
        catch (Exception e){
            System.out.println("File unable to be read");
            e.printStackTrace();
        }
        return currentMap;
    }

    public static int menuDialogue(String inputText, String menuTier,UserInterface ui){
        int choice;
        int entry = 1;
        try {
            File text = new File("menu.txt");
            Scanner myReader = new Scanner(text);
            String data = myReader.nextLine();
            while(myReader.hasNextLine() && !data.equals(menuTier) ){
                data = myReader.nextLine();
            }
            ui.setMenuText();
            while(myReader.hasNextLine()){
                data = myReader.nextLine();
                ui.getMenuText().input(entry+": "+data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
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
