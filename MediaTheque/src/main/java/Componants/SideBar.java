package Componants;

import MediaElements.IMedia;
import DataBaseBloat.DataBaseApi;

import java.awt.event.*;
import java.util.*;
import java.util.stream.*;

import javax.swing.*;
import javax.swing.event.*;

public class SideBar extends JPanel {
    // search area
    private JTextField SearchBar;
    private JCheckBox global;
    // items area
    private JList ItemsBar;
    private JButton next;
    private JButton prev;

    // local loaded data area
    private ArrayList<IMedia> items;

    // local page parameters
    int page = 1;
    boolean isGlobal = false;

    String emptyStr = " ".repeat(40);

    public SideBar() {
        this.setSize(275, 500);
        InitializeComponants();
        SetupLayout();
        HookEvents();
        //Fill();
    }

    private void InitializeComponants(){
        SearchBar = new JTextField("", 13);
        SearchBar.setBounds(5, 5,270, 40);
        global = new JCheckBox();
        String[] empty = new String[28];
        Arrays.fill(empty, emptyStr);
        ItemsBar = new JList (empty);
        ItemsBar.setBounds(5, 50, 270, 490);
        next = new JButton("Next");
        prev = new JButton("Previous");
        prev.setBounds(5, 120,50, 30);
        next.setBounds(60, 120, 50, 30);
    }

    private void SetupLayout(){
        SpringLayout layout = new SpringLayout();


        layout.putConstraint(SpringLayout.WEST , SearchBar, 5, SpringLayout.WEST , this);
        layout.putConstraint(SpringLayout.NORTH, SearchBar, 5, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST , ItemsBar , 5, SpringLayout.WEST , this);
        layout.putConstraint(SpringLayout.NORTH, ItemsBar , 20, SpringLayout.NORTH, SearchBar);
        
        layout.putConstraint(SpringLayout.NORTH, global   , 5, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST , global   , 20, SpringLayout.EAST , SearchBar);

        layout.putConstraint(SpringLayout.NORTH, next     , 5, SpringLayout.SOUTH, ItemsBar);
        layout.putConstraint(SpringLayout.WEST , next     , 5, SpringLayout.WEST , this);

        layout.putConstraint(SpringLayout.NORTH, prev     , 5, SpringLayout.SOUTH, ItemsBar);
        layout.putConstraint(SpringLayout.WEST , prev     , 5, SpringLayout.EAST , next);


        this.add(SearchBar);
        this.add(ItemsBar);
        this.add(next);
        this.add(prev);
        this.add(global);
        this.setLayout(layout);
        this.setVisible(true);
    }

    private void HookEvents(){
        next.addActionListener(e -> {
            page++;
            Fill();
        });

        prev.addActionListener(e -> {
            page = Math.max(page - 1, 1);
            Fill();
        });

        SearchBar.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                Search();
            }
            public void removeUpdate(DocumentEvent e) {
              if(e.getLength() == 0){
                Fill();
              }
              else{
                Search();
              }
            }
            public void insertUpdate(DocumentEvent e) {
                Search();
            }
          });
          global.addActionListener(e -> isGlobal = !isGlobal);
    }

    private void Fill(ArrayList<IMedia> items) {
        this.ItemsBar.removeAll();

        for (IMedia item : items) {
            JTextField element = new JTextField(item.getName());
            element.setEditable(false);
            this.ItemsBar.add(element);
        }
    }

    private void Fill() {
        items = new ArrayList<>(DataBaseBloat.DataBaseApi.Take(page, 16));
        Fill(items);
    }

    private void Search() {
        String word = this.SearchBar.getText();
        ArrayList<IMedia> foundItems;

        if (this.isGlobal) {
            foundItems = DataBaseApi.AllElements().stream().filter(itm -> itm.getName().contains(word)).collect(Collectors.toCollection(ArrayList::new));
        } else {
            foundItems = this.items.stream().filter(itm -> itm.getName().contains(word)).collect(Collectors.toCollection(ArrayList::new));
        }

        Fill(foundItems);
    }
    private static final long serialVersionUID = 1L;
    
}
