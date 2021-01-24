package Componants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.List;

public class MainPage extends JFrame {
    private final JFileChooser chooser = new JFileChooser("$HOME");
    private final SideBar sideBar = new SideBar();

    MainPage () {
        List<FileNameExtensionFilter> filters = List.of(
                new FileNameExtensionFilter("Image", "jpg", "png", "gif", "jpeg"),
                new FileNameExtensionFilter("Video", "amv", "mp4", "avi", "flv", "wmv"),
                new FileNameExtensionFilter("Texte", "txt", "docx", "pdf", "csv"),
                new FileNameExtensionFilter("Audio", "mp3", "wav", "wv", "flac"));

        filters.forEach(chooser::setFileFilter);
        BorderLayout layout = new BorderLayout();

        this.setLayout(layout);
        this.setSize(800,600);
        this.getContentPane().add(new ButtonsBar(), BorderLayout.SOUTH);
        this.getContentPane().add(new TopMenuBar(), BorderLayout.NORTH);
        this.getContentPane().add(sideBar, BorderLayout.CENTER);

        chooser.addActionListener(e -> new ImportPage(chooser.getSelectedFile().getAbsolutePath()));

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private class ButtonsBar extends JPanel {

        ButtonsBar() {
            JButton openImport = new JButton("import");
            JButton deleteSelected = new JButton("supprimer");

            openImport.setSize(20,15);
            deleteSelected.setSize(20, 15);

            openImport.addActionListener(e -> chooser.showOpenDialog(null));

            //deleteSelected.addActionListener(e -> sideBar);

            this.add(openImport);
            this.add(deleteSelected);
            this.setSize(800, 20);
            this.setVisible(true);
        }
    }

    private class TopMenuBar extends JMenuBar {

        TopMenuBar () {
            JMenu file = new JMenu("File");
            JMenuItem add = new JMenuItem("Importer");
            JMenuItem remove = new JMenuItem("Supprimer");
            JMenuItem exit = new JMenuItem("Quit");

            file.add(add);
            file.add(remove);
            file.add(exit);

            this.add(file);

            add.addActionListener(e -> {
                chooser.showOpenDialog(null);
            });

            remove.addActionListener(e -> {

            });

            exit.addActionListener(e -> dispose());
        }
    }

    public static void main(String[] args) {
        new MainPage();
    }
}
