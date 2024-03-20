package Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import MyFirstGames.GamePanel;

public class SuperObject {
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	public void draw(Graphics2D g2, GamePanel gp) {

		//QUesti calcoli assicurano che solo i tile visibili vengano disegnati sullo schermo. COsì da evitare spreco di risorse
		//Approccio 'CULLING' o 'FRUSTUM CULLING'
		int screenX = worldX - gp.player.worldX + gp.player.screenX;//220px
		int screenY = worldY - gp.player.worldY + gp.player.screenY;//210px
		
		//blocco di codice che verifica se il corrente è visibile sullo schermo
		//Controllo se il tile si estende oltre il bordo sinistro dello schermo
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				
		//Controllo se il tile si estende oltre il bordo desto dello schermo
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   
		   //Controllo se il tile si estende oltre il bordo superiore delle schermo
		   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
		   
		   //Controllo se il tile si estende oltre il bordo inferiore dello schermo
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
		//Se una di queste condizioni è vera, significa che il tile è parzialmente o completamente visibile allo schermo	
		//Se una condizione è vera disegna il tile col metodo drawImage()
			
		//tile[tileNum]: indica l'immagine del tile che verrà disegnta
		//screenX, screenY sono le coordinate di dove verranno disegnati i tile
		//gp.tileSize disegneranno ogni tile grandi 48px
		//null perchè non serve monitorare il caricamento di immgine
		g2.drawImage(image,  screenX, screenY, gp.tileSize, gp.tileSize, null);
	    }
	}

}
