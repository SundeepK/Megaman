package megaman;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class GameplayState extends BasicGameState {

	private Level level;
	private File levelFile;
	private PlayerMan player;
	private CollisionManager collisionManager;
	private CollidableMap map;
	private ArrayList<Enemy> deadEnemies;


	@Override
	public int getID() {
		return 0;
	}
	//
	public void setLevelFile(File inputStream) {
		levelFile = inputStream;

	}

	public void enter(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		deadEnemies = new ArrayList<Enemy>();
		if (levelFile == null) {
			throw new SlickException("No level to load");
		}
		
		level = new LevelImpl();

		level.createPlayer();
		level.createEnemy();
		//		collisionManager = new CollisionManager();

		//		for(Enemy b : level.getEnemys()){
		//			collisionManager.addCollidable(b);
		//		}
		//		


		//		collisionManager.addCollidable(	level.getPlayer());


		level.getPlayer().init(gc);

		//		collisionManager.addHandler(new PlayerEnemyCollisionHandler());

		TiledMap tiledMap = new TiledMap("res/map1l.tmx");

		map = new CollidableMap(tiledMap, level.getPlayer(), new Vector2f(0, 0));

		map.init(gc);

	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
	throws SlickException {



	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {
		gc.setSmoothDeltas(true);
		gc.setVSync(true);
		map.processMapCollision(level.getPlayer(), delta, gc);

		for(int i = 0; i < level.getPlayer().bulletList.size();i++){
			level.getPlayer().bulletList.get(i).update(gc, sbg, delta);

		
			
			for(int j = 0; j < level.getEnemys().size();j++){	
				if(level.getPlayer().bulletList.get(i).isCollidingWith(level.getEnemys().get(j))){
					
					level.getPlayer().bulletList.remove(i);	
//					level.getEnemys().get(j).position.set(level.getEnemys().get(j).position.x + (0f * delta), level.getEnemys().get(j).position.y);
					level.getEnemys().get(j).isDead(true);
					level.getEnemys().get(j).setDeathInt(gc.getTime());
		
					break;

				}else if(level.getPlayer().bulletList.get(i).getPosition().x > level.getPlayer().position.x + gc.getWidth() ||
						level.getPlayer().bulletList.get(i).getPosition().x < level.getPlayer().position.x - gc.getWidth()){
					level.getPlayer().bulletList.remove(i);	

					break;
				}
			}
			

		}
		
	System.out.println("x" + level.getPlayer().getPosition().x);
	System.out.println("y" + level.getPlayer().getPosition().y);
		for (int i  = 0; i < level.getEnemys().size();i++){
			map.processMapCollision(level.getEnemys().get(i), delta, gc);
			level.getEnemys().get(i).update(gc, sbg, delta);
			level.getEnemys().get(i).performAttack(level.getPlayer(), gc);
		if(level.getEnemys().get(i).isDead && 
			gc.getTime() - level.getEnemys().get(i).getDeathInt() > 300){
			level.getEnemys().remove(i);
		}
		}

		level.getPlayer().update(gc, sbg, delta);







		gc.setVSync(true);

		//		collisionManager.processCollisions();



	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
	throws SlickException {

		map.render(gr);



		
		for ( Enemy e : level.getEnemys()){
			e.render(gr);
		}

		for ( Enemy e : deadEnemies){
			e.render(gr);
		}


		level.getPlayer().render(gr);

		for ( Bullet bullet : level.getPlayer().bulletList){
			bullet.render(gr);

		}


	}







}
