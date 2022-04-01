package com.example.finalproject;

import com.my.deliverysystem.dao.implementation.DeliveryOrderDAOImplementation;
import com.my.deliverysystem.dao.implementation.ReceiptStatusDAOImplementation;
import com.my.deliverysystem.dao.implementation.RoleDAOImplementation;
import com.my.deliverysystem.dao.implementation.UserDAOImplementation;
import com.my.deliverysystem.dao.implementation.beanImpl.LocationBeanDAOImpl;
import com.my.deliverysystem.db.entity.DeliveryOrder;

import java.io.*;
import java.util.Date;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("DbManager.doGet");

        try {
//            User user = new User();
//            user.setName("aaa");
//            user.setPassword("bbb");
//            user.setEmail("ccc");
//            user.setRoleId(1);
//            DbManager manager = DbManager.getInstance();
//            manager.insertUser(user);
//            System.out.println(user);

//            manager.getUsers();

//            Cargo cargo = new Cargo();
//            cargo.setName("test cargo");
//            cargo.setDescription("someSesc");
//            DeliveryOrder deliveryOrder = new DeliveryOrder(1,1,1,"addres",1,1,1,1,1);
//            CargoService cargoService = new CargoService();
//            cargoService.add(cargo, deliveryOrder);
//            System.out.println(cargo);

//            CargoService cargoService = new CargoService();
//            System.out.println(cargoService.getById(5L));
//            System.out.println(cargoService.getByName("test cargo"));

//            CargoService cargoService = new CargoService();
//            Cargo cargo = new Cargo();
//            cargo.setId(3);
//            cargo.setName("Third");
//            cargo.setDescription("3rd");
//            cargo.setDeliveryOrderId(1);
//            System.out.println(cargoService.update(cargo));
//            System.out.print(cargoService.getById(3L));

//            CargoService cargoService = new CargoService();
//            Cargo cargo = new Cargo();
//            cargo.setId(3);
//            cargo.setName("Third");
//            cargo.setDescription("3rd");
//            cargo.setDeliveryOrderId(1);
//            System.out.println(cargoService.remove(cargo));
//            System.out.println(cargoService.getAll());

//            City city = new City();
//            city.setCityName("newCity");
//            CityService service = new CityService();
//            service.add(city);

//            CityService service = new CityService();
////            System.out.println(service.getAll());
////            System.out.println(service.getById(2L));
////            System.out.println(service.getByName("lviv"));
//            City city = new City();
//////            service.update(new City(1, "Lviv"));
//            city.setId(4);
//            city.setCityName("newCity");
////            service.remove(city);

//            LocationService service = new LocationService();
////            Location location = new Location();
////            location.setLocationName("newLoc");
////            location.setCityId(1);
////            location.setIsActive(1);
////            service.add(location, city);
////            System.out.println(service.getAll());
////            System.out.println(service.getById(2L));
////            System.out.println(service.getByName("Closed company"));
//            Location location = new Location(6, "Sixth location", 1, 1);
//            service.remove(location);

//            DeliveryTypeService service = new DeliveryTypeService();
//            DeliveryType deliveryType = new DeliveryType();
//            deliveryType.setTypeName("new DTU");
//            deliveryType.setId(5);
////            service.add(deliveryType);
////            System.out.println(service.getAll());
////            System.out.println(service.getByName("new DT"));
////            service.update(deliveryType);
////            System.out.println(service.getById(5L));
//            service.remove(deliveryType);
//            System.out.println(service.getAll());

//            RoleService service = new RoleService();
//            Role role = new Role();
//            role.setRoleName("newRoleU");
//            role.setId(4);
////            service.add(role);
////            System.out.println(service.getAll());
////            System.out.println(service.getById(1L));
////            System.out.println(service.getByName("newRole"));
////            service.update(role);
//            service.remove(role);
//            System.out.println(service.getAll());

//        ReceiptStatusService service = new ReceiptStatusService();
//        ReceiptStatus receiptStatus = new ReceiptStatus();
//        receiptStatus.setStatusName("newStatusU");
//        receiptStatus.setId(6);
////        service.add(receiptStatus);
////        System.out.println(service.getAll());
////            System.out.println(service.getById(2L));
////            System.out.println(service.getByName("newStatus"));
//            service.remove(receiptStatus);
//            System.out.println(service.getAll());

//            TariffService service = new TariffService();
//            Tariff tariff = new Tariff();
//            tariff.setTariffName("newTarU");
//            tariff.setTariffPrice(6.99);
//            tariff.setId(4);
////            service.add(tariff);
////            System.out.println(service.getById(2L));
////            System.out.println(service.getByName("newTar"));
//            service.remove(tariff);
//            System.out.println(service.getAll());

//            UserService service = new UserService();
//            User user = new User();
//            user.setId(21);
//            user.setUsername("newUserU");
//            user.setEmail("newEmailU");
//            user.setPassword("111");
//            user.setRoleId(1);
////
////            service.add(user, new Role(1, "manager"));
////            System.out.println(service.getById(2L));
////            System.out.println(service.getByUsername("newUser"));
//            service.remove(user);
//            System.out.println(service.getAll());

//            ReceiptService service = new ReceiptService();
//            User user = new User();
//            user.setId(2);
////            User manager = new User();
////            manager.setId(1);
////            ReceiptStatus receiptStatus = new ReceiptStatus();
////            receiptStatus.setId(1);
//            Receipt receipt = new Receipt();
//            receipt.setPrice(155);
//            receipt.setId(3);
//            receipt.setUserId(2);
//            receipt.setManagerId(1);
//            receipt.setReceiptStatusId(2);
////            service.add(receipt, user, manager, receiptStatus);
////            System.out.println(service.getAll());
////            System.out.println(service.getByManagerId(2L));
//            service.remove(receipt);
//            System.out.println(service.getAll());

//            DeliveryOrderDAOImplementation service = new DeliveryOrderDAOImplementation();
//            DeliveryOrder deliveryOrder = new DeliveryOrder();
//            deliveryOrder.setId(4);
//            deliveryOrder.setLocationFromID(2);
//            deliveryOrder.setLocationToId(1);
//            deliveryOrder.setAddress("new AdressU");
//            deliveryOrder.setDeliveryTypeId(1);
//            deliveryOrder.setWeight(1115);
//            deliveryOrder.setVolume(1115);
//            deliveryOrder.setTariffId(1);
//            deliveryOrder.setReceiptId(1);
//            service.add(deliveryOrder);
////            System.out.println(service.getAll());
////            System.out.println(service.getByLocationFromId(1L));
////            System.out.println(service.getByLocationToId(1L));
//            System.out.println(service.remove(deliveryOrder));
//            System.out.println(service.getAll());

//            UserDAOImplementation service = new UserDAOImplementation();
//////            System.out.println(service.getAll());
//            System.out.println(service.getByUsername("name"));

//            ReceiptStatusDAOImplementation service = new ReceiptStatusDAOImplementation();
//            System.out.println(service.getByName("Delivering"));

/*            RoleDAOImplementation service = new RoleDAOImplementation();
            System.out.println(service.getByName("manager"));*/

//            DeliveryOrderDAOImplementation deliveryOrderDAOImplementation = new DeliveryOrderDAOImplementation();
//            DeliveryOrder deliveryOrder = new DeliveryOrder();
//            deliveryOrder.setLocationFromID(1); //1
//            deliveryOrder.setLocationToId(2); //2
//            deliveryOrder.setCargoName("someNameNew"); //3
//            deliveryOrder.setCargoDescription("someNewDesc"); //4
//            deliveryOrder.setAddress("dsds"); //5
//            deliveryOrder.setDeliveryTypeId(1); //6
//            deliveryOrder.setWeight(45); //7
//            deliveryOrder.setVolume(4545); //8
//            deliveryOrder.setReceivingDate(null); //9
//            deliveryOrder.setTariffId(1); //10
//            System.out.println(deliveryOrder);

//            deliveryOrderDAOImplementation.add(deliveryOrder);
//            System.out.println(deliveryOrderDAOImplementation.getAll());
//            System.out.println(deliveryOrderDAOImplementation.getByLocationFromId(1L));
//            deliveryOrderDAOImplementation.update(deliveryOrder);
            LocationBeanDAOImpl service = new LocationBeanDAOImpl();
            System.out.println(service.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}