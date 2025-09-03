package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
	
	
	
	// SCREEN SETTING
	final int originalTileSize = 16; // 16x16
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48x48
	public final int maxScreenCol = 28;
	public final int maxScreenRow = 15;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	// WORLD SETTING
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int maxMap = 10;
	public int currentMap = 2;
	// FULL SCREEN  
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;	
	public boolean fullScreenOn = false;
	// SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Config config = new Config(this);
	Thread gameThread;
	// NHÂN VẬT VÀ VẬT PHẨM
	public Player player = new Player(this, keyH);
	public Entity obj[][] = new Entity[maxMap][20];
	public Entity npc[][] = new Entity[maxMap][20];
	public Entity monster[][] = new Entity[maxMap][20];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionsState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;
	
	//	FPS
	int FPS = 60;
	public boolean attacking;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	public void setUpGame() {
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		playMusic(0);
		stopMusic();
		gameState = titleState;
		
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
		if(fullScreenOn == true) {
			setFullScreen();
		}
	}
	public void retry() {
		player.setDefaultPosition();
		player.restoreLifeAndMana();
		aSetter.setNPC();
		aSetter.setMonster();
	}
	public void restart () {
		player.setDefaultValues();
		player.setDefaultPosition();
		player.restoreLifeAndMana();
		player.setItems();
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
	}
	public void setFullScreen() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		//Lấy full dài và rộng màn hình
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	

	
	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				drawToTempScreen();
				drawToScreen();
				delta--;
				drawCount++;

			}
			if (timer > 1000000000) {
//				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}

	}

	public void update() {

		if (gameState == playState) {
			// PLAYER
			player.update();
			// NPC
			for (int i = 0; i < obj[1].length; i++) {
				if (npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
			// MONSTER
			for (int i = 0; i < monster[1].length; i++) {
				if (monster[currentMap][i] != null) {
					if (monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
						monster[currentMap][i].update();
					}
					if (monster[currentMap][i].alive == false) {
						monster[currentMap][i].checkDrop();
						monster[currentMap][i] = null;
					}
				}
			}
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					if (projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					if (projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}
				}
			}
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive == true) {
						particleList.get(i).update();
					}
					if (particleList.get(i).alive == false) {
						particleList.remove(i);
					}
				}
			}
			for(int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) {
					iTile[currentMap][i].update();
				}
			}
			
		}
		if (gameState == pauseState) {
			// NOTHING
		}
	}
	public void drawToTempScreen() {
	    long drawStart = 0;
	    if (keyH.showDebugText) {
	        drawStart = System.nanoTime();
	    }

	    if (gameState == titleState) {
	        ui.draw(g2);
	    } else {
	        // TILE
	        tileM.draw(g2);

	        // INTERACTIVE TILE
	        for (int i = 0; i < iTile[1].length; i++) {
	            if (iTile[currentMap][i] != null) {
	                iTile[currentMap][i].draw(g2);
	            }
	        }

	        // 🎯 Vẽ object layer 0 (ví dụ: cửa)
	        for (int i = 0; i < obj[1].length; i++) {
	            if (obj[currentMap][i] != null && obj[currentMap][i].drawLayer == 0) {
	                obj[currentMap][i].draw(g2);
	            }
	        }

	        // Gom entity còn lại
	        entityList.add(player);

	        for (int i = 0; i < npc[1].length; i++) {
	            if (npc[currentMap][i] != null) {
	                entityList.add(npc[currentMap][i]);
	            }
	        }
	        for (int i = 0; i < obj[1].length; i++) {
	            if (obj[currentMap][i] != null && obj[currentMap][i].drawLayer == 1) {
	                entityList.add(obj[currentMap][i]);
	            }
	        }
	        for (int i = 0; i < monster[1].length; i++) {
	            if (monster[currentMap][i] != null) {
	                entityList.add(monster[currentMap][i]);
	            }
	        }

	        // PROJECTILE
	        for (int i = 0; i < projectileList.size(); i++) {
	            if (projectileList.get(i) != null) {
	                Entity p = projectileList.get(i);

	                if (p instanceof entity.Projectile) {
	                    entity.Projectile proj = (entity.Projectile) p;

	                    if (proj.drawOnTop) {
	                        entityList.add(proj);
	                    } else {
	                        proj.draw(g2); // vẽ ngay, nằm dưới
	                    }
	                }
	            }
	        }

	        // Sort theo worldY
	        Collections.sort(entityList, new Comparator<Entity>() {
	            @Override
	            public int compare(Entity e1, Entity e2) {
	                return Integer.compare(e1.worldY, e2.worldY);
	            }
	        });

	        for (int i = 0; i < entityList.size(); i++) {
	            entityList.get(i).draw(g2);
	        }
	        entityList.clear();

	        // UI
	        ui.draw(g2);
	    }

	    // DEBUG
	    if (keyH.showDebugText) {
	        long drawEnd = System.nanoTime();
	        long passed = drawEnd - drawStart;

	        g2.setFont(new Font("Arial", Font.PLAIN, 20));
	        g2.setColor(Color.white);
	        int x = 10;
	        int y = 400;
	        int lineHeight = 20;

	        g2.drawString("WorldX" + player.worldX, x, y); y += lineHeight;
	        g2.drawString("WorldY" + player.worldY, x, y); y += lineHeight;
	        g2.drawString("Col" + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
	        g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;

	        System.out.println("Draw time: " + passed);
	    }
	}

	
	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}
	

	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
