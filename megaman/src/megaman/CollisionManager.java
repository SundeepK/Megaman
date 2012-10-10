package megaman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollisionManager {
	 
	// the list of objects per type
	private Map<Integer, List<CollidableObject>> collidables;
	// the list of collisions per type
	private Map<Integer, List<Integer>> collisionsTypes;
	// the list handlers for collisions
	private Map<String, CollisionHandler> collisionHandlers;
 
	/**
	 * Creates a new CollisionManager
	 */
	public CollisionManager(){
		collidables 		= new HashMap<Integer, List<CollidableObject>>() ;
		collisionsTypes 	= new HashMap<Integer, List<Integer>>() ;
		collisionHandlers 	= new HashMap<String, CollisionHandler>() ;
	}
	
	public static String getKey(int type1, int type2){
		// generate key <smaller type>-<bigger type>
		return (type1 < type2) ? type1+"-"+type2 : type2+"-"+type1; 
	}
	
	public void addCollidable(CollidableObject collidable){
		// obtain the entry for this type
		System.out.println(collidables);
		List<CollidableObject> collidableList = collidables.get(collidable.getCollisionType());

		//if there is no entry for this type add one
		if(collidableList == null){
			collidableList = new ArrayList<CollidableObject>();
			collidables.put(collidable.getCollisionType(), collidableList);
	
		}
 
		// and an entry to the list
		collidableList.add(collidable);
		System.out.println(collidables);
		
		
		
	}
	
	public void removeCollidable(CollidableObject collidable){
		// obtain the entry for this type
		List<CollidableObject> collidableList = collidables.get(collidable.getCollisionType());
 
		// if the entry exists remove the object from the list (if possible)
		if(collidableList != null){
			collidableList.remove(collidable);
		}
	}
	
	public void addHandler(CollisionHandler handler){
		// generate the key
		String key = getKey(handler.getCollider1Type(), handler.getCollider2Type());
 
		// add the handler to the map
		collisionHandlers.put( key, handler );
 
		// add the collision type1 to type 2
		addTypesToCollision(handler.getCollider1Type(), handler.getCollider2Type());
		// add the collision type2 to type 1
		addTypesToCollision(handler.getCollider2Type(), handler.getCollider1Type());
	}
 
	private void addTypesToCollision(int type1, int type2){
		// obtain collision type entry
		List<Integer> typeCollisions = collisionsTypes.get(type1);
 
		// if there is no entry create one
		if(typeCollisions == null){
			typeCollisions = new ArrayList<Integer>();
			collisionsTypes.put(type1, typeCollisions);
		}
		// add collision to list
		typeCollisions.add(type2);
	}
	
	public void processCollisions(){

		// prepare a set of all keys to collide
		Set<String> allCollisionKeys = new HashSet<String>();
 
		// prepare a list of collisions to handle
		List<CollisionData> collisions = new ArrayList<CollisionData>();
 
		Set<Integer> types = collisionsTypes.keySet();
 
		// obtain every type for collision
		for(Integer type : types){
			// obtain for each type the type it collides with
			List<Integer> collidesWithTypes = collisionsTypes.get(type);
 
			for(Integer collidingType : collidesWithTypes){
				// if the pair was already treated ignore it else treat it
				if( !allCollisionKeys.contains(getKey(type, collidingType)) ){
					// obtain all object of type
					List<CollidableObject> collidableForType = collidables.get(type);
					// obtain all object of collidingtype
					List<CollidableObject> collidableForCollidingType = collidables.get(collidingType);
 
					for( CollidableObject collidable : collidableForType ){
						for( CollidableObject collidesWith : collidableForCollidingType ){
							if(collidable.isCollidingWith(collidesWith)){
							
								CollisionData cd = new CollisionData();
								cd.handler = collisionHandlers.get(getKey(type, collidingType));
								cd.object1 = collidable;
								cd.object2 = collidesWith;
 
								collisions.add(cd);
							}
						}
					}
 
					allCollisionKeys.add(getKey(type, collidingType));
				}				
			}
		}
 
		for(CollisionData cd : collisions){
			cd.handler.performCollision(cd.object1, cd.object2);
 
		}
		
//	     System.out.println("unsorted map");
//	        for (String key : collisionHandlers.keySet()) {
//	            System.out.println("key/value: " + key + "/"+collisionHandlers.get(key));
//	        }
	}

	class CollisionData{
		public CollisionHandler handler;
		public CollidableObject object1;
		public CollidableObject object2;
	}
}