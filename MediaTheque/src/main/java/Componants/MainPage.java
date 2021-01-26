package Componants;


import DataBaseBloat.DataBaseApi;
import DataBaseBloat.DatabaseConn;
import MediaElements.IMedia;
import MediaElements.MediaType;
import Utils.FileUtils;

import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.io.*;
import java.awt.Desktop;

import DataBaseBloat.*;
import MediaElements.*;
import Utils.*;


import javax.swing.*;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

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

        layout.putConstraint(SpringLayout.NORTH, importButton, 0, SpringLayout.SOUTH, attributesBar);
        layout.putConstraint(SpringLayout.WEST, importButton, 155, SpringLayout.WEST, attributesBar);

        layout.putConstraint(SpringLayout.NORTH, removeButton, 0, SpringLayout.SOUTH, attributesBar);
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
        Actions.openEvent.addObserver(this);
        Actions.exportEvent.addObserver(this);
        Actions.deletionEvent.addObserver(this);
        Actions.insertionEvent.addObserver(this);
        ImportPage.getFilters().forEach(chooser::setFileFilter);
        importButton.addActionListener(Actions.importAct());
        removeButton.addActionListener(Actions.deleteAct());
        chooser.addActionListener(Actions.chooserAct());
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DatabaseConn conn = new DatabaseConn();
        new MainPage();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof SideBar.SelectionEvent)
        {
            this.attributesBar.setItem(this.sideBar.getSelectedItem());
        }
        else if(o instanceof Actions.DeletionEvent){
            DataBaseApiDummy.remove(this.sideBar.getSelectedItem());
            this.sideBar.update() ;
        }
        else if(o instanceof Actions.ExportEvent){
            try {
                    var selectedItem = this.sideBar.getSelectedItem();
                    var chooser = new JFileChooser(); 
                    chooser.setCurrentDirectory(new java.io.File("."));
                    chooser.setDialogTitle("Chose a Location :");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setAcceptAllFileFilterUsed(false);
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
                        var location = chooser.getSelectedFile();
                        File file = File.createTempFile(selectedItem.getName(),"." + selectedItem.getAttributes().get("Original Extension"),location);
                        FileUtils.writeFile(file, StringUtils.decode(selectedItem.getData()));
                    }
                    else {
                        System.out.println("No Selection ");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if(o instanceof Actions.OpenEvent){
                try {
                    var selectedItem = this.sideBar.getSelectedItem();
                    File file = File.createTempFile(selectedItem.getName(),"." + selectedItem.getAttributes().get("Original Extension"));
                    FileUtils.writeFile(file, StringUtils.decode(selectedItem.getData()));
                    Desktop desktop = Desktop.getDesktop();
                    if (file.exists())
                        desktop.open(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if(o instanceof Actions.InsertionEvent){
            System.out.println("inserting ");
            this.sideBar.update();
        }
    }
}
