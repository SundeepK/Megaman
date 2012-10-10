package megaman;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public interface Level {
 
	public ArrayList<Enemy> getEnemys();

	public PlayerMan getPlayer();


	public void createPlayer();
	public void createEnemy() throws NumberFormatException, SlickException;
	
	
	
}