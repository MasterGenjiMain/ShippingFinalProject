package com.my.deliverysystem.db;

public abstract class DbConstants {

    public static final String INSERT_INTO_CITY = "INSERT INTO city (id, city_name) VALUES (DEFAULT, ?)";
    public static final String GET_ALL_CITIES = "SELECT id, city_name FROM city ORDER BY city.id";
    public static final String GET_CITY_BY_ID = "SELECT id, city_name FROM city where city.id=? ORDER BY city.id";
    public static final String GET_CITY_BY_NAME = "SELECT id, city_name FROM city where city.city_name=? ORDER BY city.id";
    public static final String UPDATE_CITY = "UPDATE city SET city_name=? WHERE id=?";
    public static final String DELETE_CITY = "DELETE FROM city WHERE id=?";

    public static final String INSERT_INTO_DELIVERY_ORDER = "INSERT INTO delivery_order (id, location_from_id," +
            " location_to_id, cargo_name, cargo_description, address, delivery_type_id, weight, volume, receiving_date, tariff_id)" +
            " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_DELIVERY_ORDERS = "SELECT id, location_from_id, location_to_id, cargo_name, cargo_description, address," +
            " delivery_type_id, weight, volume, receiving_date, tariff_id FROM delivery_order ORDER BY delivery_order.id";
    public static final String GET_DELIVERY_ORDER_BY_LOCATION_FROM_ID = "SELECT id, location_from_id, location_to_id, cargo_name, cargo_description," +
            " address,  delivery_type_id, weight, volume, receiving_date, tariff_id FROM delivery_order" +
            " WHERE delivery_order.location_from_id=? ORDER BY delivery_order.id";
    public static final String GET_DELIVERY_ORDER_BY_ID = "SELECT id, location_from_id, location_to_id, cargo_name, cargo_description," +
            " address,  delivery_type_id, weight, volume, receiving_date, tariff_id FROM delivery_order" +
            " WHERE delivery_order.id=? ORDER BY delivery_order.id";
    public static final String GER_DELIVERY_ORDER_BY_LOCATION_TO_ID = "SELECT id, location_from_id, location_to_id, cargo_name, cargo_description," +
            " address, delivery_type_id, weight, volume, receiving_date, tariff_id" +
            " FROM delivery_order WHERE delivery_order.location_to_id=? ORDER BY delivery_order.id";
    public static final String UPDATE_DELIVERY_ORDER = "UPDATE delivery_order SET location_from_id=?," +
            " location_to_id=?, cargo_name=?, cargo_description=?, address=?, delivery_type_id=?, weight=?, volume=?, receiving_date=?, tariff_id=? WHERE id=?";
    public static final String DELETE_DELIVERY_ORDER = "DELETE FROM delivery_order WHERE id=?";

    public static final String INSERT_INTO_DELIVERY_TYPE = "INSERT INTO delivery_type (id, type_name) VALUES (DEFAULT, ?)";
    public static final String GET_ALL_DELIVERY_TYPES = "SELECT id, type_name FROM delivery_type ORDER BY delivery_type.id";
    public static final String GET_DELIVERY_TYPE_BY_ID = "SELECT id, type_name FROM delivery_type where delivery_type.id=? ORDER BY delivery_type.id";
    public static final String GET_DELIVERY_TYPE_BY_NAME = "SELECT id, type_name FROM delivery_type where delivery_type.type_name=? ORDER BY delivery_type.id";
    public static final String UPDATE_DELIVERY_TYPE = "UPDATE delivery_type SET type_name=? WHERE id=?";
    public static final String DELETE_DELIVERY_TYPE = "DELETE FROM delivery_type WHERE id=?";

    public static final String INSERT_INTO_LOCATION = "INSERT INTO location (id, location_name, city_id, active_status_id) VALUES (DEFAULT, ?, ?, ?)";
    public static final String GET_ALL_LOCATIONS = "SELECT id, location_name, city_id, active_status_id FROM location ORDER BY location.id";
    public static final String GET_LOCATION_BY_ID = "SELECT id, location_name, city_id, active_status_id FROM location WHERE location.id=? ORDER BY location.id";
    public static final String GET_LOCATION_BY_NAME = "SELECT id, location_name, city_id, active_status_id FROM location WHERE location.location_name=? ORDER BY location.id";
    public static final String UPDATE_LOCATION = "UPDATE location SET location_name=?, city_id=?, active_status_id=? WHERE id=?";
    public static final String DELETE_LOCATION = "DELETE FROM location WHERE id=?";

    public static final String INSERT_INTO_RECEIPT = "INSERT INTO receipt (id, user_id, manager_id, price, receipt_status_id, delivery_order_id) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_RECEIPTS = "SELECT id, user_id, manager_id, price, receipt_status_id, delivery_order_id FROM receipt ORDER BY receipt.id";
    public static final String GET_RECEIPT_BY_USER_ID = "SELECT id, user_id, manager_id, price, receipt_status_id, delivery_order_id FROM receipt WHERE receipt.user_id=? ORDER BY receipt.id";
    public static final String GET_RECEIPT_BY_MANAGER_ID = "SELECT id, user_id, manager_id, price, receipt_status_id, delivery_order_id FROM receipt WHERE receipt.manager_id=? ORDER BY receipt.id";
    public static final String UPDATE_RECEIPT = "UPDATE receipt SET manager_id=?, price=?, receipt_status_id=?, delivery_order_id=? WHERE id=?";
    public static final String DELETE_RECEIPT = "DELETE FROM receipt WHERE id=?";
    public static final String GET_RECEIPT_BY_ID = "SELECT id, user_id, manager_id, price, receipt_status_id, delivery_order_id FROM receipt WHERE receipt.id=? ORDER BY receipt.id";

    public static final String INSERT_INTO_RECEIPT_STATUS = "INSERT INTO receipt_status (id, status_name) VALUES (DEFAULT, ?)";
    public static final String GET_ALL_RECEIPT_STATUSES = "SELECT id, status_name FROM receipt_status ORDER BY receipt_status.id";
    public static final String GET_RECEIPT_STATUS_BY_ID = "SELECT id, status_name FROM receipt_status WHERE receipt_status.id=? ORDER BY receipt_status.id";
    public static final String GET_RECEIPT_STATUS_BY_NAME = "SELECT id, status_name FROM receipt_status WHERE receipt_status.status_name=? ORDER BY receipt_status.id";
    public static final String UPDATE_RECEIPT_STATUS = "UPDATE receipt_status SET status_name=? WHERE id=?";
    public static final String DELETE_RECEIPT_STATUS = "DELETE FROM receipt_status WHERE id=?";

    public static final String INSERT_INTO_ROLE = "INSERT INTO role (id, role_name) VALUES (DEFAULT, ?)";
    public static final String GET_ALL_ROLES = "SELECT id, role_name FROM role ORDER BY role.id";
    public static final String GET_ROLE_BY_ID = "SELECT id, role_name FROM role WHERE role.id=? ORDER BY role.id";
    public static final String GET_ROLE_BY_NAME = "SELECT id, role_name FROM role WHERE role.role_name=? ORDER BY role.id";
    public static final String UPDATE_ROLE = "UPDATE role SET role_name=? WHERE id=?";
    public static final String DELETE_ROLE = "DELETE FROM role WHERE id=?";

    public static final String INSERT_INTO_TARIFF = "INSERT INTO tariff (id, tariff_name, tariff_price, tariff_info) VALUES (DEFAULT, ?, ?, ?)";
    public static final String GET_ALL_TARIFFS = "SELECT id, tariff_name, tariff_price, tariff_info FROM tariff ORDER BY tariff.id";
    public static final String GET_TARIFF_BY_ID = "SELECT id, tariff_name, tariff_price, tariff_info FROM tariff WHERE tariff.id=? ORDER BY tariff.id";
    public static final String GET_TARIFF_BY_NAME = "SELECT id, tariff_name, tariff_price, tariff_info FROM tariff WHERE tariff.tariff_name=? ORDER BY tariff.id";
    public static final String UPDATE_TARIFF = "UPDATE tariff SET tariff_name=?, tariff_price=?, tariff_info=? WHERE id=?";
    public static final String DELETE_TARIFF = "DELETE FROM tariff WHERE id=?";

    public static final String INSERT_INTO_USER = "INSERT INTO user (id, username, email, password, create_time, role_id) VALUES (DEFAULT, ?, ?, ?, DEFAULT, 1)";
    public static final String GET_ALL_USERS = "SELECT id, username, email, password, create_time, role_id FROM user ORDER BY user.id";
    public static final String GET_USER_BY_ID = "SELECT id, username, email, password, create_time, role_id FROM user WHERE user.id=? ORDER BY user.id";
    public static final String GET_USER_BY_USERNAME = "SELECT id, username, email, password, create_time, role_id FROM user WHERE user.username=? ORDER BY user.id";
    public static final String UPDATE_USER = "UPDATE user SET username=?, email=?, password=?, role_id=? WHERE id=?";
    public static final String DELETE_USER = "DELETE FROM user WHERE id=?";

    public static final String GET_ALL_LOCATIONS_BEAN = "SELECT location.id, location.location_name, city.city_name, active_status.active_status_name " +
            "FROM location " +
            "LEFT JOIN city ON location.city_id = city.id " +
            "LEFT JOIN active_status ON location.active_status_id = active_status.id ORDER BY location.id";

    public static final String GET_RECEIPTS_BEAN_BY_ID = "SELECT receipt.id, receipt.user_id, user.username, " +
            "receipt.price, receipt_status.status_name, receipt.delivery_order_id " +
            "FROM receipt " +
            "LEFT JOIN receipt_status ON receipt.receipt_status_id = receipt_status.id " +
            "LEFT JOIN cargo_delivery_db.user ON receipt.manager_id = user.id " +
            "WHERE receipt.user_id=? ORDER BY receipt.id";

    public static final String GET_ALL_RECEIPTS_BEANS = "SELECT receipt.id, receipt.user_id, user.username, " +
            "receipt.price, receipt_status.status_name, receipt.delivery_order_id " +
            "FROM receipt " +
            "LEFT JOIN receipt_status ON receipt.receipt_status_id = receipt_status.id " +
            "LEFT JOIN cargo_delivery_db.user ON receipt.manager_id = user.id " +
            "ORDER BY receipt.id";


    private DbConstants() {
    }
}
