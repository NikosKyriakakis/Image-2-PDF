package guiexplorer;

import pdfutils.PDFCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileExplorer extends JPanel implements ActionListener {
    private JButton openButton;
    private JFileChooser explorer;
    private final PDFCreator pdfCreator;

    public FileExplorer() {
        super(new BorderLayout());
        pdfCreator = new PDFCreator();
        createFileExplorer();
        createButton();
        createLogo();
    }

    private void createFileExplorer() {
        explorer = new JFileChooser();
        explorer.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    private void createLogo() {
        JPanel logoPanel = new JPanel();
        JLabel logo = new JLabel("IMG2PDF");
        logo.setFont(new Font("Helvetica", Font.BOLD, 42));
        logoPanel.add(logo);
        add(logoPanel, BorderLayout.NORTH);
    }

    private void createButton() {
        JPanel buttonPanel = new JPanel();
        openButton = new JButton("Open folder...");
        openButton.setBackground(Color.WHITE);
        openButton.setForeground(Color.BLACK);
        openButton.setFont(new Font("Helvetica", Font.BOLD, 16));
        openButton.addActionListener(this);
        buttonPanel.add(openButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            int status = explorer.showOpenDialog(FileExplorer.this);
            if (status == JFileChooser.APPROVE_OPTION) {
                File folder = explorer.getSelectedFile();
                try {
                    pdfCreator.setFolder(folder);
                    pdfCreator.printImages2PDF();
                    JOptionPane.showMessageDialog(null, "PDF file saved in current user's 'Documents' folder.");
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                    System.exit(1);
                }
            }
        }
    }
}
