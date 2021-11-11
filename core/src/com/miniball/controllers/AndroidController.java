package com.miniball.controllers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.miniball.models.GameWorld;
import com.miniball.views.MenuScreen;


/**
 * Classe du controller Android qui permet
 * de contrôler les mouvements des 2 joueurs sur android
 */
public class AndroidController implements InputProcessor {

    private Vector2 vitesse, vitesse2; //les vecteurs vitesse aplliqués à chaque joueur
    private float coeff; //coefficient de la vitesse
    private GameWorld gameWorld;//Monde du jeu
    private float xA, yA, xB, yB;//Coordonnées du vecteur vitesse du joueur 1 (de droite)
    private float xA2, yA2, xB2, yB2; //Coordonnées du vecteur vitesse du joueur 2 (de gauche)
    private int pointeurJ1, pointeurJ2; //pointeur des 2 joueurs sur l'écran
    private  boolean moveJ1; //booléen qui est vrai si le joueur 1 est en mouvement/bouge
    private boolean moveJ2; //booléen qui est vrai si le joueur 2 est en mouvement/bouge


    /**
     * Constructeur du controller
     * @param gameWorld monde du jeu où le controller sera activé
     */
    public AndroidController(GameWorld gameWorld){
        super();
        this.gameWorld=gameWorld;
        this.coeff=500;
        this.vitesse= new Vector2();
        this.vitesse2= new Vector2();
        this.pointeurJ1=-1;
        this.pointeurJ2=-1;
        this.moveJ1=false;
        this.moveJ2=false;
    }

    /**
     * Getter du vecteur vitesse appliqué au joueur 1
     * @return vecteur vitesse appliqué au joueur 1
     */
    public Vector2 getVitesse() {
        return vitesse;
    }

    /**
     * Getter du vecteur vitesse appliqué au joueur 2
     * @return vecteur vitesse appliqué au joueur 2
     */
    public Vector2 getVitesse2() {
        return vitesse2;
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
        Vector3 ori = gameWorld.getGameScreen().getCamera().unproject(vector3);//changement du repère pour avoir un repère orthonormé classique

        float hauteur = gameWorld.getGameScreen().getTimer().height*2; //hauteur du timer et de la porte de sortie
        float largeur =gameWorld.getGameScreen().getTimer().width/2f;//largeur du timer et de la porte de sortie

        // accès au menu
        if(ori.x>=0-largeur && ori.x <=0+largeur){
            //accès au menu lors d'un click (touchdown) sur le timer
            if(ori.y>gameWorld.getTerrain().getHeight()/2f-hauteur){
                gameWorld.getGameScreen().getGame().setScreen(new MenuScreen(gameWorld.getGameScreen().getGame()));
            }
            //accès au menu lors d'un click (touchdown) sur la porte de sortie grise
            if(ori.y <=0-gameWorld.getTerrain().getHeight()/2f+hauteur){

                gameWorld.getGameScreen().getGame().setScreen(new MenuScreen(gameWorld.getGameScreen().getGame()));
            }
        }

        //déplacement joueur 1 avec délimitation de l'aire où l'on peut cliquer (avec empiètement sur le terrain)
        if(ori.x >= gameWorld.getTerrain().getWidth()/2-gameWorld.getTerrain().getWidth()/8 && !moveJ1){
            moveJ1=true;
            pointeurJ1=pointer;

            //définition du premier point du vecteur vitesse du joueur 1
            xA=ori.x;
            yA=ori.y;
        }

        //déplacement joueur 2 avec délimitation de l'aire où l'on peut cliquer (avec empiètement sur le terrain)
        if(ori.x <= 0-gameWorld.getTerrain().getWidth()/2+gameWorld.getTerrain().getWidth()/8 && !moveJ2 ){
            moveJ2=true;
            pointeurJ2=pointer;
            //définition du premier point du vecteur vitesse du joueur 2
            xA2=ori.x;
            yA2=ori.y;
        }





        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 vector3 = new Vector3(screenX, screenY, 0);
        Vector3 ori = gameWorld.getGameScreen().getCamera().unproject(vector3);

        //si le joueur 1 enlève son doigt, le vecteur vitesse est nul
        if(moveJ1 && (pointer == pointeurJ1)){
            moveJ1=false;
            vitesse.set(0,0);
            pointeurJ1=-1;
            return true;
        }

        //si le joueur 2 enlève son doigt, le vecteur vitesse est nul
        if(moveJ2 && (pointer == pointeurJ2)){

            moveJ2=false;
            vitesse2.set(0,0);
            pointeurJ2=-1;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 vector3 = new Vector3(screenX, screenY, 0);
        Vector3 ori = gameWorld.getGameScreen().getCamera().unproject(vector3);

        //vérification de l'aire de contrôle et du bon pointeur
        if(moveJ1 && (pointer == pointeurJ1) ){
            //si l'abscisse du deuxième point dépasse de la zone acoordée, on réajuste l'abscisse du point
            if(ori.x< gameWorld.getTerrain().getWidth()/2-gameWorld.getTerrain().getWidth()/8){
                ori.x=gameWorld.getTerrain().getWidth()/2-gameWorld.getTerrain().getWidth()/8;
            }
            //définition du deuxième point du vecteur vitesse appliqué au joueur 1
            xB= ori.x; yB=ori.y;
            vitesse.set((xB- xA)*coeff,(yB- yA)*coeff);
            if(gameWorld.displayImg()){
                vitesse.set(0,0);
            }

            return true;
        }

        //vérification de l'aire de contrôle et du bon pointeur
        if(moveJ2 && (pointer == pointeurJ2) ){

            //si l'abscisse du deuxième point dépasse de la zone acoordée, on réajuste l'abscisse du point
            if(ori.x> 0-gameWorld.getTerrain().getWidth()/2+gameWorld.getTerrain().getWidth()/8){
                ori.x=0-gameWorld.getTerrain().getWidth()/2+gameWorld.getTerrain().getWidth()/8;
            }
            //définition du deuxième point du vecteur vitesse appliqué au joueur 2
            xB2= ori.x; yB2=ori.y;
            vitesse2.set((xB2- xA2)*coeff,(yB2- yA2)*coeff);
            if(gameWorld.displayImg()){
                vitesse2.set(0,0);
            }

            return true;
        }

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

