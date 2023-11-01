package Functions;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;

public class CreatePDF {

    int i = 0;
    public static CreatePDF instance;

    public static CreatePDF getInstance(){
        if(instance==null) instance = new CreatePDF();
        return instance;
    }

    private CreatePDF(){

    }

    public void createPDF() {
        System.out.println("Creating pdf");
        try{
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document,page);
            PDImageXObject grafico1 = PDImageXObject.createFromFile("/home/miquel/Documentos/appSergi/grafico1.png",document);
            PDImageXObject grafico2 = PDImageXObject.createFromFile("/home/miquel/Documentos/appSergi/grafico2.png",document);

            content.drawImage(grafico1, 10,60, 320/2,225/2);
            content.drawImage(grafico2, 250,60, 320/2,225/2);
//            content.beginText();
//            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD),12);
//            content.setLeading(16);//espai entre linies
//            content.newLineAtOffset(20,page.getMediaBox().getHeight()-50);
//            content.showText("Tu Madre ushdfiushaduifhasduifhsuiohfioashfiousdhguiohdfuisghsduiofghsdfuiohgiousdfhgiosdfuhgiousdhfguisdfhguiosdfhguiosdfhguiosdfhguiohsdfioughsdfuioghsdfio" + i);
//            content.endText();
//
//            content.close();
//            page = new PDPage(PDRectangle.A4);
//            document.addPage(page);
//            content = new PDPageContentStream(document,page);
//
//            PDImageXObject image1 = PDImageXObject.createFromFile("/home/miquel/Documentos/appSergi/grafico1.png",document);
//            content.drawImage(image1,25,25, 320,225);
//            content.close();
//
            content.close();
            document.save("/home/miquel/Documentos/appSergi/pruebapdf2." + i + ".pdf");
        }
        catch (Exception e){
            System.out.printf(e.getMessage());
        }
        ++i;


    }
}
