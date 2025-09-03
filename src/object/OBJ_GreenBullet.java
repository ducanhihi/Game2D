package object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_GreenBullet extends Projectile{
	GamePanel gp;
	public int centerX;
	public int centerY;
	public int bulletCounter = 0;
	public OBJ_GreenBullet(GamePanel gp) {
		super(gp);
		this.gp = gp;
        disappearOnHit = false; // Gió không biến mất khi trúng player
        drawOnTop = true; // Cho skill này vẽ đè

		name = "Đạn Xanh";
		speed = 4  ;
		maxLife = 40;
		life = maxLife;
		attack = 6;
		useCost = 1;
		alive = false;

		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width =42;
		solidArea.height =30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	    centerX = solidArea.x + solidArea.width / 2;
	    centerY = solidArea.y + solidArea.height / 2;
		getImage();
		update();
	}
	@Override
	public void update() {
	    super.update(); // nếu Projectile có update sẵn
	}
	// Cho solidArea nở ra giữ nguyên tâm

	public void getImage() {
		up1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		up3 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);

		down1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down3 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		
		left1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left3 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);

		right1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right3 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);

	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;

	int screenX = worldX - gp.player.worldX + gp.player.screenX;
	int screenY = worldY - gp.player.worldY + gp.player.screenY;

	
	if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

		switch (direction) {
		case "up":
			if (spriteNum == 1) {image = up1;}
			if (spriteNum == 2) {image = up2;}
			if (spriteNum == 3) {image = up3;}

			break;
		case "down":
			if (spriteNum == 1) {image = down1;}
			if (spriteNum == 2) {image = down2;}
			if (spriteNum == 3) {image = down3;}

			break;
		case "left":
			if (spriteNum == 1) {image = left1;}
			if (spriteNum == 2) {image = left3;}
			if (spriteNum == 3) {image = down3;}

			break;
		case "right":
			if (spriteNum == 1) {image = right1;}
			if (spriteNum == 2) {image = right2;}
			if (spriteNum == 3) {image = right3;}

			break;
		}
		
		
		g2.drawImage(image, screenX, screenY, null);
		changeAlpha(g2,1f);
//		g2.setColor(Color.red);
//		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

	}
}
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.ammo >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	public void subtractResource(Entity user) {
		user.ammo -= useCost;
	}
	
	public Color getParticleColor() {
		Color color = new Color(40,50,0);
		return color;
	}
	public int getParticleSize() {
		int size = 10;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
