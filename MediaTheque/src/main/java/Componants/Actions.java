package Componants;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Observable;

public class Actions {
    protected static class CustomEvent extends Observable {
        public void Notify() {
            setChanged();
            notifyObservers();
        }
    }

    public static class DeletionEvent extends CustomEvent {
    }

    public static class InsertionEvent extends CustomEvent {
    }

    public static class OpenEvent extends CustomEvent {
    }

    public static class ExportEvent extends CustomEvent {
    }

    protected static final DeletionEvent deletionEvent  = new DeletionEvent();
    protected static final ExportEvent   exportEvent    = new ExportEvent();
    protected static final OpenEvent     openEvent      = new OpenEvent();
    protected static final InsertionEvent insertionEvent= new InsertionEvent();

    public static ActionListener importAct() {
        return e -> {
            MainPage.chooser.showOpenDialog(null);
        };
    }

    public static ActionListener deleteAct() {
        return e -> {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure You want to delete this Item ?",
                    "Warning", JOptionPane.YES_NO_OPTION);

            if (dialogResult == JOptionPane.YES_OPTION) {
                deletionEvent.Notify();
            }
        };
    }

    public static ActionListener chooserAct() {
        return e -> new ImportPage(MainPage.chooser.getSelectedFile().getAbsolutePath());
    }

    public static ActionListener exitAct(MainPage mainPage) {
        return e -> mainPage.dispose();
    }

    public static ActionListener openAct() {
        return e -> openEvent.Notify();
    }

    public static ActionListener exportAct() {
        return e -> exportEvent.Notify();
    }
}
