package megaman;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class NormalBullet extends Bullet{

	
	public NormalBullet(String name, HashMap<String ,Animation> animationMap,  Vector2f position,
			Shape collisionShape, int collisionType) {
		super(name, animationMap, position, collisionShape, collisionType);
		damage = 10;
		animation = animationMap.get("normalBullet");
	
	}
	

	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		animation.update(delta);
		
		if(!movingLeft){
		position.x +=  0.4f * delta;
		}else{
			position.x -=  0.4f * delta;
		}
		

	}
	
	@Override
	public void render(Graphics graphics) {
		super.render(graphics);
		animation.draw(position.x, position.y);
		graphics.draw(getCollisionShape());
	
		
		
	}

}
