package Functions;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.awt.*;

import org.json.*;
import java.io.IOException;
import java.util.ArrayList;

public class CreatePDF {

    int i = 0;
    public static CreatePDF instance;

    public static CreatePDF getInstance(){
        if(instance==null) instance = new CreatePDF();
        return instance;
    }

    private CreatePDF(){

    }

    public void createPDF(ArrayList<String> neighbours, ArrayList<String> hours){
        for(String n: neighbours){
            String nAux = n.substring(0,n.indexOf('-'));
            try{
                PDDocument document = new PDDocument();
                Boolean first = true;
                for(String h:hours){
                    PDPage page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    PDPageContentStream content = new PDPageContentStream(document,page);
                    if(first){
                        PDImageXObject logoIdeai = PDImageXObject.createFromFile("/home/miquel/Documentos/appSergi/src/main/resources/images/Marca_UPC_IDEAI_BLAU.png",document);
                        System.out.println(page.getMediaBox().getHeight());
                        content.drawImage(logoIdeai,25,770,300,75);
                        content.beginText();
                        content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD),22);
                        content.newLineAtOffset(400,800);
                        content.setNonStrokingColor(new Color(22,156,205));
                        content.showText("i-ViSta4Bike");
                        content.endText();
                        first = false;
                    }
                    content.beginText();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD),14);
                    content.newLineAtOffset(60,760);
                    content.showText("Expected behaviour");
                    content.endText();
                    content.beginText();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD),14);
                    content.newLineAtOffset(360,760);
                    content.showText("Observed Behaviour");
                    content.endText();
                    System.out.println("XC");

                    PDImageXObject expected = PDImageXObject.createFromFile("/home/miquel/Documentos/Solucion/Output/Graficos_average_0303.png",document);
                    PDImageXObject observed = PDImageXObject.createFromFile("/home/miquel/Documentos/Solucion/Output/Graficos_day_0303.png",document);
                    System.out.println("XD");

                    content.drawImage(expected,0,455,300,300);
                    content.drawImage(observed,300,455,295,300);
//                    PDImageXObject expected = PDImageXObject.createFromFile("/home/miquel/Documentos/Solucion/Output/Graficos_average_" + nAux+h+ ".png",document);
//                    PDImageXObject observed = PDImageXObject.createFromFile("/home/miquel/Documentos/Solucion/Output/Graficos_day_" + nAux+h+ ".png",document);

                    content.close();
                    document.save("/home/miquel/Documentos/appSergi/pruebapdf2." + i + ".pdf");


                }
            }
            catch (Exception e){
                System.out.printf(e.getMessage());
            }

        }
    }
    public void createPDF() {
        System.out.println("Creating pdf");
        try{
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document,page);
            PDImageXObject logoIdeai = PDImageXObject.createFromFile("/home/miquel/Documentos/appSergi/src/main/resources/images/Marca_UPC_IDEAI_BLAU.png",document);
            System.out.println(page.getMediaBox().getHeight());
            content.drawImage(logoIdeai,25,750,300,90);
            content.beginText();
            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD),22);
            content.newLineAtOffset(400,785);
            content.setNonStrokingColor(new Color(22,156,205));
            content.showText("i-ViSta4Bike");
            content.endText();
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
