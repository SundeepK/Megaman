package megaman;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Enemy extends CollidableAnimationObject {
 
	protected Vector2f direction;
	protected SpriteSheet spriteSheet;
	protected SpriteSheet	sheetn;
	protected boolean isDead = false;

	protected long deathInterval = 0;
	protected long enemyWait = 0;
	protected Animation deathAnimation;
	protected float speed = 0.06f;
	protected Animation animationRight;
	protected Animation animationLeft;
	protected Animation aniAttackLeft;
	protected Animation aniAttackRight;
	protected Animation idleAnimation;
	SpriteSheet explosionSS;

	
	public Enemy(String name,HashMap<String ,Animation> animationMap, Vector2f initialDirection, Shape collisionShape, int collisionType) {
		
		super(name, animationMap , initialDirection, collisionShape, collisionType);
	
		this.direction = initialDirection.copy();
		animation = animationMap.get("walkLeftAni");
		
	}
	
	public void isDead(boolean b){
		isDead = b;

	}
	
	public void setDeathInt(long death){
		deathInterval = death;
	}
	
	public long getDeathInt(){
		return deathInterval;
	}
	
	public void setEnemyDead(boolean b){
		isDead = b;
	}
	
	public void setSpriteSheet(SpriteSheet ss){
		spriteSheet = ss;
	}
	
	
	public void setDirection(Vector2f direction){
		this.direction = direction.copy();
	}
 
	public Vector2f getDirection(){
		return direction;
	}
	
	public void showDeathAni() throws SlickException{
			sheetn = new SpriteSheet("res/expl.png", 40, 48);
			spriteSheet = sheetn;
			Animation ani = new Animation(sheetn,  100);
			animation.setAutoUpdate(true);
			ani.setLooping(false);
		 for(int frame = 0; frame < 7; frame++ ){
			 ani.addFrame(sheetn.getSprite(frame, 0), 100);
		 }
		 
			animation = ani;
	}
	
	public void setDeathAnimation(Animation deathAni){
		deathAnimation = deathAni;
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {}
	
	public void performAttack(PlayerMan player){
		
	}
	
	
	public void render(Graphics graphics) {
		super.render(graphics);
	}

	public void startANi(){
		animation.setAutoUpdate(true);
		 for(int frame = 0; frame < 6; frame++ ){
			 animation.addFrame(spriteSheet.getSprite(frame, 0), 100);

		 }		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		animation.update(delta);
	}

	public void performAttack(PlayerMan player, GameContainer gc) {
		
	}
	
	

}
