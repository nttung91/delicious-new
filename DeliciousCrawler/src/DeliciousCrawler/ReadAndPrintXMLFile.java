/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import model.dao.LinkDAO;
import model.pojo.Link;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class ReadAndPrintXMLFile{

    public static void main (String argv []){
    try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            System.out.println("Reading");
                   
            Document doc = docBuilder.parse (new File("D:/save.xml"));
            System.out.println("Read Complete");
            // normalize text representation
//            doc.getDocumentElement ().normalize ();
//            System.out.println ("Root element of the doc is " + 
//                 doc.getDocumentElement().getNodeName());


            NodeList listOfDocument = doc.getElementsByTagName("url");
            int totalPersons = listOfDocument.getLength();
            System.out.println("Total no of document : " + totalPersons);
int i =0;
            for(int s=0; s<listOfDocument.getLength() ; s++){


                Node document = listOfDocument.item(s);
                if(document.getNodeType() == Node.ELEMENT_NODE){


                    Element Edocument = (Element)document;

                    //-------
                    NodeList urls = Edocument.getElementsByTagName("name");
                    Element url = (Element)urls.item(0);

                    NodeList textFNList = url.getChildNodes();
                    String kq = ((Node)textFNList.item(0)).getNodeValue().trim();
                    LinkDAO dao = new LinkDAO();
                    int res = dao.processDuplicate(kq);
                    if (res==-1){
                        System.out.println("Save "+i);
                        Link link = new Link(LinkDAO.nextIndex());
                        link.setUrl(kq);
                        dao.saveOrUpdateObject(link);
                        i++;
                    }
                    else System.out.println("Trung " +res);
                   

                    

                    //------


                }//end of if clause

            }//end of for loop with s var
             System.out.println("Total save "+i);

        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
        //System.exit (0);

    }//end of main


}