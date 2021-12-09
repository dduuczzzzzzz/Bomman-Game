package com.oop.bomberman.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Screens.PlayScreen;
import com.oop.bomberman.Sprites.Bomman;
import com.oop.bomberman.Sprites.bomb.flame.Flame;

public class Ground extends InteractiveTileObject {
    public Ground(PlayScreen screen, MapObject object) {
        super(screen, object);
        setCategoryFilter(Bomberman.GROUND_BIT);
        fixture.setUserData(this);
        /*
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) , (bounds.getY() + bounds.getHeight() / 2) );

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 , bounds.getHeight() / 2 );
        fdef.shape = shape;
        body.createFixture(fdef);*/
    }

    @Override
    public void OnHeadhit(Flame flame) {
        Gdx.app.log("Ground", "Collision");
    }
}
