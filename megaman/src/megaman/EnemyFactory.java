package megaman;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class EnemyFactory {
	
	public static Enemy createEnemy(String name,  HashMap<String ,Animation> animationMap,
			 Vector2f initialDirection, Shape collisionShape, int collisionType){
		
		if(name.equalsIgnoreCase("normal")){
			return new NormalEnemy(name, animationMap,  initialDirection, collisionShape, collisionType);
		}else if(name.equalsIgnoreCase("brawler")){
			return new BrawlerEnemy(name, animationMap, initialDirection, collisionShape, collisionType);
		}
		
		return null;
	}

}
