package com.miniball.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.miniball.models.GameWorld;

/**
 * Controller du joueur 2 dans la version Desktop
 */
public class DesktopControllerBis implements InputProcessor {

    private Vector2 vitesse; //vecteur vitesse
    private float coeff; //coefficient de la vitesse
    private GameWorld gameWorld; //Monde du jeu où le controller sera appliqué


    /**
     * Constructeur
     * @param gameWorld Monde du jeu où le controller sera appliqué
     */
    public DesktopControllerBis(GameWorld gameWorld){
        super();
        this.vitesse=new Vector2();
        this.coeff=500;
        this.gameWorld=gameWorld;
    }

    public Vector2 getVitesse() {
        return vitesse;
    }


    @Override
    public boolean keyDown(int keycode) {
        /*déplacement avec ZQSD quand l'une des quatre touche est enfoncée
            et vérification du fait que le mode 2 joueurs ait été choisi
         */

        if(!gameWorld.displayImg() && gameWorld.getGameScreen().getGame().isTwoPlayers()){
            if(keycode == Input.Keys.Z){
                vitesse.y = coeff;
            }else if(keycode == Input.Keys.S){
                vitesse.y = -coeff;
            }else if(keycode == Input.Keys.Q){
                vitesse.x = -coeff;
            }else if(keycode == Input.Keys.D){
                vitesse.x = coeff;
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        //déplacement avec ZQSD vers une direction annulé quand l'une des quatre touche est retirée
        if(keycode == Input.Keys.Z){
            vitesse.y = 0;
        }else if(keycode == Input.Keys.S){
            vitesse.y = 0;
        }else if(keycode == Input.Keys.Q){
            vitesse.x = 0;
        }else if(keycode == Input.Keys.D){
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
