package edu.nahuel;
import edu.nahuel.soporte.*;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
/**
 * de leer y escribir xml
 * 
 *
 */
public class XMLPrinter 
{
	String notes;
	String newNotes;
	String filesXML;
	public XMLPrinter(){
		notes="notes.xml";
		newNotes="newNotes.xml";
		filesXML="xml\\files.xml";
	}
	public void escribirArboles(Arbol<String> arbol){
		try{
			Document doc=crearDocumento("Descarga");
			Element files=doc.createElement("Files");
			escribirArbol(doc,files,arbol);
			doc.getDocumentElement().appendChild(files);
			Source source=new DOMSource(doc);
			Result res= new StreamResult(new File(filesXML));
			
			Transformer trans=TransformerFactory.newInstance().newTransformer();
			trans.transform(source,res);
			System.out.println("Se escribio joya");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	private void escribirArbol(Document doc,Element e,Arbol<String> arbol) throws Exception{
		Iterator<Nodo<String>> itNodos=arbol.nodosIterator();
		Element as=doc.createElement("archivos");
		while(itNodos.hasNext()){
			Nodo<String> n =itNodos.next();
			Element eh =doc.createElement("archivo");
			Text textE=doc.createTextNode(n.getValor());
			eh.appendChild(textE);
			as.appendChild(eh);
		}
		e.appendChild(as);
		Iterator<Arbol<String>> itArboles=arbol.arbolIterator();
		Element ds=doc.createElement("directorios");
		while(itArboles.hasNext()){
			Arbol<String> a=itArboles.next();
			Element d = doc.createElement("directorio");
			Element n = doc.createElement("nombre");
			Text textNombre=doc.createTextNode(a.getNombre());
			n.appendChild(textNombre);
			d.appendChild(n);
			escribirArbol(doc,d,a);
			ds.appendChild(d);
		}
		e.appendChild(ds);	
	}
	private Document crearDocumento(String nombre) throws Exception{
		DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder(); 
		DOMImplementation imp=builder.getDOMImplementation();
		Document doc=imp.createDocument(null,nombre,null); 
		doc.setXmlVersion("1.0");
		return doc;
	}

	public Arbol<String> leerArboles(){
		Arbol<String> lectura=new Arbol<>("Files");
		try{
			Document doc = crearDocumentoLeer(filesXML);
			NodeList listaDirectorios=doc.getElementsByTagName("directorio");
			for(int i=0;i<listaDirectorios.getLength();i++){
				Node nHijo=listaDirectorios.item(i);
				if(nHijo.getNodeType()==Node.ELEMENT_NODE){
					Element eHijo=(Element)nHijo;
					NodeList valores=eHijo.getChildNodes();
					for(int j=0;j<valores.getLength();j++){
						Node atr=valores.item(j);
						if(atr.getNodeType()==Node.ELEMENT_NODE){
							if(atr.getNodeName().equals("nombre")){
								lectura.addArbol(atr.getTextContent());
							}
						}
					}
				}
			}
			NodeList listaArchivos=doc.getElementsByTagName("archivo");
			for(int i=0;i<listaArchivos.getLength();i++){
				Node nHijo=listaArchivos.item(i);
				if(nHijo.getNodeType()==Node.ELEMENT_NODE){
					Element eHijo=(Element)nHijo;
					lectura.addNodo(eHijo.getTextContent());
				}
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return lectura;
	}
	private Document crearDocumentoLeer(String path) throws Exception{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder= factory.newDocumentBuilder();
		Document doc= builder.parse(new File(path));
		return doc;
	}
	public void escribirNotas(){
		try{
			DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder(); 
			DOMImplementation imp=builder.getDOMImplementation();
			Document doc=imp.createDocument(null,"buzon",null); 
			doc.setXmlVersion("1.0");
			//creador
			Element notas=doc.createElement("notas");
			Element nota=doc.createElement("nota");
			Element to=doc.createElement("to");
			Element from=doc.createElement("from");
			Element heading=doc.createElement("heading");
			Element body=doc.createElement("body");
			//Escritor
			Text textTo=doc.createTextNode("bilardo");
			to.appendChild(textTo);
			Text textFrom=doc.createTextNode("Baracus");
			from.appendChild(textFrom);
			Text textHeading=doc.createTextNode("Seleccion");
			heading.appendChild(textHeading);
			Text textBody=doc.createTextNode("Ponelo al diego");
			body.appendChild(textBody);
			//Uniendo todo
			nota.appendChild(to);
			nota.appendChild(from);
			nota.appendChild(heading);
			nota.appendChild(body);
			notas.appendChild(nota);
			
			doc.getDocumentElement().appendChild(notas);
			
			Source source=new DOMSource(doc);
			Result res= new StreamResult(new File(newNotes));
			
			Transformer trans=TransformerFactory.newInstance().newTransformer();
			trans.transform(source,res);
			System.out.println("Se escribio joya");
		}
		catch(ParserConfigurationException e){
			System.out.println(e.getMessage());
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void leerNotas(){
		try{
			
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder= factory.newDocumentBuilder();
			Document doc= builder.parse(new File(notes));
			NodeList listaNotas=doc.getElementsByTagName("note");
			for(int i=0;i<listaNotas.getLength();i++){
				Node n=listaNotas.item(i);
				if(n.getNodeType()==Node.ELEMENT_NODE){
					Element e=(Element)n;
					NodeList hijos=e.getChildNodes();
					for(int j=0;j<hijos.getLength();j++){
						Node hijo=hijos.item(j);
						if(hijo.getNodeType()==Node.ELEMENT_NODE){
							Element eHijo=(Element)hijo;
							System.out.println("Propiedad: "+eHijo.getNodeName()+", value:"+eHijo.getTextContent());
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
