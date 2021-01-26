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
            new FileNameExtensionFilter("Video", "amv", "mp4", "avi", "flv", "wmv"),
            new FileNameExtensionFilter("Texte", "txt", "docx", "pdf", "csv"),
            new FileNameExtensionFilter("Audio", "mp3", "wav", "wv", "flac"),
            new FileNameExtensionFilter("Image", "jpg", "png", "gif", "jpeg"),
            new FileNameExtensionFilter("All", "jpg", "png", "gif", "jpeg",
                    "amv", "mp4", "avi", "flv", "wmv",
                    "txt", "docx", "pdf", "csv",
                    "mp3", "wav", "wv", "flac"));

    private final List<String> imgExt = List.of("jpg", "png", "gif", "jpeg");
    private final List<String> vidExt = List.of("amv", "mp4", "avi", "flv", "wmv");
    private final List<String> textExt = List.of("txt", "docx", "pdf", "csv");
    private final List<String> audioExt = List.of("mp3", "wav", "wv", "flac");

    private final JTextField textField;
    private final JButton saveButton;
    private final JButton cancelButton;
    private final String path;

    ImportPage(String path) {
        this.path = path;
        SpringLayout layout = new SpringLayout();

        JLabel label = new JLabel("File name: ");
        JLabel pathLab = new JLabel("Path: ");
        JLabel originalLabel = new JLabel();
        originalLabel.setText("<html><p style=\"width:250px\">" + path + "</p></html>");
        textField = new JTextField(26);
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, textField, 5, SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, textField, 5, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, saveButton, 110, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, saveButton, 140, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, cancelButton, 0, SpringLayout.NORTH, saveButton);
        layout.putConstraint(SpringLayout.WEST, cancelButton, 5, SpringLayout.EAST, saveButton);
        layout.putConstraint(SpringLayout.NORTH, originalLabel, 5, SpringLayout.SOUTH, label);
        layout.putConstraint(SpringLayout.WEST, originalLabel, 5, SpringLayout.EAST, pathLab);
        layout.putConstraint(SpringLayout.NORTH, pathLab, 0, SpringLayout.NORTH, originalLabel);
        layout.putConstraint(SpringLayout.WEST, pathLab, 0, SpringLayout.WEST, label);

        setSaveButton();
        setCancelButton();

        this.setLayout(layout);
        this.setSize(395, 200);
        this.add(saveButton);
        this.add(label);
        this.add(textField);
        this.add(cancelButton);
        this.add(originalLabel);
        this.add(pathLab);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setSaveButton () {
        saveButton.addActionListener(e -> {
            File file = new File(path);

            String customName = textField.getText();

            IMedia newItem = new IMedia(customName.isBlank() ? file.getName() : customName,
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

    private void setCancelButton () {
        cancelButton.addActionListener (e ->  this.dispose());
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