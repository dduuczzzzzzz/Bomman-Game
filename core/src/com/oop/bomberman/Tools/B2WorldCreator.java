package com.oop.bomberman.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Screens.PlayScreen;
import com.oop.bomberman.Sprites.Enemies.Goomba;
import com.oop.bomberman.Sprites.Enemies.Smileface;
import com.oop.bomberman.Sprites.TileObjects.Bricks;
import com.oop.bomberman.Sprites.TileObjects.Ground;
import com.oop.bomberman.Sprites.TileObjects.Walls;

public class B2WorldCreator {
    private Box2DDebugRenderer b2dr;
    private Array<Goomba> goombas;
    private Array<Smileface> smilefaces;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        // grounds
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            //Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Ground(screen, object);
        }

        // walls - cannot explaode
        for (MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            //Rectangle rect = ((RectangleMapObject) object).getRectangle();
            fdef.filter.categoryBits = Bomberman.OBJECT_BIT;
            new Walls(screen, object);
        }

        //bricks
        for (MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            //Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Bricks(screen, object);
        }

        // creat enemies
        goombas = new Array<Goomba>();
        for (MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen, rect.getX(), rect.getY()));
        }

        //create enemy2
        smilefaces = new Array<Smileface>();
        for (MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            smilefaces.add(new Smileface(screen, rect.getX(), rect.getY()));
        }
    }

    public Array<Goomba> getGoombas() {
        return goombas;
    }

    public Array<Smileface> getSmilefaces() {
        return smilefaces;
    }
}
