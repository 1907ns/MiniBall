package com.miniball.models;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miniball.MiniBall;


/**
 * Classe Ballon
 */
public class Ballon extends Sprite {

    private MiniBall game;
    private OrthographicCamera camera;
    private FitViewport fitViewport;
    private Terrain terrain;
    private Body body;
    private GameWorld gameWorld;
    private BodyDef bodyDef;


    /**
     * Constructeur du ballon
     * @param game jeu dans lequel le ballon est un acteur
     * @param camera nécessaire au positionnement du ballon
     * @param fitViewport nécessaire au positionnement du ballon
     * @param terrain terrain sur lequel est positionné le ballon
     * @param gameWorld monde du jeu dans lequel le ballon est impliqué
     */
    public Ballon(MiniBall game, OrthographicCamera camera, FitViewport fitViewport, Terrain terrain,GameWorld gameWorld){
        this.game = game;
        this.camera = camera;
        this.fitViewport=fitViewport;
        this.terrain=terrain;
        Texture texture = new Texture("images/Ballon.png");
        this.setRegion(texture);
        this.setSize();
        //this.setPosIni();
        this.gameWorld = gameWorld;
        this.setBody();
    }


    /**
     * Setter de la taille du ballon
     */
    public void setSize(){
        float rayonB =terrain.getWidth()/85;
        this.setSize(rayonB*2, rayonB*2);
    }

    /**
     * Setter de la position initiale du ballon
     */
    public void setPosIni(){
        body.setTransform(0,0,body.getAngle());
        body.setLinearVelocity(0,0);

        this.setPosition(body.getPosition().x-this.getWidth()/2f,body.getPosition().y-this.getHeight()/2f);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }


    /**
     * Setter du body du ballon
     */
    public void setBody(){
        this.setX(0-this.getWidth()/2f);
        this.setY(0-this.getHeight()/2f);
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body = gameWorld.getWorld().createBody(bodyDef);
        this.setPosition(body.getPosition().x-this.getWidth()/2f,body.getPosition().y-this.getHeight()/2f);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.f;
        fixtureDef.friction = 1.5f;
        fixtureDef.restitution = 0.5f;
        body.setLinearDamping(1.5f);
        CircleShape shape = new CircleShape();

        float rayonB =terrain.getWidth()/85;
        shape.setRadius(rayonB);
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }


    /**
     * Getter du body du ballon
     * @return body du ballon
     */
    public Body getBody() {
        return body;
    }
}
