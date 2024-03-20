package Tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import MyFirstGames.GamePanel;

//Gestione dei tile nel gioco
public class TileManager {
	//Dichiarazione di GamePanel gp così possiamo utilizzare i metodi di GamePanel
	GamePanel gp;
	//Utilizzo di un array di tipo 'Tile' per memorizzare i vari tile disponibili(grass, earth, sand, tree,...)
	public Tile[] tile;
	
	//Creazione di una matrice mapTileNum[][] di interi per memorizzare i numeri dei tile sulla mappa
	public int mapTileNum[][];
	
	//Viene passato GamePanel gp affinche TileManager possa interagire con GamePanel
	//Si chiam 'composizione' o 'aggregazione' dove una classe contiene un riferimento ad un'altra classe per usarne le funzionalità
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		//Inizializziamo l'array ponendo un limite di 10 elementi massimo, ovvero conterrà 10 tipi di tile diversi massimo
		tile = new Tile[10];
		//La usiamo per memorizzare i numeri dei tile della mapp
		//Sono stti passati 'gp.maxWorldCol e gp.maxWorldRow per determinare la dimensione della matrice. Sono i valori che rappresentano le dimensioni massime del mondo
		//Utilizzando questi valori, si sta creando una matrice che potrà contenere le informazioni sui tile per l'intera mappa del mondo.
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		//Vengono caaricate le immagini dai file inseriti nell'array tile
		getTileImage();
		loadMap("/Maps/world01.txt");
	}
	//metodo responsabile per caricare le immagini dei diversi tipi di tile memorizzati
	//nell'array 'tile'
	public void getTileImage() {
		
		try {
			//Vengono caricate le immagini utilizzando il metodo ImageIO.read()
			
			tile[0] = new Tile();
			//Si accede all'attributo image per memorizzare l''immagine associata a quel tipo di tile
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass01.png"));
			
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water00.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/earth.png"));
			
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/tree.png"));
			tile[4].collision = true;
			
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/sand.png"));
		
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	//Riceve come parametro filePath che rappresenta il percorso del file della mapp da caricare
	public void loadMap(String filePath) {
		try {
			//usiamo InputStream per importare il file txt contenente la mappa creata
			InputStream is = getClass().getResourceAsStream(filePath);
			//BufferedReader serve a leggere il contenuto della mappa
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			//inizializziamo le varibili a 0 per tenere traccia della posizione attuaale nella matrice della mappa
			int col = 0;
			int row = 0;
			
			//utilizziamo un ciclo while per leggere ogni riga del file
			//Il ciclo continua finche non abbiamo letto tutte le righe della mappa oppure finche non raggiungiamo il limite massimo della mappa
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
				//readline() serve per leggere una linea della mappa ma li legge in formato stringa
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					//Le ricghe vengono suddivise in singoli numeri tramite il metodo split(" ") che suddivide la riga in base agli spazi
					String numbers[] = line.split(" "); 
					
					//Ogni numero estratto viene convertito in in un intero usando Integer.parseInt
					//Ci restituisce la stringa che rappresenta il numero nella colonna col della riga corrente della mappa
					int num = Integer.parseInt(numbers[col]);
					
					//Assegna il valore di num all'elemento della matrice mapTileNum nella posizione indicata col e row
					//'col' e 'row' rappresentano la colonna e la riga corrente all'interno della matrice.
					
					mapTileNum[col][row] = num;
					//abbiamo usato col++ per spostarci di 1 lungo la colonna. Così da assegnare un nuovo valore 'col' alla matrice
					
					col++;
				}
				//In pratica qundo col raggiunge il massimo numero di colonne consentito, ovvero 'gp.maxWorldCol',
				//col viene reimpostato a 0 e ci spostiamo di 1 nelle righe.
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
					//Questo ci permette di leggere i dati sulla mappa e di popolare la matrice in modo adeguato
				}
				
				
			}
			//Viene chiuso per poter rilasciare risorse usate per leggere la mappa
			//Garantisce una pulizia delle risorse e ottima gestione del programma
			br.close();
			
		}catch(Exception e) {
		
		}
	}
	public void draw(Graphics2D g2) {
		//Inizializzazione delle variabili. Vengono usate per iterare attraverso la mappa del mondo
		int worldCol = 0;
		int worldRow = 0;
		
		//Ciclo while per scorrere attraverso le colonne e righe della mappa del mondo finche
		//non si raggiunge il numero massimo di righe e colonne
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			//Otteniamo il tipo di tile che si trova in quella specifica colonna e riga della mappa del mondo
			//Questo numero poi utilizzato per accedere all'array 'til' e ottenere l'immagine corrispondente da disegnare
			int tileNum = mapTileNum[worldCol][worldRow];
			
			//Le coordinate del tile vengono calcolate moltiplicando il numero di colonna e riga per la dimesnione del tile
			//ESEMPIO: worldX = 3 * 48(grandezza tile) = 144px
			int worldX = worldCol * gp.tileSize;//144px
			int worldY = worldRow * gp.tileSize;//86px
			
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
			g2.drawImage(tile[tileNum].image,  screenX, screenY, gp.tileSize, gp.tileSize, null);
		    }
			//Dopo aver elaborato tutti i tile della colonna corrente, passiamo all'elaborazione della colonn successiva
			worldCol++;
			//questo if controlla se il ciclo interno ha raggiunto l'ultima colonna della mappa tile
			//cioè se worldCol è uguale al numero massimo di colonne gp.worldCol
			if(worldCol == gp.maxWorldCol) {
				//Se la condizione è vera, abbiamo completato una riga e passiamo a quella successiva
				//Reimpostiamo worldCol a 0 per riiniziare dalla prima colonna
				worldCol = 0;
				//Incrementiamo worldRow per indicare che stiamo passando alla riga successiva della mappa tile
				worldRow++;
			}
		}
	}
}
