package com.MapEditor.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Map {
    private ArrayList<ArrayList<String>> mapContent;
    int originX;
    int originY;
    String fileName;

    public void setMapContent() {
        this.mapContent = mapContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

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

    public Map(String fileName) {
        this.fileName = fileName;
        this.originX=0;
        this.originY=0;
        this.mapContent = new ArrayList<>();
        this.mapContent.add(new ArrayList<String>());
    }
    public ArrayList<ArrayList<String>> getMapContent() {
        return mapContent;
    }

    public void tileAdd(int x, int y, String tileName) {
        int newX = (x-(Gdx.graphics.getWidth()/2));
        int newY = (-(y-Gdx.graphics.getHeight()/2));
        int counter = 0;
        int index = 0;
        index= Math.round(((newY-originY)/32));
        while (newY<originY) {
            mapContent.add(0,new ArrayList<String>());
            newY+=32;
            counter++;
            index=0;
        }
        newY = (newY)/32 *32;
        originY-=counter*32;
        if(index>=mapContent.size()-1) {
            for(int i=mapContent.size();i<index+1;i++) {
                mapContent.add(new ArrayList<String>());
            }
        }
        try{
            mapContent.get(index).get((newX-originX)/32);
            mapContent.get(index).set((newX-originX)/32, tileName);
        }
        catch(Exception e) {
            counter = 0;
            int oldX=newX;
            while(newX<originX) {
                for(ArrayList<String> s: mapContent) {
                    s.add(0,"");
                }
                newX+=32;
                counter++;
            }
            originX-=counter*32;
            int xIndex = (int) ((oldX-originX)/32);
            if(xIndex>mapContent.get(index).size()-1) {
                for(int i=mapContent.get(index).size();i<xIndex+1;i++) {
                    mapContent.get(index).add("");
                }
            }
            mapContent.get(index).set(xIndex, tileName);
        }
    }

}
