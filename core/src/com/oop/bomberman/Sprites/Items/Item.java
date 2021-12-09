package com.oop.bomberman.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.oop.bomberman.Screens.PlayScreen;
import com.oop.bomberman.Sprites.Bomman;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected boolean toDestroy;
    protected boolean destroy;
    protected Body body;
    protected Vector2 velocity;

    public Item(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        setBounds(getX(), getY(), 24, 24);
        defineItem();
        toDestroy = false;
        destroy = false;
    }

    public abstract void defineItem();
    public abstract void useItem();

    public void update(float delta) {
        if(toDestroy && !destroy) {
            world.destroyBody(body);
            destroy = true;
        }
    }

    public void setDestroy() {
        toDestroy = true;
    }

    @Override
    public void draw(Batch batch) {
        if(!destroy) {
            super.draw(batch);
        }
    }
}
