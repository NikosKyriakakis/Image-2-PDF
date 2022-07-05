package imgload;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    private static String getExtension(File file) {
        String extension = "other";
        String filename = file.getName();
        for (int i = filename.length() - 1; i >= 0; i--) {
            if (filename.charAt(i) == '.') {
                if ((i + 1) < filename.length() - 1)
                    extension = filename.substring(i + 1);
            }
        }

        return extension;
    }

    public static List<PDImageXObject> loadImages(PDDocument document, File folder) throws IOException {
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        if (listOfFiles.length == 0) {
            JOptionPane.showMessageDialog(null, "Given folder is empty.");
            System.exit(1);
        }

        List<PDImageXObject> imageList = new ArrayList<>();
        PDImageXObject currentImage;
        int imageCounter = 0;
        for (File file : listOfFiles) {
            String fileExtension = getExtension(file);
            if (!fileExtension.equals("jpeg")
                    && !fileExtension.equals("jpg")
                    && !fileExtension.equals("png"))
                continue;
            imageCounter++;
            currentImage = PDImageXObject.createFromFile(file.getPath(), document);
            imageList.add(currentImage);
        }

        if (imageCounter == 0) {
            JOptionPane.showMessageDialog(null, "Given folder contains no supported images.");
            System.exit(1);
        }

        return imageList;
    }
}
