package MyFirstGames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Entity.Player;
import Object.SuperObject;
import Tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	//Screen settings----
	final int originalTileSize = 16; //16x16 tile grandezza degli oggetti in pixel
	final int scale = 3; //16 * 3 così dara grande 48 pixel
	
	public final int tileSize = originalTileSize * scale;//16*3 = 48 pixel di larghezza e 48 pixel di lunghezza
	public final int maxScreenCol = 16; //La grandezza dello screen sara
	public final int maxScreenRow = 12; 
	public final int screenWidth = tileSize * maxScreenCol; //48 * 16 = 768 pixel di larghezza
	public final int screenHeigth = tileSize * maxScreenRow; //48 * 12 = 576 px di altezza
	//---------
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	//vengono utilizzati per limitare il movimento del gioctore ai confini del mondo
	public final int worldWidth = tileSize * maxWorldCol; // 48 * 50 = 2400 pixel é la misura dela larghezza massima del mondo 
	public final int worldHeight = tileSize * maxWorldRow;// 48 * 50 = 2400 pixel é la misura dela lunghezza massima del mondo
	
	
	//FPS
	int fps = 60;
	//facciamo passare this come parametro cosè tileM può accedere ai metodi o variabili di GamePanel
	// per eseguire determinate operazioni, come ad esempio aggiornre, disegnare i tile all'interno del gioco
	TileManager tileM = new TileManager(this);
	
	//keyH è un oggetto di KeyHandler che può essere utilizzato per gestire gli eventi della tastiera all'interno del pannello di gioco
	KeyHandler keyH = new KeyHandler();
	
	//Thread è un oggetto che può svolgere azioni contemporaneamente
	Thread gameThread;
	
	//Class Collisione passiamo come parametro la classe GamePanel
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	public AssetSetter aSetter = new AssetSetter(this);
	//Istanza classe Player
	public Player player = new Player(this, keyH);
	//10 corrisponde al numero massimo slot per contenere gli oggetti
	public SuperObject obj[] = new SuperObject[10];
	
	
	
	

	//Costruttore
		public GamePanel() {
		
			//Imposta le dimensioni preferite del pannello di gioco utilizzando screenWidth e screenHeigth
		this.setPreferredSize(new Dimension(screenWidth, screenHeigth)); //768 pixel di larghezza e 576 pixel di altezza
		this.setBackground(Color.BLACK);
		
		//Il double buffering è una tecnica utilizzata per migliorare la fluidità delle animazioni riducendo l'effetto di sfarfallio.
		this.setDoubleBuffered(true);
		
		//Aggiunge un gestore degli eventi KeyListener al pannello
		//QUesto consentirà l pannello di ricevere input da tastier e gestirli utilizzando l'oggetto KeyHandler
		this.addKeyListener(keyH);
		
		//Imposta il pannello come focusable, ovvero può ricevere lo stato attivo della tastiera quando è in primo piano
		this.setFocusable(true);
	    }
		
		public void setUpGame() {
			aSetter.setObject();
		}
	
	public void startGameThread() {
		//Significa che il nuovo Thread utilizzerà il metodo run() per eseguire il proprio codice
		gameThread = new Thread(this);
		//Avvia l'esecuzione del thread creato con il metodo start()
		//Questo metodo richiamerà run(), dando inizio al gioco
		gameThread.start();
	}

	@Override
	//Metodo che controlla il rendering del gico
	public void run() {
		// calcola la quantità di tempo (in nanosecondi) che dovrebbe trascorrere tra ogni
		//Calcola la durata tra i frame in nanosecondi
		double drawInterval = 1000000000 / fps; //draw interval è di circa 16.67 milioni di nanosecondi
		
		// tiene traccia del tempo accumulato trascorso tra un frame e l'altro.
		//Questo viene utilizzato per determinare quando è il momento di aggiornare e disegnare un nuovo frame.
		double delta =0;
		
		long lastTime = System.nanoTime();// memorizza il tempo in nanosecondi al quale è stato eseguito l'ultimo frame.
		long currentTime;// viene utilizzato per memorizzare il tempo corrente in nanosecondi.
		
		//iniziamo un ciclo while che continua finche gameThread non è nullo
	while(gameThread != null) {
		
		currentTime = System.nanoTime(); //Si ottiene il tempo corrente in nanoSecondi
		
		// Viene calcolato quanto tempo è passato dall'ultimo frame e viene aggiunto a delta dividendo la differenza temporale per drawInterval
		//Questo ci dà un valore che indica quante volte dovremmo aggiornare e disegnare un frame.
		delta += (currentTime - lastTime) / drawInterval;
		
		//aggiorna il tempo dell'ultimo frame con il tempo corrente
		lastTime = currentTime;
		
		// Se delta è maggiore di 1, significa che è passato abbastanza tempo da aggiornare e disegnare almeno un frame.
		if(delta >1) {
			// Vengono chiamati i metodi update() e repaint() per aggiornare e disegnare il nuovo frame.
			update();
			repaint();
			//Viene sottratto 1 a delta, indicando che un frame è stato aggiornato e disegnato.
			delta--;	
		}	
	}
	}
	//Metodo per aggiornare la posizione dei personaggi
	public void update() {
		player.update();
		
	}
	//Standard method to draw scenes on JPanel
	public void paintComponent(Graphics g) {
		//metodo per eseguire il disegno
		super.paintComponent(g);
		
		//convertiamo la grafica in grafica 2D
		Graphics2D g2 = (Graphics2D)g;
		
		//Disegniamo prima lo sfondo e poi il personaggio. Come un livello di layer
		//passando g2 come parametro consentiamo a Graphics2D di di eseguire il disegno
		tileM.draw(g2);
		
		//Per settare il colore dello screen
		//g2.setColor(Color.white);
		
		//Disegnare un rettangolo
		//g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		//OBJECT
		for(int i = 0; i< obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		player.draw(g2);
		
		//Viene richiamato questo metodo per liberare la memoria una volta completato il disegno
		g2.dispose();
	}
	

}
