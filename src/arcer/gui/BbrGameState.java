package arcer.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import arcer.main.ArcerRunner.States;

// Provides helper methods
// State Id should be the corresponding constant in BlackbeardsRedemption
public abstract class BbrGameState extends BasicGameState {
	protected int stateId = -1; // negative ids are invalid
	public int getID() { return stateId; }

	protected List<Button> buttons = new ArrayList<Button>();

	public BbrGameState(States state) throws IllegalArgumentException {
		this.stateId = state.ordinal();
		if (stateId < 0) {
			throw new IllegalArgumentException("State ID must be non-negative.");
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		drawButtons(g);
	}
	public void drawButtons(Graphics g) {
		for (Button b : buttons) {
			b.draw(g);
		}
	}

	/**
	 * Check if any buttons were clicked.
	 */
	public void mousePressed(int button, int mouseX, int mouseY) {
		for (Button b : buttons) {
			if (b.checkClick(mouseX, mouseY)) {
				try {
					buttonClicked(b);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// no-op optional override instead of ButtonListener interface as design choice
	public void buttonClicked(Button b) throws SlickException { }
}
