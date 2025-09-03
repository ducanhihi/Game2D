package tile;	

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[1000];
		
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/map003.txt", 0);
		loadMap("/maps/map002.txt", 1);
		loadMap("/maps/map001.txt", 2);

	}
	
	public void getTileImage() {

	    setup(0,"000", false);
	    setup(1,"001", false);
	    setup(2,"002", false);
	    setup(3,"003", false);
	    setup(4,"004", false);
	    setup(5,"005", false);

	    setup(6,"006", false);
	    setup(7,"007", false);
	    setup(8,"008", false);
	    setup(9,"009", false);
	    setup(10,"010", false);
	    setup(11,"011", false);
	    setup(12,"012", false);
	    setup(13,"013", false);
	    setup(14,"014", false);
	    setup(15,"015", false);
	    setup(16,"016", false);
	    setup(17,"017", true);
	    setup(18,"018", true);
	    setup(19,"019", true);
	    setup(20,"020", true);
	    setup(21,"021", true);
	    setup(22,"022", true);
	    setup(23,"023", true);
	    setup(24,"024", true);
	    setup(25,"025", true);
	    setup(26,"026", true);
	    setup(27,"027", true);
	    setup(28,"028", true);
	    setup(29,"029", true);
	    setup(30,"030", true);
	    setup(31,"031", true);
	    setup(32,"032", true);
	    setup(33,"033", true);
	    setup(34,"034", true);
	    setup(35,"035", true);
	    setup(36,"036", true);
	    setup(37,"037", true);

	    setup(38,"038", true);
	    setup(39,"039", true);
	    setup(40,"040", true);
	    setup(41,"041", true);
	    setup(42,"042", true);
	    setup(43,"043", true);
	    setup(44,"044", true);
	    setup(45,"045", true);
	    setup(46,"046", true);
	    setup(47,"047", true);
	    setup(48,"048", true);
	    setup(49,"049", true);
	    setup(50,"050", true);
	    setup(51,"051", true);
	    setup(52,"052", true);
	    setup(53,"053", true);
	    setup(54,"054", true);
	    setup(55,"055", true);
	    setup(56,"056", true);
	    setup(57,"057", true);
	    setup(58,"058", true);
	    setup(59,"059", true);
	    setup(60,"060", true);
	    setup(61,"061", true);
	    setup(62,"062", true);
	    
	    setup(63,"063", true);
	    setup(64,"064", true);
	    setup(65,"065", true);
	    setup(66,"066", true);
	    setup(67,"067", true);
	    setup(68,"068", false);
	    setup(69,"069", false);
	    setup(70,"070", false);
	    setup(71,"071", false);
	    setup(72,"072", false);
	    setup(73,"073", false);
	    setup(74,"074", false);
	    setup(75,"075", false);
	    setup(76,"076", false);
	    setup(77,"077", false);
	    setup(78,"078", false);
	    setup(79,"079", false);
	    setup(80,"080", false);
	    setup(81,"081", false);
	    setup(82,"082", false);
	    setup(83,"083", true);
	    setup(84,"084", false);
	    setup(85,"085", false);
	    setup(86,"086", false);
	    setup(87,"087", false);
	    setup(88,"088", true);
	    setup(89,"089", true);
	    setup(90,"090", true);
	    setup(91,"091", true);
	    setup(92,"092", true);
	    setup(93,"093", true);
	    setup(94,"094", true);
//	    setup(95,"095", true);
//	    setup(96,"096", true);
//	    setup(97,"097", true);
//	    setup(98,"098", true);
//	    setup(99,"099", true);
//	    setup(100,"100", true);
	    

	}

	
	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadMap(String filePath, int map) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[map][col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e) {
			
		}
	}
	
	
	
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;

		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);

			}
			if (tileNum == 62) { // ô cửa
			    if (gp.player.hasKey > 0) {
			        tile[tileNum].collision = false; // mở cửa
			    } else {
			        tile[tileNum].collision = true;  // khóa cửa
			    }
			}
			worldCol++;

			 
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
