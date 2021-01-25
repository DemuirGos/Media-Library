package Componants;

import MediaElements.*;
import DataBaseBloat.DataBaseApi;

import java.util.*;
import java.util.stream.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.DimensionUIResource;

public class SideBar extends JPanel {
    // search area
    private JTextField SearchBar;
    private JCheckBox global;
    // items area
    private JList<String> ItemsBar;
    private JButton next;
    private JButton prev;

    // local loaded data area
    private ArrayList<IMedia> items;
    private IMedia selectedItem = null;
    // local page parameters
    int page = 1;
    boolean isGlobal = false;

    public SideBar() {
        super();
        this.setPreferredSize(new DimensionUIResource(280, 600));
        InitializeComponants();
        SetupLayout();
        HookEvents();
        Fill();
    }

    private void InitializeComponants(){
        SearchBar = new JTextField();
        SearchBar.setPreferredSize(new DimensionUIResource(235, 30));
        global = new JCheckBox();
        ItemsBar = new JList<String>();
        ItemsBar.setPreferredSize(new DimensionUIResource(245, 465));
        next = new JButton("Next");
        prev = new JButton("Previous");
        prev.setPreferredSize(new DimensionUIResource(245/2, 25));
        next.setPreferredSize(new DimensionUIResource(245/2, 25));
    }

    private void SetupLayout(){

        SpringLayout layout = new SpringLayout();
        
        layout.putConstraint(SpringLayout.WEST, SearchBar, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, SearchBar, 5, SpringLayout.NORTH, this      );
        
        layout.putConstraint(SpringLayout.WEST , ItemsBar , 5, SpringLayout.WEST , SearchBar );
        layout.putConstraint(SpringLayout.NORTH, ItemsBar , 30, SpringLayout.SOUTH, SearchBar);
        
        layout.putConstraint(SpringLayout.NORTH, global   , 10, SpringLayout.NORTH, this     );
        layout.putConstraint(SpringLayout.WEST , global   , 2, SpringLayout.EAST , SearchBar );
        
        layout.putConstraint(SpringLayout.NORTH, next     , 2, SpringLayout.SOUTH, SearchBar );
        layout.putConstraint(SpringLayout.EAST , next     , 0, SpringLayout.EAST , ItemsBar  );
        
        layout.putConstraint(SpringLayout.NORTH, prev     , 2, SpringLayout.SOUTH, SearchBar );
        layout.putConstraint(SpringLayout.WEST , prev     , 0, SpringLayout.WEST , ItemsBar  ); 
        
        this.add(SearchBar);
        this.add(ItemsBar);
        this.add(next);
        this.add(prev);
        this.add(global);
        this.setLayout(layout);
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

        ItemsBar.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedItem = items.get(ItemsBar.getSelectedIndex());
            }
        });

    }

    private void Fill(ArrayList<IMedia> items) {
        this.ItemsBar.removeAll();
        int i = 0;
        for (IMedia item : items) {
            JTextField element = new JTextField(item.getName());
            element.setBounds(5, 5 + 30*i, 235, 30);
            element.setEditable(false);
            this.ItemsBar.add(element);
            i++;
        }
    }

    private void Fill() {
        items = new ArrayList<>(DataBaseBloat.DataBaseApi.Taketemp(page, 15));// to change to Take
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
    public IMedia getSelectedItem(){
        return selectedItem;
    }
}
