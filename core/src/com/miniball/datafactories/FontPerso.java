package com.miniball.datafactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Classe de la police d'écriture utilisée dans le jeu
 */
public class FontPerso {

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    /**
     * Constructeur vide
     */
    public FontPerso(){

    }


    /**
     * Créateur et getter du font utilisé dans le jeu
     * @return font utilisé dans le jeu
     */
    public BitmapFont getFont(){
        BitmapFont font = new BitmapFont();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/comic.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderWidth=(float)3;
        parameter.borderColor= Color.BLACK;
        parameter.color = new Color(255, 255, 0, (float)0.75); //opacité de 75% comme demandé
        parameter.size=60;

        font= generator.generateFont(parameter);
        generator.dispose();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);//texture linéaire
        font.getData().setScale(0.1f);

        return font;
    }
}
