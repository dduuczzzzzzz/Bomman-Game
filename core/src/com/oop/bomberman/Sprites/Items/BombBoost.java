package com.oop.bomberman.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Screens.PlayScreen;

public class BombBoost extends Item {
    public BombBoost(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("powerup_bombs"), 0, 0, 16, 16);
        velocity = new Vector2(0, 0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);

        fdef.filter.categoryBits = Bomberman.ITEM2_BIT;
        fdef.filter.maskBits = Bomberman.BOMMAN_BIT | Bomberman.GROUND_BIT | Bomberman.BRICK_BIT | Bomberman.ENEMY_BIT | Bomberman.OBJECT_BIT | Bomberman.ENEMY_HEAD_BIT;

        fdef.shape = shape;
        body.createFixture(fdef);
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void useItem() {
        Bomberman.manager.get("audio/sounds/coin.wav", Sound.class).play();
        setDestroy();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}
