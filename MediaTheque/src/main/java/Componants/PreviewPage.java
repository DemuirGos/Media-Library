package Componants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import MediaElements.*;

public class PreviewPage extends JPanel {

    public enum Paths {
        Image("imageIconPath"),
        Video("VideoIconPath"),
        Text("TextIconPath"),
        Audio("AudioIconPath"),
        Dummy("C:\\Users\\CykaBlyat\\Desktop\\InitLogo.jpg");
        
        private final String value;
        
        Paths(final String text) {
            this.value = text;
        }
    }

    private static final long serialVersionUID = 1L;
    // title field
    private JTextField titleComponent;
    // local Item
    protected IMedia localElement;
    // layout manager
    protected SpringLayout layout = new SpringLayout();

    public PreviewPage(IMedia iMedia){
        BufferedImage myPicture;
        try {
            String path = switch (iMedia.getType()){
                /* case Audio -> Paths.Audio.value;
                case Image -> Paths.Image.value;
                case Video -> Paths.Video.value;
                case Text  -> Paths.Text.value ; */
                default    -> Paths.Dummy.value;
            };
            myPicture = ImageIO.read(new File(path));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            add(picLabel);
            localElement = iMedia;
            this.setLayout(layout);
            this.setPreferredSize(new DimensionUIResource(485, 530));
            titleComponent = new JTextField(localElement.getName());
            titleComponent.setSize(100, 40);
            layout.putConstraint(SpringLayout.EAST , titleComponent, 15, SpringLayout.EAST , this);
            layout.putConstraint(SpringLayout.NORTH, titleComponent,  5, SpringLayout.NORTH, this);
            this.add(titleComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setItem(IMedia element){
        localElement = element;
        Fill();
    }

    private void Fill(){

    }
}
