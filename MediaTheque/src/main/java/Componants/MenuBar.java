package Componants;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

public class MenuBar extends JMenuBar {

    MenuBar(MainPage parent) {
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem export = new JMenuItem("Export");
        JMenuItem remove = new JMenuItem("Remove");
        JMenuItem add = new JMenuItem("Import");
        JMenuItem exit = new JMenuItem("Quit");
        

        this.setPreferredSize(new DimensionUIResource(800, 25));

        file.add(open);
        file.add(export);
        file.add(add);
        file.add(remove);
        file.add(exit);

        this.add(file);

        open.addActionListener(Actions.openAct());
        
        export.addActionListener(Actions.exportAct());
        
        add.addActionListener(Actions.importAct());
        
        remove.addActionListener(Actions.deleteAct());

        exit.addActionListener(Actions.exitAct(parent));
    }
}
