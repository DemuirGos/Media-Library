package Componants;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

public class MenuBar extends JMenuBar {
        private static final long serialVersionUID = 1L;
        private static final JFileChooser chooser = new JFileChooser("$HOME");
        
        MenuBar() {

            ImportPage.getFilters().forEach(chooser::setFileFilter);

            JMenu file = new JMenu("File");
            JMenuItem add = new JMenuItem("Importer");
            JMenuItem remove = new JMenuItem("Supprimer");
            JMenuItem exit = new JMenuItem("Quit");

            this.setPreferredSize(new DimensionUIResource(800, 25));

            file.add(add);
            file.add(remove);
            file.add(exit);

            this.add(file);

            add.addActionListener(e -> {
                chooser.showOpenDialog(null);
            });

            remove.addActionListener(e -> {

            });

            chooser.addActionListener(e -> new ImportPage(chooser.getSelectedFile().getAbsolutePath()));

            exit.addActionListener(e -> {
                System.exit(0);
            });
        }
    }
