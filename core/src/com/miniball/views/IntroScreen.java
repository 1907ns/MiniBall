package com.miniball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miniball.MiniBall;


/**
 * Ecran d'introduction du jeu
 */
public class IntroScreen extends ScreenAdapter {

    private MiniBall game;
    private Texture img;
    private Sprite intro;
    private float longueur, largeur;
    private FitViewport fitViewport;
    private OrthographicCamera camera;
    private float elapsed;


    /**
     * Constructeur
     * @param game jeu auquel l'intro est reliée
     */
    public IntroScreen(MiniBall game){
        this.game = game;
        this.intro = new Sprite(new Texture("images/Intro.jpg"));
        this.longueur=(float)76.4;
        this.largeur=(float)102.4;
        fitViewport=new FitViewport(largeur,longueur);
        camera = new OrthographicCamera(largeur,longueur);
        fitViewport = new FitViewport(largeur, longueur, camera);
        this.intro.setSize(fitViewport.getWorldWidth()*(float)0.8,
                fitViewport.getWorldHeight());

    }



    @Override
    public void render (float delta){
        super.render(delta);
        elapsed+=delta;
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        game.getBatch().draw(intro, 0-intro.getWidth()/2f, 0-intro.getHeight()/2f, fitViewport.getWorldWidth()*(float)0.8, fitViewport.getWorldHeight());
        game.getBatch().end();
        if (elapsed > 3.0) {
            //au bout de 3 secondes on accède au menu
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void dispose() {
        this.game.getBatch().dispose();
    }
}
