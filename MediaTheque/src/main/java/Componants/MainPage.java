package Componants;

import Componants.Actions.CustomEvent;
import DataBaseBloat.DataBaseApi;
import MediaElements.IMedia;
import Utils.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainPage extends JFrame implements Observer {

    private final SideBar sideBar = new SideBar(this);
    private final PreviewPage attributesBar = new PreviewPage(IMedia.Init());
    protected static final JFileChooser chooser = new JFileChooser("$HOME");
    private final JButton importButton = new JButton("Import");
    private final JButton removeButton = new JButton("Delete");
    private final JButton exportButton = new JButton("Export");
    private final JButton openButton = new JButton("Open");
    private final MenuBar menuBar;

    public MainPage() {
        SpringLayout layout = new SpringLayout();

        this.setLayout(layout);
        this.setSize(800, 600);

        menuBar = new MenuBar(this);

        layout.putConstraint(SpringLayout.NORTH, menuBar, 3, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, menuBar, 3, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, sideBar, 3, SpringLayout.SOUTH, menuBar);
        layout.putConstraint(SpringLayout.WEST, sideBar, 1, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, attributesBar, 5, SpringLayout.SOUTH, menuBar);
        layout.putConstraint(SpringLayout.WEST, attributesBar, 1, SpringLayout.EAST, sideBar);

        layout.putConstraint(SpringLayout.NORTH, openButton, 0, SpringLayout.SOUTH, attributesBar);
        layout.putConstraint(SpringLayout.WEST, openButton, 95, SpringLayout.WEST, attributesBar);

        layout.putConstraint(SpringLayout.NORTH, importButton, 0, SpringLayout.SOUTH, attributesBar);
        layout.putConstraint(SpringLayout.WEST, importButton, 5, SpringLayout.EAST, openButton);

        layout.putConstraint(SpringLayout.NORTH, exportButton, 0, SpringLayout.SOUTH, attributesBar);
        layout.putConstraint(SpringLayout.WEST, exportButton, 5, SpringLayout.EAST, importButton);

        layout.putConstraint(SpringLayout.NORTH, removeButton, 0, SpringLayout.SOUTH, attributesBar);
        layout.putConstraint(SpringLayout.WEST, removeButton, 5, SpringLayout.EAST, exportButton);

        this.add(menuBar);
        this.add(sideBar);
        this.add(attributesBar);

        this.add(openButton);
        this.add(importButton);
        this.add(exportButton);
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
        openButton.addActionListener(Actions.openAct());
        exportButton.addActionListener(Actions.exportAct());
    }
    
    @Override
    public void update(Observable o, Object arg) {
        switch (((CustomEvent) o).getType()) {
            case SelectionEvent -> this.attributesBar.setItem(this.sideBar.getSelectedItem());

            case DeletionEvent -> {
                DataBaseApi.remove(this.sideBar.getSelectedItem());
                this.sideBar.update();
            }

            case ExportEvent -> {
                try {
                    var selectedItem = this.sideBar.getSelectedItem();
                    var chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new File("."));
                    chooser.setDialogTitle("Chose a Location :");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setAcceptAllFileFilterUsed(false);
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        var location = chooser.getSelectedFile();
                        File file = File.createTempFile(selectedItem.getName(), "." + selectedItem.getAttributes().get("Original Extension"), location);
                        FileUtils.writeFile(file, StringUtils.decode(selectedItem.getData()));
                    } else {
                        System.out.println("No Selection ");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            case OpenEvent -> {
                try {
                    var selectedItem = this.sideBar.getSelectedItem();
                    File file = File.createTempFile(selectedItem.getName(), "." + selectedItem.getAttributes().get("Original Extension"));
                    FileUtils.writeFile(file, StringUtils.decode(selectedItem.getData()));
                    Desktop desktop = Desktop.getDesktop();
                    if (file.exists())
                        desktop.open(file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            case InsertionEvent -> {
                this.sideBar.update();
            }
        }
    }
}
