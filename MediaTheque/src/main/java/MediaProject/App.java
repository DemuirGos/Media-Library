package MediaProject;

import Componants.MainPage;
import DataBaseBloat.DataBaseApi;

import java.sql.SQLException;

public final class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataBaseApi.startDB();
        new MainPage();
    }
}