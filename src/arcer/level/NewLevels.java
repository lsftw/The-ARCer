package arcer.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

// Keeps track of all the loaded levels
public class NewLevels {
	protected static List<NewLevel> levels = new ArrayList<NewLevel>();

	static {
		LevelListReader llr = new LevelListReader(new File("data/levellist.txt"));
		try {
			llr.readFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static NewLevel loadLevel(String levelPath) throws FileNotFoundException {
		File f = new File(levelPath);
		NewLevelFileReader lfr = new NewLevelFileReader(f);
		lfr.readFile();
		levels.add(lfr.getLevel());
		lfr.getLevel().setName(f.getName());
		return lfr.getLevel();
	}

	public static NewLevel getFirstLevel() {
		return levels.get(0);
	}
	public static NewLevel getNextLevel(NewLevel curLevel) {
		int nextLevelIndex = levels.indexOf(curLevel) + 1;
		if (nextLevelIndex >= 0 && nextLevelIndex < levels.size()) {
			return levels.get(nextLevelIndex);
		}
		return null;
	}

}
