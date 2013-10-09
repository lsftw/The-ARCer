package arcer.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public final class Song {
	public static final File SONG_LIST = new File("data/songlist.txt");
	protected static HashMap<String, Music> songs = new HashMap<String, Music>();

	private static boolean loaded = false;
	private static boolean isMuted = false;
	private static Music currentSong;
	private Song() { }
	//
	//	static {
	//		String classJar = Art.class.getResource("/" + Art.class.getName().replace('.', '/') + ".class").toString();
	//		if (classJar.startsWith("jar:")) {
	//			runningInJar = true;
	//		} else {
	//			runningInJar = false;
	//		}
	//	}
	public static void load() { // Not in a static block due to Slick
		if(loaded) {
			Utility.printWarning("Loading song files after songs have already been loaded.");
		}

		loadSongs();
		loaded = true;
		isMuted = Settings.valueBoolean("muted");
	}
	private static void loadSongs() {
		SongFileReader fileReader = new SongFileReader(SONG_LIST);
		try {
			fileReader.readFile();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	protected static Music loadSong(String songName) throws SlickException {
		return new Music(songName);
	}
	public static void playMusic(String songName){
		if (isMuted) return;
		stopMusic(); // stop previous song
		currentSong = songs.get(songName);
		if (currentSong != null) {
			currentSong.loop();
			return;
		}
	}
	public static void stopMusic(){
		if (currentSong != null) {
			currentSong.stop();
		}
	}
	public static boolean isPlaying(String songName){
		return currentSong == songs.get(songName);
	}
}
