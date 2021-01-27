package Componants;

import DataBaseBloat.DataBaseApi;
import MediaElements.*;
import Utils.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.DimensionUIResource;

import java.io.File;
import java.sql.SQLException;
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
    private String path;
    private final JTextField originalLabel;
    private final JLabel label;
    private final JLabel pathLab;
    private final JButton browse;
    private final JFileChooser chooser;

    ImportPage(String path) {
        this.path = path;
        SpringLayout layout = new SpringLayout();

        setResizable(false);

        chooser = new JFileChooser();
        chooser.setFileFilter(MainPage.chooser.getFileFilter());
        browse = new JButton("+");
        browse.setPreferredSize(new DimensionUIResource(30, 30));

        label = new JLabel("File name: ");
        pathLab = new JLabel("Path: ");

        originalLabel = new JTextField(path);
        originalLabel.setPreferredSize(new DimensionUIResource(240, 30));
        originalLabel.setEditable(false);

        textField = new JTextField();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        textField.setPreferredSize(new DimensionUIResource(275, 30));

        layout.putConstraint(SpringLayout.WEST, label, 15, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label, 20, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, textField, 5, SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, textField, 0, SpringLayout.NORTH, label);
        layout.putConstraint(SpringLayout.NORTH, originalLabel, 10, SpringLayout.SOUTH, textField);
        layout.putConstraint(SpringLayout.WEST, originalLabel, 0, SpringLayout.WEST, textField);
        layout.putConstraint(SpringLayout.NORTH, pathLab, 0, SpringLayout.NORTH, originalLabel);
        layout.putConstraint(SpringLayout.WEST, pathLab, 0, SpringLayout.WEST, label);
        layout.putConstraint(SpringLayout.WEST, saveButton, 110, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, saveButton, 30, SpringLayout.SOUTH, originalLabel);
        layout.putConstraint(SpringLayout.SOUTH, cancelButton, 0, SpringLayout.SOUTH, saveButton);
        layout.putConstraint(SpringLayout.WEST, cancelButton, 5, SpringLayout.EAST, saveButton);
        layout.putConstraint(SpringLayout.SOUTH, browse, 0, SpringLayout.SOUTH, originalLabel);
        layout.putConstraint(SpringLayout.EAST, browse, 0, SpringLayout.EAST, textField);

        setSaveButton();
        setCancelButton();
        setBrowseButton();

        this.setLayout(layout);
        this.setSize(395, 200);
        this.add(saveButton);
        this.add(label);
        this.add(textField);
        this.add(cancelButton);
        this.add(originalLabel);
        this.add(pathLab);
        this.add(browse);
        this.setVisible(true);
    }

    public void setSaveButton() {
        saveButton.addActionListener(e -> {
            File file = new File(path);

            String customName = textField.getText();

            IMedia newItem = new IMedia(customName.isBlank() ? file.getName() : customName,
                    parseFileExt(file.getName()), StringUtils.encode(FileUtils.readFile(file)),
                    FileUtils.getAttributes(file));

            if (!DataBaseApi.exists(newItem)) {

                try {
                    DataBaseApi.insert(newItem);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Actions.insertionEvent.Notify();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "File Already in database");
            }
        });
    }

    private void setCancelButton() {
        cancelButton.addActionListener(e -> this.dispose());
    }

    private void setBrowseButton() {
        browse.addActionListener(e -> {
            chooser.showOpenDialog(null);
            path = chooser.getSelectedFile().getAbsolutePath();
            originalLabel.setText(path);
        });
    }

    private boolean checkFileEnd(List<String> exts, String fileName) {
        return exts.stream().anyMatch(fileName::endsWith);
    }

    public MediaType parseFileExt(String fileName) {
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

    public static List<FileNameExtensionFilter> getFilters() {
        return filters;
    }
}