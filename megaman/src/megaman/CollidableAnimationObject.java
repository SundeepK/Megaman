package megaman;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
 
public abstract class CollidableAnimationObject extends AnimationObject implements CollidableObject {
 
	private Shape collisionShape;
	private int collisionType;
	protected boolean isJumping = true;
	protected long jumpInterval;
	protected float verticalSpeed = 0.0f;
	protected boolean isFacingRight = false;
	protected boolean isBlocked = false;
	protected boolean stopJump = false;
 
	public CollidableAnimationObject(String name, HashMap<String ,Animation> animationMap, Vector2f position, Shape collisionShape, int collisionType) {
		super(name, animationMap, position);
		this.collisionShape = collisionShape;
		this.collisionType = collisionType;
	}
 
	@Override
	public void setPosition(Vector2f position) {
		super.setPosition(position);
	}
	
	public void stopJump(boolean b){
		stopJump = b;
	}
	
	public void setJumping(boolean b){
		isJumping = b;
	}
	
	public void setAnimation(Animation ani){
		animation = ani;
	}
	
	public void setVerticleSpeed(float s){
		verticalSpeed = s;
	}
	
	public float getVerticleSpeed(){
		return verticalSpeed;
	}
	
	public boolean isJumping(){
		return isJumping;
	}
 
 
	@Override
	public Shape getNormalCollisionShape() {
		return collisionShape;
	}
 
 
	@Override
	public Shape getCollisionShape() {
		return collisionShape.transform( Transform.createTranslateTransform(position.x, position.y));
	}
 
	@Override
	public int getCollisionType() {
		return collisionType;
	}
 
	public Animation getAnimation() {
		return animation;
	}
 
	@Override
	public void render(Graphics graphics) {
		super.render(graphics);
//		graphics.setColor(new Color(0, 0, 0, 0));
//		graphics.draw(getCollisionShape());
	
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		
	}
	
	@Override
	public boolean isCollidingWith(CollidableObject collidable){
		return 		this.getCollisionShape().intersects(collidable.getCollisionShape());
	}
	


}
