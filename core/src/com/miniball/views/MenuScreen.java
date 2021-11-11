package com.miniball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miniball.MiniBall;
import com.miniball.controllers.MenuController;


/**
 * Classe de l'écran du menu du jeu
 */
public class MenuScreen  extends ScreenAdapter {
    private MiniBall game;
    private Texture img;
    private Sprite fond, menu;
    private float longueur, largeur;
    private FitViewport fitViewport;
    private OrthographicCamera camera;
    private MenuController menuController;


    /**
     * Constructeur
     * @param game jeu auquel le menu est relié
     */
    public MenuScreen(MiniBall game){
        this.game = game;
        this.fond = new Sprite(new Texture("images/Intro.jpg"));
        this.menu= new Sprite(new Texture("images/Menu.jpg"));
        this.longueur=(float)76.4;
        this.largeur=(float)102.4;
        fitViewport=new FitViewport(largeur,longueur);
        camera = new OrthographicCamera(largeur,longueur);
        fitViewport = new FitViewport(largeur, longueur, camera);
        menuController = new MenuController(this);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(menuController);
        Gdx.input.setInputProcessor(inputMultiplexer);
        this.fond.setSize(fitViewport.getWorldWidth()*(float)0.8,
                fitViewport.getWorldHeight());

    }

    /**
     * Getter de l'OrthographicCamera
     * @return l'OrthographicCamera
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Getter du sprite représentant le menu
     * @return sprite représentant le menu
     */
    public Sprite getMenu() {
        return menu;
    }

    /**
     * Getter du fond de l'écran de menu
     * @return fond de l'écran de menu
     */
    public Sprite getFond() {
        return fond;
    }

    /**
     * Getter du jeu auquel le Menu est relié
     * @return jeu auquel le Menu est relié
     */
    public MiniBall getGame() {
        return game;
    }


    @Override
    public void render (float delta){
        super.render(delta);
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        game.getBatch().draw(fond, 0-fond.getWidth()/2f, 0-fond.getHeight()/2f, fitViewport.getWorldWidth()*(float)0.8, fitViewport.getWorldHeight());
        game.getBatch().draw(menu,0-fond.getWidth()/4F,0-fond.getHeight()/4F,fond.getWidth()/2F,fond.getHeight()/2F);
        game.getBatch().end();

    }

    @Override
    public void dispose() {
        this.game.getBatch().dispose();
    }
}
