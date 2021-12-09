package com.oop.bomberman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oop.bomberman.Screens.PlayScreen;

public class Bomberman extends Game {
	public static final int V_WIDTH = 1200;
	public static final int V_HEIGHT = 638;
	public static final int PPM = 2;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short BOMMAN_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short DESTROYED_BIT = 8;
	public static final short OBJECT_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short ENEMY2_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short BOMB_BIT = 256;
	public static final short ITEM_BIT = 512;
	public static final short FLAME_BIT = 1024;
	public static final short ITEM2_BIT = 2048;
	public static final short ITEM3_BIT = 4096;

	public SpriteBatch batch;
	public static AssetManager manager;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/theme.wav", Music.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.load("audio/sounds/stomp.wav", Sound.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/mariodie.wav", Sound.class);
		manager.load("audio/sounds/CRYST_UP.wav", Sound.class);
		manager.load("audio/sounds/BOM_SET.wav", Sound.class);
		manager.finishLoading();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();

	}
}
