package Componants;

import FileUtils.FileUtils;
import MediaElements.IMedia;
import MediaElements.MediaType;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List;

public class ImportPage extends JFrame{
    private final FileNameExtensionFilter filters =
            new FileNameExtensionFilter ("File exts:",
                    "jpg", "png", "gif", "jpeg",
                    "amv", "mp4", "avi", "flv", "wmv",
                    "txt", "docx", "pdf", "csv",
                    "mp3", "wav", "wv", "flac");

    private final List<String> imgExt = List.of("jpg", "png", "gif", "jpeg");
    private final List<String> vidExt = List.of("amv", "mp4", "avi", "flv", "wmv");
    private final List<String> textExt = List.of("txt", "docx", "pdf", "csv");
    private final List<String> audioExt = List.of("mp3", "wav", "wv", "flac");

    private JLabel label;
    private JTextField textField;
    private JButton saveButton;

    ImportPage (String name) {
        SpringLayout layout = new SpringLayout();

        label = new JLabel("Nom Fichier: ");
        textField =  new JTextField(name);
        saveButton = new JButton("Sauvegarder");

        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, textField, 5, SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, textField, 5, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, saveButton, 5, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, saveButton, 22, SpringLayout.NORTH, textField);
        saveButton.setSize(20, 15);

        setSaveButton();

        this.setLayout(layout);
        this.setSize(300,200);
        this.add(saveButton);
        this.add(label);
        this.add(textField);
        this.setVisible(true);
    }
    
    public void setSaveButton () {
        saveButton.addActionListener(e -> {
            String fileName = textField.getText();
            IMedia md = new IMedia(fileName, parseFileExt(fileName), FileUtils.readFile(fileName));
            System.out.println(md);
        });
    }

    private boolean checkFileEnd(List<String> exts, String fileName) {
        return exts.stream().anyMatch(fileName::endsWith);
    }

    public MediaType parseFileExt (String fileName) {
        if (checkFileEnd(imgExt, fileName))
            return MediaType.Image;

        if (checkFileEnd(audioExt, fileName))
            return MediaType.Audio;

        if (checkFileEnd(vidExt, fileName))
            return MediaType.Video;

        if (checkFileEnd(textExt, fileName))
            return MediaType.Text;

        return null;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton openImport = new JButton("import");
        JFileChooser chooser = new JFileChooser("$HOME");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.add(openImport);

        openImport.addActionListener(e -> {
            chooser.showOpenDialog(null);
        });

        chooser.addActionListener(e -> {
            new ImportPage(chooser.getSelectedFile().getAbsolutePath());
        });
       //frame.add(new ImportPage(openImport.getText()));
        frame.setVisible(true);
    }
}
