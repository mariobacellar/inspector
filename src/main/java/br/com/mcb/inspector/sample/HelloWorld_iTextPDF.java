package br.com.mcb.inspector.sample;

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * https://www.baeldung.com/java-pdf-creation
 * @author mario.bacellar
 *
 */
public class HelloWorld_iTextPDF {

	public static void main(String[] args) {

		try {

			Document	document= new Document();

			PdfWriter.getInstance(document, new FileOutputStream("iTextImageExample.pdf"));
			document.open();
			
			Image img = Image.getInstance( "logo.jpg" );
			//img.setAbsolutePosition(100f, 550f);
		    //img.scaleAbsolute(200, 200);
			img.scalePercent(40);

			
			
			Font 		font		= FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.RED);
			Chunk		chunk		= new Chunk("Hello World", font);

			Phrase		frase1		= new Phrase("Frase 1");
			Phrase		frase2		= new Phrase("Frase 2");

			Paragraph	parag1		= new Paragraph("A Hello World PDF document.");
			Paragraph	parag2		= new Paragraph("...continue.");
			Paragraph	parag3		= new Paragraph(frase1); parag3.add(frase2);


			PdfPTable 
			table = new PdfPTable(3); // 3 columns.
	        table.setWidthPercentage(100); //Width 100%
	        table.setSpacingBefore(10f); //Space before table
	        table.setSpacingAfter(10f); //Space after table
	 
	        //Set Column widths
	        float[] columnWidths = {1f, 1f, 1f};
	        table.setWidths(columnWidths);
	 
	        PdfPCell 
	        cell1 = new PdfPCell(new Paragraph("Total de Cenarios"));
	        cell1.setBorderColor(BaseColor.BLUE);
	        cell1.setPaddingLeft(10);
	        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 
	        PdfPCell 
	        cell2 = new PdfPCell(new Paragraph("Sucesso"));
	        cell2.setBorderColor(BaseColor.GREEN);
	        cell2.setPaddingLeft(10);
	        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 
	        PdfPCell 
	        cell3 = new PdfPCell(new Paragraph("Erro"));
	        cell3.setBorderColor(BaseColor.RED);
	        cell3.setPaddingLeft(10);
	        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 
	        PdfPCell 
	        cell4 = new PdfPCell(new Paragraph("100"));
	        cell4.setBorderColor(BaseColor.RED);
	        cell4.setPaddingLeft(10);
	        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

	        table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
			
		    document.addAuthor("Lokesh Gupta");
		    document.addCreationDate();
		    document.addCreator("HowToDoInJava.com");
		    document.addTitle("Set Attribute Example");
		    document.addSubject("An example to show how attributes can be added to pdf files.");
		    
		    document.addHeader("Inspector Siebel63", "content");
			document.add(img);
			document.add(parag1);
			document.add(parag2);
			document.add(parag3);
			
			document.add(table);
			document.add(chunk);
			document.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
