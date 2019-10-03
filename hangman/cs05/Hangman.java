package eg.edu.alexu.csd.datastructure.hangman.cs05;

import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import eg.edu.alexu.csd.datastructure.hangman.IHangman;

/**
 * @author Ahmed Ali
 *
 */

public class Hangman implements IHangman {

	/**
	 * @author Ahmed Ali
	 *
	 */
	String[] word;
	/**
	 * @author Ahmed Ali
	 *
	 */
	String secretWord = new String();
	/**
	 * @author Ahmed Ali
	 *
	 */
	String shownWord = new String();
	/**
	 * @author Ahmed Ali
	 *
	 */
	Integer maxWrongGuesses = null;

	/**
	 * @author Ahmed Ali
	 *
	 */
	/**
	 * @throws IOException some text
	 */
	public void readFile() throws IOException {
		FileReader fr = new FileReader("Words.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = new String();
		int i = 0;
		while ((line = br.readLine()) != null) {
			word[i] = line;
			i++;
			fr.close();
			setDictionary(word);
		}
	}

	@Override
	public void setDictionary(final String[] words) {
		// TODO Auto-generated method stub
		word = new String[words.length];
		int j = 0;
		for (j = 0; j < words.length; j++) {
			word[j] = words[j];
		}
	}

	@Override
	public String selectRandomSecretWord() {
		// TODO Auto-generated method stub
		if (word == null) {
			return null;
		}
		Random rand = new Random();
		int randomNumber = rand.nextInt(word.length) + 0;
		secretWord = word[randomNumber];
		shownWord = secretWord;
		char[] currentWord = shownWord.toCharArray();
		int k = 0;
		for (k = 0; k < secretWord.length(); k++) {
			currentWord[k] = '-';
		}
		shownWord = "";
		for (int c2 = 0; c2 < secretWord.length(); c2++) {
			shownWord = shownWord + currentWord[c2];
		}
		return secretWord;
	}

	@Override
	public String guess(final Character c) throws Exception {
		// TODO Auto-generated method stub
		if (secretWord == null) {
			throw new NullPointerException(null);
		}
		if (secretWord.length() == 0 || secretWord.charAt(0) == ' '
				|| maxWrongGuesses == null) {
			throw new Exception();
		}
		int endGame = 1;
		for (int c1 = 0; c1 < secretWord.length(); c1++) {
			if (shownWord.charAt(c1) == '-') {
				endGame = 0;
			}
		}
		if ((endGame == 1) || (maxWrongGuesses <= 0)) {
			return null;
		}
		int flag = 0;
		if (c == null) {
			return shownWord;
		}
		if (!Character.isLetter(c)) {
			return shownWord;
		}
		String upperSecret = secretWord.toUpperCase();
		char upperLetter = Character.toUpperCase(c);
		char[] cuRrentWord = shownWord.toCharArray();
		int l = 0;
		shownWord = "";
		for (l = 0; l < secretWord.length(); l++) {
			if (upperLetter == upperSecret.charAt(l)) {
				cuRrentWord[l] = secretWord.charAt(l);
				flag = 1;
			}
			shownWord = shownWord + cuRrentWord[l];
		}
		if (flag == 0) {
			maxWrongGuesses--;
		}
		if (maxWrongGuesses <= 0) {
			return null;
		}
		return shownWord;
	}

	@Override
	public void setMaxWrongGuesses(final Integer max) {
		// TODO Auto-generated method stub
		if (max == null) {
			maxWrongGuesses = 1;
		} else {
			maxWrongGuesses = max;
		}
	}
}
