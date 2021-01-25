package Componants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import MediaElements.*;

import java.util.Hashtable;
import java.util.List;

public class MainPage extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JFileChooser chooser = new JFileChooser("$HOME");
    private final SideBar sideBar = new SideBar();
    private final TopMenuBar menuBar = new TopMenuBar();
    private final PreviewPage AttributesBar = new PreviewPage(new IMedia("test" ,MediaType.Text,"rawr", new Hashtable<>()));

    MainPage () {
        List<FileNameExtensionFilter> filters = List.of(
                new FileNameExtensionFilter("Image", "jpg", "png", "gif", "jpeg"),
                new FileNameExtensionFilter("Video", "amv", "mp4", "avi", "flv", "wmv"),
                new FileNameExtensionFilter("Texte", "txt", "docx", "pdf", "csv"),
                new FileNameExtensionFilter("Audio", "mp3", "wav", "wv", "flac"));

        filters.forEach(chooser::setFileFilter);
        SpringLayout layout = new SpringLayout();

        this.setLayout(layout);
        this.setSize(800,600);
    
        layout.putConstraint(SpringLayout.NORTH ,menuBar, 3, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST  ,menuBar, 3, SpringLayout.WEST, this);
        
        layout.putConstraint(SpringLayout.NORTH ,sideBar, 3, SpringLayout.SOUTH, menuBar);
        layout.putConstraint(SpringLayout.WEST  ,sideBar, 1, SpringLayout.WEST, this);
        
        layout.putConstraint(SpringLayout.NORTH ,AttributesBar, 5, SpringLayout.SOUTH, menuBar);
        layout.putConstraint(SpringLayout.WEST  ,AttributesBar, 1, SpringLayout.EAST, sideBar);

        this.add(menuBar);
        this.add(sideBar);
        this.add(AttributesBar);

        chooser.addActionListener(e -> new ImportPage(chooser.getSelectedFile().getAbsolutePath()));

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    private class TopMenuBar extends JMenuBar {

        private static final long serialVersionUID = 1L;

        TopMenuBar() {
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
