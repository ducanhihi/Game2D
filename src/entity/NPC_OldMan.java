package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{

	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDiaLogue();
	}
	public void getImage() {

		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);

		
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
	
//	public void draw(Graphics2D g2) {
//		
//		BufferedImage image = null;
//
//	int screenX = worldX - gp.player.worldX + gp.player.screenX;
//	int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//	
//	if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
//			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
//			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
//			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
//
//		switch (direction) {
//		case "up":
//			if (spriteNum == 1) {
//				image = up1;
//			}
//			if (spriteNum == 2) {
//				image = up2;
//			}
//			break;
//		case "down":
//			if (spriteNum == 1) {
//				image = down1;
//			}
//			if (spriteNum == 2) {
//				image = down2;
//			}
//			break;
//		case "left":
//			if (spriteNum == 1) {
//				image = left1;
//			}
//			if (spriteNum == 2) {
//				image = left2;
//			}
//			break;
//		case "right":
//			if (spriteNum == 1) {
//				image = right1;
//			}
//			if (spriteNum == 2) {
//				image = right2;
//			}
//			break;
//		}
//		
//		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
//
//	}
//}
}






