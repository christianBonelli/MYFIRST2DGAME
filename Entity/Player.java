package Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import MyFirstGames.GamePanel;
import MyFirstGames.KeyHandler;

public class Player extends Entity {
		GamePanel gp;
		KeyHandler keyH;
		
		public final int screenX;
		public final int screenY;
		int hasKey = 0;
		
		
		//Assegniamo l'istanza del GamePanel fornita come parametro 'gp'. QUesto permette al Player di
		//Accedere alle sue funzionalità
		//Assegniamo anche l'istanza dei gestori dei tasto. Questo ci permette di acced
		public Player(GamePanel gp, KeyHandler keyH) {
			this.gp = gp;
			this.keyH = keyH;
			
			//Calcolaa la posizione iniziale X del Giocatore
			screenX = gp.screenWidth/2 - (gp.tileSize/2);
			
			//Calcola la posizione iniziale Y del giocatore sullo Schermo
			screenY = gp.screenHeigth/2 - (gp.tileSize/2);
			
			//Istanziato la classe Rectangle e passiamo valori x,y, altezza e largehzza
			//Imposta le dimensione e le coordinate dell'area solida del giocatore .
			//Questi valori definiscono l'area di collisione del giocatore all'interno della immagine
			solidArea = new Rectangle();
			solidArea.x = 8;//La parte solida dell'entità parte a 8 pixel dal bordo sinistro
			solidArea.y = 16;//La parte solida inizia a 16 pixel dal bordo superiore
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			solidArea.width = 16;//Indica che la parte solida ha una larghezza di 16 pixel
			solidArea.height = 16;//Indica che la parte solida ha una altezza di a6 px
			
			
			setDefaultValues();
			getPlayerImage();
			
		}
		public void setDefaultValues() {
			
			//Calcoliamo le coordinate iniziali X e Y in formato pixel dello starting point
			worldX = gp.tileSize * 23; //48 * 23 == 1104 px
			worldY = gp.tileSize * 21;//48 * 21 = 1008 px
			//Impostiamo la velocità di movimento del giocatore
			speed = 8;
			//Impostiamo la direzione injziale del giocatore
			direction = "down";
			
		}
		//questo metodo caric le immagini del giocatore da file e le prepara per essere utilizzate
		public void getPlayerImage() {
			try {
				//Utilizzo di ImageIO.read() per leggere le immgini dai file forniti e memorizzati in BufferedImage
				//Utilizzo si getClass().getResourceAsStream(/Path/nome_file.FORMATOFILE(png, jpeg); per ottenere i file immagini
				// dal percorso specificato all'interno di un package
				up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		//Metodo che aggiorna lo stato del giocatore
		//Il metodo viebe richiamato 60 volte al secondo
		public void update() {
			//Lo sprite del personaggio non cambia se non si schiaccia un pulsante
			if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if(keyH.upPressed == true) {
				//I valori di x aumentano andando a destra
				//I valori di y aumentano andando verso il basso
				//In java il punto X:0 e Y:0 è in alto a sinistra nell'angolo
				direction ="up";
				
			//Ad ogni input dei tasti il personaggio si muoverà di 4px che corrisponde alla velocità di movimento del personaggio
			}else if(keyH.downPressed == true) {
				direction = "down";
				
			}else if(keyH.leftPressed == true) {
				direction = "left";
			
			}else if(keyH.rightPressed == true) {
				direction = "right";
				
			}
			//Check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
				
			
			//If collisione is false, player can moove
			if(collisionOn == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "right": worldX += speed; break;
				case "left": worldX -= speed; break;
				}
			}
			
			
			//Abbiamo creato un metodo per creare il movimento del cammino
			//Ogni volta che viene chiamato updte() lo spriteCounter aumenta di 1
			//Piche il metodo viene chimato 60 volte al secondo, lo spriteCounter aumenta di 1 ogni 1/60 di secondo circa 16ms
			spriteCounter++;
			//gestione per l'animazione del personaggio
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		  }
		}
		public void pickUpObject(int i) {
			if(i != 999 ) {
				//Facendo così eliminiamo l'oggetto 
				//gp.obj[i] = null;
				String objectName =  gp.obj[i].name;
				
				switch(objectName) {
				case "Key":
					hasKey++;
					gp.obj[i] = null;
					System.out.println("Key: " + hasKey);
					break;
				case "Door":
					if(hasKey > 0) {
						gp.obj[i] = null;
						hasKey--;
						System.out.println("Key: " + hasKey);
					}
					break;
				}
			}
		}
		
		public void draw(Graphics2D g2) {
			//dichiarata una variabile 'image' di tipo BUfferedImage e inizializzat a null
			//Questa varriabile conterrà l'immagine dello sprite del personaggio da disegnare
			BufferedImage image = null;
			//Switch statement per determinare quale direzione sta affrontando il personaggio
			switch(direction) {
			//A seconda della direzione e delllo spriteNum viene selezionata l'immagine corrispondente
			case "up":
				if(spriteNum == 1) {
					image = up1;
				}
				if(spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1) {
					image = down1;
				}
				if(spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1) {
					image = left1;
				}
				if(spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1) {
					image = right1;
				}
				if(spriteNum == 2) {
					image = right2;
				}
				break;
			}
			//Utilizzo del metodo drawImage() per disegnare l'immagine del personaggio alle coordinate
			//screenX e screenY cone le dimensioni di un singolo 'tile' quindi 48*48 pixel
			//verra disegnato alle coordinate di 1104 e 1008 pixel
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);//Null sarebbe L'ImageObserver, viene usato per ricevere notifiche riguardo llo stato del cricamento della immagine
		}
}
