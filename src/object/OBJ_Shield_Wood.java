package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity{

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);
		type = type_shield;
		name = "Khiên Gỗ";
		down1 = setup("/objects/khien_go", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		description = "[" + name + "]\nPhòng thủ + 1\nChiếc khiên gỗ cũ nhưng\n có còn hơn không";
		price = 15;
	}

}
