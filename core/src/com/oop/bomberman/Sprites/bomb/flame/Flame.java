package com.oop.bomberman.Sprites.bomb.flame;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Screens.PlayScreen;
import com.oop.bomberman.Sprites.Bomman;
import com.oop.bomberman.Sprites.bomb.Bomb;

public abstract class Flame extends Sprite {
    PlayScreen screen;
    World world;
    private Array<TextureRegion> frames;
    protected TextureRegion fire;
    protected float stateTime;
    protected boolean destroyed = false;
    protected boolean setToDestroy = false;
    public Body b2body;

    public Flame(PlayScreen screen, float x, float y, Bomb bomb) {
        this.screen = screen;
        this.world = screen.getWorld();
        destroyed = false;
        setToDestroy = false;
        setBounds(x, y, 24 , 24);
        defineFlame(bomb);
    }

    public abstract void defineFlame(Bomb bomb);

    public void update(float dt, Bomb bomb){
        setRegion(fire);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((bomb.stateTime >= 2.5 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

}
