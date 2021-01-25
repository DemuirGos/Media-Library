package DataBaseBloat;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

import MediaElements.*;

public class DataBaseApi {
    private static String JdbcDriver = "com.mysql.jdbc.Driver";  
    private static String DbLocation = "jdbc:mysql://localhost/EMP";
    private static String User = "usernamse";
    private static String Password = "password";

    private static Connection Connect() throws ClassNotFoundException, SQLException {
        Class.forName(JdbcDriver);
        return DriverManager.getConnection(DbLocation,User,Password);
    }

    private static List<IMedia> runQuery(String Query){
        try(Connection local = Connect()){
            try(Statement query = local.createStatement()){
                try(ResultSet set = query.executeQuery(Query)){
                    List<IMedia> results = new LinkedList<IMedia>();
                    while(set.next()){
                        String name  = set.getString("Name");
                        MediaType type  =  switch (set.getString("Type")) {
                            case "Image" -> MediaType.Image;
                            case "Video" -> MediaType.Video;
                            case "Text"  -> MediaType.Text;
                            case "Audio" -> MediaType.Audio;
                            default -> throw new IllegalStateException("Invalid type");
                        };
                        var attributes = new Hashtable<String,String>();
                        attributes.put("Original Extension", set.getString("Extension"));
                        attributes.put("Date Inserted", set.getString("Date"));
                        attributes.put("Original Path", set.getString("Path"));
                        attributes.put("File Size"    , set.getString("Size"));
                        String data = set.getString("Data");
                        results.add(new IMedia(name,type,data, attributes));
                    }
                    return results;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  new LinkedList<IMedia>();
    }
    
    public static void insert(IMedia element){
        runQuery("INSERT INTO Medias VALUES (" + element.toString() + ")");
    } 

    public static void remove(IMedia element){
        runQuery("DELETE FROM Medias WHERE "                  + 
                    "Name = " + element.getName()  + " AND "  + 
                    "Type = " +  element.getType().toString() );
    } 

    public static List<IMedia> Select(IMedia element){
        return  runQuery("SELECT * FROM Medias WHERE " + 
                             "Name = " + element.getName() + " AND "  + 
                             "Type =" +  element.getType().toString() );
    } 

    public static List<IMedia> AllElements(){
        return runQuery("SELECT * FROM Medias");
    } 

    public static List<IMedia> Take(int idx,int quantity){
        List<IMedia> items = runQuery("SELECT * FROM Medias");
        int itemsCount = items.size();
        if((idx-1) * quantity > itemsCount ){
            return new LinkedList<IMedia>();
        }
        int limit = Math.min(itemsCount - (idx-1)*quantity,quantity);
        return items.stream().skip((idx -1) * quantity).limit(limit).collect(Collectors.toList());
    }
    
    public static List<IMedia> Taketemp(int idx,int quantity){ //only for testing to be removed
        List<IMedia> items = new LinkedList<IMedia>();
        for(int i=0;i<quantity;i++){
            items.add(new IMedia("test" + String.valueOf(i),MediaType.Text,"rawr", new Hashtable<>()));
        }
        return items;
    }

}
