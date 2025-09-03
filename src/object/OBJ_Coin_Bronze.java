package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity{
	GamePanel gp;
	
	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_pickupOnly;
		name = "Đồng xu";
		value = 1;
		down1 = setup("/objects/coin", gp.tileSize, gp.tileSize);
	}
	public void use(Entity entity) {
		gp.playSE(1);
		gp.ui.addMessage("Vàng + " + value);
		gp.player.coin += value;
	}
}
