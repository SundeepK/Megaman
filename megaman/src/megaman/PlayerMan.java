package megaman;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerMan extends CollidableAnimationObject{

	protected Vector2f direction;
	private int dir;
	protected Animation playerRun;
	protected Animation playerIdle;
	protected Animation playerIdle1;
	protected Animation playerRunback;
	protected Animation playerJumpR;
	protected Animation playerIdleBack;
	protected Animation playerIdleBack1;
	protected Animation playerJumpL;
	protected Animation playerStandingShoot;
	private boolean facingRight;
	long shootInterval = 0;
	ArrayList<Bullet> bulletList;







	public PlayerMan(String name, HashMap<String ,Animation> animationMap, Vector2f initialDirection, Shape collisionShape, int collisionType, int d) throws SlickException {
		super(name, animationMap, initialDirection, collisionShape, collisionType);

		this.direction = initialDirection.copy();

		dir = d;

		playerIdle = new Animation();
		playerIdle.setAutoUpdate(true);
		for(int frame = 0; frame < 5; frame++ ){
			playerIdle.addFrame(getSheet().getSprite(frame, 0), 30);

		}

		bulletList = new ArrayList<Bullet>(); 

	}



	public void setDirection(Vector2f direction){
		this.direction = direction.copy();
	}

	public Vector2f getDirection(){
		return direction;
	}

	public SpriteSheet getRunSS() throws SlickException{

		SpriteSheet sheetn = new SpriteSheet("res/runs2.png", 48, 44);
		return sheetn;

	}

	public SpriteSheet getRunbackSS() throws SlickException{

		SpriteSheet sheetn = new SpriteSheet("res/runs2b.png", 48, 40);
		return sheetn;

	}


	public SpriteSheet getIdleBack() throws SlickException{

		SpriteSheet sheetn = new SpriteSheet("res/idlemanback2.png", 38, 45);
		return sheetn;

	}

	public SpriteSheet getstandingShoot() throws SlickException{

		SpriteSheet sheetn = new SpriteSheet("res/standingshoot.png", 48, 45);
		return sheetn;

	}

	public SpriteSheet getJumpR() throws SlickException{

		SpriteSheet sheetn = new SpriteSheet("res/jump1.png", 44, 60);
		return sheetn;

	}

	public SpriteSheet getJumpL() throws SlickException{

		SpriteSheet sheetn = new SpriteSheet("res/jumpl.png", 44, 60);
		return sheetn;

	}

	public SpriteSheet getSheet() throws SlickException{

		SpriteSheet sheetn = new SpriteSheet("res/idleman2.png", 38, 43);
		return sheetn;

	}

	@Override
	public void init(GameContainer container) throws SlickException {


		playerIdle = new Animation();
		playerIdle.setAutoUpdate(true);
		for(int frame = 0; frame < 5; frame++ ){
			playerIdle.addFrame(getSheet().getSprite(frame, 0), 900);

		}


		playerIdleBack = new Animation();
		playerIdleBack.setAutoUpdate(true);
		for(int frame = 0; frame < 5; frame++ ){
			playerIdleBack.addFrame(getIdleBack().getSprite(frame, 0), 900);

		}

		playerJumpR = new Animation();
		playerJumpR.setAutoUpdate(true);
		for(int frame = 0; frame < 1; frame++ ){
			playerJumpR.addFrame(getJumpR().getSprite(frame, 0), 150);

		}

		playerJumpL = new Animation();
		playerJumpL.setAutoUpdate(true);
		for(int frame = 0; frame < 1; frame++ ){
			playerJumpL.addFrame(getJumpL().getSprite(frame, 0), 150);

		}

		playerRun = new Animation(false);
		for(int frame = 0; frame < 9; frame++ ){
			playerRun.addFrame(getRunSS().getSprite(frame, 0), 60);

		}

		playerStandingShoot = new Animation();
		playerStandingShoot.setAutoUpdate(true);
		for(int frame = 0; frame < 3; frame++ ){
			playerStandingShoot.addFrame(getstandingShoot().getSprite(frame, 0), 130);

		}

		playerRunback = new Animation(false);

		for(int frame = 0; frame < 9; frame++ ){
			playerRunback.addFrame(getRunbackSS().getSprite(frame, 0), 60);

		}

		playerIdle1 =  playerIdle;



		playerIdleBack1 = playerIdleBack;



	}


	@Override 
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{




		if(gc.getInput().isKeyDown(Input.KEY_SPACE) ){
			if(!isJumping){
				playerIdle = playerStandingShoot;
			}
			if((gc.getTime() -  shootInterval >  300)){
				shootInterval =  gc.getTime();
				SpriteSheet ss = new SpriteSheet("res/bullet.png", 14, 10);
				Animation ani = new Animation(true);
				ani.addFrame(ss.getSprite(0, 0), 100);
				Vector2f bulletPos = new Vector2f(position.x + 22, position.y+14);
				Shape bullShape = new Rectangle(0, 0, 14, 10);
				HashMap<String, Animation> animationMap = new HashMap<String, Animation>();
				animationMap.put("normalBullet", ani);
				Bullet bullet = new NormalBullet("normal", animationMap, bulletPos, bullShape, 1);
				if(facingRight){
					bullet.isMovingLeft(false);
				}else{
					bullet.isMovingLeft(true);
				}
				bulletList.add(bullet);

			}

		}

		if(facingRight==true && !isJumping && !gc.getInput().isKeyDown(Input.KEY_SPACE)){

			playerIdle = playerIdle1;
		}

		if(facingRight==false && !isJumping){
			playerIdle = playerIdleBack;
		}


		if(gc.getInput().isKeyDown(Input.KEY_LEFT)){
			if(!isJumping){
				playerIdle = playerRunback;
			}

			playerRunback.update(delta);
			facingRight = false;
			position.set(position.x - (0.22f * delta), position.y);
			//			    getPlayerPoly().setX(getPos().getX());

		}

		if(gc.getInput().isKeyDown(Input.KEY_RIGHT)){
			if(!isJumping){
				playerIdle = playerRun;
			}
			playerRun.update(delta);
			facingRight = true;

			position.set(position.x+ (0.22f * delta), position.y);
			//			    getPlayerPoly().setX(getPos().getX());
			//			    setPosition(position);


		}


		if(gc.getInput().isKeyDown(Input.KEY_UP) && !isJumping && ( gc.getTime() - jumpInterval > 500 ) && !stopJump ){

			if(facingRight){
				playerIdle= playerJumpR;
			}

			if(!facingRight){
				playerIdle= playerJumpL;
			}

			verticalSpeed = -1.1f * delta;//negative value indicates an upward movement 
			isJumping = true;
			jumpInterval= gc.getTime();



		}

		position.y = ((int) position.y+verticalSpeed);

		float sp = getVerticleSpeed();
		if(sp > 1.5){
			stopJump(true);
			if(facingRight){
				playerIdle= playerJumpR;
			}

			if(!facingRight){
				playerIdle= playerJumpL;
			}
		}
	}





	@Override 
	public void render(Graphics graphics) {
		
		playerIdle.draw(position.x, position.y);
//		playerIdle.drawFlash(position.x, position.y, 30, 40);
		graphics.draw(getCollisionShape());




	}





}
