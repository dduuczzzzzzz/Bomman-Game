package com.oop.bomberman.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Sprites.Bomman;
import com.oop.bomberman.Sprites.Enemies.Enemy;
import com.oop.bomberman.Sprites.Items.Item;
import com.oop.bomberman.Sprites.TileObjects.Bricks;
import com.oop.bomberman.Sprites.TileObjects.InteractiveTileObject;
import com.oop.bomberman.Sprites.bomb.flame.Flame;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture FixA = contact.getFixtureA();
        Fixture FixB = contact.getFixtureB();

        int cDef = FixA.getFilterData().categoryBits | FixB.getFilterData().categoryBits;

        /*if(FixA.getUserData() == "head" || FixB.getUserData() == "head") {
            Fixture head = FixA.getUserData() == "head" ? FixA : FixB;
            Fixture object = head == FixA ? FixB : FixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).OnHeadhit();
            }
        }*/

        switch (cDef) {
            case Bomberman.ENEMY_BIT | Bomberman.BRICK_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.ENEMY_BIT) {
                    ((Enemy) FixA.getUserData()).reverseVelocity(true, false);
                }
                else {
                    if(FixB.getUserData() != null) {
                        ((Enemy) FixB.getUserData()).reverseVelocity(true, false);
                    }
                }
                break;
            case Bomberman.ENEMY_BIT | Bomberman.GROUND_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.ENEMY_BIT) {
                    ((Enemy) FixA.getUserData()).reverseVelocity(true, false);
                }
                else {
                    if(FixB.getUserData() != null) {
                        ((Enemy) FixB.getUserData()).reverseVelocity(true, false);
                    }
                }
                break;
            case Bomberman.ENEMY2_BIT | Bomberman.BRICK_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.ENEMY_BIT) {
                    ((Enemy) FixA.getUserData()).reverseY();
                }
                else {
                    if(FixB.getUserData() != null) {
                        ((Enemy) FixB.getUserData()).reverseY();
                    }
                }
                break;
            case Bomberman.ENEMY2_BIT | Bomberman.GROUND_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.ENEMY2_BIT) {
                    ((Enemy) FixA.getUserData()).reverseY();
                }
                else {
                    if(FixB.getUserData() != null) {
                        ((Enemy) FixB.getUserData()).reverseY();
                    }
                }
                break;
            case Bomberman.ENEMY2_BIT | Bomberman.ENEMY_BIT:
                if(FixA.getUserData() != null) {
                    ((Enemy) FixA.getUserData()).reverseVelocity(true, false);
                }
                if(FixB.getUserData() != null) {
                    ((Enemy) FixB.getUserData()).reverseY();
                }
                break;
            case Bomberman.ENEMY_BIT | Bomberman.BOMMAN_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.BOMMAN_BIT)
                    if(FixA.getUserData() != null && FixB.getUserData() != null) {
                        ((Bomman)FixA.getUserData()).die();
                    }
                else
                    if(FixA.getUserData() != null && FixB.getUserData() != null)
                        ((Bomman)FixB.getUserData()).die();
                break;
            case Bomberman.ENEMY_BIT | Bomberman.BOMB_BIT:
                System.out.println("Enemy die");
                break;
            case Bomberman.ENEMY_BIT | Bomberman.FLAME_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.ENEMY_BIT)
                    if(FixA.getUserData() != null && FixB.getUserData() != null)
                    ((Enemy)FixA.getUserData()).isKill((Flame) FixB.getUserData());
                else
                    if(FixA.getUserData() != null && FixB.getUserData() != null)
                    ((Enemy)FixB.getUserData()).isKill((Flame) FixA.getUserData());
                break;
            case Bomberman.ENEMY2_BIT | Bomberman.FLAME_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.ENEMY2_BIT)
                    if(FixA.getUserData() != null && FixB.getUserData() != null)
                        ((Enemy)FixA.getUserData()).isKill((Flame) FixB.getUserData());
                    else
                    if(FixA.getUserData() != null && FixB.getUserData() != null)
                        ((Enemy)FixB.getUserData()).isKill((Flame) FixA.getUserData());
                break;
            case Bomberman.ENEMY2_BIT | Bomberman.BOMMAN_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.BOMMAN_BIT)
                    if(FixA.getUserData() != null && FixB.getUserData() != null) {
                        ((Bomman)FixA.getUserData()).die();
                    }
                    else
                    if(FixA.getUserData() != null && FixB.getUserData() != null)
                        ((Bomman)FixB.getUserData()).die();
                break;
            case Bomberman.ENEMY2_BIT | Bomberman.BOMB_BIT:
                System.out.println("Enemy die");
                break;
            case Bomberman.BOMMAN_BIT | Bomberman.BOMB_BIT:
                System.out.println("bomb");
                break;
            case Bomberman.ITEM_BIT | Bomberman.BOMMAN_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.ITEM_BIT) {
                    ((Item) FixA.getUserData()).useItem();
                    ((Bomman) FixB.getUserData()).Increase_Speed();
                }
                else {
                    if(FixB.getUserData() != null && FixA.getUserData() != null) {
                        System.out.println("boost");
                        ((Item) FixB.getUserData()).useItem();
                        ((Bomman) FixA.getUserData()).Increase_Speed();
                    }
                }
                break;
            case Bomberman.ITEM2_BIT | Bomberman.BOMMAN_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.ITEM2_BIT) {
                    ((Item) FixA.getUserData()).useItem();
                    ((Bomman) FixB.getUserData()).Increase_Bomb();
                }
                else {
                    if(FixB.getUserData() != null && FixA.getUserData() != null) {
                        System.out.println("boost");
                        ((Item) FixB.getUserData()).useItem();
                        ((Bomman) FixA.getUserData()).Increase_Bomb();
                    }
                }
                break;
            case Bomberman.ITEM3_BIT | Bomberman.BOMMAN_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.ITEM3_BIT) {
                    ((Item) FixA.getUserData()).useItem();
                    ((Bomman) FixB.getUserData()).win_game();
                }
                else {
                    if(FixB.getUserData() != null && FixA.getUserData() != null) {
                        System.out.println("boost");
                        ((Item) FixB.getUserData()).useItem();
                        ((Bomman) FixA.getUserData()).win_game();
                    }
                }
                break;
            case Bomberman.FLAME_BIT | Bomberman.BRICK_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.FLAME_BIT)
                    ((InteractiveTileObject) FixB.getUserData()).OnHeadhit((Flame) FixA.getUserData());
                else
                    ((InteractiveTileObject) FixA.getUserData()).OnHeadhit((Flame) FixB.getUserData());
                System.out.println("collide");
                break;
            /*case Bomberman.BOMMAN_BIT | Bomberman.BRICK_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.BOMB_BIT)
                    ((Bricks) FixB.getUserData()).Hit((Bomman) FixA.getUserData());
                else
                    ((Bricks) FixA.getUserData()).Hit((Bomman) FixB.getUserData());
                System.out.println("collide");
                break;*/
            case Bomberman.FLAME_BIT | Bomberman.BOMMAN_BIT:
                if(FixA.getFilterData().categoryBits == Bomberman.BOMMAN_BIT)
                    if(FixA.getUserData() != null && FixB.getUserData() != null) {
                        ((Bomman)FixA.getUserData()).die();
                    }
                else
                    if(FixA.getUserData() != null && FixB.getUserData() != null)
                        ((Bomman)FixB.getUserData()).die();
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
