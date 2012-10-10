package megaman;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.tiled.TiledMap;

public class BottomTile {
	
	private Point points;
	private TiledMap bottomTile;
	private Polygon poly;
	
	public BottomTile() throws SlickException{
		
		points = new Point(0,0);
		bottomTile = new TiledMap("res/bottile.tmx");
		poly = new Polygon(new float[] { points.getX(), points.getY(), points.getX()+64, 
				points.getY(), points.getX(), points.getY()+66, points.getX()+64, points.getY()+66});
	
	
	}
	
	public Point getTilePos(){
		
		return points;
		
	}
	
	public Polygon getTilePoly(){
		return poly;
	}
	
	public TiledMap getTileMap(){
		
		return bottomTile;
	}

}
