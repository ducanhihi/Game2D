package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity{
	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);
		type = type_shield;
		name = "Khiên Sắt";
		down1 = setup("/objects/khien_sat", gp.tileSize, gp.tileSize);
		defenseValue = 2;
		description = "[" + name + "]\nPhòng thủ + 1\nThật phong cách";
		price = 20;

	}
}
