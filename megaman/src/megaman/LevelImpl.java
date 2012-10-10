package megaman;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LevelImpl implements Level{
		
	File levelFile;
	Document xmlLvlDoc;
	NodeList playerData;
	ArrayList<Node> enemyNodes;
	ArrayList<Enemy> enemyList;
	HashMap<String ,Animation> animationMap;
	HashMap<String ,Animation> playerAnimationMap;
	PlayerMan player;
		public LevelImpl(){
		try {
			
//			String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
		levelFile = new File(new File("./megaman/bin").toURI().relativize(new File("megaman/bin/level1.xml").toURI()).getPath());
		
	
			DocumentBuilderFactory	docFactory =  DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			xmlLvlDoc = docBuilder.parse(levelFile); 
			xmlLvlDoc.getDocumentElement().normalize();
			
			playerData = xmlLvlDoc.getElementsByTagName("player");
			 enemyNodes = new ArrayList<Node>();
			 enemyList = new ArrayList<Enemy>();
			 animationMap = new HashMap<String ,Animation>();
			 playerAnimationMap = new HashMap<String ,Animation>();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	public void createPlayer(){
		NodeList rootNode = xmlLvlDoc.getChildNodes();
		Node levelNode = getNode("level", rootNode);
		Node playerNode = getNode("player", levelNode.getChildNodes());
	    NodeList playerNodeList = playerNode.getChildNodes();
	    
	    String CollisionShape = getNodeValue("collision_shape", playerNodeList);
	    String [] collisionData = getNodeValue("collision_shape_data", playerNodeList).split(",");
	    String animationLocation = getNodeValue("animation_location", playerNodeList);
	    String [] aniFrameData = getNodeValue("animation_frame_data", playerNodeList).split(",");
	    
	    SpriteSheet ss;
		try {
			ss = new SpriteSheet(animationLocation,  Integer.parseInt(aniFrameData[0]),  Integer.parseInt(aniFrameData[1]));
		    Animation playerAni = new Animation(ss, Integer.parseInt(aniFrameData[2]));
		    
		    String [] playerPos = getNodeValue("position", playerNodeList).split(",");
		
		    Vector2f position = new Vector2f(Integer.parseInt(playerPos[0]), Integer.parseInt(playerPos[1]));
		    
		    Shape collisionShape = new Rectangle(Integer.parseInt(collisionData[0]), 
		    		Integer.parseInt(collisionData[1]), Integer.parseInt(collisionData[2]), Integer.parseInt(collisionData[3]));
		    
		    playerAnimationMap.put("walfLeft", playerAni);
		    
		    player = new PlayerMan("megaman", playerAnimationMap, position, collisionShape, 1, 1);
		
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void createEnemy() throws NumberFormatException, SlickException{
		NodeList rootNode = xmlLvlDoc.getChildNodes();
		NodeList levelNode = getNode("level", rootNode).getChildNodes();
		
	
		
		for(int i =0; i < levelNode.getLength();i++){
			Node enemyNode = levelNode.item(i);
			
			if(enemyNode.getNodeName().equalsIgnoreCase("enemy")){
			
				
				NodeList  enemyNodeList = levelNode.item(i).getChildNodes();
			    String CollisionShape = getNodeValue("collision_shape", enemyNodeList);
			    String [] collisionData = getNodeValue("collision_shape_data", enemyNodeList).split(",");
			    String animationLeft = getNodeValue("left_animation", enemyNodeList);
			    String [] leftAniData = getNodeAttribute("left_animation", enemyNodeList).split(",");
			    String enemyType = getNodeValue("enemy_type", enemyNodeList);
			    String animationRight = getNodeValue("right_animation", enemyNodeList);
			    String [] rightAniData = getNodeAttribute("right_animation", enemyNodeList).split(",");
			    String deathAniss = getNodeValue("death_animation", enemyNodeList);
			    String [] deathAnimationData = getNodeAttribute("death_animation", enemyNodeList).split(",");
			    String idleAniSS = getNodeValue("idle_animation", enemyNodeList);
			    String [] idleAniData = getNodeAttribute("idle_animation", enemyNodeList).split(",");
			    
			    SpriteSheet	idleSS = new SpriteSheet(idleAniSS,  Integer.parseInt(idleAniData[0]),  Integer.parseInt(idleAniData[1]));
				Animation idleAni = new Animation(idleSS, Integer.parseInt(idleAniData[2]));
  
			    SpriteSheet	ss = new SpriteSheet(animationLeft,  Integer.parseInt(leftAniData[0]),  Integer.parseInt(leftAniData[1]));
				Animation enemyAniLeft = new Animation(ss, Integer.parseInt(leftAniData[2]));
				
			    SpriteSheet	ssRight = new SpriteSheet(animationRight,  Integer.parseInt(rightAniData[0]),  Integer.parseInt(rightAniData[1]));
				Animation enemyAniRight = new Animation(ssRight, Integer.parseInt(rightAniData[2]));
				
				SpriteSheet	deathAni = new SpriteSheet(deathAniss,  Integer.parseInt(deathAnimationData[0]),  Integer.parseInt(deathAnimationData[1]));
				Animation deathAnimation = new Animation(deathAni, Integer.parseInt(deathAnimationData[2]));
				
				    
				    String [] enemyPos = getNodeValue("position", enemyNodeList).split(",");
				
				    Vector2f position = new Vector2f(Integer.parseInt(enemyPos[0]), Integer.parseInt(enemyPos[1]));
				    
				    Shape collisionShape = new Rectangle(Integer.parseInt(collisionData[0]), 
				    		Integer.parseInt(collisionData[1]), Integer.parseInt(collisionData[2]), Integer.parseInt(collisionData[3]));
				    
			    animationMap.put("walkLeftAni" ,enemyAniLeft );
			    animationMap.put("walkRightAni" , enemyAniRight);	
			    animationMap.put("deathAni",deathAnimation);	
			    animationMap.put("idleAni",idleAni);
			    
				    	Enemy enemy = EnemyFactory.createEnemy(enemyType, animationMap, position, collisionShape, 1);
				    	
				    enemy.setDeathAnimation(deathAnimation);
				    enemyList.add(enemy);
				    
					
				
			}
		}
		

	    
	}
	
	public Node getNode(String element, NodeList nodeList){
		for(int i = 0; i <nodeList.getLength() ; i++){
			Node node = nodeList.item(i);
			if(node.getNodeName().equalsIgnoreCase(element)  ){
				return node;
			}
		}
		return null;
	}
	
	public String getNodeValue(Node node){
		  NodeList childNodes = node.getChildNodes();
		    for (int x = 0; x < childNodes.getLength(); x++ ) {
		        Node data = childNodes.item(x);
		        if ( data.getNodeType() == Node.TEXT_NODE )
		            return data.getNodeValue().trim();
		    }
		return "";
	}
	
	public String getNodeValue(String tagName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.TEXT_NODE )
	                    return data.getNodeValue().trim();
	            }
	        }
	    }
	    return "";
	}
	
	
	
	public String getNodeAttribute(String tagName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	        	if(node.hasAttributes()){
	        		 return node.getAttributes().getNamedItem("animation_data").getNodeValue().toString();
	        	}
	          
	        }
	  
	    }
	    return "";   
	}
	
	
	
	public NodeList getNodeList(){
		return playerData;
	}
	

	@Override
	public ArrayList<Enemy> getEnemys() {
		return enemyList;
	}

	@Override
	public PlayerMan getPlayer() {
		return player;
	}


}

