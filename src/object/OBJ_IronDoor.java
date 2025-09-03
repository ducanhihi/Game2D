package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_IronDoor extends Entity{
    public boolean opened = false;

	public OBJ_IronDoor(GamePanel gp) {
		super(gp);
		name = "Iron Door";
		down1 = setup("/objects/door", gp.tileSize, gp.tileSize);
		collision = true;
		drawLayer = 0;

		solidArea.x =0;
		solidArea.y=16;
		solidArea.width=48;
		solidArea.height=32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
	}
    public void open() {
        opened = true;
        down1 = setup("/objects/door_open.png", gp.tileSize, gp.tileSize);
        collision = false;
        drawLayer = 0;

    }
}
