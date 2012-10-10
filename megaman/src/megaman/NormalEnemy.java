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

public class NormalEnemy extends Enemy {



	public NormalEnemy(String name,  HashMap<String ,Animation> animationMap,
			Vector2f initialDirection, Shape collisionShape, int collisionType) {
		super(name, animationMap,  initialDirection, collisionShape, collisionType);
		animationLeft = animationMap.get("walkLeftAni");
		animationRight = animationMap.get("walkRightAni");
		
	    try {
	    	explosionSS = new SpriteSheet("res/expl.png", 40, 48);
		} catch (SlickException e) {

			e.printStackTrace();
		}

		deathAnimation = new Animation(explosionSS,  80);
	
	}
	
	
	public void render(Graphics graphics) {
		super.render(graphics);
//		graphics.setColor(new Color(0, 0, 0, 0));
		animation.draw(position.x, position.y);

	
	}

	@Override 
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
	super.update(gc, sbg, delta);	
		
		if(isJumping ){
			float sp = getVerticleSpeed()-1f;
			setVerticleSpeed(sp += 0f * delta);
		 position.y = ((int) position.y+(verticalSpeed));
		 }
		
		if(isFacingRight && !isDead){
			
			position.set(position.x + (speed * delta), position.y);
			animation = animationRight;
	
		}else{
			 position.set(position.x - (speed * delta), position.y);
				animation = animationLeft;
		}
		
		if(isDead){
			speed = 0f;
			
//				SpriteSheet sheetn2 = new SpriteSheet("res/expl.png", 40, 48);
//				spriteSheet = sheetn;
//				Animation ani = new Animation(sheetn2,  20);
//				ani.setAutoUpdate(true);
//				ani.setLooping(false);
//			 for(int frame = 0; frame < 7; frame++ ){
//				 ani.addFrame(sheetn2.getSprite(frame, 0), 100);
//			 }
			 
				animation = deathAnimation;
				
			
		}
		 

	}
	
	
	
	

}
