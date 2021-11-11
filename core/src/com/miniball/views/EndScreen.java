package com.miniball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miniball.MiniBall;
import com.miniball.controllers.MenuController;
import com.miniball.datafactories.FontPerso;
import com.miniball.models.GameWorld;


/**
 * Classe de l'écran de fin de partie
 */
public class EndScreen extends ScreenAdapter {
    private MiniBall game;
    private Texture img;
    private Sprite fond, menu;
    private float longueur, largeur;
    private FitViewport fitViewport;
    private OrthographicCamera camera;
    private GameWorld gameWorld;
    private BitmapFont font;
    private GlyphLayout resultat;
    private  float elapsed;


    /**
     * Constructeur
     * @param game jeu auquel l'écran est relié
     * @param gameWorld monde du jeu auquel l'écran est relié
     */
    public EndScreen(MiniBall game, GameWorld gameWorld){
        this.game=game;
        this.gameWorld=gameWorld;
        this.fond = new Sprite(new Texture("images/Intro.jpg"));
        this.menu= new Sprite(new Texture("images/Menu.jpg"));
        this.longueur=(float)76.4;
        this.largeur=(float)102.4;
        fitViewport=new FitViewport(largeur,longueur);
        camera = new OrthographicCamera(largeur,longueur);
        fitViewport = new FitViewport(largeur, longueur, camera);
        this.fond.setSize(fitViewport.getWorldWidth()*(float)0.8,
                fitViewport.getWorldHeight());
        FontPerso fontPerso = new FontPerso();
        this.font = new BitmapFont();
        this.font = fontPerso.getFont();
        this.resultat= new GlyphLayout(font, "");

    }

    @Override
    public void render (float delta){
        super.render(delta);
        elapsed+=delta;
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        this.game.getBatch().setProjectionMatrix(camera.combined);
        this.game.getBatch().begin();
        this.game.getBatch().draw(fond, 0-fond.getWidth()/2f, 0-fond.getHeight()/2f, fitViewport.getWorldWidth()*(float)0.8, fitViewport.getWorldHeight());
        String str="";

        // 3 différents cas, 3 différents messages de fin
        if(gameWorld.getJoueurUn().getScore()>gameWorld.getJoueurDeux().getScore()){
            str="Joueur droit gagnant!";
        }else if(gameWorld.getJoueurUn().getScore()<gameWorld.getJoueurDeux().getScore()){
            str="Joueur gauche  gagnant!";
        }else if(gameWorld.getJoueurUn().getScore()==gameWorld.getJoueurDeux().getScore()){
            str="Match nul!";
        }
        this.font.draw(this.game.getBatch(),str,0-resultat.width/2f,0-resultat.height/2f, resultat.width, Align.center,true);
        this.game.getBatch().end();

        if (elapsed > 3.0) {
            game.setScreen(new GameScreen(game));
        }
    }




}
