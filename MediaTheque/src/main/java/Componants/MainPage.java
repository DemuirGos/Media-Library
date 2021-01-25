package Componants;

import javax.swing.*;

import MediaElements.*;
import Utils.FileUtils;

import java.util.Hashtable;

public class MainPage extends JFrame {
    private static final long serialVersionUID = 1L;

    private final SideBar sideBar = new SideBar();
    private final MenuBar menuBar = new MenuBar();
    private final PreviewPage AttributesBar = new PreviewPage(new IMedia("test" ,MediaType.Text,"rawr", FileUtils.getAttributes()));

    MainPage () {
        
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


        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    
    public static void main(String[] args) {
        new MainPage();
    }
}
