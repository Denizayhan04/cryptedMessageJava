public class DbConfig {

    private static final String URL = "jdbc:mysql://mysql-3db2bab5-epicmessageapp.f.aivencloud.com:28769/defaultdb?ssl-mode=REQUIRED";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_KKpAyafK2hugfn7VMzC";

    public static String getUrl() {
        return URL;
    }

    public static String getUser() {
        return USER;
    }

    public static String getPassword() {
        return PASSWORD;
    }

}
