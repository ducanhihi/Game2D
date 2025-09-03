package main;

import entity.NPC_LinhCamGiao;
import entity.NPC_NinjaSale;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import monster.MON_Skeleton;
import object.OBJ_Axe;
import object.OBJ_Door;
import object.OBJ_GreenStone;
import object.OBJ_Heart;
import object.OBJ_IronDoor;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	public void setObject() {
		
		int mapNum = 0;
		int i =0;
		mapNum = 2;

//		gp.obj[mapNum][i] = new OBJ_Key(gp);
//		gp.obj[mapNum][i].worldX = gp.tileSize*25;
//		gp.obj[mapNum][i].worldY = gp.tileSize*23;
//		i++;

//		gp.obj[mapNum][i]=new OBJ_GreenStone(gp);
//		gp.obj[mapNum][i].worldX=gp.tileSize*19;
//		gp.obj[mapNum][i].worldY=gp.tileSize*24;
//		i++;
		
		gp.obj[mapNum][i]=new OBJ_Door(gp);
		gp.obj[mapNum][i].worldX=gp.tileSize*20;
		gp.obj[mapNum][i].worldY=gp.tileSize*37;
		i++;
		
		gp.obj[mapNum][i]=new OBJ_Key(gp);
		gp.obj[mapNum][i].worldX=gp.tileSize*29;
		gp.obj[mapNum][i].worldY=gp.tileSize*36;
		i++;
		
		mapNum = 1;
		gp.obj[mapNum][i]=new OBJ_IronDoor(gp);
		gp.obj[mapNum][i].worldX=gp.tileSize*26;
		gp.obj[mapNum][i].worldY=gp.tileSize*28;
	}
	
	public void setNPC() {
		int mapNum = 0;


		
		mapNum = 2;

		
		gp.npc[mapNum][1] = new NPC_NinjaSale(gp);
		gp.npc[mapNum][1].worldX = gp.tileSize*26;
		gp.npc[mapNum][1].worldY = gp.tileSize*10;
		

		
		
		mapNum = 1;
		gp.npc[mapNum][1] = new NPC_NinjaSale(gp);
		gp.npc[mapNum][1].worldX = gp.tileSize*21;
		gp.npc[mapNum][1].worldY = gp.tileSize*33;
	}
	
	
	public void setMonster() {
		int mapNum = 0;

		int i = 0;

		
		
		mapNum = 2;
		gp.monster[mapNum][i] = new MON_Orc(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*33;

		i++;
		gp.monster[mapNum][i] = new MON_Orc(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*30;
		gp.monster[mapNum][i].worldY = gp.tileSize*35;
		i++;
		gp.monster[mapNum][i] = new MON_Orc(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*29;
		i++;

		
	
		mapNum = 1;
		gp.monster[mapNum][i] = new MON_Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*20;
		gp.monster[mapNum][i].worldY = gp.tileSize*24;

		i++;
		gp.monster[mapNum][i] = new MON_Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*22;
		gp.monster[mapNum][i].worldY = gp.tileSize*25;

	}
	
	public void setInteractiveTile() {
		int mapNum = 2;

		int i = 0;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 10);
		i++;
		

		gp.iTile[mapNum][i] = new IT_DryTree(gp, 25, 26);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 24, 26);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 24, 24);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 25, 24);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 26, 24);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 24, 23);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 25, 23);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 26, 23);
		i++;
		
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 24, 25);
		i++;

	}
	
	
	
	
	
	
}
