package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Key extends Entity{
	
	public OBJ_Key(GamePanel gp) {
		super(gp);
		
		name = "Chìa khóa cửa";
		down1 = setup("/objects/key", (int)(gp.tileSize*0.8), (int)(gp.tileSize*0.8));
		description = "[" + name + "]\nCó sẽ thể mở được\nmột cánh cửa nào đó?";
		price = 10;
	}
	
	
}
