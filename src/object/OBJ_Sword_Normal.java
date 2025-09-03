package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{

	public OBJ_Sword_Normal(GamePanel gp) {
		super(gp);
		type = type_sword;
		name = "Kiếm Đấu Tập";
		down1 = setup("/objects/sword", gp.tileSize, gp.tileSize);
		attackValue = 2;
		attackArea.width = 36;
		attackArea.height =36;
		description = "[" + name + "]\nTấn công + 2\nMột thanh kiếm cũ đã từng\nđược dùng bởi một chiến\nbinh";
		price = 10;
	}

}
