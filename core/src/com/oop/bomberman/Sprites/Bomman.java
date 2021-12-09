package com.oop.bomberman.Sprites;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oop.bomberman.Bomberman;
import com.oop.bomberman.Screens.PlayScreen;
import com.oop.bomberman.Sprites.Enemies.Enemy;
import com.oop.bomberman.Sprites.bomb.Bomb;
import com.oop.bomberman.Sprites.bomb.flame.*;

public class Bomman extends Sprite implements InputProcessor {
    public enum State {UP, DOWN, LEFT, RIGHT, STAND};
    public State currentState;
    public State prevState;
    public World world;
    public Body b2body;
    private PlayScreen screen;
    public float velocity = 35;
    public BodyDef bdef;

    private TextureRegion bUp, bDown, bLeft, bRight, currentWay;
    private Animation BommanUP, BommanDown, BommanLeft, BommanRight;
    private float stateTimer;
    private boolean isDead;
    public boolean isWin;

    private Array<Bomb> bombs;
    private int numberBomb = 1;

    public Bomman(PlayScreen screen) {
        super(screen.getAtlas().findRegion("spritesheet"));
        this.screen = screen;
        this.world = screen.getWorld();
        isDead = false;
        isWin = false;
        defineBomman();
        currentState = State.DOWN;
        prevState = State.DOWN;
        stateTimer = 0;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i=0; i<2; i++) {
            frames.add(new TextureRegion(getTexture(), i*16, 4, 16, 16));
        }
        BommanDown = new Animation(0.15f, frames);
        frames.clear();

        for(int i=2; i<4; i++) {
            frames.add(new TextureRegion(getTexture(), i*16, 4, 16, 16));
        }
        BommanLeft = new Animation(0.15f, frames);
        frames.clear();

        for(int i=4; i<6; i++) {
            frames.add(new TextureRegion(getTexture(), i*16, 4, 16, 16));
        }
        BommanRight = new Animation(0.15f, frames);
        frames.clear();

        for(int i=6; i<8; i++) {
            frames.add(new TextureRegion(getTexture(), i*16, 4, 16, 16));
        }
        BommanUP = new Animation(0.15f, frames);
        frames.clear();

        bDown = new TextureRegion(screen.getAtlas().findRegion("spritesheet"), 0, 0, 16, 16);
        bUp = new TextureRegion(screen.getAtlas().findRegion("spritesheet"), 16*7, 0, 16, 16);
        bLeft = new TextureRegion(screen.getAtlas().findRegion("spritesheet"), 16*2, 0, 16, 16);
        bRight = new TextureRegion(screen.getAtlas().findRegion("spritesheet"), 16*4, 0, 16, 16);
        setBounds(0, 0, 24, 24);
        setRegion(bDown);

        bombs = new Array<Bomb>();
    }

    public void update(float delta) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() /2);
        setRegion(getFrame(delta));
        for(Bomb bomb : bombs) {
            bomb.update(delta);
            if(bomb.isDestroyed()) {
                bombs.removeValue(bomb, true);
                numberBomb++;
            }
        }
    }
    public TextureRegion getFrame(float delta) {
        //currentState = getState();
        //System.out.println("State is: " + currentState);
        TextureRegion region = new TextureRegion();
        switch (currentState) {
            case UP:
                region = (TextureRegion) BommanUP.getKeyFrame(stateTimer, true);
                break;
            case RIGHT:
                region  = (TextureRegion) (BommanRight.getKeyFrame(stateTimer, true));
                break;
            case LEFT:
                region = (TextureRegion) (BommanLeft.getKeyFrame(stateTimer, true));
                break;
            case DOWN:
                region = (TextureRegion) BommanDown.getKeyFrame(stateTimer, true);
                break;
            default:
                region = currentWay;
                break;
        }
        stateTimer = currentState == prevState ? stateTimer + delta : 0;
        prevState = currentState;
        return region;
    }

    public State getState() {
        if(b2body.getLinearVelocity().y > 0) {
            return State.UP;
        }
        else if(b2body.getLinearVelocity().y < 0) {
            return State.DOWN;
        }
        else if(b2body.getLinearVelocity().x < 0) {
            return State.LEFT;
        }
        else if(b2body.getLinearVelocity().x > 0){
            return State.RIGHT;
        }
        else {
            return State.STAND;
        }
    }

    public void defineBomman() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32, 600);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);

        fdef.filter.categoryBits = Bomberman.BOMMAN_BIT;
        fdef.filter.maskBits = Bomberman.GROUND_BIT | Bomberman.FLAME_BIT | Bomberman.ENEMY_BIT | Bomberman.OBJECT_BIT | Bomberman.ENEMY_HEAD_BIT | Bomberman.ITEM_BIT | Bomberman.BOMB_BIT | Bomberman.BRICK_BIT | Bomberman.ITEM2_BIT | Bomberman.ITEM3_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.createFixture(fdef).setUserData(this);

        /*EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2,6), new Vector2(2, 6));
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("head");*/
    }

    public void place_Bomb() {
        if(numberBomb >= 1) {
            //bombs.add(new Bomb(screen, b2body.getPosition().x, b2body.getPosition().y, this));
            Bomb bomb = new Bomb(screen, b2body.getPosition().x, b2body.getPosition().y, this);
            bombs.add(bomb);
            numberBomb --;
        }
    }

    public void Increase_Speed() {
        System.out.println(velocity);
        velocity *= 2;
    }

    public void Increase_Bomb() {
        numberBomb++;
    }

    public void die() {
        if (!isDead()) {
            Bomberman.manager.get("audio/music/theme.wav", Music.class).stop();
            Bomberman.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            isDead = true;
            Filter filter = new Filter();
            filter.maskBits = Bomberman.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }
            b2body.applyLinearImpulse(new Vector2(0, 0), b2body.getWorldCenter(), true);
        }
    }

    public void win_game() {
        if(!isWin_Game()) {
            Bomberman.manager.get("audio/music/theme.wav", Music.class).stop();
            isWin = true;
            Filter filter = new Filter();
            filter.maskBits = Bomberman.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }
            b2body.applyLinearImpulse(new Vector2(0, 0), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return isDead;
    }
    public boolean isWin_Game() {return isWin;}

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        for(Bomb bomb : bombs) {
            bomb.draw(batch);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(isDead == false) {
            switch (keycode) {
                case Input.Keys.W :
                    b2body.setLinearVelocity(new Vector2(0,velocity));
                    currentState = State.UP;
                    currentWay = bUp;
                    break;
                case Input.Keys.S:
                    b2body.setLinearVelocity(new Vector2(0,-velocity));
                    currentState = State.DOWN;
                    currentWay = bDown;
                    break;
                case Input.Keys.A:
                    b2body.setLinearVelocity(new Vector2(-velocity,0));
                    currentState = State.LEFT;
                    currentWay = bLeft;
                    break;
                case Input.Keys.D:
                    b2body.setLinearVelocity(new Vector2(velocity,0));
                    currentState = State.RIGHT;
                    currentWay = bRight;
                    break;
                case Input.Keys.B:
                    //plant a bomb
                    place_Bomb();
                    System.out.println("press");
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                b2body.setLinearVelocity(new Vector2(0,0));
                currentWay = bLeft;
                currentState = State.STAND;
                break;
            case Input.Keys.D:
                b2body.setLinearVelocity(new Vector2(0,0));
                currentWay = bRight;
                currentState = State.STAND;
                break;
            case Input.Keys.W :
                b2body.setLinearVelocity(new Vector2(0,0));
                currentWay = bUp;
                currentState = State.STAND;
                break;
            case Input.Keys.S:
                b2body.setLinearVelocity(new Vector2(0,0));
                currentWay = bDown;
                currentState = State.STAND;
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
