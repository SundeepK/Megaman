package megaman;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract class Bullet extends CollidableAnimationObject{

	protected int damage;
	protected boolean movingLeft=false;

	public Bullet(String name,  HashMap<String ,Animation> animationMap, Vector2f position,
			Shape collisionShape, int collisionType) {
		super(name, animationMap, position, collisionShape, collisionType);

		
	}

	public int getDmg(){
		
		return damage;
	}
	
	public void setDmg(int d){
		damage = d;
	}
	
	public void isMovingLeft(boolean b){
		movingLeft = b;
	}
	
	@Override
	public void render(Graphics graphics) {
		super.render(graphics);
	}

	
}
