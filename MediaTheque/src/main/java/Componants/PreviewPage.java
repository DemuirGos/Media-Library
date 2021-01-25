package Componants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import MediaElements.*;

public class PreviewPage extends JPanel {

    private enum Paths {
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

    private class JImage extends JPanel {
        private static final long serialVersionUID = 1L;

        protected JImage(File f) {
            this.setPreferredSize(new DimensionUIResource(175, 175));
            BufferedImage myPicture;
            try {
                myPicture = resize(ImageIO.read(f), 0.25);
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                picLabel.setPreferredSize(new DimensionUIResource(175, 175));
                add(picLabel);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private BufferedImage resize(BufferedImage in, double scale){
            BufferedImage out = new BufferedImage((int)(in.getWidth() * scale), (int)(in.getHeight() * scale), BufferedImage.TYPE_INT_ARGB);
            AffineTransform scaleInstance = AffineTransform.getScaleInstance(scale, scale);
            AffineTransformOp  scaleOp 
                = new AffineTransformOp (scaleInstance, AffineTransform.TYPE_UNIFORM_SCALE);
            scaleOp.filter(in, out);
            return out;
        }
    }

    private static final long serialVersionUID = 1L;
    
    //type icon 
    private JImage icon;

    //attributes list
    private JPanel attributesList;

    // title field
    private JTextField titleComponent;
    
    //attribute list label
    private JTextField Label;

    // local Item
    protected IMedia localElement;
    
    // layout manager
    protected SpringLayout layout = new SpringLayout();

    public PreviewPage(){
        this.setPreferredSize(new DimensionUIResource(485, 530));
        InitializeComponants();
        this.setLayout(layout);
        SetupLayout();
        Fill();
    }

    private void SetupLayout() {
        layout.putConstraint(SpringLayout.WEST , titleComponent, 15, SpringLayout.WEST , this);
        layout.putConstraint(SpringLayout.NORTH, titleComponent,  5, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.NORTH, icon,  50, SpringLayout.SOUTH, titleComponent);
        layout.putConstraint(SpringLayout.WEST , icon, 150, SpringLayout.WEST , this);
        
        layout.putConstraint(SpringLayout.NORTH, Label, 50, SpringLayout.SOUTH, icon);
        layout.putConstraint(SpringLayout.WEST , Label, 10, SpringLayout.WEST , this);

        layout.putConstraint(SpringLayout.NORTH, attributesList, 15, SpringLayout.SOUTH, Label);
        layout.putConstraint(SpringLayout.WEST , attributesList, 10, SpringLayout.WEST , this);
        
        this.add(titleComponent);
        this.add(icon);
        this.add(Label);
        this.add(attributesList);
    }

    public PreviewPage(IMedia iMedia) {
        setItem(iMedia);
        this.setPreferredSize(new DimensionUIResource(485, 530));
        InitializeComponants();
        this.setLayout(layout);
        SetupLayout();
        Fill();
    }

    private void InitializeComponants() {
        icon =  new JImage( new File(
                                    switch (localElement.getType()){
                                        case Audio -> Paths.Audio.value;
                                        case Image -> Paths.Image.value;
                                        case Video -> Paths.Video.value;
                                        case Text  -> Paths.Text.value ;
                                        default    -> Paths.Dummy.value; }));
        
        titleComponent = new JTextField(localElement.getName() + " :");
        titleComponent.setPreferredSize(new DimensionUIResource(100, 40));
        titleComponent.setEditable(false);
        titleComponent.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        attributesList = new JPanel();
        attributesList.setPreferredSize(new DimensionUIResource(475, 150));
        
        Label = new JTextField("Attributes :");
        Label.setPreferredSize(new DimensionUIResource(250, 35));
        Label.setEditable(false);
        Label.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }

    public void setItem(IMedia element) {
        localElement = element;
    }

    private void Fill(){
        this.attributesList.removeAll();
        GridLayout layout = new GridLayout(localElement.getAttributes().size() + 1, 2);
        this.attributesList.setLayout(layout);
        for(var att : localElement.getAttributes().keySet()){
            JTextField title = new JTextField(att);
            JTextField value = new JTextField(localElement.getAttributes().get(att));
            this.attributesList.add(title);
            this.attributesList.add(value);
        }
    }
}
