package arcer.level;

import java.io.File;

import arcer.resource.SequentialFileReader;

public class NewLevelFileReader extends SequentialFileReader {
	protected NewLevel level = new NewLevel();

	public NewLevelFileReader(File file) {
		super(file);
		cleanLines = true;
		ignoreBlankLines = true;
	}

	public NewLevel getLevel() { return level; }

	protected void processLine(String curLine, int lineNumber) {
		// TODO processLine
	}
}
