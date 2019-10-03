package eg.edu.alexu.csd.filestructure.btree.cs_5_20;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eg.edu.alexu.csd.filestructure.btree.ISearchEngine;
import eg.edu.alexu.csd.filestructure.btree.ISearchResult;

public class MySearchEngine implements ISearchEngine {

	int t = 50;

	public MySearchEngine(int t) {
		this.t = t;
	}

	// B-tree to apply insertion and deletion and searching
	private MyBtree<String, List<ISearchResult>> BTree = new MyBtree<>(t);

	@Override
	public void indexWebPage(String filePath) {// content is an array contains all the words in the document
		// using DOM XML parser, we can get the documents in the files and storing the
		// words in the array
		if (filePath == null || filePath.isEmpty() || filePath.replaceAll(" ", "") == "") {
			throw new RuntimeErrorException(null);
		}
		String[] content;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new FileInputStream(new File(filePath)));
			NodeList allDocs = doc.getElementsByTagName("doc");
			for (int i = 0; i < allDocs.getLength(); i++) {
				Element element = (Element) allDocs.item(i);
				content = element.getTextContent().toLowerCase().replaceAll("\n\n", " ").replaceAll("\n", " ")
						.replaceFirst(" ", "").split("\\s");

				indexDocs(content, element.getAttribute("id"));
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeErrorException(null);
		}
	}

	private void indexDocs(String[] content, String DocID) {
		boolean foundWord = false;
		boolean foundDoc = false;
		for (int j = 0; j < content.length; j++) {
			foundDoc = false;
			if (BTree.getRoot()!=null)
				foundWord = BTree.contains(content[j], BTree.getRoot());// check if word is stored in the B-tree or not
			if (foundWord) {// word is stored
				List<ISearchResult> list = BTree.search(content[j]);
				for (int i = 0; i < list.size(); i++) {
					if (DocID.equals(list.get(i).getId())) {// check if document id is in the list or not, if found do
															// the following
						ISearchResult res = list.get(i);
						int oldRank = res.getRank();
						int newRank = oldRank + 1;
						res.setRank(newRank);
						foundDoc = true;
						break;
					}
				}
				if (!foundDoc) {// if document id not found
					list.add(new MySearchResult(DocID, 1));
				}
			} else {// word is inserted for the first time
				List<ISearchResult> newList = new ArrayList<>();
				newList.add(new MySearchResult(DocID, 1));
				BTree.insert(content[j], newList);
			}
		}
	}

	@Override
	public void indexDirectory(String directoryPath) {
		if (directoryPath == null || directoryPath.isEmpty() || directoryPath.replaceAll(" ", "") == "") {
			throw new RuntimeErrorException(null);
		}
		File directory = new File(directoryPath);

		// Get all files from a directory.
		File[] fList = directory.listFiles();
		if (fList != null)
			for (File file : fList) {
				if (file.isFile()) {
					indexWebPage(file.getAbsolutePath());
				} else if (file.isDirectory()) {
					indexDirectory(file.getAbsolutePath());
				}
			}
	}

	@Override
	public void deleteWebPage(String filePath) {// using DOM XML parser to get the documents in file path and storing
												// the words in an array to apply deletion process
		if (filePath == null || filePath.isEmpty() || filePath.replaceAll(" ", "") == "") {
			throw new RuntimeErrorException(null);
		}
		String[] content;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new FileInputStream(new File(filePath)));
			NodeList allDocs = doc.getElementsByTagName("doc");
			for (int i = 0; i < allDocs.getLength(); i++) {
				Element element = (Element) allDocs.item(i);
				content = element.getTextContent().toLowerCase().replaceAll("\n\n", " ").replaceAll("\n", " ")
						.split(" ");

				deleteDocs(content, element.getAttribute("id"));
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeErrorException(null);
		}
	}

	private void deleteDocs(String[] content, String DocID) {
		for (int i = 0; i < content.length; i++) {
			if (content[i].length() != 0) {
				String word = content[i].toLowerCase();
				if (BTree.getRoot() == null || BTree.search(word) == null) { // this word appeared for first time
					continue;
				} else { // word is already present in btree (inserted before)
					List<ISearchResult> list = BTree.search(word);
					for (int j = 0; j < list.size(); j++) {
						if (DocID.equals(list.get(j).getId())) {
							list.remove(j);
							if (list.size() == 0) {// if an empty list is created after deleting the document
								BTree.delete(word);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<ISearchResult> searchByWordWithRanking(String word) {
		if (word == null) {
			throw new RuntimeErrorException(null);
		}
		if (word.replaceAll("\\s+", "") == "" || BTree.contains(word, BTree.getRoot()) == false) {
			return new ArrayList<ISearchResult>();
		}
		word = word.replaceAll("\\s+", "");
		return BTree.search(word.toLowerCase());
	}

	@Override
	public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
		if (sentence == null || sentence.isEmpty()) {
			throw new RuntimeErrorException(null);
		}
		if (sentence.replaceAll("\\s+", " ") == " ") {
			return new ArrayList<ISearchResult>();
		}
		List<ISearchResult> commonDocs = new ArrayList<>();
		List<List<ISearchResult>> wordsResults = new ArrayList<>();
		sentence = sentence.replaceAll("\\s+", " ");
		if (sentence.charAt(0) == ' ')
			sentence = sentence.replaceFirst(" ", "");
		String[] words = sentence.split("\\s");
		int foundID = 0;// counter of id if found in all list search results then we add it to the final
						// list(commonDocs)
		for (int i = 0; i < words.length; i++) {
			if (BTree.getRoot() == null || BTree.search(words[i]) == null) { // this word appeared for first time
				continue;
			}
			List<ISearchResult> res = new ArrayList<>();
			res = searchByWordWithRanking(words[i]);
			if (res.size() != 0)
				wordsResults.add(res);
		}
		if (wordsResults.size() == 0)
			return new ArrayList<ISearchResult>();
		// using the smallest list to decrease the time as much as we can
		int SmallestListIndex = getSmallestList(wordsResults);
		List<ISearchResult> comparingList = wordsResults.get(SmallestListIndex);
		wordsResults.remove(SmallestListIndex);
		for (int i = 0; i < comparingList.size(); i++) {
			foundID = 0;
			for (int j = 0; j < wordsResults.size(); j++) {
				List<ISearchResult> temp = new ArrayList<>();
				temp = wordsResults.get(j);
				for (int k = 0; k < temp.size(); k++) {
					if (comparingList.get(i).getId().equals(temp.get(k).getId())) {
						if (temp.get(k).getRank() < comparingList.get(i).getRank())
							comparingList.get(i).setRank(temp.get(k).getRank());
						foundID++;
						break;
					}
				}
			}
			if (foundID == wordsResults.size())
				commonDocs.add(comparingList.get(i));
		}
		return commonDocs;
	}

	private int getSmallestList(List<List<ISearchResult>> results) {
		if (results.size() == 0)
			return 0;
		int minSize = results.get(0).size();
		int index = 0;
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).size() < minSize) {
				minSize = results.get(i).size();
				index = i;
			}
		}
		return index;
	}

}
