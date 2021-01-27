package Componants;

import DataBaseBloat.DataBaseApi;
import MediaElements.IMedia;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.DimensionUIResource;

import Componants.Actions.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.stream.Collectors;

public class SideBar extends JPanel {
    public static class SelectionEvent extends CustomEvent {
        protected SelectionEvent(EventType type) {
            super(type);
        }

        public void setElement(IMedia element) {
            setChanged();
            notifyObservers(element);
        }
    }

    // observable/parent
    SelectionEvent SelectionEventHandler = new SelectionEvent(EventType.SelectionEvent);
    Observer Parent;

    // search area
    private JTextField searchBar;
    private JCheckBox global;

    // items area
    private JList<String> itemsBar;
    JScrollPane scrollableBar;
    DefaultListModel<String> model;
    private JButton next;
    private JButton prev;

    // local loaded data area
    private ArrayList<IMedia> items;
    private IMedia selectedItem = null;
    // local page parameters
    int page = 1;
    boolean isGlobal = false;

    public SideBar(Observer container) {
        super();
        Parent = container;
        this.setPreferredSize(new DimensionUIResource(280, 600));
        InitializeComponents();
        SetupLayout();
        HookEvents();
        Fill();
    }

    private void InitializeComponents(){
        SelectionEventHandler.addObserver(Parent);

        searchBar = new JTextField();
        searchBar.setPreferredSize(new DimensionUIResource(230, 30));
        
        global = new JCheckBox();
        
        model = new DefaultListModel<>(); 
        itemsBar = new JList<>(model);
        scrollableBar = new JScrollPane(itemsBar);  
        scrollableBar.setPreferredSize(new DimensionUIResource(245, 455));
        itemsBar.setFixedCellHeight(35);
        itemsBar.setFixedCellWidth(450);


        next = new JButton("Next");
        next.setPreferredSize(new DimensionUIResource(245/2, 25));
        
        prev = new JButton("Previous");
        prev.setPreferredSize(new DimensionUIResource(245/2, 25));
    }

    private void SetupLayout(){

        SpringLayout layout = new SpringLayout();
        
        layout.putConstraint(SpringLayout.WEST, searchBar, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, searchBar, 5, SpringLayout.NORTH, this);
        
        layout.putConstraint(SpringLayout.WEST , scrollableBar , 0, SpringLayout.WEST , searchBar );
        layout.putConstraint(SpringLayout.NORTH, scrollableBar , 30, SpringLayout.SOUTH, searchBar);
        
        layout.putConstraint(SpringLayout.NORTH, global   , 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST , global   , 3, SpringLayout.EAST , scrollableBar);
        
        layout.putConstraint(SpringLayout.NORTH, next     , 2, SpringLayout.SOUTH, searchBar);
        layout.putConstraint(SpringLayout.WEST , next     , 5, SpringLayout.WEST , this);
        
        layout.putConstraint(SpringLayout.NORTH, prev     , 2, SpringLayout.SOUTH, searchBar);
        layout.putConstraint(SpringLayout.EAST , prev     , 0, SpringLayout.EAST , scrollableBar);

        this.add(searchBar);
        this.add(scrollableBar);
        this.add(next);
        this.add(prev);
        this.add(global);
        this.setLayout(layout);
    }

    private void HookEvents(){
        next.addActionListener(e -> {
            page++;
            Fill();
            if(items.size()==0){
                page--;
                Fill();
            }
        });

        prev.addActionListener(e -> {
            page = Math.max(page - 1, 1);
            Fill();
        });

        searchBar.getDocument().addDocumentListener(new DocumentListener() {
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
        
        var itemsBarSelectionModel = itemsBar.getSelectionModel();
        itemsBarSelectionModel.addListSelectionListener(e -> {
            for(var item :items)
                if(item.getName().equals(itemsBar.getSelectedValue())){
                    selectedItem = item;
                    break;
                }
            SelectionEventHandler.setElement(selectedItem);
        });

        itemsBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Actions.openEvent.Notify();
                }
            }
        });
    }

    private void Fill(ArrayList<IMedia> items) {
        model.removeAllElements();
        for (IMedia item : items) {
            model.addElement(item.getName());
        }
    }

    private void Fill() {
        items = new ArrayList<>(DataBaseApi.Take(page, 13));// to change to DataBaseApi
        Fill(items);
    }

    private void Search() {
        String word = this.searchBar.getText();
        ArrayList<IMedia> foundItems;

        if (this.isGlobal) {
            foundItems = DataBaseApi.AllElements().stream().filter(itm -> itm.getName().contains(word)).collect(Collectors.toCollection(ArrayList::new));
        } else {
            foundItems = this.items.stream().filter(itm -> itm.getName().contains(word)).collect(Collectors.toCollection(ArrayList::new));
        }
        Fill(foundItems);
    }

    public IMedia getSelectedItem(){
        return selectedItem;
    }

    public void update(){
        Fill();
        selectedItem = null;
    }
}
