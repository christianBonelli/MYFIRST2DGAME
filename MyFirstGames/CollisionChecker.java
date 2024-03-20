package MyFirstGames;

import Entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
		
	}
	
	//metodo per check per le collisioni
	//Utilizziamo la classe 'ENTITY' e non 'player' per generalizzare il più possibile l'uso di quest classe
	public void checkTile(Entity entity) {
		//Questi valori rappresentano i bordi sinistro, destro, superiore e inferiore della entità
		//Questi valori corrisponderanno alle cordinaate dei 4 angoli dell'ipotetico rettangolo che rappresenterà la hitbox
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;// 100 + 8 = 108
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;//100 + 8 + 16 =124px
		int entityTopWorldY = entity.worldY + entity.solidArea.y;//150 + 16 = 166 px
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height; //150+16+16 =182 px
		
		//COn questi calcoli servono a determinare in quale riga e colonna della matrice si trova la hitbox
		//Questi valori verranno utilizzati per controllare eventuali collisioni
		int entityLeftCol = entityLeftWorldX / gp.tileSize;//108 / 48 = 2
		int entityRightCol = entityRightWorldX / gp.tileSize; //124 / 48 = 2
		int entityTopRow = entityTopWorldY / gp.tileSize; //166 / 48 = 3
		int entityBottomRow = entityBottomWorldY / gp.tileSize; //182 / 48 =3
		
		//queste due variabili verranno utilizzzare per ottenere i numeri dei tile corrispondenti alla posizione dell'entità
		int tileNum1 , tileNum2;
		
		//Gestione delle collisioni con i tile della mappa in base alla direzione in cui l'entità si sta muovendo
		switch(entity.direction) {
		case "up":
			//Calcoliamo la riga del tile superiore rispetto all'entià dopo che si è spostato verso l'alto
			//entityTopWorldY rappresenta le coordinate Y del bordo superiore dell'entità nel mondo di gioco, dividiamo per per tileSize per ottenere la riga corrispondente
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;//(124 -4) / 48 = 2
			
			//entityLeftCol e entityRightCol sono le colonne della mappa dei tile in cui si trovano i bordi sinistro e destro dell'entità
			//entityTopRow è la riga in cui si trova il bordo superiore dell'entità
			//gp.tileM.maptileNum è un array bidimensionale che memorizza i numeri dei tile per ciascuna posizione dulla mappa.
			//E' usato qui per ottenere i numeri dei tile alle posizioni specificate dall'entità
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];//Restituisce il il numero del tile alla posizione in cui si trova il bordo sinistro
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];//Restituisce il numero del tile alla posizione del bordo destro dell'entità
			
			//tileNum1 e tileNum2 sono i numeri dei tile che sono stati ottenuti in precedenza in base alla posizione dell'entità
			//questo if verifica se uno dei due tile con cui l'entità potrebbe entrare in contatto ha il flag 'COLLISION' impostato su 'True'
			//Se collision è true per uno di qquesti tile, viene impostato entity.collision su true, indicando che c'è stata una collisione
			//gp.tileM.tile[] viene usato per accedere all'array oggetti 'Tile[]' all'interno dell''oggetto TileManager
			//'collision' è un attributo di ciascun oggetto 'Tile' che indica se quel tipo è solido e può causare collisioni
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM. tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM. tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM. tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
			
		}
	//Controlliamo che il giocatore sta colpendo un oggetto
	public int checkObject(Entity entity, boolean player) {
		int index = 999;
		
		for(int i = 0; i< gp.obj.length; i++) {
			if(gp.obj[i] != null) {
				//Get entitys solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				
				//Get the obejct solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					break;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
			
		}
		
		return index;
	}
		
	}
	
	


