package com.oop.bomberman.Sprites.bomb;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Screens.PlayScreen;
import com.oop.bomberman.Sprites.Bomman;
import com.oop.bomberman.Sprites.bomb.flame.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bomb extends Sprite {
    private PlayScreen screen;
    private World world;
    private Array<TextureRegion> frames;
    private Animation fireAnimation;
    public float stateTime;
    private boolean destroyed;
    private boolean setToDestroy;
    public Body b2body;
    private Flame_Mid fmid;
    private Flame_Up fup;
    private Flame_Right fright;
    private Flame_Left fleft;
    private Flame_Down fdown;
    private boolean canExplode;
    private int fire_stack;

    public Bomb(PlayScreen screen, float x, float y, Bomman bomman) {
        this.screen = screen;
        this.world = screen.getWorld();
        canExplode = false;
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(screen.getBomb_atlas().findRegion("bomb1"), i * 16, 0, 16, 16));
        }
        fireAnimation = new Animation(0.2f, frames);
        //setRegion((Texture) fireAnimation.getKeyFrame(0));
        setRegion((TextureRegion) fireAnimation.getKeyFrame(0));
        setBounds(x, y, 24 , 24);
        defineBomb(bomman);
        stateTime = 0;
        fire_stack = 1;
        //Fire();
    }

    public void defineBomb(Bomman bomman) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(bomman.b2body.getPosition().x, bomman.b2body.getPosition().y);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);
        fdef.filter.categoryBits = Bomberman.BOMB_BIT;
        fdef.filter.maskBits = Bomberman.GROUND_BIT |
                Bomberman.BRICK_BIT |
                Bomberman.ENEMY_BIT |
                Bomberman.ENEMY2_BIT |
                Bomberman.OBJECT_BIT ;
                //Bomberman.BOMMAN_BIT ;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt){
        stateTime += dt;
        setRegion((TextureRegion) fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if(stateTime > 2 && stateTime < 2.2) {
            canExplode = true;
        }
        if(canExplode && this.fire_stack >= 1) {
            Fire();
            Bomberman.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
            this.fire_stack--;
        }
        if(canExplode) {
            fup.update(dt, this);
            fright.update(dt, this);
            fleft.update(dt, this);
            fdown.update(dt, this);
            fmid.update(dt, this);
        }
        if((stateTime > 2.5 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            //Bomberman.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if(canExplode) {
            fmid.draw(batch);
            fleft.draw(batch);
            fright.draw(batch);
            fup.draw(batch);
            fdown.draw(batch);
        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public boolean isCanExplode() {
        return canExplode;
    }

    public void Fire() {
        fmid = new Flame_Mid(screen, b2body.getPosition().x, b2body.getPosition().y, this);
        fup = new Flame_Up(screen, b2body.getPosition().x, b2body.getPosition().y, this);
        fdown = new Flame_Down(screen, b2body.getPosition().x, b2body.getPosition().y, this);
        fleft = new Flame_Left(screen, b2body.getPosition().x, b2body.getPosition().y, this);
        fright = new Flame_Right(screen, b2body.getPosition().x, b2body.getPosition().y, this);
    }

}
