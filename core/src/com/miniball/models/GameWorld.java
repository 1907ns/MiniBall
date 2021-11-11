package com.miniball.models;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.miniball.datafactories.Sounds;
import com.miniball.views.EndScreen;
import com.miniball.views.GameScreen;


/**
 * Monde du jeu, gérant les événement du jeu
 */
public class GameWorld {

    private float largeur, longueur;
    private GameScreen gameScreen;
    private World world;
    private Ballon ballon;
    private JoueurUn joueurUn;
    private JoueurDeux joueurDeux;
    private Terrain terrain;
    private float elapsed;
    private boolean goalScored;
    private boolean displayImg;
    private Timer.Task chrono;
    private Sounds sounds;
    private Sound goalSound, shootSound;
    private int repet;
    private  int tempsRestant;


    /**
     * Constructeur du monde avec les différents acteurs
     * @param gameScreen
     */
    public GameWorld (GameScreen gameScreen){
        this.gameScreen = gameScreen;
        this.world= new World( new Vector2(0f,0f),true);
        this.terrain=new Terrain(this.gameScreen.getGame(),this.gameScreen.getCamera(), this.gameScreen.getFitViewport(),this);
        this.ballon=new Ballon(this.gameScreen.getGame(),this.gameScreen.getCamera(),this.gameScreen.getFitViewport(),this.terrain,this);
        this.joueurUn= new JoueurUn(this.gameScreen.getGame(),this.gameScreen.getCamera(),this.gameScreen.getFitViewport(),this.terrain,this);
        this.joueurDeux = new JoueurDeux(this.gameScreen.getGame(),this.gameScreen.getCamera(),this.gameScreen.getFitViewport(),this.terrain,this);
        this.elapsed=0;
        this.repet=1;
        this.goalScored=false;
        this.displayImg=false;
        this.contactListener();
        this.sounds= new Sounds();
        this.goalSound= sounds.getGoal();
        this.shootSound= sounds.getShoot();
        this.tempsRestant=180;
        this.chrono= new Timer.Task() {
            @Override
            public void run() {
                if(!displayImg){
                    --tempsRestant;
                }

            }
        };
        Timer.schedule(chrono, 1,1);
    }

    /**
     * Fonction mettant à jour le monde
     * @param delta réglé à 1/60 (60 images/secondes)
     */
    public void update(float delta){
        if(tempsRestant>0){
            delta=1/60f; //60 fps
            float aX= Gdx.input.getAccelerometerX();
            float aY = Gdx.input.getAccelerometerY();

            //getter des 2 (j1, j2) vecteurs vitesses appliqués dans le mode Desktop
            Vector2 force = new Vector2(aX , -aY);
            force.add(gameScreen.getDesktopController().getVitesse());
            Vector2 forceBis =new Vector2(aX , -aY);
            forceBis.add(gameScreen.getDesktopControllerBis().getVitesse());

            //verification des différents modes de jeu
           if(Gdx.app.getType()== Application.ApplicationType.Desktop) {
                joueurUn.applyForce(force);

                //mode 1 joueur
                if(!gameScreen.getGame().isTwoPlayers()){
                    joueurDeux.getBody().setLinearVelocity((ballon.getBody().getPosition().x - joueurDeux.getBody().getPosition().x) * 1.5f, (ballon.getBody().getPosition().y - joueurDeux.getBody().getPosition().y)* 1.5f);
                    joueurDeux.setPosToBody();
                    if(displayImg){
                        joueurDeux.getBody().setLinearVelocity(0,0);
                        joueurUn.getBody().setLinearVelocity(0,0);
                    }
                }else { //mode 2 joueurs
                    joueurDeux.applyForce(forceBis);
                    if(displayImg){
                        joueurDeux.getBody().setLinearVelocity(0,0);
                        joueurUn.getBody().setLinearVelocity(0,0);
                    }
                }
            }else if(Gdx.app.getType()== Application.ApplicationType.Android){
               if(!gameScreen.getGame().isTwoPlayers()){ //mode 1 joueur
                   joueurUn.applyForce(getGameScreen().getAndroidController().getVitesse());
                   joueurDeux.getBody().setLinearVelocity((ballon.getBody().getPosition().x - joueurDeux.getBody().getPosition().x) * 1.5f, (ballon.getBody().getPosition().y - joueurDeux.getBody().getPosition().y)* 1.5f);
                    joueurDeux.setPosToBody();
                   if(displayImg){
                       joueurDeux.getBody().setLinearVelocity(0,0);
                       joueurUn.getBody().setLinearVelocity(0,0);
                   }
               }else if(gameScreen.getGame().isTwoPlayers()){ //mode 2 joueurs
                   joueurUn.applyForce(getGameScreen().getAndroidController().getVitesse());
                   joueurDeux.applyForce(getGameScreen().getAndroidController().getVitesse2());
                   if(displayImg){
                       joueurDeux.getBody().setLinearVelocity(0,0);
                       joueurUn.getBody().setLinearVelocity(0,0);
                   }
               }


           }
           //placement du sprite du ballon par rapport à sont body
            ballon.setX(ballon.getBody().getPosition().x-ballon.getWidth()/2f);
            ballon.setY(ballon.getBody().getPosition().y-ballon.getHeight()/2f);
            this.matchBegin(delta);
            world.step(delta, 6, 2);
        }else if(tempsRestant<=0){
            //écran de fin avec un message si les 180 secondes se sont écoulées
            this.gameScreen.getGame().setScreen(new EndScreen(this.gameScreen.getGame(), this));
        }

    }


    /**
     * Getter du world du monde du jeu
     * @return world du monde du jeu
     */
    public World getWorld() {
        return world;
    }

    /**
     * Getter du joueur 1
     * @return joueur 1
     */
    public JoueurUn getJoueurUn() {
        return joueurUn;
    }

    /**
     * Getter du joueur 2
     * @return joueur 2
     */
    public JoueurDeux getJoueurDeux() {
        return joueurDeux;
    }

    /**
     * Getter du ballon
     * @return ballon
     */
    public Ballon getBallon() {
        return ballon;
    }

    /**
     * Getter du terrain
     * @return terrain
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     *  Getter du booléen qui détermine si un but est inscrit
     * @return booléen qui détermine si un but est inscrit
     */
    public boolean isGoalScored() {
        return goalScored;
    }

    /**
     *  Getter du booléen qui détermine l'image "but" est affichée
     * @return booléen qui détermine l'image "but" est affichée
     */
    public boolean displayImg() {
        return displayImg;
    }

    /**
     *  Getter du chrono de la partie
     * @return le chrono
     */
    public Timer.Task getChrono() {
        return chrono;
    }

    /**
     *  Getter du temps restant dans la partie
     * @return temps restant dans la partie
     */
    public int getTempsRestant() {
        return tempsRestant;
    }

    /**
     * Getter de l'écran de jeu
     * @return l'écran de jeu
     */
    public GameScreen getGameScreen() {
        return gameScreen;
    }

    /**
     * Fonction permettant de savoir si un but est inscrit
     * et d'adapter les différents évènements (image but, incrémentation des scores etc..
     * @param delta 1/60 -comme dans la fonction update)
     */
    public void matchBegin(float delta){
        elapsed+=Gdx.graphics.getDeltaTime();

        //affichage de l'image but pendant 3 secondes
        while(this.elapsed<3 && this.goalScored){
            this.displayImg=true;
            this.goalScored=false;
            elapsed=0;
        }
        if (!goalScored && elapsed>3){
            this.displayImg=false;
        }
        //si le joueur 2 inscrit un but
        if(ballon.getX()+ballon.getWidth()>terrain.getMaxX()/2-ballon.getWidth()){
            elapsed=0;
            this.goalScored=true;
            this.joueurDeux.setScore(this.joueurDeux.getScore()+1);
            goalSound.play(0.3f);
            this.ballon.setPosIni();
            this.joueurDeux.setPosIni();
            this.joueurUn.concedePos();
            this.gameScreen.getGame().pause();

        }
        //si le joueur 1 inscrit un but
        if(ballon.getX()-ballon.getWidth()/2f<terrain.getMinX()-terrain.getWidth()/2f){
            elapsed=0;
            this.goalScored = true;
            this.joueurUn.setScore(this.joueurUn.getScore()+1);
            goalSound.play(0.3f);
            this.joueurUn.setPosIni();
            this.ballon.setPosIni();
            this.joueurDeux.concedePos();
            this.gameScreen.getGame().pause();
        }
    }


    /**
     * Contact listener pour permettre au son "shoot" d'être joué
     * avec les contact j1/ballon et j2/ballon
     */
    public void contactListener(){
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if((contact.getFixtureA().getUserData() instanceof Ballon &&
                        contact.getFixtureB().getUserData() instanceof JoueurDeux )
                        ||
                        (contact.getFixtureA().getUserData() instanceof JoueurDeux &&
                                contact.getFixtureB().getUserData() instanceof Ballon)){
                    shootSound.play(0.3f);

                }
                else if ((contact.getFixtureA().getUserData() instanceof JoueurUn &&
                        contact.getFixtureB().getUserData() instanceof JoueurDeux )
                        ||
                        (contact.getFixtureB().getUserData() instanceof JoueurUn &&
                                contact.getFixtureA().getUserData() instanceof JoueurDeux)) {


                }else if((contact.getFixtureA().getUserData() instanceof Ballon &&
                        contact.getFixtureB().getUserData() instanceof JoueurUn )
                        ||
                        (contact.getFixtureA().getUserData() instanceof JoueurUn &&
                                contact.getFixtureB().getUserData() instanceof Ballon)){

                            shootSound.play(0.3f);
                }

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }



}
