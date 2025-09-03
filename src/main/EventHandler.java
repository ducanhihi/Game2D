package main;

import entity.Entity;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][][];
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	public EventHandler(GamePanel gp) {	
		this.gp = gp;
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		int map = 0;
		int col = 0;
		int row = 0;
		while( map < gp.maxMap && col <  gp.maxWorldCol && row < gp.maxWorldRow) {
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y =23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height =2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			
			col++;
			if(col==gp.maxWorldCol) {
				col=0;
				row++;
				
				if(row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
		
		
	}
	
	public void checkEvent() {
		
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}
		if(canTouchEvent == true) {
			
			if(hit(0, 21, 21, "any")== true) {
				damagePit(gp.dialogueState);
				System.out.print("Ban bi danh");
			}

			else if(hit(2, 21, 13, "any")== true) {
				healingPool(gp.dialogueState);
			}
			else if(hit(2, 22, 13, "any")== true) {
				healingPool(gp.dialogueState);
			}
			else if(hit(2, 14, 37, "any")== true) {
				teleport(1, 17, 35);
			}
			else if(hit(2, 15, 37, "any")== true) {
				teleport(1, 17, 35);
			}
			else if(hit(2, 16, 37, "up")== true) {
				teleport(1, 17, 35);
			}
			else if(hit(2, 38, 38, "down")== true) {
				teleport(1, 17, 35);
			}
			else if(hit(1, 17, 37, "any")== true) {
				teleport(2, 21, 37);
			}
			else if(hit(1, 18, 37, "any")== true) {
				teleport(2, 21, 37);
			}
			else if(hit(1, 29, 23, "any")== true) {
				speak(gp.npc[1][0]);
			}
			
			//MAP 3
			else if(hit(1, 29, 25, "any")== true) {
				teleport(0, 16, 17);
			}
			else if(hit(1, 30, 25, "any")== true) {
				teleport(0, 16, 17);
			}
		}
		
	}
	
	public boolean hit(int map, int col, int row, String reqDirection) {
		boolean hit = false;
		if(map == gp.currentMap) {
		gp.player.solidArea.x = gp.player.worldX +  gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY +  gp.player.solidArea.y;
		eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
		eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
		
		if(gp.player.solidArea.intersects(eventRect[map][col][row])&& eventRect[map][col][row].eventDone == false) {
			if(gp.player.direction.contentEquals(reqDirection)|| reqDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
		eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		
		}

		return hit;

	}
	public void teleport(int map, int col, int row) {

		gp.gameState = gp.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		canTouchEvent = false;
		gp.playSE(14);


	}
	public void speak(Entity entity) {
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gp.dialogueState;
			gp.player.attackCancecled = true;
			entity.speak();
		}
	}
	
	
	
	public void damagePit(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDiaLogue = "Bạn bị tấn công!";
		gp.player.life -= 1;
//		eventRect[col][row].eventDone =true;
		canTouchEvent = false;
	}
	
	public void healingPool(int gameState) {
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.player.attackCancecled = true;
			gp.ui.currentDiaLogue = "Bạn đã uống nước.\nSức khỏe của bạn được phục hồi.";
			gp.player.life = gp.player.maxLife;
			gp.player.mana = gp.player.maxMana;
			gp.aSetter.setMonster();
		}
	}
	
	
}






