package DataBaseBloat;

import java.util.*;
import java.util.stream.*;

import MediaElements.*;

public class DataBaseApiDummy {
    private static List<IMedia> items = new LinkedList<IMedia>();
    
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
            return new LinkedList<IMedia>();
        }
        int limit = Math.min(itemsCount - (idx-1)*quantity,quantity);
        return items.stream().skip((idx -1) * quantity).limit(limit).collect(Collectors.toList());
    }

}
