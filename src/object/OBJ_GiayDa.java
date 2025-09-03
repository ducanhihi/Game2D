package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_GiayDa extends Entity{
	public OBJ_GiayDa(GamePanel gp) {
		super(gp);
		name = "Giầy da";
		down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
	}
}
