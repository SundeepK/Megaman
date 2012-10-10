package megaman;


	import java.util.Random;
	 
	import org.newdawn.slick.geom.Shape;
	import org.newdawn.slick.geom.Vector2f;
	 

	 
	public class PlayerEnemyCollisionHandler implements CollisionHandler {
	 
		private Random r ;
	 
		public PlayerEnemyCollisionHandler(){
			r = new Random();
		}
	 
		@Override
		public int getCollider1Type() {
			return 1;
		}
	 
		@Override
		public int getCollider2Type() {
			return 2;
		}
	 
		@Override
		public void performCollision(CollidableObject collidable1,
				CollidableObject collidable2) {
			
			// check if 2 collidable objects are colliding
			if(!collidable1.isCollidingWith(collidable2)){
				return;
			}
			
//			System.out.println("colliding");
			
			Enemy enemy = null;
			CollidableObject object = null;
	 
			// Cast the correct objects		
			if(collidable1 instanceof Enemy){
				enemy = (Enemy) collidable1;
				object = collidable2;
			}else{
				enemy = (Enemy) collidable2;
				object = collidable1;
			}


		}
	 
}
	
