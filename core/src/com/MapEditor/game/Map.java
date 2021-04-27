package com.MapEditor.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
        this.originX=Gdx.graphics.getWidth()/2;
        this.originY=Gdx.graphics.getHeight()/2;
        this.mapContent = new ArrayList<>();
        this.mapContent.add(new ArrayList<String>());
    }
    public ArrayList<ArrayList<String>> getMapContent() {
        return mapContent;
    }

    public void tileAdd(int x, int y, String tileName) {
        y=Gdx.graphics.getHeight()-y;
    	int newX = x-originX;
        System.out.println("origin:"+originX);
        int newY = y-originY;
        System.out.println("X: "+newX);
        int counter = 0;
        int index = 0;
        if(newY<0) {
            while (newY < 0) {//if clicked above originY
                mapContent.add(0, new ArrayList<String>()); //sets arrayLists above current originY
                newY += 32;
                counter++;
                index=0;
            }
            originY -= counter * 32;
        }
        else if(newY >= 0) {//if clicked below originY
            index = (newY/32);
            System.out.println("index:"+index);
            if (index >= mapContent.size() - 1) {
                for (int i = mapContent.size(); i < index + 1; i++) {
                    mapContent.add(new ArrayList<String>());
                }
            }
        }
        try{
            mapContent.get(index).get((newX)/32);
            mapContent.get(index).set((newX)/32, tileName);
            System.out.println("simples");
        }
        catch(Exception e) {
            counter = 0;
            int xIndex=0;
            if(newX<0) {//clicked on the left side of originX
                while (newX < 0) {
                    for (ArrayList<String> s : mapContent) {
                        s.add(0, "");
                    }
                    newX += 32;
                    counter++;
                    xIndex=0;
                }
                originX-=counter*32;
            }
            else if(newX>=0) {
                System.out.println("newX: "+newX);
                System.out.println("originX:"+originX);
                xIndex = (newX) / 32;
                System.out.println("xIndex: "+xIndex);
                if (xIndex > mapContent.get(index).size() - 1) {
                    for (int i = mapContent.get(index).size(); i < xIndex + 1; i++) {
                        mapContent.get(index).add("");
                    }
                }
            }
            mapContent.get(index).set(xIndex, tileName);
        }
    }
    public void render(SpriteBatch batch){
        Texture texture;
        int column = 0;
        int row = 0;

        for (ArrayList<String> a:this.getMapContent()) {
            for (String s:a) {
                if(s=="") {
                }
                else {
                    batch.begin();
                    texture= new Texture(s+".png");
                    //System.out.println(s+".png");
                    //batch.draw(texture,100,100);
//                    System.out.println(this.getOriginX()+column*32);
//                    System.out.println(64+this.getOriginY()-row*32);
                    batch.draw(texture,this.getOriginX()+column*32,Gdx.graphics.getHeight()-(this.getOriginY()+row*32)-32);
                    //System.out.println(this.getOriginX()+column*32);
                    //System.out.println(this.getOriginY()+column*32);
                    //batch.draw(texture,(column*32)+this.getOriginX(),-this.getOriginY()-(row*32)-32);
                    batch.end();
                }
                column++;
            }
            row++;
            column=0;
        }

    }

}
