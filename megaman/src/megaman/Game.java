package megaman;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

	public Game() {
		super("game");
		
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		 
	
 
		GameplayState state = new GameplayState();
		
		state.setLevelFile(new File("level1.xml"));
 
		addState(state);
	}
	
	public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new Game());
 
         // Application properties
         app.setDisplayMode(800, 600, false);
         //app.setSmoothDeltas(true);
 
         app.start();
    }
	

}
