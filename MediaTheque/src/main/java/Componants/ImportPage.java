package Componants;

import DataBaseBloat.DataBaseApiDummy;
import MediaElements.IMedia;
import MediaElements.MediaType;
import Utils.FileUtils;
import Utils.StringUtils;

import Componants.Actions.*;

import javax.swing.*;
import javax.swing.filechooser.*;

import java.io.File;
import java.util.List;

public class ImportPage extends JFrame {
    private static final List<FileNameExtensionFilter> filters = List.of(
                        new FileNameExtensionFilter("Image", "jpg", "png", "gif", "jpeg"),
                        new FileNameExtensionFilter("Video", "amv", "mp4", "avi", "flv", "wmv"),
                        new FileNameExtensionFilter("Texte", "txt", "docx", "pdf", "csv"),
                        new FileNameExtensionFilter("Audio", "mp3", "wav", "wv", "flac"));

    private final List<String> imgExt = List.of("jpg", "png", "gif", "jpeg");
    private final List<String> vidExt = List.of("amv", "mp4", "avi", "flv", "wmv");
    private final List<String> textExt = List.of("txt", "docx", "pdf", "csv");
    private final List<String> audioExt = List.of("mp3", "wav", "wv", "flac");

    private final JTextField textField;
    private final JButton saveButton;
    private final String path;

    ImportPage(String path) {
        this.path = path;
        SpringLayout layout = new SpringLayout();

        JLabel label = new JLabel("Nom Fichier: ");
        textField = new JTextField(path, 17);
        saveButton = new JButton("Sauvegarder");

        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, textField, 5, SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, textField, 5, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, saveButton, 5, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, saveButton, 22, SpringLayout.NORTH, textField);
        textField.setSize(10, 15);
        saveButton.setSize(20, 15);

        setSaveButton();

        this.setLayout(layout);
        this.setSize(450, 200);
        this.add(saveButton);
        this.add(label);
        this.add(textField);
        this.setVisible(true);
    }

    public void setSaveButton () {
        saveButton.addActionListener(e -> {
            File file = new File(path);

            String[] splt = path.split("\\.");
            String ext = splt[splt.length - 1];
            String customName = new File(textField.getText()).getName();
            IMedia newItem = new IMedia(customName + (customName.endsWith(ext) ? "" : "." + ext),
                                        parseFileExt(file.getName()), 
                                        StringUtils.encode(FileUtils.readFile(file)),
                                        FileUtils.getAttributes(file));
            if(!DataBaseApiDummy.exists(newItem))
            {
                DataBaseApiDummy.insert(newItem);
                Actions.insertionEvent.Notify();
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, "File Already in database");
            }
        });
    }

    private boolean checkFileEnd(List<String> exts, String fileName) {
        return exts.stream().anyMatch(fileName::endsWith);
    }

    public MediaType parseFileExt (String fileName) {
        if (checkFileEnd(imgExt, fileName))
            return MediaType.Image;
        else if (checkFileEnd(audioExt, fileName))
            return MediaType.Audio;
        else if (checkFileEnd(vidExt, fileName))
            return MediaType.Video;
        else if (checkFileEnd(textExt, fileName))
            return MediaType.Text;
        throw new IllegalStateException("Invalid type");
    }

    public static List<FileNameExtensionFilter>  getFilters() {
        return filters;
    }
}
