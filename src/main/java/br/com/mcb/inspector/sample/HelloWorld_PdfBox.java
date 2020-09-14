package br.com.mcb.inspector.sample;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class HelloWorld_PdfBox {

	public static void main(String[] args) {
		try {


			PDDocument document = new PDDocument();
			PDPage		page = new PDPage();
			document.addPage(page);

			PDImageXObject image = PDImageXObject.createFromFile("logo.jpg", document);
			image.setHeight(100);
			image.setWidth(100);
			PDPageContentStream  contentStream = new PDPageContentStream(document, page);
			
			contentStream.setFont(PDType1Font.COURIER, 12);
			contentStream.beginText();
			contentStream.showText("Hello World");
			contentStream.endText();
			contentStream.drawImage(image, 0, 0);
			contentStream.close();
			
			
			document.save("pdfBoxHelloWorld.pdf");
			document.close();
			
		


			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
