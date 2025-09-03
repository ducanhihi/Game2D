package entity;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Key;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Sword_Normal;

public class NPC_NinjaSale extends Entity{

	
	public NPC_NinjaSale(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 0;
		
		getImage();
		setDiaLogue();
		setItems();
	}
	public void getImage() {

		up1 = setup("/npc/farmer", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/farmer", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/farmer", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/farmer", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/farmer", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/farmer", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/farmer", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/farmer", gp.tileSize, gp.tileSize);

		
	}
	
	public void setDiaLogue() {
		dialogues[0] = "Những món đồ này\n sẽ có ích đấy!";



	}
	
	public void setItems() {
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Axe(gp));

		inventory.add(new OBJ_Sword_Normal(gp));
		inventory.add(new OBJ_Shield_Blue(gp));
		inventory.add(new OBJ_Sword_Normal(gp));

		inventory.add(new OBJ_Shield_Blue(gp));
		inventory.add(new OBJ_Sword_Normal(gp));

		inventory.add(new OBJ_Shield_Blue(gp));
		inventory.add(new OBJ_Axe(gp));
	}
	public void speak() {
		super.speak();
		gp.gameState = gp.tradeState;
		gp.ui.npc = this;
	}
}
