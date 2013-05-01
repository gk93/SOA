
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.*;
import javax.xml.soap.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Servlet implementation class getStuInfo
 */
@WebServlet("/getStuInfo")
public class getStuInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getStuInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
	    
	    File file=new File("F:/GitHub/SOA/A3/"+id+".xml");
	    if(file.exists()){
	    	try{
	    		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
	    		SOAPBody body=soapMessage.getSOAPBody();
	    		SOAPFactory s = SOAPFactory.newInstance();
	    
	    		Document xmlDoc;
	    		DocumentBuilderFactory builderFactory = DocumentBuilderFactory 
	    		        .newInstance(); 
	    		DocumentBuilder builder=builderFactory.newDocumentBuilder(); 
	    		xmlDoc = builder.parse(file);
	    		
	    		//namespance
	    		body.addNamespaceDeclaration("s", "http://jw.nju.edu.cn/schema");
	    		body.addNamespaceDeclaration("p", "http://www.nju.edu.cn/schema");
	    		body.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	    		body.addAttribute(s.createName("xsi:schemaLocation"), "http://jw.nju.edu.cn/schema Student.xsd");
	    		
	    		//body
	    		Node Dstudent=xmlDoc.getChildNodes().item(0);
	    		SOAPElement student=body.addChildElement("student", "s");
	    		student.addChildElement("studentID","s").setValue(Dstudent.getChildNodes().item(1).getTextContent());
	    		student.addChildElement("enterYear","s").setValue(Dstudent.getChildNodes().item(3).getTextContent());
	    		student.addChildElement("nowYear","s").setValue(Dstudent.getChildNodes().item(5).getTextContent());
	    		student.addChildElement("classNum","s").setValue(Dstudent.getChildNodes().item(7).getTextContent());
	    		
	    		Node DpersonInfo=Dstudent.getChildNodes().item(9);
	    		SOAPElement personInfo=student.addChildElement("personInfo", "p");
	    		personInfo.addChildElement("name", "p").setValue(DpersonInfo.getChildNodes().item(1).getTextContent());
	    		personInfo.addChildElement("gender", "p").setValue(DpersonInfo.getChildNodes().item(3).getTextContent());
	    		personInfo.addChildElement("birthday", "p").setValue(DpersonInfo.getChildNodes().item(5).getTextContent());
	    		personInfo.addChildElement("identity", "p").setValue(DpersonInfo.getChildNodes().item(7).getTextContent());
	    		personInfo.addChildElement("mobilephone", "p").setValue(DpersonInfo.getChildNodes().item(9).getTextContent());
	    		personInfo.addChildElement("email", "p").setValue(DpersonInfo.getChildNodes().item(11).getTextContent());
	    		
	    		Node Ddep=DpersonInfo.getChildNodes().item(13);
	    		SOAPElement dep=personInfo.addChildElement("department", "p");
	    		dep.addChildElement("name", "p").setValue(Ddep.getChildNodes().item(1).getTextContent());
	    		dep.addChildElement("tpye", "p").setValue(Ddep.getChildNodes().item(3).getTextContent());
	    		dep.addChildElement("address", "p").setValue(Ddep.getChildNodes().item(5).getTextContent());
	    		dep.addChildElement("phone", "p").setValue(Ddep.getChildNodes().item(7).getTextContent());
	    		dep.addChildElement("homepage", "p").setValue(Ddep.getChildNodes().item(9).getTextContent());
	    		
	    		
	    		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    		soapMessage.writeTo(baos);
	    		String soap=baos.toString();
	    		
	    		out.print(soap);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }else{
	    	try{
	    		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
	    		SOAPBody body=soapMessage.getSOAPBody();
	    		SOAPFactory s = SOAPFactory.newInstance();
	    		body.addFault(s.createName("F"), "id illegal or ont exist");
	    		
	    		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    		soapMessage.writeTo(baos);
	    		String soap=baos.toString();
	    		
	    		out.print(soap);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    }
	}

}
