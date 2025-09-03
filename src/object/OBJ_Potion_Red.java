package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
	GamePanel gp;

	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);
		this.gp=gp;

		type = type_consumable;
		name = "Tiết Canh";
		value = 20;
		down1 = setup("/objects/potion", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\n+20HP\nHồi phục";
		price = 5;

	}
	public void use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDiaLogue = "Bạn đã uống "+ name + "!\n"
				+ "sức khỏe được hồi phục";
		entity.life += value;
		gp.playSE(2);
	}
}
