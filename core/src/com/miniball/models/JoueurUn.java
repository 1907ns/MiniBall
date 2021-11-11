package com.miniball.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miniball.MiniBall;

/**
 * Classe du joueur 1 qui hérite de la classe Sprite
 */
public class JoueurUn extends Sprite {

    private MiniBall game;
    private OrthographicCamera camera;
    private FitViewport fitViewport;
    private Terrain terrain;
    private int score;
    private Body body;
    private GameWorld gameWorld;


    /**
     * Constructeur du joueur 1
     * @param game jeu dans lequel le joueur est un acteur
     * @param camera nécessaire au positionnement du joueur
     * @param fitViewport nécessaire au positionnement du joueur
     * @param terrain terrain sur lequel est positionné le joueur
     * @param gameWorld monde du jeu dans lequel le joueur est impliqué
     */
    public JoueurUn(MiniBall game, OrthographicCamera camera, FitViewport fitViewport, Terrain terrain,GameWorld gameWorld){
        this.game = game;
        this.camera = camera;
        this.fitViewport=fitViewport;
        this.terrain=terrain;
        Texture texture = new Texture("images/JoueurDroite.png");
        this.setRegion(texture);
        this.setSize();
        this.score=0;
        this.gameWorld=gameWorld;
        this.setBody();
    }

    /**
     * Setter du rayon du joueur
     */
    public void setSize(){
        float rayon = terrain.getWidth()*(float)0.025;
        this.setSize(rayon*2,rayon*2);
    }

    /**
     * Setter de la position initiale du joueur
     */
    public void setPosIni(){
        body.setTransform(fitViewport.getWorldWidth()/4f,0,body.getAngle());
        body.setLinearVelocity(0,0);
        this.setPosition(body.getPosition().x-this.getWidth()/2f,body.getPosition().y-this.getHeight()/2f);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    /**
     * Setter de la position du joueur après avoir encaissé un but
     */
    public void concedePos(){
        body.setTransform(terrain.getWidth()/8,0,body.getAngle());
        body.setLinearVelocity(0,0);
        this.setPosition(body.getPosition().x-this.getWidth()/2f,body.getPosition().y-this.getHeight()/2f);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    /**
     * Getter du score du joueur
     * @return score du joueur
     */
    public int getScore() {
        return score;
    }


    /**
     * Setter du score du joueur
     * @param score nouveau score
     */
    public void setScore(int score) {
        this.score = score;
    }


    /**
     * Setter du body du joueur
     */
    public  void setBody(){
        float rayon = terrain.getWidth()*(float)0.025;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(fitViewport.getWorldWidth()/4f, 0);
        body = gameWorld.getWorld().createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        this.setPosition(body.getPosition().x-this.getWidth()/2f,body.getPosition().y-this.getHeight()/2f);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        fixtureDef.density = 1.f;
        fixtureDef.friction = 1.5f;
        fixtureDef.restitution = 0.25f;
        body.setLinearDamping(1.5f);
        CircleShape shape = new CircleShape();
        shape.setRadius(rayon);
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }


    /**
     * Fonction qui applique un vecteur au body du joueur
     * @param force vecteur appliqué
     */
    public void applyForce(Vector2 force){
        body.applyForce(force, body.getPosition(), true);
        this.setX(body.getPosition().x-this.getWidth()/2f);
        this.setY(body.getPosition().y-this.getHeight()/2f);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }


    /**
     * getter du body du joueur
     * @return body du joueur
     */
    public Body getBody() {
        return body;
    }
}
