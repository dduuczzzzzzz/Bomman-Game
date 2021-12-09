package com.oop.bomberman.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Screens.PlayScreen;
import com.oop.bomberman.Sprites.Bomman;
import com.oop.bomberman.Sprites.Items.BombBoost;
import com.oop.bomberman.Sprites.Items.ItemDef;
import com.oop.bomberman.Sprites.Items.Portal;
import com.oop.bomberman.Sprites.Items.SpeedBosst;
import com.oop.bomberman.Sprites.bomb.flame.Flame;

import java.util.Map;

public class Bricks extends InteractiveTileObject {
    public Bricks(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Bomberman.BRICK_BIT);

        /*BodyDef bdef = new BodyDef();
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
        setCategoryFilter(Bomberman.DESTROYED_BIT);
        if(getCell() != null) {
            getCell().setTile(null);
        }
        if(object.getProperties().containsKey("speedBoost")) {
            screen.SpawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y), SpeedBosst.class));
        }
        if(object.getProperties().containsKey("BombBoost")) {
            screen.SpawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y), BombBoost.class));
        }
        if(object.getProperties().containsKey("portal")) {
            screen.SpawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y), Portal.class));
        }
        //Bomberman.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }
}
