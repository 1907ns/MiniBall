package com.miniball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miniball.MiniBall;
import com.miniball.controllers.AndroidController;
import com.miniball.controllers.DesktopController;
import com.miniball.controllers.DesktopControllerBis;
import com.miniball.datafactories.FontPerso;
import com.miniball.models.Ballon;
import com.miniball.models.GameWorld;
import com.miniball.models.JoueurDeux;
import com.miniball.models.JoueurUn;
import com.miniball.models.StickDeux;
import com.miniball.models.StickUn;
import com.miniball.models.Terrain;


/**
 * Classe de l'écran de jeu
 */
public class GameScreen extends ScreenAdapter {
    private MiniBall game;

    private StickUn stick1;
    private StickDeux stick2;
    private BitmapFont font;
    private float longueur, largeur;
    private FitViewport fitViewport;
    private GlyphLayout score1, score2, timer;
    private OrthographicCamera camera;
    private Texture goalTexture;
    private DesktopController desktopController;
    private DesktopControllerBis desktopControllerBis;
    private AndroidController androidController;
    private GameWorld gameWorld;
    private Box2DDebugRenderer box2DDebugRenderer;


    /**
     * Constructeur
     * @param game jeu auquel l'écran de partie est relié
     */
    public GameScreen(MiniBall game){
        this.game = game;
        this.longueur=(float)76.4;
        this.largeur=(float)102.4;
        FontPerso fontPerso = new FontPerso();
        this.font = new BitmapFont();
        this.font = fontPerso.getFont();

        this.camera = new OrthographicCamera(largeur,longueur);
        this.fitViewport=new FitViewport(largeur,longueur,camera);

        this.gameWorld=new GameWorld(this);

        this.stick1= new StickUn(this.game,this.camera,this.fitViewport,this.gameWorld.getTerrain());
        this.stick2= new StickDeux(this.game,this.camera,this.fitViewport,this.gameWorld.getTerrain());

        this.timer = new GlyphLayout(font, String.valueOf(gameWorld.getTempsRestant()));
        this.score1 = new GlyphLayout(font, "0");
        this.score2 = new GlyphLayout(font, "0");
        this.goalTexture=new Texture(Gdx.files.internal("images/But.bmp"));
        this.desktopController= new DesktopController(gameWorld);
        this.desktopControllerBis= new DesktopControllerBis(gameWorld);
        this.androidController = new AndroidController(gameWorld);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(desktopController);
        inputMultiplexer.addProcessor(desktopControllerBis);
        inputMultiplexer.addProcessor(androidController);
        Gdx.input.setInputProcessor(inputMultiplexer);
        this.box2DDebugRenderer=new Box2DDebugRenderer();

    }


    public void gameIni(){
        this.game.getBatch().draw(this.gameWorld.getTerrain(), this.gameWorld.getTerrain().getX(), this.gameWorld.getTerrain().getY(), this.gameWorld.getTerrain().getWidth(), this.gameWorld.getTerrain().getHeight());

        /** Création est rendu des joueurs */
        this.game.getBatch().draw(this.gameWorld.getJoueurUn(),this.gameWorld.getJoueurUn().getX(),this.gameWorld.getJoueurUn().getY(), this.gameWorld.getJoueurUn().getWidth(),this.gameWorld.getJoueurUn().getHeight());
        this.game.getBatch().draw(this.gameWorld.getJoueurDeux(),this.gameWorld.getJoueurDeux().getX(),this.gameWorld.getJoueurDeux().getY(), this.gameWorld.getJoueurDeux().getWidth(),this.gameWorld.getJoueurDeux().getHeight());

        this.game.getBatch().draw(this.gameWorld.getBallon(),this.gameWorld.getBallon().getX(),this.gameWorld.getBallon().getY(),this.gameWorld.getBallon().getWidth(),this.gameWorld.getBallon().getHeight());

        /** Affichage des 3 textes */
        this.font.draw(this.game.getBatch(),String.valueOf(gameWorld.getTempsRestant()),0-timer.width/2f, fitViewport.getWorldHeight()/2f-timer.height/2f,timer.width+1, Align.center,true);
        this.font.draw(this.game.getBatch(),Integer.toString(this.gameWorld.getJoueurDeux().getScore()),0-fitViewport.getWorldWidth()/4f-score2.width/2f,fitViewport.getWorldHeight()/2f-score2.height/2f, score2.width+1,Align.center,true);
        this.font.draw(this.game.getBatch(),Integer.toString(this.gameWorld.getJoueurUn().getScore()),fitViewport.getWorldWidth()/4f-score1.width/2f,fitViewport.getWorldHeight()/2f-score1.height/2f, score1.width+1,Align.center,true);

        /** Coloration et rendu des sticks */
        this.game.getBatch().setColor(new Color(240, 255, 0, 1));
        this.game.getBatch().draw(stick1,stick1.getX(),stick1.getY(),stick1.getWidth(),stick1.getHeight());
        this.game.getBatch().setColor(new Color( 0,181, 204, 1));
        this.game.getBatch().draw(stick2,stick2.getX() ,stick2.getY(),stick2.getWidth(),stick2.getHeight());
        this.game.getBatch().setColor(new Color(1,1,1,1));
        if(this.gameWorld.displayImg()){
            this.game.getBatch().draw(goalTexture,0-this.gameWorld.getTerrain().getWidth()/4f,0-this.gameWorld.getTerrain().getHeight()/4f, this.gameWorld.getTerrain().getWidth()/2f,this.gameWorld.getTerrain().getHeight()/2f);
        }
    }


    @Override
    public void render (float delta){
        super.render(delta);
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        this.game.getBatch().setProjectionMatrix(camera.combined);
        box2DDebugRenderer.render(gameWorld.getWorld(), camera.combined);
        this.game.getBatch().begin();
        this.gameIni();
        this.gameWorld.update(delta);
        this.camera.update();
        this.game.getBatch().end();

    }

    @Override
    public void dispose(){
        this.game.getBatch().dispose();
        this.font.dispose();
    }

    /**
     * Getter de la FitViewport
     * @return la FitViewport
     */
    public FitViewport getFitViewport() {
        return fitViewport;
    }

    /**
     * Getter du controller du joueur 1 dans la version desktop
     * @return controller du joueur 1 dans la version desktop
     */
    public DesktopController getDesktopController() {
        return desktopController;
    }

    /**
     * Getter du jeu
     * @return le jeu
     */
    public MiniBall getGame() {
        return game;
    }

    /**
     * Getter de l'OthographicCamera
     * @return l'OthographicCamera
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Getter du controller du joueur 2 dans la version desktop
     * @return controller du joueur 2 dans la version desktop
     */
    public DesktopControllerBis getDesktopControllerBis() {
        return desktopControllerBis;
    }

    /**
     * Getter du controller des 2 joueurs dans la version android
     * @return controller des 2 joueurs dans la version android
     */
    public AndroidController getAndroidController() {
        return androidController;
    }

    /**
     * Getter du GlyphLayout représentant le timer du jeu
     * @return GlyphLayout représentant le timer du jeu
     */
    public GlyphLayout getTimer() {
        return timer;
    }
}
