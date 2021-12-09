package com.oop.bomberman.Sprites.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Screens.PlayScreen;
import com.oop.bomberman.Sprites.Enemies.Enemy;
import com.oop.bomberman.Sprites.bomb.flame.Flame;

public class Smileface extends Enemy {
    private float stateTimer;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Smileface(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i=0; i<5; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), i*16, 0, 16, 16));
        }
        walkAnimation = new Animation(0.15f, frames);
        stateTimer = 0;
        setBounds(getX(), getY(), 24, 24);
        setToDestroy = false;
        destroyed = false;
        velocity = new Vector2(0, 50);
    }

    public void update(float delta) {
        stateTimer += delta;
        if(setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("enemy"), 32, 0, 16, 16));
            stateTimer = 0;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTimer, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);

        fdef.filter.categoryBits = Bomberman.ENEMY2_BIT;
        fdef.filter.maskBits = Bomberman.GROUND_BIT | Bomberman.BOMB_BIT | Bomberman.BRICK_BIT | Bomberman.OBJECT_BIT | Bomberman.ENEMY_BIT | Bomberman.BOMMAN_BIT | Bomberman.FLAME_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-4, 8);
        vertice[1] = new Vector2(4, 8);
        vertice[2] = new Vector2(-3, 3);
        vertice[3] = new Vector2(3, 3);
        head.set(vertice);

        fdef.shape = head;
        fdef.filter.categoryBits = Bomberman.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void isKill(Flame flame) {
        setToDestroy = true;
        Bomberman.manager.get("audio/sounds/stomp.wav", Sound.class).play();
    }

    @Override
    public void draw(Batch batch) {
        if(!destroyed || stateTimer < 1) {
            super.draw(batch);
        }
    }
}
