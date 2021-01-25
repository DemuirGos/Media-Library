package Componants;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

public class MenuBar extends JMenuBar {

    MenuBar(MainPage parent) {
        JMenu file = new JMenu("File");
        JMenuItem add = new JMenuItem("Import");
        JMenuItem remove = new JMenuItem("Remove");
        JMenuItem exit = new JMenuItem("Quit");

        this.setPreferredSize(new DimensionUIResource(800, 25));

        file.add(add);
        file.add(remove);
        file.add(exit);

        this.add(file);

        add.addActionListener(Actions.importAct());

        remove.addActionListener(Actions.deleteAct());

        exit.addActionListener(Actions.exitAct(parent));
    }
}
