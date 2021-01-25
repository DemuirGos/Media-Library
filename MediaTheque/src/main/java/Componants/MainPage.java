package Componants;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import DataBaseBloat.DataBaseApi;
import MediaElements.*;
import Utils.FileUtils;

public class MainPage extends JFrame implements Observer {

    private final SideBar sideBar = new SideBar(this);
    private final PreviewPage attributesBar = new PreviewPage(
            new IMedia("test", MediaType.Text, "rawr", FileUtils.getAttributes(23)));
    protected static final JFileChooser chooser = new JFileChooser("$HOME");
    private final JButton importButton = new JButton("Import");
    private final JButton removeButton = new JButton("Delete");

    MainPage() {
        SpringLayout layout = new SpringLayout();

        this.setLayout(layout);
        this.setSize(800, 600);

        MenuBar menuBar = new MenuBar(this);
        layout.putConstraint(SpringLayout.NORTH, menuBar, 3, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, menuBar, 3, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, sideBar, 3, SpringLayout.SOUTH, menuBar);
        layout.putConstraint(SpringLayout.WEST, sideBar, 1, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, attributesBar, 5, SpringLayout.SOUTH, menuBar);
        layout.putConstraint(SpringLayout.WEST, attributesBar, 1, SpringLayout.EAST, sideBar);

        layout.putConstraint(SpringLayout.NORTH, importButton, 5, SpringLayout.SOUTH, attributesBar);
        layout.putConstraint(SpringLayout.WEST, importButton, 130, SpringLayout.WEST, attributesBar);

        layout.putConstraint(SpringLayout.NORTH, removeButton, 5, SpringLayout.SOUTH, attributesBar);
        layout.putConstraint(SpringLayout.WEST, removeButton, 5, SpringLayout.EAST, importButton);

        this.add(menuBar);
        this.add(sideBar);
        this.add(attributesBar);
        this.add(importButton);
        this.add(removeButton);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        setElems();
    }

    private void setElems () {
        Actions.deletionEvent.addObserver(this);
        ImportPage.getFilters().forEach(chooser::setFileFilter);
        importButton.setSize(20, 15);
        removeButton.setSize(20, 15);
        importButton.addActionListener(Actions.importAct());
        removeButton.addActionListener(Actions.deleteAct());
        chooser.addActionListener(Actions.chooserAct());
    }

    public static void main(String[] args) {
        new MainPage();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof SideBar.SelectionEvent)
            this.attributesBar.setItem(this.sideBar.getSelectedItem());
        else if(o instanceof Actions.DeletionEvent){
            DataBaseApi.remove(this.sideBar.getSelectedItem());
            this.sideBar.update() ;
        }
    }
}
