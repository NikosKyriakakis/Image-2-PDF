package pdfutils;

import imgload.ImageLoader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFCreator {
    private List<PDImageXObject> imageList;
    private final PDDocument document;
    private PDPage[] pages;

    public PDFCreator() {
        document = new PDDocument();
    }

    public void setFolder(File folder) throws IOException {
        imageList = ImageLoader.loadImages(document, folder);
        initDocumentPages();
    }

    private void initDocumentPages() {
        PDImageXObject image;

        pages = new PDPage[imageList.size()];
        for (int i = 0; i < pages.length; i++) {
            image = imageList.get(i);
            pages[i] = new PDPage(new PDRectangle(image.getWidth() + 15, image.getHeight() + 15));
            document.addPage(pages[i]);
        }
    }

    public void printImages2PDF() throws IOException {
        PDPageContentStream contentStream;
        PDImageXObject image;

        final int imageListSize = imageList.size();
        for (int i = 0; i < imageListSize; i++) {
            image = imageList.get(i);
            contentStream = new PDPageContentStream(document, pages[i]);
            contentStream.drawImage(image,0,0, image.getWidth(), image.getHeight());
            contentStream.close();
        }

        String saveLocation = System.getProperty("user.home").replaceAll("\\\\", "/");
        saveLocation += "/Documents/";
        String name = JOptionPane.showInputDialog("Please enter the name of the .pdf file to save.");
        saveLocation += (name + ".pdf");
        document.save(saveLocation);
        document.close();
    }
}
