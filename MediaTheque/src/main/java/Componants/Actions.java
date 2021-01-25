package Componants;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Observable;

public class Actions {
    public static class DeletionEvent extends Observable {
        public void Notify() {
            setChanged();
            notifyObservers();
        }
    }

    protected static final DeletionEvent deletionEvent = new DeletionEvent();

    public static ActionListener importAct () {
        return e -> {
            MainPage.chooser.showOpenDialog(null);
        };
    }

    public static ActionListener deleteAct () {
        return e -> {
            int dialogResult = JOptionPane.showConfirmDialog (
                    null,
                    "Are you sure You want to delete this Item ?",
                    "Warning", JOptionPane.YES_NO_OPTION);

            if(dialogResult == JOptionPane.YES_OPTION){
                deletionEvent.Notify();
            }
        };
    }

    public static ActionListener chooserAct () {
        return e -> new ImportPage(MainPage.chooser.getSelectedFile().getAbsolutePath());
    }

    public static ActionListener exitAct (MainPage mainPage) {
        return e -> mainPage.dispose();
    }
}
