package DataBaseBloat;

import java.util.*;
import java.util.stream.*;

import MediaElements.*;

public class DataBaseApiDummy {
    private static final List<IMedia> items = new LinkedList<IMedia>();
    
    public static boolean exists(IMedia e){
        for(var i : AllElements()){
            if(i.getData().contentEquals(e.getData())) return true;
        }
        return false;
    }

    public static void insert(IMedia e){
        items.add(e);
    } 

    public static void remove(IMedia e){
        items.remove(e);
    } 

    public static List<IMedia> AllElements(){
        return items;
    } 

    public static List<IMedia> Take(int idx,int quantity){
        int itemsCount = items.size();
        if((idx-1) * quantity > itemsCount ){
            return new LinkedList<>();
        }
        int limit = Math.min(itemsCount - (idx-1)*quantity,quantity);
        return items.stream().skip((long) (idx - 1) * quantity).limit(limit).collect(Collectors.toList());
    }

}
