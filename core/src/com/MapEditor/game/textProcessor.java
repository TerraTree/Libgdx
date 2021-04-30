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
        //System.out.println(newFileName);
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
                System.out.println(currentMap.getMapContent().size());
                currentMap.setFileName(newFileName);
        }
        catch (Exception e){
            System.out.println("File unable to be read");
            e.printStackTrace();
        }
        return currentMap;
    }

    public void menuDialogue(int menuTier, UserInterface ui){
        System.out.println("menuTier:" + menuTier);
        int entry = 1;
        try {
            File text = new File("text/menu.txt");
            Scanner myReader = new Scanner(text);
            String data = myReader.nextLine();
            while(myReader.hasNextLine() && !data.equals(menuTier+":") ){
                data = myReader.nextLine();
            }
            ui.setMenuText();
            data = myReader.nextLine();
            while(myReader.hasNextLine() && !data.equals("")){
                ui.getMenuText().input(entry+": "+data);
                entry++;
                data = myReader.nextLine();
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int processing(String text,int menuTier,ArrayList<String> menuContent){
        text=text.toLowerCase();
        if (text.equals("help")){
            //Lists the commands that can be typed in the console.
        }
        else {
            try {
                int intText = Integer.parseInt(text);
                		//Character.getNumericValue(text.charAt(0));
                String action = menuContent.get(intText-1).substring(menuContent.get(intText-1).indexOf(":")+2);
                if(action.equals("back")){
                    int mod = menuTier%10;
                    String menuComp = Integer.toString(menuTier);
                    //menuComp = menuComp.substring(menuComp.length())
                    menuTier = (menuTier-1-10*mod)/10;
                    if (menuTier<1){
                        menuTier=1;
                    }
                    System.out.println("menu: " + menuTier);
                }
                else {
                    menuTier=(menuTier+intText-1)*10 +1;
                    System.out.println("yooooooo"+menuTier);
                        if(menuTier == 21) {
                            fileSaving = true;
                            menuTier = 1;
                            System.out.println("it works!");
                        }
                        if(menuTier == 31) {
                            menuTier = 1;
                            System.out.println("loading file");
                            fileLoad = true;
                        }
                        if(menuTier == 141) {
                        	tileName="";
                        	menuTier = 1;
                        }
                        if(menuTier == Integer.parseInt("11"+intText+"1")) {
                        	System.out.println("yo");
                        	if(menuContent.size()>=intText) {
                        		System.out.println("nooo");
                        		menuTier = 1;
                        		System.out.println("tilename: " + tileName);
                        		tileName=menuContent.get(intText-1).substring(menuContent.get(intText-1).indexOf(":")+2);
                        		System.out.println("tileName: "+tileName);
                        	}
                        }
                    }
                } catch (Exception e) {

            }
        }
        return menuTier;
    }
}
