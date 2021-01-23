package DataBaseBloat;

import java.sql.*;
import java.util.*;
import java.util.List;
import MediaElements.*;

public class DataBaseApi {
    private String JdbcDriver = "com.mysql.jdbc.Driver";  
    private String DbLocation = "jdbc:mysql://localhost/EMP";
    private String User = "usernamse";
    private String Password = "password";

    private Connection Connect() throws ClassNotFoundException, SQLException {
        Class.forName(JdbcDriver);
        return DriverManager.getConnection(DbLocation,User,Password);
    }

    private List<IMedia> runQuery(String Query){
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
                        String data = set.getString("Data");
                        results.add(new IMedia(name,type,data));
                    }
                    return results;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void insert(IMedia element){
        runQuery("INSERT INTO Medias VALUES (" + element.toString() + ")");
    } 

    public void remove(IMedia element){
        runQuery("DELETE FROM Medias WHERE "                  + 
                    "Name = " + element.getName()  + " AND "  + 
                    "Data = " + element.getData()  + " AND "  +
                    "Type = " +  element.getType().toString() );
    } 

    public List<IMedia> Select(IMedia element){
        return  runQuery("SELECT Name, Type, Data FROM Medias WHERE " + 
                             "Name = " + element.getName() + " AND "  + 
                             "Data = " + element.getData() + " AND "  +
                             "Type =" +  element.getType().toString() );
    } 

    public List<IMedia> SelectAll(){
        return  runQuery("SELECT * FROM Medias");
    } 
}
