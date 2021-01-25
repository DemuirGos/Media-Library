package Componants;

import java.util.Observable;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

public class MenuBar extends JMenuBar {

    public class DeletionEvent extends Observable {
        public void Notify() {
            setChanged();
            notifyObservers();
        }
    };
    
    DeletionEvent DeletionEventHandler =  new DeletionEvent();

    private static final long serialVersionUID = 1L;
    private static final JFileChooser chooser = new JFileChooser("$HOME");
    private MainPage Container; 

    MenuBar(MainPage parent) {

        Container = parent;
        ImportPage.getFilters().forEach(chooser::setFileFilter);

        DeletionEventHandler.addObserver(Container);

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
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure You want to delete this Item ?", "Warning", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                DeletionEventHandler.Notify();
            }
        });

        chooser.addActionListener(e -> new ImportPage(chooser.getSelectedFile().getAbsolutePath()));

        exit.addActionListener(e -> {
            Container.dispose();
        });
    }
}
