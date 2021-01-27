package DataBaseBloat;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

import MediaElements.*;
import Utils.FileUtils;

public class DataBaseApi {
    private static Connection conn;

    public static void startDB () throws SQLException, ClassNotFoundException {
        conn = connect();
    }

    private static Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        String password = "";
        String user = "silverest";
        String dbLocation = "jdbc:mysql://0.0.0.0:3306/MEDIATHEQUE";
        Connection conn = DriverManager.getConnection(dbLocation, user, password);
        if (conn != null)
            System.out.println(true);
        return conn;
    }

    private static List<IMedia> runQuery(String Query){
        try(Statement query = conn.createStatement()){
            try(ResultSet set = query.executeQuery(Query)){
                List<IMedia> results = new LinkedList<>();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new LinkedList<>();
    }
    
    public static boolean exists(IMedia e){
        return Select(e).size() > 0 ;
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
            return new LinkedList<>();
        }
        int limit = Math.min(itemsCount - (idx-1)*quantity,quantity);
        return items.stream().skip((long) (idx - 1) * quantity).limit(limit).collect(Collectors.toList());
    }
    
}
