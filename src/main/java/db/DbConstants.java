package db;

public abstract class DbConstants {

    public static final String F_LANGUAGE_ID = "id";
    public static final String F_LOCATION_NAME = "location_name";
    public static final String F_LOCATION_CITY_ID = "city_id";
    public static final String F_LOCATION_IS_ACTIVE = "is_active";

    public static final String GET_ALL_LOCATIONS = "SELECT * FROM location l ORDER BY l.location_name;";
    public static final String FIND_LOCATION_BY_NAME = "SELECT * FROM location l WHERE l.location_name=? " +
            "ORDER BY l.location_name";
    public static final String INSERT_INTO_RECEIPT = "INSERT INTO receipt (`user_id`, `manager_id`," +
            " `price`, `receipt_status_id`) VALUES (?, ?, ?, ?);";


    private DbConstants() {
    }
}
