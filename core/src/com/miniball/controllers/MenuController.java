package com.miniball.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.miniball.views.GameScreen;
import com.miniball.views.MenuScreen;

/**
 * Classe du controller intéragissant avec le menu
 * dans la version Desktop
 */
public class MenuController implements InputProcessor {

    private MenuScreen menuScreen;// Ecran où le controller sera appliqué

    /**
     * Constructeur du controller
     * @param menuScreen Ecran où le controller sera appliqué
     */
    public MenuController(MenuScreen menuScreen){
        this.menuScreen=menuScreen;
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 vector3 = new Vector3(screenX, screenY, 0);

        Vector3 ori = menuScreen.getCamera().unproject(vector3);//changement de repère orthonormé

        float hauteur = menuScreen.getFond().getHeight()/4F;//hauteur du menu
        float largeur =menuScreen.getFond().getWidth()/4F; //largeur du menu
        float div = menuScreen.getFond().getHeight()/8F; //hauteur d'une division du menu

        if(ori.x>=0-largeur && ori.x <=0+largeur){
            //click sur le mode un joueur
            if(ori.y>menuScreen.getMenu().getY()+div && ori.y <=hauteur){
                menuScreen.getGame().setTwoPlayers(false);
                menuScreen.getGame().setScreen(new GameScreen(menuScreen.getGame()));
            }else if (ori.y>menuScreen.getMenu().getY() && ori.y <=div){ //click sur le mode deux joueurs
                menuScreen.getGame().setTwoPlayers(true);
                menuScreen.getGame().setScreen(new GameScreen(menuScreen.getGame()));
            }else if(ori.y>=menuScreen.getMenu().getY()-hauteur && ori.y<=menuScreen.getMenu().getY()-hauteur+div){
                //click sur "Quitter"
                Gdx.app.exit();
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
