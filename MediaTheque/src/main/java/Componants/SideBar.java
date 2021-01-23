package Componants;

import MediaElements.IMedia;
import DataBaseBloat.DataBaseApi;

import java.awt.event.*;
import java.util.*;
import java.util.stream.*;

import javax.swing.*;
import javax.swing.event.*;

public class SideBar extends JFrame {
    // search area
    private JTextField SearchBar;
    private JCheckBox global;
    // items area
    private JList<String> ItemsBar;
    private JButton next;
    private JButton prev;

    // local loaded data area
    private ArrayList<IMedia> items;

    // local page parameters
    int page = 1;
    boolean isGlobal = false;

    public SideBar() {
        this.setSize(275, 560);
        InitializeComponants();
        SetupLayout();
        HookEvents();
        Fill();
    }

    private void InitializeComponants(){
        SearchBar = new JTextField();
        SearchBar.setSize(270, 40);
        global = new JCheckBox();
        global.setSize(35, 35);
        ItemsBar = new JList<String>();
        ItemsBar.setSize(270, 505);
        next = new JButton("Next");
        prev = new JButton("Previous");
        prev.setSize(50, 30);
        next.setSize(50, 30);
    }

    private void SetupLayout(){
        SpringLayout layout = new SpringLayout();

        this.setLayout(layout);

        layout.putConstraint(SpringLayout.WEST , SearchBar, 5, SpringLayout.WEST , this     );
        layout.putConstraint(SpringLayout.NORTH, SearchBar, 5, SpringLayout.NORTH, this     );

        layout.putConstraint(SpringLayout.WEST , ItemsBar , 5, SpringLayout.WEST , SearchBar);
        layout.putConstraint(SpringLayout.NORTH, ItemsBar , 5, SpringLayout.SOUTH, SearchBar);
        
        layout.putConstraint(SpringLayout.NORTH, global   , 5, SpringLayout.NORTH, SearchBar);
        layout.putConstraint(SpringLayout.EAST , global   , 5, SpringLayout.EAST , SearchBar);
        
        layout.putConstraint(SpringLayout.NORTH, next     , 5, SpringLayout.SOUTH, SearchBar);
        layout.putConstraint(SpringLayout.EAST , next     , 5, SpringLayout.EAST , SearchBar);

        layout.putConstraint(SpringLayout.NORTH, prev     , 5, SpringLayout.NORTH, SearchBar);
        layout.putConstraint(SpringLayout.EAST , prev     , 5, SpringLayout.WEST , next     );

        this.add(SearchBar);
        this.add(ItemsBar);
        this.add(next);
        this.add(prev);
        this.add(global);
    }

    private void HookEvents(){
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                page++;
                Fill();
            }
        });
        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                page = Math.max(page - 1, 1);
                Fill();
            }
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
          global.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isGlobal = !isGlobal;
            }
        });
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
            foundItems = new ArrayList<IMedia>(DataBaseApi.AllElements().stream().filter(itm -> itm.getName().contains(word)).collect(Collectors.toList()));
        } else {
            foundItems = new ArrayList<IMedia>(this.items.stream().filter(itm -> itm.getName().contains(word)).collect(Collectors.toList()));
        }
        Fill(foundItems);
    }
    private static final long serialVersionUID = 1L;
    
}
