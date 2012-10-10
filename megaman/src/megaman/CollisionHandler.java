package megaman;

public interface CollisionHandler {


		 
		public int getCollider1Type();
	 
		public int getCollider2Type();
	 
		public void performCollision(CollidableObject collidable1, CollidableObject collidable2);

}
