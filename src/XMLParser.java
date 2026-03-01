import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLParser extends DatabaseHandler implements Export{
	
	/**
	 * @author Ali Umut Karaca / MonteCarlo
	 */
	
	public XMLParser(String fileName) {
		super(fileName);
	}
	
	private File file;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	
	@Override
	public void BuildExport() {
		try {
			String [][] data = loadDataFromFile(fileName);
			System.out.println(ApplicationParentFolderHandler.getExecutionDirectory()+"\\Items.xml");
			file = new File(ApplicationParentFolderHandler.getExecutionDirectory()+"\\Items.xml");
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			
			Element rootElement = doc.createElement("Inventory");
	        doc.appendChild(rootElement);
	        
	        for (String[] row : data) {
	        	if(row[0].equals("ID")) {
	        		
	        	}
	        	else {
		            Element item = doc.createElement("Item");
		            rootElement.appendChild(item);

		            for (int i = 0; i < row.length; i++) {
		                Element field = doc.createElement(data[0][i]);
		                field.appendChild(doc.createTextNode(row[i]));
		                item.appendChild(field);
		            }
	        	}
	        }
	        
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(file);

	        transformer.transform(source, result);
	        
	        
		}
		catch(Exception e) {
			
		}
	}
	
	public void addNode() {
		//TODO Add a node to an XML document
	}
	
	public void removeNode() {
		//TODO Add a node to an XML document
	}
	
}
