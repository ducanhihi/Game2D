package entity;

import java.util.Random;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;

public class Knight_01 extends Entity{
	public Knight_01(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDiaLogue();
	}
	public void getImage() {

		up1 = setup("/npc/red_down1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/red_down2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/red_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/red_down2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/red_down1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/red_down2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/red_down1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/red_down2", gp.tileSize, gp.tileSize);

		
	}
	
	public void setDiaLogue() {
		dialogues[0] = "Chao nhoc";
		dialogues[1] = "May den day de lam cai deo gi?";
		dialogues[2] = "Tao biết số đá đó ở đâu,\n nhưng tao đã quá già để đến đó!";
		dialogues[3] = "Ở dưới đất mày sẽ không tìm ra đâu";
		dialogues[4] = "Về đi";

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
