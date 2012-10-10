package megaman;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class CollidableMap implements TileMap {

	private static final int PLAYER_SIZE = 43;
	/** The size of the tiles - used to determine the amount to draw */
	private static final int TILE_SIZE = 63;

	private TiledMap map;  
	private int transX, transY;
	private int topOffsetInTiles;
	boolean OnPlatform;
	private boolean[][]blocked;
	private ArrayList<Shape> collisionPlatform;
	private ArrayList<Shape> enemyPlatform;
	private int widthInTiles;
	private int heightInTiles;
	private int leftOffsetInTiles;
	private Vector2f position;
	private PlayerMan player;
	private Rectangle collidingShapeTopSide;
	private int cameraHeight;
	private int cameraWidth;

	public CollidableMap (TiledMap map, PlayerMan player, Vector2f position){

		this.player = player;
		this.position = position;
		this.map = map;
		collidingShapeTopSide = new Rectangle(0, 0, 0, 0);

		collisionPlatform = new ArrayList<Shape>();
		enemyPlatform = new ArrayList<Shape>();
		for (int x=0;x<map.getWidth();x++) {
			for (int y=0;y<map.getHeight();y++) {
				int tileID = map.getTileId(x, y, 0);
				int enemytileID = map.getTileId(x, y, 1);
				String value = map.getTileProperty(tileID, "platform", "false");
				String enemyPlatformProp = map.getTileProperty(enemytileID, "enemyCollidable", "false");
				if("true".equals(value)){	
					collisionPlatform.add(new Rectangle(x * 63 + position.x , y * 64 + position.y , 63, 64));
				}
				if("true".equals(enemyPlatformProp)){	
					enemyPlatform.add(new Rectangle(x * 63 + position.x , y * 64 + position.y , 63, 64));
				}
				
				
			}
		}


		//		for(Shape shape :  collisionShape){
		//			System.out.println(shape.getX());
		//		}



	}

	@Override
	public boolean blocked(int x, int y) {
		return blocked[ x][y];
	}




	@Override
	public Vector2f getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		widthInTiles = container.getWidth() / TILE_SIZE;
		heightInTiles = container.getHeight() / TILE_SIZE;
		heightInTiles = heightInTiles / 2;
		leftOffsetInTiles = widthInTiles / 2;

		cameraWidth = container.getWidth();
		cameraHeight = container.getHeight();

	}

	@Override
	public boolean isCollidingWith(CollidableObject collidable, GameContainer gc) {
		// TODO Auto-generated method stub
		return false;
	}


	public float checkCollision(CollidableAnimationObject shape1, Shape shape2){
		//		
		float[] playerEdges =	(shape1.getCollisionShape().getPoints());
		float[] collShape =  (shape2.getPoints());

		double minOverlap = Double.MAX_VALUE;

		ArrayList<Vector2f> playerV = new ArrayList<Vector2f>();
		ArrayList<Vector2f> shape2V = new ArrayList<Vector2f>();

		ArrayList<Vector2f> normals = new ArrayList<Vector2f>();

		float playerMin = 0; 
		float playerMax = playerMin;

		float shapeMin = 0; 
		float shapeMax = shapeMin;
		Vector2f shapeNormal = new Vector2f(0,0);


		for (int i = 0; i < playerEdges.length -1 ;i+=2){
			playerV.add(new Vector2f(playerEdges[i], playerEdges[i == playerEdges.length - 2 ?  playerEdges.length - 1 : i +1]));
			shape2V.add(new Vector2f(collShape[i], collShape[i == collShape.length - 2 ?  collShape.length - 1 : i +1]));
		}

		Vector2f mtv = new Vector2f (0,0);
		Vector2f copyaxis = new Vector2f (0,0);

		for(int j = playerV.size()-1, i = 0; i < playerV.size(); j = i, i++){

			Vector2f playerCorner1 = new Vector2f(playerV.get(j));
			Vector2f playerCorner2 = new Vector2f(playerV.get(i));

			Vector2f shapeCorner1 = new Vector2f(shape2V.get(j));
			Vector2f shapeCorner2 = new Vector2f(shape2V.get(i));

			Vector2f playerNormal = new Vector2f (playerCorner2.sub(playerCorner1).getPerpendicular().normalise());
			shapeNormal.set(shapeCorner2.sub(shapeCorner1).getPerpendicular().normalise());

			//		System.out.println(playerNormal);



			playerMin = playerV.get(0).dot(playerNormal); 
			playerMax = playerMin;

			shapeMin = shape2V.get(0).dot(playerNormal); 
			shapeMax = shapeMin;


			for(int v = 0; v < playerV.size(); v++){
				float dot = playerV.get(v).dot(playerNormal);

				if(dot < playerMin){
					playerMin = dot;
				}else if(dot > playerMax){
					playerMax = dot;
				}		
			}

			for(int u = 0; u < shape2V.size(); u++){
				float dot = shape2V.get(u).dot(playerNormal);

				if(dot < shapeMin){
					shapeMin = dot;
				}else if(dot > shapeMax){
					shapeMax = dot;
				}	    
			}

			//			 playerMin += offset;
			//			 playerMax += offset;


			if(playerMin > shapeMax ||   shapeMin >  playerMax){
				return 0;
			}else{

				double overlap = Math.min(playerMax - shapeMin, shapeMax - playerMin); 
				if (overlap <= minOverlap) {
					minOverlap = overlap;
				}

			}

		}


		//	System.out.println("playermin " + playerMin);
		//	System.out.println("playermx " + playerMax);
		//	System.out.println("shapemin " + shapeMin);
		//	System.out.println("shapemax " + shapeMax);
		//	System.out.println(minOverlap);


		return (float) minOverlap;



	}

	//Most ugliest collision detection processing ever written
	public void processMapCollision(CollidableAnimationObject colObject, int delta, GameContainer gc){

		//		for(Shape platform : collisionPlatform){
		//			
		//	 checkCollision(player, platform);
		//		
		//		}

		int playerx = (int) (colObject.getPosition().getX() / TILE_SIZE);
		int playery = (int) (colObject.getPosition().getY() / TILE_SIZE);

		ArrayList<Shape> tempShapes = new ArrayList<Shape>();
		
		for(int i = 0; i < collisionPlatform.size();i++){
			int pX= (int) collisionPlatform.get(i).getMinX() / TILE_SIZE;
			int pY= (int) collisionPlatform.get(i).getMinY() / TILE_SIZE;
			if((pX <= playerx + 1 && pX >= playerx - 1 )){
				if( pY <= playery + 4 ){
				tempShapes.add(collisionPlatform.get(i));
				
				}
			}
		}
		

	for(int i = 0; i < tempShapes.size();i++ ){
		float collisionValue = checkCollision(colObject, tempShapes.get(i));
			if(collisionValue > 0){


				Vector2f vectorSide = new Vector2f(tempShapes.get(i).getCenter()[0] - colObject.getCollisionShape().getCenter()[0], 
						tempShapes.get(i).getCenter()[1] - player.getCollisionShape().getCenter()[1]);

				if(vectorSide.x > 0 
						&& colObject.getCollisionShape().getMaxY() > tempShapes.get(i).getMinY() 
						&& colObject.getCollisionShape().getMinX() + 30 <  tempShapes.get(i).getMinX() 

				){
					
					colObject.getPosition().set(colObject.getPosition().x - collisionValue, colObject.getPosition().y +  (0.02f * delta));
//					0.02f * delta)

				}else if( colObject.getCollisionShape().getMaxY() >= tempShapes.get(i).getMinY()  &&
						(vectorSide.y > 0) && (int) (tempShapes.get(i).getY() / TILE_SIZE) - 1 == playery
				){
					colObject.getPosition().set(colObject.getPosition().x, colObject.getPosition().y - (collisionValue));
					//					player.getPosition().set(player.getPosition().x, player.getPosition().y -10);
//										player.getPosition().set(player.getPosition().x, player.getPosition().y -  (0.1f * delta));

					colObject.setVerticleSpeed(0);
					colObject.setJumping(false);
					colObject.stopJump(false);

				}else if(vectorSide.x < 0 && colObject.getCollisionShape().getMaxY() > tempShapes.get(i).getMinY()
						&& colObject.getCollisionShape().getMaxX() + 30 >  tempShapes.get(i).getMaxX()){
					colObject.getPosition().set(colObject.getPosition().x + collisionValue, colObject.getPosition().y +  (0.02f * delta));
				}
				
			}
				
			
				float sp = colObject.getVerticleSpeed();

				colObject.setVerticleSpeed(sp += 0.0075f  * delta); 
			
			
		}

		if(colObject.isJumping()){
			float sp = colObject.getVerticleSpeed();

			colObject.setVerticleSpeed(sp += 0.07f * delta);   

		}
		
		if(colObject instanceof Enemy){
			for(Shape platform: enemyPlatform){
				
			
				if(checkCollision(colObject, platform) > 0){
					Vector2f vectorSide = new Vector2f(platform.getCenter()[0] - colObject.getCollisionShape().getCenter()[0], 
							platform.getCenter()[1] - player.getCollisionShape().getCenter()[1]);
//					System.out.println(platform.getCenter()[0]);
//					System.out.println("enemy" + colObject.getCollisionShape().getCenter()[0]);
//					System.out.println(vectorSide.x);

					if(vectorSide.x < 0 
//							&& colObject.getCollisionShape().getMaxY() > platform.getMinY() 
//							&& colObject.getCollisionShape().getMinX() + 30 <  platform.getMinX()
							){
						colObject.isFacingRight = true;
						

					}else if(vectorSide.x  > 0){
							
//							&& colObject.getCollisionShape().getMaxX() - 30 >  platform.getMaxX()
							
						colObject.isFacingRight = false;
					}
					
				}
				
				
			}
			
		}

	}







	@Override
	public void render(Graphics graphics) {

		int mapWidth = cameraWidth * TILE_SIZE;
		int mapHeight = cameraHeight * TILE_SIZE;
//simple map scrolling and clipping

		if(player.getPosition().x-cameraWidth/2+16 < 0)
			transX = 0;
		else if(player.getPosition().x+cameraWidth/2+16 > mapWidth)
			transX = -mapWidth+cameraWidth;
		else
			transX = (int)-player.getPosition().x+cameraWidth/2-16;

		if(player.getPosition().y-cameraHeight/2+16 < 0)
			transY = 0;
		else if(player.getPosition().y+cameraHeight/2+16 > mapHeight)
			transY = -mapHeight+cameraHeight;
		else
			transY = (int)-player.getPosition().y+cameraHeight/2;

		graphics.translate(transX, -10);

		map.render(0, 0);


		for (Shape shapePlatform : collisionPlatform){
//			graphics.draw(shapePlatform);
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {
		// TODO Auto-generated method stub

	}



}