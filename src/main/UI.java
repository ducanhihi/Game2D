package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font cambria, DFVN;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDiaLogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0;
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	int subState = 0;
	int counter = 0;
	public Entity npc;
	public UI(GamePanel gp) {
		this.gp = gp;
		
		InputStream is = getClass().getResourceAsStream("/font/Cambria-Font-For-Windows.ttf");
		try {
			cambria = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/DFVN.ttf");
			DFVN = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		OBJ_Heart heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		OBJ_ManaCrystal crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		Entity goldCoin = new OBJ_Coin_Bronze(gp);
		coin = goldCoin.down1;

	}
	
	public void addMessage(String text) {

		message.add(text);
		messageCounter.add(0);
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(DFVN);
		g2.setColor(Color.white);
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessage();
		}
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		if(gp.gameState == gp.dialogueState) {
			drawDiaLogueScreen();
//			drawPlayerLife();

		}
		//CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true);
		}
		//OPTIONS STATE
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		//GAME OVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		//TRANSITION STATE
		if(gp.gameState == gp.transitionState) {
			drawTransition();
		}
		//TRADE STATE
		if(gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
	}

	public void drawPlayerLife() {
//		gp.player.life =2;
		
		int healthBarX = gp.tileSize/2;
		int healthBarY = gp.tileSize/2;
		int healthBarWidth = gp.tileSize * 4;
		int healthBarHeight = 24;
		
		// Draw health bar white border
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
		
		// Draw health bar black interior
		g2.setColor(Color.BLACK);
		g2.fillRect(healthBarX + 2, healthBarY + 2, healthBarWidth - 4, healthBarHeight - 4);
		
		// Calculate health percentage and draw filled portion
		double healthPercentage = (double)gp.player.life / gp.player.maxLife;
		int filledWidth = (int)((healthBarWidth - 4) * healthPercentage);
		
		g2.setColor(Color.RED);
		g2.fillRect(healthBarX + 2, healthBarY + 2, filledWidth, healthBarHeight - 4);
		
		// Draw health text
		g2.setFont(DFVN.deriveFont(Font.BOLD, 14F));
		g2.setColor(Color.WHITE);
		String healthText = gp.player.life + "/" + gp.player.maxLife;
		int textX = healthBarX + (healthBarWidth / 2) - (g2.getFontMetrics().stringWidth(healthText) / 2);
		int textY = healthBarY + 16;
		g2.drawString(healthText, textX, textY);
		
		int manaBarX = gp.tileSize/2;
		int manaBarY = (int)(gp.tileSize*1.5);
		int manaBarWidth = gp.tileSize * 4;
		int manaBarHeight = 24;
		
		// Draw mana bar white border
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(manaBarX, manaBarY, manaBarWidth, manaBarHeight);
		
		// Draw mana bar black interior
		g2.setColor(Color.BLACK);
		g2.fillRect(manaBarX + 2, manaBarY + 2, manaBarWidth - 4, manaBarHeight - 4);
		
		// Calculate mana percentage and draw filled portion
		double manaPercentage = (double)gp.player.mana / gp.player.maxMana;
		int filledManaWidth = (int)((manaBarWidth - 4) * manaPercentage);
		
		g2.setColor(Color.BLUE);
		g2.fillRect(manaBarX + 2, manaBarY + 2, filledManaWidth, manaBarHeight - 4);
		
		// Draw mana text
		g2.setColor(Color.WHITE);
		String manaText = gp.player.mana + "/" + gp.player.maxMana;
		textX = manaBarX + (manaBarWidth / 2) - (g2.getFontMetrics().stringWidth(manaText) / 2);
		textY = manaBarY + 16;
		g2.drawString(manaText, textX, textY);
	}
	
	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(DFVN.deriveFont(Font.BOLD, 18F));
		
		for(int i = 0; i < message.size(); i++) {
			if(message.get(i) != null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i)+1;
				messageCounter.set(i, counter);
				messageY += 25; // Reduced line spacing for smaller font
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	
	
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			
			try {
				BufferedImage forestBg = ImageIO.read(getClass().getResourceAsStream("/backgrounds/PixelArt-Forest.png"));
				// Scale and draw the forest background to fill the screen
				g2.drawImage(forestBg, 0, 0, gp.screenWidth, gp.screenHeight, null);
			} catch (Exception e) {
				// Fallback to gradient if image fails to load
				GradientPaint gradient = new GradientPaint(
					0, 0, new Color(15, 15, 35),
					0, gp.screenHeight, new Color(45, 15, 65)
				);
				g2.setPaint(gradient);
				g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			}
			
			// drawAnimatedBackground();
			
			//TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
			String text = "Game 2D";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			
			g2.setColor(new Color(0, 0, 0, 150));
			g2.drawString(text, x+3, y+3);
			
			// White title text
			g2.setColor(Color.WHITE);
			g2.drawString(text, x, y);
			
			//TITLE IMAGE
			x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			y += gp.tileSize*2;
			
			g2.setColor(new Color(255, 215, 0, 100));
			g2.fillRoundRect(x-10, y-10, gp.tileSize*2+20, gp.tileSize*2+20, 20, 20);
			g2.setColor(new Color(255, 215, 0));
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(x-5, y-5, gp.tileSize*2+10, gp.tileSize*2+10, 15, 15);
			g2.setStroke(new BasicStroke(1)); // Reset stroke
			
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			//MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			
			text = "CHƠI MỚI";
			x = getXforCenteredText(text);
			y += gp.tileSize*5;
			g2.setColor(Color.WHITE);
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "CHƠI TIẾP";
			x = getXforCenteredText(text);
			y += gp.tileSize + 20;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "THOÁT GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize + 20;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}

		}
		
		else if(titleScreenState == 1) {
			// Gradient background
			GradientPaint gradient = new GradientPaint(
				0, 0, new Color(25, 25, 45),
				0, gp.screenHeight, new Color(45, 25, 65)
			);
			g2.setPaint(gradient);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			drawAnimatedBackground();
			
			//CHỌN NHÂN VẬT
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
			
			String text = "Chọn Nhân Vật Của Bạn!";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			
			// Text shadow
			g2.setColor(new Color(0, 0, 0, 150));
			g2.drawString(text, x+3, y+3);
			
			// White title text
			g2.setColor(Color.WHITE);
			g2.drawString(text, x, y);
			
			String[] characters = {"Kiếm Vương", "Thương Quỷ", "Thần Trượng", "Ma Tăng", "Quay Lại"};
			
			y = gp.tileSize*8;
			
			for(int i = 0; i < characters.length; i++) {
				text = characters[i];
				x = getXforCenteredText(text);
				
				// Character option background when selected
				if(commandNum == i) {
					g2.setColor(new Color(255, 255, 255, 20));
					g2.fillRoundRect(x-30, y-35, getTextWidth(text)+60, 45, 10, 10);
					g2.setColor(new Color(255, 215, 0));
					g2.drawRoundRect(x-30, y-35, getTextWidth(text)+60, 45, 10, 10);
				}
				
				// Text shadow
				g2.setColor(new Color(0, 0, 0, 150));
				g2.drawString(text, x+2, y+2);
				
				// White character text
				g2.setColor(Color.WHITE);
				g2.drawString(text, x, y);
				
				// Selection arrow
				if(commandNum == i) {
					g2.setColor(Color.WHITE);
					g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));
					int arrowX = x - gp.tileSize;
					int offset = (int)(Math.sin(System.currentTimeMillis() * 0.01) * 3);
					g2.drawString("►", arrowX + offset, y);
				}
				
				y += gp.tileSize;
			}
		}
	}
	
	private void drawAnimatedBackground() {
		// Simple animated stars effect
		g2.setColor(new Color(255, 255, 255, 100));
		long time = System.currentTimeMillis();
		
		for(int i = 0; i < 50; i++) {
			int starX = (int)((i * 137 + time * 0.1) % gp.screenWidth);
			int starY = (int)((i * 211 + time * 0.05) % gp.screenHeight);
			int size = (int)(Math.sin(time * 0.01 + i) * 2 + 3);
			g2.fillOval(starX, starY, size, size);
		}
	}
	
	private int getTextWidth(String text) {
		FontMetrics fm = g2.getFontMetrics();
		return fm.stringWidth(text);
	}

	public void drawPauseScreen	() {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
		String text = "TẠM DỪNG";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	
	public void drawDiaLogueScreen() {
		//CỬA SỔ HỘP THOẠI
		int x = gp.tileSize*2;
		int y = gp.tileSize*4;
		int width = gp.screenWidth - (gp.tileSize*20) ;
		int height = gp.tileSize*5;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(DFVN.deriveFont(Font.BOLD, 18F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDiaLogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
	}
	

	
	
	public void drawCharacterScreen() {

		//CREATE A FRAME;
		final int frameX = gp.tileSize*2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*8;
		final int frameHeight = gp.tileSize*12;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		
		//TEXT
		g2.setColor(Color.white);
		g2.setFont(DFVN.deriveFont(Font.BOLD, 18F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 40;
		
		//NAME
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("HP", textX, textY);
		textY += lineHeight;
		g2.drawString("MP", textX, textY);
		textY += lineHeight;
		g2.drawString("Sức mạnh", textX, textY);
		textY += lineHeight;
		g2.drawString("Khéo léo", textX, textY);
		textY += lineHeight;
		g2.drawString("Tấn công", textX, textY);
		textY += lineHeight;
		g2.drawString("Phòng thủ", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Cấp sau", textX, textY);
		textY += lineHeight;
		g2.drawString("Xu", textX, textY);
		textY += lineHeight +20;
		g2.drawString("Vũ khí", textX, textY);
		textY += lineHeight +15;
		g2.drawString("Khiên", textX, textY);
		textY += lineHeight;
		
		
		
		//VALUES
		int tailX = (frameX + frameWidth)-30;
		//Reset textY
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.life +"/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.mana +"/" + gp.player.maxMana);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-15, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-15, null);
		
	}
	
	public void drawInventory(Entity entity, boolean cursor) {
		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;
		
		if(entity == gp.player) {
			frameX = gp.tileSize*16;
			frameY = gp.tileSize*1;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			frameX = gp.tileSize*2;
			frameY = gp.tileSize*1;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		
		
		//FRAME
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//SLOT
		final int slotXstart = frameX +20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize+3;
		
				//VAT PHAM CUA NGUOI CHOI
		for(int i = 0; i < entity.inventory.size(); i++) {
			
			// DÙNG VẬT PHẨM
			if(entity.inventory.get(i) == entity.currentWeapon ||
					entity.inventory.get(i)== entity.currentShield) {
				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			
			slotX += slotSize;
//			if(i == 7 || i == 15 || i == 23|| i == 31|| i == 39){
//				slotX = slotXstart;
//				slotY += slotSize;
//			}
			if ((i + 1) % 5 == 0) {  // Cứ đủ 5 slot thì xuống hàng
			    slotX = slotXstart;
			    slotY += slotSize;
			}
			
		}
		
		//CURSOR
		if(cursor == true) {
			int cursorX = slotXstart + (slotSize * slotCol);
			int cursorY = slotYstart + (slotSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;
			//DRAW CURSOR
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
			
			//DESCRIPTION FRAME
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize*5;
			
			//DESCRIPTION TEXT
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(DFVN.deriveFont(Font.BOLD, 18F));
			
			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
			
			if(itemIndex < entity.inventory.size()) {
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

				for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 32;
				}
			}	
		}
	}
	public void drawGameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(DFVN.deriveFont(Font.BOLD, 110F));
		text = "Thua Cuộc";
		//Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		//Main
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		//Retry
		g2.setFont(DFVN.deriveFont(Font.BOLD, 50F));
		text = "Chơi lại";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		//Back to title screen
		text = "Thoát";
		x = getXforCenteredText(text);
		y += 55 ;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
	}

	
	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(DFVN.deriveFont(Font.BOLD, 24F));
		
		int frameX = gp.tileSize*9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*10;
		int frameHeight = gp.tileSize*12;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: options_top(frameX, frameY); break;
		case 1: options_fullSceenNotification(frameX, frameY); break;
		case 2: options_control(frameX, frameY); break;
		case 3: options_endGameConfirmation(frameX, frameY); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void options_top(int frameX, int frameY) {
		int textX;
		int textY;
		
		//TITLE
		String text = "Cài đặt";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text,textX, textY);
		
		//ON/OFF FULL SCREEN
		textX = frameX + gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Toàn màn hình",textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		
		//Music
		textY += gp.tileSize;
		g2.drawString("Âm nhạc",textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		//SE
		textY += gp.tileSize;
		g2.drawString("SE",textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		//Control
		textY += gp.tileSize;
		g2.drawString("Điều khiển",textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		//End game
		textY += gp.tileSize;
		g2.drawString("Kết thúc",textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		//BACK
		textY += gp.tileSize*2;
		g2.drawString("Quay lại",textX, textY);
		if(commandNum == 5) {
			g2.drawString(">", textX-25, textY);
		}

		//Full screen check box
		textX = frameX + (int)(gp.tileSize*6);
		textY = frameY + gp.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		// music volume
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		// SE volume
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();
	}
	public void options_fullSceenNotification(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDiaLogue = "Thay đổi sẽ cần khởi động lại\n trò chơi!";
		for(String line:currentDiaLogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
	//Back
		textY = frameY + gp.tileSize*9;
		g2.drawString("Quay lại", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
		
	}
	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;
		//title
		String text = "Điều khiển";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Di chuyển", textX, textY); textY += gp.tileSize;
		g2.drawString("Tương tác", textX, textY); textY += gp.tileSize;
		g2.drawString("Bắn", textX, textY); textY += gp.tileSize;
		g2.drawString("Bảng trạng thái", textX, textY); textY += gp.tileSize;
		g2.drawString("Tạm dừng", textX, textY); textY += gp.tileSize;
		g2.drawString("Cài đặt", textX, textY); textY += gp.tileSize;
		
		textX = frameX + gp.tileSize*7;
		textY = frameY + gp.tileSize*2;
		g2.drawString("WASD", textX, textY); textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
		g2.drawString("F", textX, textY); textY += gp.tileSize;
		g2.drawString("C", textX, textY); textY += gp.tileSize;
		g2.drawString("P", textX, textY); textY += gp.tileSize;
		g2.drawString("ESC", textX, textY); textY += gp.tileSize;

		//back
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("Quay lại", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}
	}
	public void options_endGameConfirmation(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		currentDiaLogue = "Bạn có muốn thoát game?";
		for(String line: currentDiaLogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		//yes
		String text = "Có";
		textX = getXforCenteredText(text);
		textY += gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.stopMusic();
//				System.exit(1); // đóng trò chơi thay vì về màn hình chính
			}
		}
		//no
		text = "Không";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}
	public void drawTransition() {
		counter++;
		g2.setColor(new Color(0,0,0, counter*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		if(counter == 10) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}
	}
	public void drawTradeScreen() {
		switch(subState) {
		case 0: trade_select(); break;
		case 1: trade_buy(); break;
		case 2: trade_sell(); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void trade_select() {
		drawDiaLogueScreen();
		
		int x = gp.tileSize*9;
		int y = gp.tileSize*6;
		int width = gp.tileSize*3;
		int height = (int)(gp.tileSize*3.5);
		drawSubWindow(x,y,width,height);
		
		// DRAW TEXTS
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Mua", x, y);
		if (commandNum == 0) {
		    g2.drawString(">", x - 24, y);
		    if(gp.keyH.enterPressed == true) {
		    	subState = 1;
		    }
		}

		y += gp.tileSize;
		g2.drawString("Bán", x, y);
		if (commandNum == 1) {
		    g2.drawString(">", x - 24, y);
		    if(gp.keyH.enterPressed == true) {
		    	subState = 2;
		    }
		}

		y += gp.tileSize;
		g2.drawString("Thoát", x, y);
		if (commandNum == 2) {
		    g2.drawString(">", x- 24, y);
		    if(gp.keyH.enterPressed == true) {
		    	commandNum = 0;
		    	gp.gameState = gp.dialogueState;
		    	currentDiaLogue = "Hẹn gặp lại!....";
		    	
		    	
		    }
		}

	}
	public void trade_buy() {
		//DRAW PLAYER INVENTORY
		drawInventory(gp.player, false);
		//DRAW NPC INVENTORY
		drawInventory(npc, true);
		
		//DRAW HINT WINDOW
		int x = gp.tileSize*2;
		int y = gp.tileSize*11;
		int width = gp.tileSize*6;
		int height = gp.tileSize*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("[ESC] Quay lại", x+24, y+60);
		
		//DRAW PLAYER COIN WINDOW
		x = gp.tileSize*16;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("$$$ : "+ gp.player.coin, x+24, y+60);

		//DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if(itemIndex < npc.inventory.size()) {
			x = (int)(gp.tileSize*5.5);
			y = (int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x+10, y+8, 32, 32, null);
			
			int price = npc.inventory.get(itemIndex).price;
			String text = " " + price;
			x = getXforAlignToRightText(text, gp.tileSize*8-20);
			g2.drawString(text, x, y+34);
			
			
			//BUY AN ITEM
			if(gp.keyH.enterPressed == true) {
				if(npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					
					currentDiaLogue = "Không có tiền\n mà đòi mua!";
					drawDiaLogueScreen();
				}
				else if(gp.player.inventory.size()== gp.player.maxInventorySize) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDiaLogue = "Không thể mang\n thêm nữa!";
				}
				else {
					gp.player.coin -= npc.inventory.get(itemIndex).price;
					gp.player.inventory.add(npc.inventory.get(itemIndex));
					String ObjectName = npc.inventory.get(itemIndex).name;
					if(ObjectName == "Chìa khóa cửa") {
						gp.player.hasKey++;
					}
					gp.playSE(15);

				}
			}

		}
	}
	public void trade_sell() {
		
		//DRAW PLAYER INVENTORY SELL
		drawInventory(gp.player, true);
		int x;
		int y;
		int width;
		int height;
		
		//DRAW HINT WINDOW
		x = gp.tileSize*2;
		y = gp.tileSize*11;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("[ESC] Quay lại", x+24, y+60);
		
		//DRAW PLAYER COIN WINDOW
		x = gp.tileSize*2;
		y = gp.tileSize*8;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("Xu : "+ gp.player.coin, x+24, y+60);

		//DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if(itemIndex < gp.player.inventory.size()) {
			x = (int)(gp.tileSize*19.5);
			y = (int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x+10, y+8, 32, 32, null);
			
			int price = gp.player.inventory.get(itemIndex).price/2;
			String text = " " + price;
			x = getXforAlignToRightText(text, gp.tileSize*22-20);
			g2.drawString(text, x, y+34);
			
			//SELL AN ITEM
			if(gp.keyH.enterPressed == true) {
				if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon||gp.player.inventory.get(itemIndex)== gp.player.currentShield){
					commandNum = 0;
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDiaLogue = "Bạn không thể bán\nvật phẩm đang sử dụng";
				}
				else {
					Entity item = gp.player.inventory.get(itemIndex);
					String ObjectName = item.name;

					if(ObjectName.equals("Chìa khóa cửa")) {
					    gp.player.hasKey--;

					}
					gp.player.coin += price;
					gp.player.inventory.remove(itemIndex);

					gp.playSE(15);
				}
			}

		}
	}
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35	);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	public int getXforAlignToRightText(String text, int tailX) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}
