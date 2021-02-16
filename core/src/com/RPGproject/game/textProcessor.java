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

    public int processing(String text,ArrayList<String> menuContent){
        text=text.toLowerCase();
        if (text.equals("help")){
            //Lists the commands that can be typed in the console.
        }
        else if(text.equals("attack")){
            return -1;
        }
        else if(text.equals("items")){
            return -2;
        }
        else {
            try {
                //int intText = java.lang.Character.getNumericValue(text.charAt(0));
                //System.out.println(intText);
                //String action = menuContent.get(intText-1).substring(menuContent.get(intText-1).indexOf(":")+2);
                //System.out.println(action);
//                if(action.equals("back")){
//                    //System.out.println("true");
//                    int mod = menuTier%10;
//                    String menuComp = Integer.toString(menuTier);
//                    setMenuTier((menuTier-1-10*mod)/10);
//                    if (menuTier<1){
//                        menuTier=1;
//                    }
//                    //System.out.println("menu: " + menuTier);
//                }
            } catch (Exception e) {

            }
        }
        return 0;
    }


}
