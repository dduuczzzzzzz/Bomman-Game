package com.oop.bomberman.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Sprites.Bomman;
import com.oop.bomberman.Sprites.Enemies.Enemy;
import com.oop.bomberman.Sprites.Items.*;
import com.oop.bomberman.Tools.B2WorldCreator;
import com.oop.bomberman.Tools.WorldContactListener;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    private FitViewport gameport;
    private OrthographicCamera gamecam;
    public Bomberman game;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private TextureAtlas atlas;
    private TextureAtlas bomb_atlas;

    private Bomman bomman;

    private Music music;
    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    Texture texture;

    public PlayScreen(Bomberman game) {
        atlas = new TextureAtlas("player GFX/bommann.pack");
        bomb_atlas = new TextureAtlas("player GFX/bomb2/bombb.pack");
        this.game = game;
        gamecam = new OrthographicCamera();

        //do not change viewport value
        gameport = new FitViewport(960,640,gamecam);

        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();
        b2dr.setDrawBodies(false);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/secondmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gameport.getWorldWidth()/2,gameport.getWorldHeight()/2,0);
        renderer.setView(gamecam);

        bomman = new Bomman(this);
        Gdx.input.setInputProcessor(bomman);

        creator = new B2WorldCreator(this);
        world.setContactListener(new WorldContactListener());
        /*
        music = Bomberman.manager.get("audio/music/theme.wav", Music.class);
        music.setLooping(true);
        music.play();*/

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<>();
    }

    public void SpawnItem(ItemDef idef) {
        itemsToSpawn.add(idef);
    }

    public void HandleSpawnItem() {
        if(!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == SpeedBosst.class) {
                items.add(new SpeedBosst(this, idef.position.x, idef.position.y));
            }
            if(idef.type == BombBoost.class) {
                items.add(new BombBoost(this, idef.position.x, idef.position.y));
            }
            if(idef.type == Portal.class) {
                items.add(new Portal(this, idef.position.x, idef.position.y));
            }
        }
    }

    public void update(float dt) {
        //handleInput(dt);
        HandleSpawnItem();
        world.step(1/50f, 10 , 2);
        if(bomman.b2body.getPosition().x >= 480 && bomman.b2body.getPosition().x <= 638) {
            //System.out.println(bomman.b2body.getPosition());
            gamecam.position.x = bomman.b2body.getPosition().x;
        }
        bomman.update(dt);
        for (Enemy enemy : creator.getGoombas()) {
            enemy.update(dt);
        }
        for (Enemy enemy : creator.getSmilefaces()) {
            enemy.update(dt);
        }
        for(Item item: items) {
            item.update(dt);
        }
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void show() {
        //create wall
        /*
        for (MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
        }
        //create crate          đừng đọc:)
        for (MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
        }*/
    }

    @Override
    public void render(float delta) {
        update(delta);
        //handleInput(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render batch
        renderer.render();
        b2dr.render(world,gamecam.combined);

        //bomman.update(Gdx.graphics.getDeltaTime());
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        bomman.draw(game.batch);
        for (Enemy enemy : creator.getGoombas()) {
            enemy.draw(game.batch);
        }
        for (Enemy enemy : creator.getSmilefaces()) {
            enemy.draw(game.batch);
        }
        for (Item item : items) {
            item.draw(game.batch);
        }
        game.batch.end();

        if(isGameOver()) {
            game.setScreen(new GameOver(game));
            dispose();
        }

        if(Congrat()) {
            game.setScreen(new Win(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        //adjust the viewport when the game window got resized
        gameport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        b2dr.dispose();
        renderer.dispose();
        world.dispose();
    }

    public boolean isGameOver() {
        return bomman.isDead();
    }

    public boolean Congrat() {
        return bomman.isWin_Game();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }
    public TextureAtlas getBomb_atlas() {
        return bomb_atlas;
    }
    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {return map;}
}
