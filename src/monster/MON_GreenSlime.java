package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_GreenSlime extends Entity{
	
	GamePanel gp;
    int spawnX, spawnY;        // tọa độ gốc

	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.spawnX = worldX;
		this.spawnY = worldY;

		
		
		type = type_monster;
		name = "Green Slime";
		speed = 1;
		maxLife = 4 ;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 2;
		projectile = new OBJ_Rock(gp);
		
		
		
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
		up1 = setup("/monters/fs_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monters/fs_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monters/fs_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monters/fs_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monters/fs_left_2", gp.tileSize, gp.tileSize);
		left2 = setup("/monters/fs_left_1", gp.tileSize, gp.tileSize);
		right1 = setup("/monters/fs_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monters/fs_right_2", gp.tileSize, gp.tileSize);
	}
	
	public void resetMonster() {
	    this.life = maxLife;
	    this.alive = true;
	    this.dying = false;
	    this.worldX = spawnX;
	    this.worldY = spawnY;
	    this.respawnCounter = 0;
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
		int i = new Random().nextInt(100)+1;
		if(i > 99 && projectile.alive == false && shotAvailableCounter == 60) {
			projectile.set(worldX, worldY, direction, true, this);
			gp.projectileList.add(projectile);
			
			shotAvailableCounter = 0;
		}

	}
	
	// quái chạy khi bị đánh
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;
		
	}
	public void checkDrop() {
	    int i = new Random().nextInt(100) + 1;
	    Random rand = new Random();

	    if (i < 75) {
	        // coin rơi 1 object nhưng giá trị random 10-100
	        OBJ_Coin_Bronze coin = new OBJ_Coin_Bronze(gp);
	        coin.value = rand.nextInt(91) + 10; // random từ 10 -> 100
	        dropItem(coin);

	    } else if (i > 85) {
	        dropItem(new OBJ_ManaCrystal(gp));

	    } else {
	        dropItem(new OBJ_Heart(gp));
	    }
	}


//	public void checkDrop() { //Hàm này là hàm ban đầu
//	    int i = new Random().nextInt(100) + 1; // Ngẫu nhiên từ 1 đến 100
//	    if (i < 75) {
//	        dropItem(new OBJ_Coin_Bronze(gp)); // 50% xác suất
//	    } else if (i > 85) {
//	        dropItem(new OBJ_ManaCrystal(gp)); // 25% xác suất
//	    } else {
//	        dropItem(new OBJ_Heart(gp)); // 25% xác suất
//	    }
//	}
// Hàm này khi muốn rơi nhiều đồ 1 l
//	public void checkDrop() {
//	    int i = new Random().nextInt(100) + 1; // Ngẫu nhiên từ 1 đến 100
//	    if (i < 75) {
//	        // Rơi 50 đồng coin
//	        for (int j = 0; j < 50; j++) {
//	            dropItem(new OBJ_Coin_Bronze(gp));
//	        }
//	    } else if (i > 85) {
//	        dropItem(new OBJ_ManaCrystal(gp)); 
//	    } else {
//	        dropItem(new OBJ_Heart(gp)); 
//	    }
//	}

	
	
	
	
	
	
	
	
}
