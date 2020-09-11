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

    public textProcessor(){
        setMapContent();
    }

    public ArrayList<ArrayList<String>> getMapContent() {
        return mapContent;
    }

    public void setMapContent() {
        this.mapContent = new ArrayList<>();
        this.mapContent.add(new ArrayList<String>());
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
        }
        catch (Exception e){
            System.out.println("something went wrong");
            try {
                File file = new File(newFileName);
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
            fileChoice(text);// goes to seperate method to ask for file name.
            fileLoad = false;
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
