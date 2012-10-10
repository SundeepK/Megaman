package megaman;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public interface TileMap {


	public boolean isCollidingWith(CollidableObject collidable, GameContainer gc);
	
//	public boolean blocked(float x, float y);
	public boolean blocked(int x, int y);

//	public boolean blocked(int x);
	
	public Vector2f getPosition();
 
	public void render( Graphics graphics );
	
	public void init(GameContainer container) throws SlickException ;
 
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException;
	
	
}
