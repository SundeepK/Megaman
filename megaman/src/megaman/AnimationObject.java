package megaman;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
 
public class AnimationObject implements LevelObject {
 
	protected String name;
	protected Animation animation;
	protected Vector2f position;
	protected HashMap<String ,Animation> animationMap;

 
	public AnimationObject(String name, HashMap<String ,Animation> animationMap, Vector2f position){
		this.name = name;
		this.position = position;
		this.animationMap = animationMap;
	}
 
	@Override
	public String getName() {
		return name;
	}
 
	@Override
	public Vector2f getPosition() {
		return position;
		
		
	}
 
	@Override
	public void setPosition(Vector2f position) {
		this.position = position;
	}
 
 
	
	
	@Override
	public void render(Graphics graphics) {
		animation.draw(position.x, position.y);
	}
 
 
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}


	@Override
	public void init(GameContainer container) throws SlickException {
		
		
	}


 
}
