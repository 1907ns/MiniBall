package com.miniball.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miniball.MiniBall;


/**
 * Classe du stick du joueur 2
 */
public class StickDeux extends Sprite {

    private MiniBall game;
    private OrthographicCamera camera;
    private FitViewport fitViewport;
    private Terrain terrain;
    private float diametre;


    /**
     * Constructeur du stick 2
     * @param game jeu dans lequel le joeur est un acteur
     * @param camera nécessaire au positionnement du joueur
     * @param fitViewport nécessaire au positionnement du joueur
     * @param terrain terrain sur lequel est positionné le joueur
     */
    public StickDeux(MiniBall game, OrthographicCamera camera, FitViewport fitViewport, Terrain terrain){
        this.game = game;
        this.camera = camera;
        this.fitViewport=fitViewport;
        this.terrain=terrain;
        Texture texture = new Texture("images/Pad.png");
        diametre=texture.getWidth()*(float)0.1;
        this.setRegion(texture);
        this.setSize();
        this.setPosIni();
    }

    /**
     * Setter de la taille du stick
     */
    public void setSize(){
        this.setSize(diametre,diametre);
    }


    /**
     * Setter de la position initiale du stick
     */
    public void setPosIni(){
        this.setX(0+terrain.getWidth()/2f );
        this.setY(0-diametre/2f);
    }
}
