package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_GreenStone extends Entity {
	GamePanel gp;
	
	
	public OBJ_GreenStone(GamePanel gp) {
		super(gp);
		this.gp=gp;

		name = "Đá xanh";
		value = 3;
		down1 = setup("/objects/Green_Stone", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nMở ra con đường mới!";
		price = 5;

	}
}
