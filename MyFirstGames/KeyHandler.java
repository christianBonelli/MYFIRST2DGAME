package MyFirstGames;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Classe che gestisce gli eventi per la tastiera che implementa l'interfacci KeyListener
public class KeyHandler implements KeyListener{
	
	//creazioni di variabili booleane per i tasti
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	//questo metodo viene chimato quando un tasto viene chiamato
	public void keyPressed(KeyEvent e) {
		
		//Ci ritorna in un numero intero il tasto schiacciato
		int code = e.getKeyCode();
		
		//Se il tasto premuto corrisponde a uno dei tasti direzionali 
		// la rispettiva variabile booleana viene impostata su true.
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
	    }
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
			}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		//Se i bottoni vengono rilasciati diventa false
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
	    }
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
	    }
		
	}

}
