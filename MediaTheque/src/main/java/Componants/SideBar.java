package Componants;

import MediaElements.*;
import DataBaseBloat.DataBaseApi;
import java.util.*;
import java.util.stream.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.DimensionUIResource;
import java.util.Observable;

public class SideBar extends JPanel{
    public class SelectionEvent extends Observable {
        private IMedia _element;
        public void setElement(IMedia element) {
            this._element = element;
            setChanged();
            notifyObservers(element);
        }
    };
    // observable/parent
    SelectionEvent SelectionEventHandler = new SelectionEvent();  
    Observer Parent;
    // search area
    private JTextField SearchBar;
    private JCheckBox global;
    // items area
    private JList<String> ItemsBar;
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
        InitializeComponants();
        SetupLayout();
        HookEvents();
        Fill();
    }

    private void InitializeComponants(){
        SelectionEventHandler.addObserver(Parent);

        SearchBar = new JTextField();
        SearchBar.setPreferredSize(new DimensionUIResource(235, 30));
        
        global = new JCheckBox();
        
        model = new DefaultListModel<>(); 
        ItemsBar = new JList<String>(model);
        ItemsBar.setPreferredSize(new DimensionUIResource(245, 455));
        ItemsBar.setFixedCellHeight(35);
        ItemsBar.setFixedCellWidth(450);


        next = new JButton("Next");
        next.setPreferredSize(new DimensionUIResource(245/2, 25));
        
        prev = new JButton("Previous");
        prev.setPreferredSize(new DimensionUIResource(245/2, 25));
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
        
        var itemsBarSelectionModel = ItemsBar.getSelectionModel();
        itemsBarSelectionModel.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                for(var item :items)
                    if(item.getName() == ItemsBar.getSelectedValue()){
                        selectedItem = item;
                        break;
                    }
                SelectionEventHandler.setElement(selectedItem);
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
        items = new ArrayList<>(DataBaseBloat.DataBaseApi.Taketemp(page, 13));// to change to Take
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

    public void update(){
        Fill();
        selectedItem = null;
    }
}
