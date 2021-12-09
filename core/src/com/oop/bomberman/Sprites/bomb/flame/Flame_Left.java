package com.oop.bomberman.Sprites.bomb.flame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Screens.PlayScreen;
import com.oop.bomberman.Sprites.bomb.Bomb;

public class Flame_Left extends Flame {
    public Flame_Left(PlayScreen screen, float x, float y, Bomb bomb) {
        super(screen, x, y,bomb);
        fire = new TextureRegion(screen.getBomb_atlas().findRegion("explode_left"), 0, 0, 32, 16);
        setRegion(fire);
    }

    @Override
    public void defineFlame(Bomb bomb) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(bomb.b2body.getPosition().x - 24, bomb.b2body.getPosition().y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);
        fdef.filter.categoryBits = Bomberman.FLAME_BIT;
        fdef.filter.maskBits = Bomberman.BRICK_BIT |
                Bomberman.ENEMY_BIT |
                Bomberman.ENEMY2_BIT |
                Bomberman.BOMMAN_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.createFixture(fdef).setUserData(this);
    }
}
