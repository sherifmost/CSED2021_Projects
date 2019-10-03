package eg.edu.alexu.csd.datastructure.hangman.cs05;

public class test {
	
	public static void main(String[] args) throws Exception {
		Hangman hangman = new Hangman();
		String dictionary[] = new String[] { "EGYPT" };
		hangman.setDictionary(dictionary);
		hangman.selectRandomSecretWord();
		hangman.setMaxWrongGuesses(2);
		System.out.println(hangman.guess('y')); // will return --Y--
	}
}