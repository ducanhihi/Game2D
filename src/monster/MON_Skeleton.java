package monster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_GreenStone;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;
import object.OBJ_WindAtk;

public class MON_Skeleton extends Entity{
	GamePanel gp;
	int baseSpeed = 1;
	int attackCooldown = 100; // 3 giây
	int attackTimer = 0;
	int attackCounter = 0;
	public MON_Skeleton(GamePanel gp) {
		super(gp);
		this.gp = gp;
	    contactDamage = false; //  Orc gây sát thương khi chạm

		
		
		type = type_monster;
		name = "Skeleton King";
		maxLife = 80 ;
		life = maxLife;
		speed = 2;
		attack = 10;
		defense = 0;
		exp = 10;
		projectile = new OBJ_WindAtk(gp);
		
		//TẠO VÙNG RẮN TÁC ĐỘNG CHO THỰC THỂ
		solidArea.x = 22;
		solidArea.y = 20;
		solidArea.width =42;
		solidArea.height =60;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}

	public void getImage() {
		up1 = setup("/monters/ske_l1", gp.tileSize*2, gp.tileSize*2);
		up2 = setup("/monters/ske_l2", gp.tileSize*2, gp.tileSize*2);
		down1 = setup("/monters/ske_r1", gp.tileSize*2, gp.tileSize*2);
		down2 = setup("/monters/ske_r2", gp.tileSize*2, gp.tileSize*2);
		left1 = setup("/monters/ske_l1", gp.tileSize*2, gp.tileSize*2);
		left2 = setup("/monters/ske_l2", gp.tileSize*2, gp.tileSize*2);
		right1 = setup("/monters/ske_r1", gp.tileSize*2, gp.tileSize*2);
		right2 = setup("/monters/ske_l2", gp.tileSize*2, gp.tileSize*2);
		
		attackUp1 = setup("/monters/ske_atk_u1", gp.tileSize*2+3, gp.tileSize*2);
		attackUp2 = setup("/monters/ske_atk_u2", gp.tileSize*2+3, gp.tileSize*2);
		attackDown1 = setup("/monters/ske_atk_d1", gp.tileSize*2+3, gp.tileSize*2);
		attackDown2 = setup("/monters/ske_atk_d2", gp.tileSize*2+3, gp.tileSize*2);
		attackLeft1 = setup("/monters/ske_atk_l1", gp.tileSize*2+3, gp.tileSize*2);
		attackLeft2 = setup("/monters/ske_atk_l2", gp.tileSize*2+3, gp.tileSize*2);
		attackRight1 = setup("/monters/ske_atk_r1", gp.tileSize*2+3, gp.tileSize*2);
		attackRight2 = setup("/monters/ske_atk_r2", gp.tileSize*2+3, gp.tileSize*2);

	}
	
	
	@Override
	public void setAction() {
	    if (chasingPlayer) {
	        chaseCounter++;

	        // Tính hướng chạy theo player
	        int dx = gp.player.worldX - worldX;
	        int dy = gp.player.worldY - worldY;

	        if (Math.abs(dx) > Math.abs(dy)) {
	            direction = (dx > 0) ? "right" : "left";
	        } else {
	            direction = (dy > 0) ? "down" : "up";
	        }

	     // --- Nếu đang attack thì xử lý animation ---
	        if (attacking) {
	            attackCounter++;

	            // Spawn projectile ở frame 15 (giữa animation)
	            if (attackCounter == 15 && projectile.alive == false) {
	                int offset = gp.tileSize;
	                int projX = worldX;
	                int projY = worldY;

	                switch (direction) {
	                    case "up": projY -= offset; break;
	                    case "down": projY += offset; break;
	                    case "left": projX -= offset; break;
	                    case "right": projX += offset; break;
	                }

	                projectile.set(projX, projY, direction, true, this);
	                gp.projectileList.add(projectile);
	            }

	            // Kết thúc animation sau 30 frame (~0.5s)
	            if (attackCounter > 30) {
	                attacking = false;
	                attackCounter = 0;
	            }
	            return; // đang attack thì không di chuyển
	        }

	        // --- Khi chasing mà đủ cooldown thì bắt đầu attack ---
	        attackTimer++;
	        if (attackTimer >= attackCooldown) {
	            attacking = true;  // bật trạng thái attack
	            attackTimer = 0;   // reset cooldown
	        }


	        if (chaseCounter > chaseTime) {
	            chasingPlayer = false;
	            attackTimer = 0;
	            attacking = false;
	        }

	        return; // khi chasing thì không random
	    }

	    // --- random move ---
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
	}

	@Override
	public void damageReaction() {
	    actionLockCounter = 0;
	    chasingPlayer = true;
	    chaseCounter = 0;
	    attackTimer = 0;     // reset, để lần đánh đầu tiên xảy ra sau 1 khoảng chasing
	}



	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;

	int screenX = worldX - gp.player.worldX + gp.player.screenX;
	int screenY = worldY - gp.player.worldY + gp.player.screenY;

	
	if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

		
		if(attacking == false) {
			switch (direction) {
			case "up":
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}
				break;
			case "down":
				if (spriteNum == 1) {image = down1;}
				if (spriteNum == 2) {image = down2;}
				break;
			case "left":
				if (spriteNum == 1) {image = left1;}
				if (spriteNum == 2) {image = left2;}
				break;
			case "right":
				if (spriteNum == 1) {image = right1;}
				if (spriteNum == 2) {image = right2;}
				break;
			}
		}
			else {
				switch (direction) {
				case "up":
					if (spriteNum == 1) {image = attackUp1;}
					if (spriteNum == 2) {image = attackUp2;}
					break;
				case "down":
					if (spriteNum == 1) {image = attackDown1;}
					if (spriteNum == 2) {image = attackDown2;}
					break;
				case "left":
					if (spriteNum == 1) {image = attackLeft1;}
					if (spriteNum == 2) {image = attackLeft2;}
					break;
				case "right":
					if (spriteNum == 1) {image = attackRight1;}
					if (spriteNum == 2) {image = attackRight2;}
					break;
				}
			}
		}
		
		
		//THANH HP QUAI
		if(type == 2 && hpBarOn == true) {
		    double oneScale = (double)solidArea.width / maxLife;
		    double hpBarValue = oneScale * life;

		    int barWidth = solidArea.width;   // ngang bằng solidArea
		    int barHeight = 10;

		    // căn giữa dựa trên solidArea
		    int barX = screenX + solidArea.x + (solidArea.width/2) - (barWidth/2);
		    int barY = screenY + solidArea.y - 15;   // đặt trên đầu vùng va chạm

		    // viền nền
		    g2.setColor(new Color(35,35,35));
		    g2.fillRect(barX - 1, barY - 1, barWidth + 2, barHeight + 2);

		    // máu hiện tại
		    g2.setColor(new Color(255,0,30));
		    g2.fillRect(barX, barY, (int) hpBarValue, barHeight);

		    hpBarCounter++;
		    if(hpBarCounter > 600) {
		        hpBarCounter = 0;
		        hpBarOn = false;
		    }
		}


		
		
		if(invincible == true) {
			hpBarOn = true;
			hpBarCounter = 0;
			changeAlpha(g2,0.4f);
		}
		if(dying == true) {
			dyingAnimation(g2);
		}
		
		
		g2.drawImage(image, screenX, screenY, null);
		changeAlpha(g2,1f);
//		g2.setColor(Color.red);
//		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

	}


	public void checkDrop() {
	    int i = new Random().nextInt(100) + 1;
	    Random rand = new Random();

	    if (i < 75) {
	        // coin rơi 1 object nhưng giá trị random 10-100
	        OBJ_Coin_Bronze coin = new OBJ_Coin_Bronze(gp);
	        coin.value = rand.nextInt(91) + 10; // random từ 10 -> 100
	        dropItem(coin);
	        dropItem(new OBJ_GreenStone(gp));


	    } else if (i > 85) {
	        dropItem(new OBJ_ManaCrystal(gp));
	        dropItem(new OBJ_GreenStone(gp));


	    } else {
	        dropItem(new OBJ_Heart(gp));
	        dropItem(new OBJ_GreenStone(gp));

	    }
	}
}
