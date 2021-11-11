package com.miniball.datafactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Classe regourpant les sons utilisés dans le jeu
 */
public class Sounds {

    private Sound goal, shoot; //les sons du but et du tir
    private AssetManager assetManager;

    public Sounds(){
        assetManager= new AssetManager();
        assetManager.load("sounds/goal.ogg", Sound.class);// chargement du son "but"
        assetManager.load("sounds/shoot.ogg", Sound.class); // chargement du son "tir"
        assetManager.finishLoading();
        this.goal= assetManager.get("sounds/goal.ogg");
        this.shoot= assetManager.get("sounds/shoot.ogg");
    }

    /**
     * Getter du son joué lorsqu'un but est marqué
     * @return son joué lorsqu'un but est marqué
     */
    public Sound getGoal() {
        return goal;
    }

    /**
     * Getter du son joué lorsqu'un tir est provoqué
     * @return son joué son joué lorsqu'un tir est provoqué
     */
    public Sound getShoot() {
        return shoot;
    }
}
