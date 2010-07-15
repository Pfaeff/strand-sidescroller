package engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScore {
	
	static final private String filename = "highscore.txt";
	
	static public int readHighScore() {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(filename));
			String line;
			line = in.readLine();
			in.close();
			if (line == null) {
				return 0;
			} else {
				return Integer.valueOf(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
		return 0;
	}
	
	static public void writeHighScore(int score) {
		BufferedReader in;
		BufferedWriter out;
		try {
			in = new BufferedReader(new FileReader(filename));
			out = new BufferedWriter(new FileWriter(filename));
			String line;
			line = in.readLine();
			if ((line == null) || (Integer.valueOf(line) < score)) {
				out.write(score);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
