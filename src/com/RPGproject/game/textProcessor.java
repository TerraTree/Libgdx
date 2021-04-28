package com.RPGproject.game;

import com.MapEditor.game.Map;
import com.RPGproject.game.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class textProcessor {
	private int menuTier;

	public int getMenuTier() {
		return menuTier;
	}

	public void setMenuTier(int menuTier) {
		this.menuTier = menuTier;
	}
	
    public textProcessor(){
      
    }

//    public void menuDialogue(UserInterface ui){
//        System.out.println("menuTier:" + menuTier);
//        int entry = 1;
//        try {
//            File text = new File("text/menu.txt");
//            Scanner myReader = new Scanner(text);
//            String data = myReader.nextLine();
//            while(myReader.hasNextLine() && !data.equals(menuTier+":") ){
//                data = myReader.nextLine();
//            }
//            ui.setMenuText();
//            //data = myReader.nextLine();
//            while(myReader.hasNextLine() && !data.equals("")){
//                ui.getMenuText().input(entry+": "+data);
//                entry++;
//                data = myReader.nextLine();
//            }
//            myReader.close();
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public Map fileChoice(String newFileName, Map currentMap){
        newFileName=newFileName+".txt";
        //System.out.println(newFileName);
        try{
            File file = new File(newFileName);
            Scanner lineReader = new Scanner(file);
            String line = "";
            boolean check = true;
            int row=0;
            //System.out.println("File: "+file.getAbsoluteFile());
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

    public int processing(String text,ArrayList<String> actions){
        text=text.toLowerCase();
        if (text.equals("help")){
            //Lists the commands that can be typed in the console.
        }
        String chosenAction="";
        for (String action:actions) { //checks if that specific action is usable by that character
            if(text.equals(action)){
                chosenAction = action;
            }
        }
        if(chosenAction==""){
        }
        else if(chosenAction=="attack"){
                return 1;
        }
        else if(chosenAction=="items"){
            return 2;
        }
        else if(chosenAction=="run"){
            return 3;
        }
        else if(chosenAction=="magic"){
            return 4;
        }
        return 0;
    }


}
