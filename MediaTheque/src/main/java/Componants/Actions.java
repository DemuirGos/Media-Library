package Componants;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Observable;

public class Actions {
    public enum EventType {
        DeletionEvent,
        SelectionEvent,
        InsertionEvent,
        OpenEvent,
        ExportEvent;
    }

    protected static class CustomEvent extends Observable {
        protected CustomEvent(EventType type){
            _type = type;
        }
        private EventType _type;
        public void Notify() {
            setChanged();
            notifyObservers();
        }
        public EventType getType() {
            return _type;
        }
    }

    protected static final CustomEvent deletionEvent  = new CustomEvent(EventType.DeletionEvent);
    protected static final CustomEvent   exportEvent  = new CustomEvent(EventType.ExportEvent);
    protected static final CustomEvent     openEvent  = new CustomEvent(EventType.OpenEvent);
    protected static final CustomEvent insertionEvent = new CustomEvent(EventType.InsertionEvent);

    public static ActionListener importAct() {
        return e -> MainPage.chooser.showOpenDialog(null);
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
