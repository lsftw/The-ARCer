package arcer.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

// Keeps track of all the loaded levels
public class Levels {
	protected static List<Level> levels = new ArrayList<Level>();

	static {
		LevelListReader llr = new LevelListReader(new File("data/levellist.txt"));
		try {
			llr.readFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Level loadLevel(String levelPath) throws FileNotFoundException {
		File f = new File(levelPath);
		LevelFileReader lfr = new LevelFileReader(f);
		lfr.readFile();
		levels.add(lfr.getLevel());
		lfr.getLevel().setName(f.getName());
		return lfr.getLevel();
	}

	public static Level getFirstLevel() {
		return levels.get(0);
	}
	public static Level getNextLevel(Level curLevel) {
		int nextLevelIndex = levels.indexOf(curLevel) + 1;
		if (nextLevelIndex >= 0 && nextLevelIndex < levels.size() && !invalidLevel(levels.get(nextLevelIndex))) {
			return levels.get(nextLevelIndex);
		}
		return null;
	}

	public static boolean invalidLevel(Level l) {
		return l.getName().equals("gameover.txt") || l.getName().equals("credits.txt");
	}

	public static Level gameOver() {
		for (int i = 0; i < levels.size(); i++) {
			if (levels.get(i).getName().equals("gameover.txt"))
				return levels.get(i);
		}
		return null; // we have no game over screen :x
	}

}
