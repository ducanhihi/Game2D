package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile{
	GamePanel gp;
	public int centerX;
	public int centerY;

	public OBJ_Rock(GamePanel gp) {
		super(gp);
        disappearOnHit = true; // Đá biến mất khi trúng player
        drawOnTop = true; // Cho skill này vẽ đè

		this.gp = gp;
		name = "Đạn Pháo";
		speed = 3  ;
		maxLife = 60;
		life = maxLife;
		attack = 2;
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
//	    check();
	}
	// Cho solidArea nở ra giữ nguyên tâm
	public void check() {
	    if (alive) {
	        solidArea.width += 2;
	        solidArea.height += 2;

	        // Cập nhật lại x, y để giữ tâm
	        solidArea.x = centerX - solidArea.width / 2;
	        solidArea.y = centerY - solidArea.height / 2;
	    } else {
	        solidArea.x = solidAreaDefaultX;
	        solidArea.y = solidAreaDefaultY;
	        solidArea.width = 42;
	        solidArea.height = 30;

	        // Reset lại tâm
	        centerX = solidArea.x + solidArea.width / 2;
	        centerY = solidArea.y + solidArea.height / 2;
	    }
	}
	
	public void getImage() {
		up1 = setup("/projectile/fb_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/fb_up2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/fb_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/fb_down2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/fb_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/fb_left2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/fb_right1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/fb_right2", gp.tileSize, gp.tileSize);
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
