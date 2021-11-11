package com.miniball.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miniball.MiniBall;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


/**
 * Classe du Terrain du jeu
 */
public class Terrain extends Sprite {


    private MiniBall game;
    private OrthographicCamera camera;
    private FitViewport fitViewport;
    private Body body;
    private GameWorld gameWorld;
    Vector2[] vertices;

    /**
     * Constructeur du terrain
     * @param game jeu dans lequel le terrain est un acteur
     * @param camera nécessaire au positionnement du terrain
     * @param fitViewport nécessaire au positionnement du terrain
     * @param gameWorld monde du jeu dans lequel le terrain est impliqué
     */
    public Terrain(MiniBall game, OrthographicCamera camera, FitViewport fitViewport, GameWorld gameWorld) {
        this.game = game;
        this.camera = camera;
        this.fitViewport = fitViewport;
        Texture texture = new Texture("images/Terrain.png");
        this.setRegion(texture);
        this.setSize();
        this.setPosIni();
        this.gameWorld=gameWorld;
        vertices= new Vector2[17];
        this.setBody();
    }

    /**
     * Setter de la taille du terrain
     */
    public void setSize(){
        this.setSize(fitViewport.getWorldWidth()*(float)0.8,
                fitViewport.getWorldHeight());
    }

    /**
     * Setter de la position initiale du terrain
     */
    public  void setPosIni(){
        this.setX(0-this.getWidth()/2f);
        this.setY(0-this.getHeight()/2f);

    }

    /**
     * Setter du body du terrain
     */
    public  void setBody(){
        this.setList();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(this.getX(), this.getY());
        body = gameWorld.getWorld().createBody(bodyDef);
        ChainShape shape=new ChainShape();
        shape.createChain(vertices);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    /**
     * Setter de la liste des points utilisé dans la création de la chainShape attribuée au terrain
     */
    public void setList(){
            String filename = "models/terrain.txt";
            ClassLoader classLoader = getClass().getClassLoader();
           InputStream inp = classLoader.getResourceAsStream("models/terrain.txt");
           BufferedReader r = new BufferedReader(new InputStreamReader(Gdx.files.internal(filename).read()), 2048);
            Scanner s = new Scanner(r);
            int cpt =0;
            int i =0;
            float x=0;
            float y =0;
            while (s.hasNext()) {
                if(cpt==0){
                    String str = s.next();
                    x= Float.parseFloat(str);
                    x=x*0.82f;
                    cpt=1;
                }
                if(cpt==1){
                    String str = s.next();
                    y= Float.parseFloat(str);
                    y=y*0.76555f;
                    Vector2 vector2 = new Vector2(x,y);
                    //vector2.setLength(vector2.len()*0.8f);
                    vertices[i]=vector2;
                    ++i;
                    cpt=0;
                }
           }
            s.close();

    }

    /**
     * Getter du body du terrain
     * @return body du terrain
     */
    public Body getBody() {
        return body;
    }

    /**
     * Getter du X max du terrain utile dans la définition de
     * l'aire où le stick du joueur 1 sera actif
     * @return X max du terrain
     */
    public float getMaxX(){
        ArrayList<Float> list = new ArrayList<Float>();
        for (int i =0;i<vertices.length;i++){
            list.add(vertices[i].x);
        }
        float res = Collections.max(list);
        return res;
    }


    /**
     * Getter du X min du terrain utile dans la définition de
     * l'aire où le stick du joueur 2 sera actif
     * @return X min du terrain
     */
    public float getMinX(){
        ArrayList<Float> list = new ArrayList<Float>();
        for (int i =0;i<vertices.length;i++){
            list.add(vertices[i].x);
        }
        float res = Collections.min(list);
        return res;
    }
}
