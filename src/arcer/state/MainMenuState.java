package arcer.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import arcer.gui.BbrGameState;
import arcer.gui.Button;
import arcer.main.ArcerRunner;
import arcer.resource.Art;
import arcer.resource.Settings;
import arcer.resource.Song;

public class MainMenuState extends BbrGameState {
	private static final int TITLE_X = 150, TITLE_Y = 50;

	private static final int BUTTONS_HEIGHT = 50;
	private static final int BUTTONS_WIDTH = 400;
	private static final int BUTTONS_X = Settings.valueInt("windowWidth") / 2 - BUTTONS_WIDTH / 2;
	private static final int BUTTONS_Y = 300;

	private static final int START_X = BUTTONS_X, START_Y = BUTTONS_Y;
	private static final int HELP_X = BUTTONS_X, HELP_Y = BUTTONS_Y + BUTTONS_HEIGHT;
	private static final int EXIT_X = BUTTONS_X, EXIT_Y = BUTTONS_Y + BUTTONS_HEIGHT * 2;
	protected Button startButton, startButtonGlow;
	protected Button helpButton, helpButtonGlow;
	protected Button exitButton, exitButtonGlow;

	protected boolean goingToStart = false;
	protected boolean goingToExit = false;

	private int mouseX = Integer.MIN_VALUE, mouseY = Integer.MIN_VALUE;

	private static final String MENU_MUSIC_NAME = "Menu";
	protected Image imageTitle;

	public MainMenuState() throws SlickException {
		super(ArcerRunner.States.MENU);
		imageTitle = Art.getImage("TitleImage");

		startButton = new Button(Art.getImage("StartButton"), START_X, START_Y);
		buttons.add(startButton);
		helpButton = new Button(Art.getImage("HelpButton"), HELP_X, HELP_Y);
		buttons.add(helpButton);
		exitButton = new Button(Art.getImage("ExitButton"), EXIT_X, EXIT_Y);
		buttons.add(exitButton);

		startButtonGlow = new Button(Art.getImage("StartButton_mouse"), START_X, START_Y);
		helpButtonGlow = new Button(Art.getImage("HelpButton_mouse"), HELP_X, HELP_Y);
		exitButtonGlow = new Button(Art.getImage("ExitButton_mouse"), EXIT_X, EXIT_Y);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Song.playMusic(MENU_MUSIC_NAME);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		super.render(gc, sbg, g);
		imageTitle.draw(TITLE_X, TITLE_Y);

		if (mouseX > BUTTONS_X && mouseX < BUTTONS_X + BUTTONS_WIDTH) {
			if (startButton.checkClick(mouseX, mouseY)) {
				startButtonGlow.draw(g);
			}
			if (helpButton.checkClick(mouseX, mouseY)) {
				helpButtonGlow.draw(g);
			}
			if (exitButton.checkClick(mouseX, mouseY)) {
				exitButtonGlow.draw(g);
			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (goingToStart) {
			goingToStart = false;
			sbg.enterState(ArcerRunner.States.GAME.ordinal());
		} else if (goingToExit) {
			gc.exit();
		}
	}

	public void buttonClicked(Button b) throws SlickException {
		if (b == startButton) {
			goingToStart = true;
		} else if (b == exitButton) {
			goingToExit = true;
		} else if (b == helpButton) {
			b.setImage(Art.getSprite("Archer").getFrame());
		}
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		mouseX = newx;
		mouseY = newy;
	}
}
