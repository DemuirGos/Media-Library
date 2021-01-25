package Componants;

import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import MediaElements.*;
import Utils.FileUtils;

public class MainPage extends JFrame implements Observer {
    private static final long serialVersionUID = 1L;

    private final SideBar sideBar = new SideBar(this);
    private final MenuBar menuBar = new MenuBar(this);
    private final PreviewPage AttributesBar = new PreviewPage(
            new IMedia("test", MediaType.Text, "rawr", FileUtils.getAttributes(23)));

    MainPage() {

        SpringLayout layout = new SpringLayout();

        this.setLayout(layout);
        this.setSize(800, 600);

        layout.putConstraint(SpringLayout.NORTH, menuBar, 3, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, menuBar, 3, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, sideBar, 3, SpringLayout.SOUTH, menuBar);
        layout.putConstraint(SpringLayout.WEST, sideBar, 1, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, AttributesBar, 5, SpringLayout.SOUTH, menuBar);
        layout.putConstraint(SpringLayout.WEST, AttributesBar, 1, SpringLayout.EAST, sideBar);

        this.add(menuBar);
        this.add(sideBar);
        this.add(AttributesBar);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    private void run() {

    }

    public static void main(String[] args) {
        new MainPage();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.AttributesBar.setItem(this.sideBar.getSelectedItem());
    }
}
