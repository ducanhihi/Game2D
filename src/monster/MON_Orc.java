package monster;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_GreenBullet;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_Orc extends Entity{
	GamePanel gp;
	boolean chasingPlayer = false;
	int chaseCounter = 0;
	int chaseTime = 180; // 3 giây đuổi (60 FPS)

	public MON_Orc(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
	    contactDamage = false; //  Orc gây sát thương khi chạm

		
		type = type_monster;
		name = "Orc";
		speed = 1;
		maxLife = 30 ;
		life = maxLife;
		attack = 6;
		defense = 0;
		exp = 10;
		projectile = new OBJ_GreenBullet(gp);
		
		//TẠO VÙNG RẮN TÁC ĐỘNG CHO THỰC THỂ
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width =42;
		solidArea.height =30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}

	public void getImage() {
		up1 = setup("/monters/orc_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/monters/orc_up2", gp.tileSize, gp.tileSize);
		down1 = setup("/monters/orc_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/monters/orc_down2", gp.tileSize, gp.tileSize);
		left1 = setup("/monters/orc_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/monters/orc_left2", gp.tileSize, gp.tileSize);
		right1 = setup("/monters/orc_right1", gp.tileSize, gp.tileSize);
		right2 = setup("/monters/orc_right2", gp.tileSize, gp.tileSize);
	}
	
	
	@Override
	public void setAction() {
	    if (chasingPlayer) {
	        chaseCounter++;

	        // đuổi player
	        int dx = gp.player.worldX - worldX;
	        int dy = gp.player.worldY - worldY;

	        if (Math.abs(dx) > Math.abs(dy)) {
	            direction = (dx > 0) ? "right" : "left";
	        } else {
	            direction = (dy > 0) ? "down" : "up";
	        }

	     // ==== BẮN LIÊN TỤC 100% ==== 
	        if (projectile.alive == false && shotAvailableCounter == 60) { 
	        	projectile.set(worldX, worldY, direction, true, this); 
	        	gp.projectileList.add(projectile); shotAvailableCounter = 0; 
	        	} 
	        if (chaseCounter > chaseTime) 
	        { chasingPlayer = false; // hết thời gian đuổi -> quay lại random }
	        }

	        return;
	    }

	    // --- AI random khi không chasing ---
	    actionLockCounter++;
	    if (actionLockCounter == 120) {
	        Random random = new Random();
	        int i = random.nextInt(100) + 1;
	        if (i <= 25) direction = "up";
	        if (i > 25 && i <= 50) direction = "down";
	        if (i > 50 && i <= 75) direction = "left";
	        if (i > 75) direction = "right";
	        actionLockCounter = 0;
	    }

	    // Nếu player lại gần thì chuyển sang chasing
	    int xDistance = Math.abs(gp.player.worldX - worldX);
	    int yDistance = Math.abs(gp.player.worldY - worldY);
	    int tileDistance = (xDistance + yDistance) / gp.tileSize;

	    if (tileDistance < 3) { // player ở gần trong 5 ô
	        chasingPlayer = true;
	        chaseCounter = 0;
	    }
	

//COMMENT ĐOẠN NÀY VÌ BỎ QUÁI ĐÁNH LUNG TUNG
	    // Thỉnh thoảng bắn đạn (tỉ lệ 1%)
//	    int i = new Random().nextInt(100) + 1;
//	    if (i > 99 && projectile.alive == false && shotAvailableCounter == 60) {
//	        projectile.set(worldX, worldY, direction, true, this);
//	        gp.projectileList.add(projectile);
//	        shotAvailableCounter = 0;
//	    }
	}


	
	// quái chạy khi bị đánh // đuổi theo tấn công player
	@Override
	public void damageReaction() {
	    actionLockCounter = 0;
	    chasingPlayer = true; // bật trạng thái đuổi
	    chaseCounter = 0;
	}


	public void checkDrop() {
	    int i = new Random().nextInt(100) + 1;
	    Random rand = new Random();

	    if (i < 75) {
	        // coin rơi 1 object nhưng giá trị random 10-100
	        OBJ_Coin_Bronze coin = new OBJ_Coin_Bronze(gp);
	        coin.value = rand.nextInt(91) + 10; // random từ 10 -> 100
	        dropItem(new OBJ_Key(gp));

	    } else if (i > 85) {
	        OBJ_Coin_Bronze coin = new OBJ_Coin_Bronze(gp);
	        coin.value = rand.nextInt(91) + 10; // random từ 10 -> 100
	        dropItem(coin);


	    } else {
	        dropItem(new OBJ_Key(gp));

	    }
	}
}
