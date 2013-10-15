package arcer.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import arcer.resource.Art;
import arcer.resource.Settings;
import arcer.resource.Song;
import arcer.state.GameplayState;
import arcer.state.MainMenuState;

public class ArcerRunner extends StateBasedGame {
	public enum States {MENU, GAME};

	public ArcerRunner() {
		super("The ARCer: A Parabolic Arrows Game. Naively speaking, parabolas are x^2=4py or y^2=4px. Formally, they are Ax^2 + Bxy + Cy^2 + Dx + Ey + F = 0 where B^2 = 4AC.");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new ArcerRunner());
		app.setDisplayMode(Settings.valueInt("windowWidth"), Settings.valueInt("windowHeight"), Settings.valueBoolean("fullScreen"));
		app.setTargetFrameRate(Settings.valueInt("fps"));
		app.setShowFPS(false);
		app.start();
	}

	public void initStatesList(GameContainer gameContainer) throws SlickException {
		Art.load();
		Song.load();

		/* Note: States MUST be added in the same order
		 * as they appear in the enum ArcerRunner.States
		 */
		this.addState(new MainMenuState());
		this.addState(new GameplayState());
		if (Settings.valueBoolean("skipTitle")) {
			this.enterState(States.GAME.ordinal());
		} else {
			this.enterState(States.MENU.ordinal());
		}
	}
}
