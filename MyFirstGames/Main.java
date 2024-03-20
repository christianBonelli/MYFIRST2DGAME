package MyFirstGames;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Adventure");
		
		//Creazine istanza della classe GamePanel
		GamePanel gamePanel = new GamePanel();
		//Aggiunta del GamePanel alla nostra window
		window.add(gamePanel);
		
		//è utile per assicurarsi che una finestra abbia le dimensioni appropriate per contenere tutti i suoi componenti interni senza lasciare spazi vuoti o tagliare i componenti stessi.
		window.pack();
		
		
		
		
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		gamePanel.setUpGame();
		gamePanel.startGameThread();
		
	}

}
