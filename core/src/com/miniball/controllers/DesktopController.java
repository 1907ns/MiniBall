package com.miniball.controllers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.miniball.models.GameWorld;
import com.miniball.views.GameScreen;
import com.miniball.views.MenuScreen;


/**
 * Controller du joueur 1 dans la version Desktop
 */
public class DesktopController implements InputProcessor {



    private Vector2 vitesse; //vecteur vitesse
    private float coeff; //coefficient de la vitesse
    private GameWorld gameWorld; //Monde du jeu où le controller sera appliqué


    /**
     * Constructeur
     * @param gameWorld Monde du jeu où le controller sera appliqué
     */
    public DesktopController(GameWorld gameWorld){
        super();
        this.vitesse= new Vector2();
        this.coeff= 500;
        this.gameWorld=gameWorld;

    }

    /**
     * Getter du vecteur vitesse appliqué au joueur 1
     * @return Getter du vecteur vitesse appliqué au joueur 1
     */
    public Vector2 getVitesse() {
        return vitesse;
    }


    @Override
    public boolean keyDown(int keycode) {


        //déplacement avec les flèches quand l'une des quatre touche est enfoncée
        if(Gdx.app.getType()!= Application.ApplicationType.Android){
            if(!gameWorld.displayImg()) {
                if (keycode == Input.Keys.UP) {
                    vitesse.y = coeff;
                } else if (keycode == Input.Keys.DOWN) {
                    vitesse.y = -coeff;
                } else if (keycode == Input.Keys.LEFT) {
                    vitesse.x = -coeff;
                } else if (keycode == Input.Keys.RIGHT) {
                    vitesse.x = coeff;
                }
            }
        }

        return false;
    }



    @Override
    public boolean keyUp(int keycode) {

        //déplacement avec les flèches annulé vers une direction quand l'une des quatre touche est retirée
        if(keycode == Input.Keys.UP){
            vitesse.y = 0;
        }else if(keycode == Input.Keys.DOWN){
            vitesse.y = 0;
        }else if(keycode == Input.Keys.LEFT){
            vitesse.x = 0;
        }else if(keycode == Input.Keys.RIGHT){
            vitesse.x = 0;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 vector3 = new Vector3(screenX, screenY, 0);
        Vector3 ori = gameWorld.getGameScreen().getCamera().unproject(vector3);
        float hauteur = gameWorld.getGameScreen().getTimer().height*2;
        float largeur =gameWorld.getGameScreen().getTimer().width/2f;

        //retour au menu
        if(ori.x>=0-largeur && ori.x <=0+largeur){
            if(ori.y>gameWorld.getTerrain().getHeight()/2f-hauteur){
                //click sur le timer
                gameWorld.getGameScreen().getGame().setScreen(new MenuScreen(gameWorld.getGameScreen().getGame()));
            }
            if(ori.y <=0-gameWorld.getTerrain().getHeight()/2f+hauteur){
                //click sur la porte de sortie
                gameWorld.getGameScreen().getGame().setScreen(new MenuScreen(gameWorld.getGameScreen().getGame()));
            }
        }


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        return false;
    }
}
