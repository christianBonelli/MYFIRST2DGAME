package Entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

	//Rappresentano le coordinate della posizione dell'entità(personaggi) nel mondo ddi gioco
	public int worldX, worldY;
	//rappresenta la velocità con cui si muovono i personaggi
	public int speed;
	
	//BufferedImage viene utilizzato per rappresentare una immagine.
	//Nel nostro caso viene usato per memorizzare le immagini associate lle diverse direzioni del movimento
	//Usiamo questo metodo per salvare le nostre immagini
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	
	//Indicia la direzione in cui l'entità sta attualmendo muovendosi
	public String direction;
	
	//Uno sprite è un elemento grafico utilizzato per rappresentare uno o un insieme di dimmagini che rappresenta un oggetto grafico
	//spriteCounter tiene traccia del conteggio delle immagini utilizzate per l'nimazione
	public int spriteCounter = 0;
	
	//Inidica il numero totale di immagini utilizzate per l'animazione
	public int spriteNum = 1;
	
	//Con questa classe possiamo creare un invisibile rettangolo a cui possiamo passare dei dati
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	
}
