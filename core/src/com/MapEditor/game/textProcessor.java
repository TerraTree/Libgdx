package com.MapEditor.game;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class textProcessor {
    String fileName;
    boolean fileLoad;
    private ArrayList<ArrayList<String>> mapContent;
    int originX;
    int originY;

    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }

    public textProcessor(){
        setMapContent();
    }

    public ArrayList<ArrayList<String>> getMapContent() {
        return mapContent;
    }

    public void setMapContent() {
        originX=0;
        originY=0;
        this.mapContent = new ArrayList<>();
        this.mapContent.add(new ArrayList<String>());
    }

    public void tileAdd(String currentTile, int x, int y) {
    	int tileX = (x / 20)*20;
    	int tileY = (y / 20)*20;
    	//if (originY< 0){

        //}
    	getMapContent().add(new ArrayList<String>() );
    	ArrayList<String> row = mapContent.get(0);
    	//System.out.println("X:"+tileX+" Y:"+tileY);
    	for (int i=0;i<tileY-20;i=i+20) {
    		row.add("");
    	}
    	row.add(currentTile);
    	System.out.println(row);
    	
    }
    
    public void fileChoice(String newFileName){

        try{
            File file = new File(newFileName);
                Scanner lineReader = new Scanner(file);
                String line = "";
                boolean check = true;
                int row=0;
                setMapContent();
                while (lineReader.hasNextLine() && (check)){
                    line = lineReader.nextLine();
                    System.out.println(line);
                    if(line.equals("")){
                        this.getMapContent().add(new ArrayList<String>());
                        row++;
                        System.out.println("new line");
                    }
                    else {
                        int num = Integer.parseInt(line.substring(0, line.indexOf(" ")));
                        for (int i = 0; i < num; i++) {
                            this.getMapContent().get(row).add(line.substring(line.indexOf(" ") + 1));
                            System.out.println(this.getMapContent().get(row));
                        }
                    }
                }
                lineReader.close();
                System.out.println("It is working");
        }
        catch (Exception e){
            System.out.println("something went wrong");
            try {
                File file = new File(newFileName);
                System.out.println(file.createNewFile());
                if(file.createNewFile()) {
                    System.out.println("file created");
                }

            }
            catch(IOException e2){
                e2.printStackTrace();
                System.out.println("File can't be created");
            }
        }
        //If file is not found, it will ask if you want a new file with that name.
    }

    public void processing(String text){

        text=text.toLowerCase();
        if (fileLoad){
            //System.out.println(text);
            fileLoad = false;
            fileChoice(text);// goes to separate method to ask for file name.
        }

        if(text.equals("save")){
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

        }
        else if (text.equals("2")){

        }
    }
}
