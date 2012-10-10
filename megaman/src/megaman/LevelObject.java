package megaman;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public interface LevelObject {
	
	public String getName();
	 
	public void setPosition(Vector2f position);
 
	public Vector2f getPosition();
 
	public void render( Graphics graphics );
	
	public void init(GameContainer container) throws SlickException ;
 
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException;

}
