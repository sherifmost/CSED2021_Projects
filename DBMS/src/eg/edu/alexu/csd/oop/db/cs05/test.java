package eg.edu.alexu.csd.oop.db.cs05;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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
import org.xml.sax.SAXException;

public class test {

	public static void main(String[] args) {
		MyDatabase mydb = new MyDatabase();
		//mydb.createDatabase("BigTest", false);
		//try {
			//mydb.executeStructureQuery("CREATE TABLE school(firstname varchar,lastname varchar , age int, address varchar, grade int)");
//			mydb.executeUpdateQuery("INSERT INTO school(firstname, lastname, age,address,grade) VALUES ('ahmed', 'ali', 21,'ebrahemeya',150)");
//			mydb.executeUpdateQuery("INSERT INTO school(firstname, lastname, age,address,grade) VALUES ('hazem', 'morsy', 21,'ekbal',200)");
//			mydb.executeUpdateQuery("INSERT INTO school(firstname, lastname, age,address,grade) VALUES ('sherif', 'mohamed', 20,'bokla',250)");
//			mydb.executeUpdateQuery("INSERT INTO school(firstname, lastname, age,address,grade) VALUES ('gamal', 'khalaf', 19,'ekbal',200)");
//			mydb.executeUpdateQuery("INSERT INTO school(firstname, lastname, age,address,grade) VALUES ('tarek', 'ossama', 20,'shatby',225)");
//			mydb.executeUpdateQuery("INSERT INTO school(firstname, lastname, age,address,grade) VALUES ('bassam', 'rageh', 19,'cleopatra',175)");
//			mydb.executeUpdateQuery("INSERT INTO school(firstname, lastname, age,address,grade) VALUES ('mostafa', 'ahmed', 18,'mandara',125)");
//			mydb.executeUpdateQuery("UPDATE school SET age=21 where firstname='tarek'");
//			mydb.executeUpdateQuery("delete from school where age<19");
//			Object[][] data =mydb.executeQuery("select * from school where age =21");
//			System.out.println(data.length);
//			System.out.println(data[0].length);
//			for (int  i=0;i<data.length;i++) {
//				for (int j=0;j<data[0].length;j++) {
//					System.out.println(data[i][j]);
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		//CreateTableXMLFile("bigtest", "school");
		AppendXML("bigtest" + System.getProperty("file.separator") + "school.xml");
	}
	public static boolean CreateTableXMLFile(String dbname, String tbname) {
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
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, tbname+".dtd");
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
	public static void AppendXML(String xmlFilePath) {
		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(xmlFilePath);

			// root element
			Element root = document.getDocumentElement();

			// employee element
			Element employee = document.createElement("row");

			root.appendChild(employee);

			// set an attribute to staff element


			// you can also use staff.setAttribute("id", "1") for this

			// firstname element
			Element firstName = document.createElement("firstname");
			Attr attr = document.createAttribute("type");
			attr.setValue("varchar");
			firstName.setAttributeNode(attr);
			firstName.appendChild(document.createTextNode("Ahmed"));
			employee.appendChild(firstName);

			// lastname element
			Element lastname = document.createElement("lastname");
			Attr attr1 = document.createAttribute("type");
			attr1.setValue("varchar");
			lastname.setAttributeNode(attr1);
			lastname.appendChild(document.createTextNode("Ali"));
			employee.appendChild(lastname);

			// email element
			Element email = document.createElement("email");
			Attr attr2 = document.createAttribute("type");
			attr2.setValue("varchar");
			email.setAttributeNode(attr2);
			email.appendChild(document.createTextNode("ahm@example.org"));
			employee.appendChild(email);

			// department elements
			Element department = document.createElement("department");
			department.appendChild(document.createTextNode("CSED"));
			Attr attr3 = document.createAttribute("type");
			attr3.setValue("varchar");
			department.setAttributeNode(attr3);
			employee.appendChild(department);

			// create the xml file
			// transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));

			// If you use
			// StreamResult result = new StreamResult(System.out);
			// the output will be pushed to the standard output ...
			// You can use that for debugging
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "school.dtd");
			transformer.transform(domSource, streamResult);

			System.out.println("Done creating XML File");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
