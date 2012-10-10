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

public class BrawlerEnemy extends Enemy {
	Vector2f trackingPosition;
	boolean isAttacking = false;
	SpriteSheet attackRight;
	SpriteSheet idle;

	public BrawlerEnemy(String name, HashMap<String, Animation> animationMap,
			Vector2f initialDirection, Shape collisionShape, int collisionType) {
		super(name, animationMap, initialDirection, collisionShape,
				collisionType);
		Vector2f trackingPosition = new Vector2f(0, 0);
		animationRight = animationMap.get("walkRightAni");
		try {
			attackRight = new SpriteSheet("res/tankattackleft.png", 84, 65);
			aniAttackRight = new Animation(attackRight, 500);

			animationLeft = new Animation();
			animationLeft = animation;

		} catch (SlickException e) {

			e.printStackTrace();
		}
		
		idleAnimation = animationMap.get("idleAni");

	}

	@Override
	public void performAttack(PlayerMan player, GameContainer gc){
		trackingPosition = player.getPosition().copy();
//basic code for brawler attacking -- needs to be improved
		
		if(position.x - trackingPosition.x <= 200 && position.x - trackingPosition.x >=40){
			trackingPosition = trackingPosition.sub(position);
			trackingPosition.normalise();
			animation = animationLeft;
			isAttacking = true;
			isFacingRight = false;
		}else if(player.getCollisionShape().intersects(getCollisionShape())){
			trackingPosition = trackingPosition.sub(position);
			trackingPosition.normalise();
			trackingPosition.x = 0;
			isAttacking = true;
//			animation  = aniAttackRight;
		}else if(position.x - trackingPosition.x <= -40 && position.x - trackingPosition.x >=-200 ){
			trackingPosition = trackingPosition.sub(position);
			trackingPosition.normalise();
			isAttacking = true;
			isFacingRight = true;
			animation  = animationRight;

		}else{
			enemyWait = gc.getTime();
			isAttacking = false;
		}

	}

	public void render(Graphics graphics) {
		super.render(graphics);
		// graphics.setColor(new Color(0, 0, 0, 0));
		animation.draw(position.x, position.y);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {
		super.update(gc, sbg, delta);
		
		if(isJumping ){
			float sp = getVerticleSpeed();
			setVerticleSpeed(sp += 0.0001f * delta);
		 position.y = ((int) position.y+(verticalSpeed));
		 }

		if (isAttacking && !isFacingRight ) {
			this.position.x += trackingPosition.x + (0.0001 * delta);
		} else if (isAttacking && isFacingRight){
			this.position.x += trackingPosition.x - (0.0001 * delta);
		}else{
			animation = idleAnimation;
		}
		
		
		if(isFacingRight  && !isDead){
			
			position.set(position.x + (speed * delta), position.y);
			animation = animationRight;
		
		}else{
			 position.set(position.x - (speed * delta), position.y);
				animation = animationLeft;
			
		}

	}

}
