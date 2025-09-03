package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_LinhCamGiao extends Entity{
	 
	
	public NPC_LinhCamGiao(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDiaLogue();
	}
	public void getImage() {

		up1 = setup("/npc/linh_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/linh_up2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/linh_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/linh_down2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/linh_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/linh_left2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/linh_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/linh_right2", gp.tileSize, gp.tileSize);

		
	}
	
	public void setDiaLogue() {
		dialogues[0] = "Tôi gác ở quanh đây, bạn có thắc mắc gì à?";
		dialogues[1] = "Nhớ tránh xa khu rừng đó ra nhé.";
		dialogues[2] = "Gặp lại sau";

	}
	

	public void setAction() {
		
		actionLockCounter++;
		if(actionLockCounter == 120) {
			
			Random random = new Random();
			int i = random.nextInt(100)+1;//Ngẫu nhiên từ 1 đến 100
				
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 70) {
				direction = "left";
			}
			if(i > 75 &&	i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}


	}
	public void speak() {
		super.speak();
	}

}
