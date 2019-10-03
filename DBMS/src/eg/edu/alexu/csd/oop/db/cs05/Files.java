package eg.edu.alexu.csd.oop.db.cs05;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Files {

	// creating table as xml file to store its data returning true if created false
	// otherwise
	// the xml created using DOM xml parser
	public boolean CreateTableXMLFile(String dbname, String tbname) {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			// root element
			Element root = document.createElement(tbname);
			document.appendChild(root);
			// create the xml file
			// transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(
					new File(dbname + System.getProperty("file.separator") + tbname + ".xml"));
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, tbname + ".dtd");
			transformer.transform(domSource, streamResult);
			return true;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return false;
	}

	// using DOM xml parser to append data to xml file
	public boolean AppendToXMLFile(String xmlFilePath, Map<String, String> data, String tbname,
			Map<String, String> attributes) {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new File(xmlFilePath));
			// get root element
			Element root = document.getDocumentElement();
			// looping through map of columns and their values based on insert sql statement
			// and ading them to the xml file
			Element row = document.createElement("row");
			root.appendChild(row);
			for (Map.Entry<String, String> entry : data.entrySet()) {
				String colName = entry.getKey().toLowerCase();
				Object colValue = entry.getValue();
				if (colValue == null) {
					for (Map.Entry<String, String> att : attributes.entrySet()) {
						if (colName.equalsIgnoreCase(att.getKey())) {
							if (att.getValue().equals("int")) {
								colValue = 0;
							} else if (att.getValue().equals("varchar")) {
								colValue = "";
							}
						}
					}
				}
				Element elementData = document.createElement(colName);
				for (Map.Entry<String, String> att : attributes.entrySet()) {
					if (colName.equalsIgnoreCase(att.getKey().toString())) {
						Attr attr = document.createAttribute("type");
						attr.setValue(att.getValue().toString());
						elementData.setAttributeNode(attr);
					}
				}
				elementData.appendChild(document.createTextNode(colValue.toString()));
				row.appendChild(elementData);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, tbname + ".dtd");
			transformer.transform(domSource, streamResult);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// using DOM xml parser to delete a row according to specific condition
	public int DeleteRowFromXML(Map<String, String> data, String op, String xmlFilePath, String tbname) {
		try {
			int rowsDeletedCounter = 0;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFilePath);
			NodeList nList = doc.getElementsByTagName("row");
			for (int i = 0; i < nList.getLength(); i++) {
				Node row = nList.item(i);
				if (!data.isEmpty()) {
					NodeList allCols = row.getChildNodes();
					for (Map.Entry<String, String> entry : data.entrySet()) {
						for (int l = 0; l < allCols.getLength(); l++) {
							Node col = allCols.item(l);
							if (col.getNodeName().equalsIgnoreCase(entry.getKey().toString())) {
								String colValue = col.getTextContent();
								if (op.equals("=")) {
									if (colValue.equalsIgnoreCase(entry.getValue().toString())) {
										row.getParentNode().removeChild(row);
										rowsDeletedCounter++;
										i--;
										break;
									}
								} else {
									if (!entry.getValue().toString().contains("'")) {
										if (op.equals("<")) {
											if (Integer.parseInt(colValue) < Integer
													.parseInt(entry.getValue().toString())) {
												row.getParentNode().removeChild(row);
												rowsDeletedCounter++;
												i--;
												break;
											}
										} else if (op.equals(">")) {
											if (Integer.parseInt(colValue) > Integer
													.parseInt(entry.getValue().toString())) {
												row.getParentNode().removeChild(row);
												rowsDeletedCounter++;
												i--;
												break;
											}
										}
									}
								}
							}
						}
					}
				} else {
					row.getParentNode().removeChild(row);
					rowsDeletedCounter++;
					i--;
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, tbname + ".dtd");
			transformer.transform(domSource, streamResult);
			return rowsDeletedCounter;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	// updating rows in table
	// the map data is the columns that will be updated based on the condition
	// mentioned in the map conditionData
	public int UpdateRowInXML(String xmlFilePath, Map<String, String> data, Map<String, String> conditionData,
			String op, String tbname) throws SQLException {
		try {
			int rowsUpdatedCounter = 0;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = null;
			try {
				doc = docBuilder.parse(xmlFilePath);

			} catch (Exception e) {

				throw new SQLException();

			}

			NodeList allRows = doc.getElementsByTagName("row");

			for (int i = 0; i < allRows.getLength(); i++) {
				boolean updated = false;
				NodeList allCols = allRows.item(i).getChildNodes();
				for (int j = 0; j < allCols.getLength(); j++) {
					if (!conditionData.isEmpty()) {
						for (Map.Entry<String, String> conData : conditionData.entrySet()) {
							Node col = allCols.item(j);
							if (conData.getKey().equalsIgnoreCase(col.getNodeName())) {
								if (op.equals("=")) {
									if (conData.getValue().equalsIgnoreCase(col.getTextContent())) {
										for (int l = 0; l < allCols.getLength(); l++) {
											for (Map.Entry<String, String> entry : data.entrySet()) {
												Node colToBeUpdated = allCols.item(l);
												if (entry.getKey().equalsIgnoreCase(colToBeUpdated.getNodeName())) {
													colToBeUpdated.setTextContent(entry.getValue().toString());
													updated = true;
												}
											}
										}
									}
								} else if (op.equals(">")) {
									if (Integer.parseInt(col.getTextContent()) > Integer.parseInt(conData.getValue())) {
										for (int l = 0; l < allCols.getLength(); l++) {
											for (Map.Entry<String, String> entry : data.entrySet()) {
												Node colToBeUpdated = allCols.item(l);
												if (entry.getKey().equals(colToBeUpdated.getNodeName())) {
													colToBeUpdated.setTextContent(entry.getValue().toString());
													updated = true;
												}
											}
										}
									}
								} else if (op.equals("<")) {
									if (Integer.parseInt(col.getTextContent()) < Integer.parseInt(conData.getValue())) {
										for (int l = 0; l < allCols.getLength(); l++) {
											for (Map.Entry<String, String> entry : data.entrySet()) {
												Node colToBeUpdated = allCols.item(l);
												if (entry.getKey().equals(colToBeUpdated.getNodeName())) {
													colToBeUpdated.setTextContent(entry.getValue().toString());
													updated = true;
												}
											}
										}
									}
								}
							}
						}
					} else {
						for (int l = 0; l < allCols.getLength(); l++) {
							for (Map.Entry<String, String> entry : data.entrySet()) {
								Node colToBeUpdated = allCols.item(l);
								if (entry.getKey().equalsIgnoreCase(colToBeUpdated.getNodeName())) {
									colToBeUpdated.setTextContent(entry.getValue().toString());
									updated = true;
								}
							}
						}
					}
				}
				if (updated)
					rowsUpdatedCounter++;
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlFilePath));
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, tbname + ".dtd");
			transformer.transform(source, result);
			return rowsUpdatedCounter;
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		return 0;
	}

	// select from xml file usng DOM xml parser
	// selecting all cols in the table with no condition
	public Object[][] SelectAllFromXML(String xmlFilePath) {
		Object[][] data;
		try {
			File fXmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList allRows = doc.getElementsByTagName("row");
			data = new Object[allRows.getLength()][allRows.item(0).getChildNodes().getLength()];
			for (int i = 0; i < allRows.getLength(); i++) {
				Node row = allRows.item(i);
				NodeList allCols = row.getChildNodes();
				for (int j = 0; j < allCols.getLength(); j++) {
					if (allCols.item(j) instanceof Element == true) {
						Node coln = allCols.item(j);
						if (coln.getTextContent().contains("'")) {
							data[i][j] = coln.getTextContent();
						} else {
							data[i][j] = Integer.parseInt(coln.getTextContent());
						}
					}
				}
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// selecting specific columns from the table with no condition
	public Object[][] SelectColsFromXML(String xmlFilePath, LinkedList<String> cols) {
		Object[][] data;
		try {
			File fXmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList allRows = doc.getElementsByTagName("row");
			data = new Object[allRows.getLength()][cols.size()];
			for (int i = 0; i < allRows.getLength(); i++) {
				Node row = allRows.item(i);
				NodeList allCols = row.getChildNodes();
				for (int j = 0; j < allCols.getLength(); j++) {
					if (allCols.item(j) instanceof Element == true) {
						Node coln = allCols.item(j);
						for (int k = 0; k < cols.size(); k++) {
							if (coln.getNodeName().equalsIgnoreCase(cols.get(k))) {
								if (coln.getTextContent().contains("'")) {
									data[i][k] = coln.getTextContent();
								} else {
									data[i][k] = Integer.parseInt(coln.getTextContent());
								}
							}
						}
					}
				}
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// selecting all columns in the table based on condition
	public Object[][] SelectAllWhereFromXML(String xmlFilePath, Map<String, String> conditionData, String op) {
		Object[][] data;
		try {
			File fXmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList allRows = doc.getElementsByTagName("row");
			data = new Object[WantedRowsCount(xmlFilePath, conditionData, op)][allRows.item(0).getChildNodes()
					.getLength()];
			boolean found = false;
			int k = 0;
			for (int i = 0; i < allRows.getLength(); i++) {
				found = false;
				Node row = allRows.item(i);
				NodeList allCols = row.getChildNodes();
				for (int j = 0; j < allCols.getLength(); j++) {
					if (allCols.item(j) instanceof Element == true) {
						Node col = allCols.item(j);
						for (Map.Entry<String, String> conData : conditionData.entrySet()) {
							if (conData.getKey().equalsIgnoreCase(col.getNodeName())) {
								if (op.equals("=")) {
									if (conData.getValue().equalsIgnoreCase(col.getTextContent())) {
										found = true;
									}
								} else if (op.equals(">")) {
									if (Integer.parseInt(col.getTextContent()) > Integer.parseInt(conData.getValue())) {
										found = true;
									}
								} else if (op.equals("<")) {
									if (Integer.parseInt(col.getTextContent()) < Integer.parseInt(conData.getValue())) {
										found = true;
									}
								}
							}
						}
						if (found) {
							for (int l = 0; l < allRows.item(0).getChildNodes().getLength(); l++) {
								if (allCols.item(l) instanceof Element == true) {
									Node coln = allCols.item(l);
									if (coln.getTextContent().contains("'")) {
										data[k][l] = coln.getTextContent();
									} else {
										data[k][l] = Integer.parseInt(coln.getTextContent());
									}
								}
							}
						}
					}
				}
				if (found) {
					k++;
				}
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// selecting specific columns from the table based on condition
	public Object[][] SelectColsWhereFromXML(String xmlFilePath, Map<String, String> conditionData,
			LinkedList<String> cols, String op) {
		Object[][] data;
		try {
			File fXmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList allRows = doc.getElementsByTagName("row");
			data = new Object[WantedRowsCount(xmlFilePath, conditionData, op)][cols.size()];
			boolean found = false;
			for (int i = 0; i < allRows.getLength(); i++) {
				found = false;
				Node row = allRows.item(i);
				NodeList allCols = row.getChildNodes();
				for (int j = 0; j < allCols.getLength(); j++) {
					if (allCols.item(j) instanceof Element == true) {
						Node col = allCols.item(j);
						for (Map.Entry<String, String> conData : conditionData.entrySet()) {
							if (conData.getKey().equalsIgnoreCase(col.getNodeName())) {
								if (op.equals("=")) {
									if (conData.getValue().equalsIgnoreCase(col.getTextContent())) {
										found = true;
									}
								} else if (op.equals(">")) {
									if (Integer.parseInt(col.getTextContent()) > Integer.parseInt(conData.getValue())) {
										found = true;
									}
								} else if (op.equals("<")) {
									if (Integer.parseInt(col.getTextContent()) < Integer.parseInt(conData.getValue())) {
										found = true;
									}
								}
							}
						}
						if (found) {
							if (i < data.length) {
								for (int l = 0; l < allCols.getLength(); l++) {
									if (allCols.item(l) instanceof Element == true) {
										Node coln = allCols.item(l);
										for (int k = 0; k < cols.size(); k++) {
											if (coln.getNodeName().equalsIgnoreCase(cols.get(k))) {
												if (coln.getTextContent().contains("'")) {
													data[i][k] = coln.getTextContent();
												} else {
													data[i][k] = Integer.parseInt(coln.getTextContent());
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// function to get the number of rows that will be selected based on condition
	// in select statement
	private int WantedRowsCount(String xmlFilePath, Map<String, String> conditionData, String op) {
		int counter = 0;
		try {
			File fXmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList allRows = doc.getElementsByTagName("row");
			for (int i = 0; i < allRows.getLength(); i++) {
				Node row = allRows.item(i);
				NodeList allCols = row.getChildNodes();
				for (Map.Entry<String, String> conData : conditionData.entrySet()) {
					for (int j = 0; j < allCols.getLength(); j++) {
						if (conData.getKey().equalsIgnoreCase(allCols.item(j).getNodeName())) {
							if (op.equals("=")) {
								if (conData.getValue().equalsIgnoreCase(allCols.item(j).getTextContent())) {
									counter++;
								}
							} else if (op.equals(">")) {
								if (Integer.parseInt(allCols.item(j).getTextContent()) > Integer
										.parseInt(conData.getValue())) {
									counter++;
								}
							} else if (op.equals("<")) {
								if (Integer.parseInt(allCols.item(j).getTextContent()) < Integer
										.parseInt(conData.getValue())) {
									counter++;
								}
							}
						}
					}
				}
			}
			return counter;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counter;
	}

	// deleting database folder returns true if deleted false otherwise
	public boolean Delete(File f) {
		if (f.exists()) {
			if (f.isDirectory()) {
				File[] finside = f.listFiles();
				for (File fdelete : finside) {
					if (fdelete.isDirectory()) {
						Delete(fdelete);
					} else {
						fdelete.delete();
					}
				}
				f.delete();
			} else {
				f.delete();
			}
		} else {
			return false;
		}
		return true;
	}

}
