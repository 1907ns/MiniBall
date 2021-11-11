package com.miniball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.miniball.views.IntroScreen;

public class MiniBall extends Game {
	private SpriteBatch batch;
	private boolean twoPlayers;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new IntroScreen(this));
		twoPlayers=false;
	}


	public SpriteBatch getBatch() {
		return batch;
	}

	public boolean isTwoPlayers() {
		return twoPlayers;
	}

	public void setTwoPlayers(boolean twoPlayers) {
		this.twoPlayers = twoPlayers;
	}
}
