package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{
	public OBJ_Axe(GamePanel gp) {
		super(gp);
		type = type_axe;
		name = "Rìu tự chế";
		down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
		attackValue = 1;
		attackArea.width = 30;
		attackArea.height =30;
		description = "[" + name + "]\nTấn công + 1\nDùng để chặt gỗ tốt\n hơn là chiến đấu";
		price = 15;
	}

}
