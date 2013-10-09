package arcer.level;

import java.io.File;
import java.io.FileNotFoundException;

import arcer.resource.SequentialFileReader;

public class LevelListReader extends SequentialFileReader {
	public LevelListReader(File file) {
		super(file);
		cleanLines = true;
		ignoreBlankLines = true;
	}

	protected void processLine(String curLine, int lineNumber) {
		try {
			Levels.loadLevel(curLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
