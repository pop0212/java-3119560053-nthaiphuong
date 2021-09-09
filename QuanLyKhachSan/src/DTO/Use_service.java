package DTO;

import DAL.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Use_service {
    private static PreparedStatement ps;
    private static Connection conn = new Database().conn;
    int id;
    Customer customer;
    Booking booking;
    Service service;
    Timestamp timestamp;
    ArrayList<Service> list_service;
    ArrayList<Timestamp> date;

    public Use_service() {
    }

    public Use_service(int id, Customer customer, Booking booking, ArrayList<Service> list_service, ArrayList<Timestamp> days) {
        this.id = id;
        this.customer = customer;
        this.booking = booking;
        this.list_service = list_service;
        this.date = days;
    }

    public Use_service(Customer customer, Booking booking, ArrayList<Service> list_service, ArrayList<Timestamp> days) {
        this.customer = customer;
        this.booking = booking;
        this.list_service = list_service;
        this.date = days;
    }
    
    public Use_service(Customer customer, Booking booking, Service service, Timestamp timestamp) {
        this.customer = customer;
        this.booking = booking;
        this.service = service;
        this.timestamp = timestamp;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<Service> getList_service() {
        return list_service;
    }

    public void setList_service(ArrayList<Service> list_service) {
        this.list_service = list_service;
    }

    public ArrayList<Timestamp> getDate() {
        return date;
    }

    public void setDate(ArrayList<Timestamp> date) {
        this.date = date;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    public static ArrayList<Use_service> SelectAll(){
        ArrayList<Use_service> list_service = new ArrayList<>();
        String sql = "SELECT *,b.id id_booking FROM `use_service` us INNER JOIN booking b ON us.id_customer=b.id_customer GROUP BY us.id_customer; ";
        try {
            Statement s = conn.createStatement();
//          PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Use_service c = new Use_service();
                c.setId(rs.getInt("id"));
                c.setCustomer(Customer.SelectbyId(rs.getInt("id_customer")));
                c.setBooking(Booking.SelectbyId(rs.getInt("id_booking")));
                int id = rs.getInt("id_customer");
                ArrayList<Timestamp> days = new ArrayList<>();
                ArrayList<Service> list_service1 = new ArrayList<>();
                String sql1 = "SELECT * FROM `use_service` us INNER JOIN service s on us.id_service = s.id WHERE us.id_customer=?;";
                try {
                    PreparedStatement ps = conn.prepareStatement(sql1);
                    ps.setInt(1, id);
                    ResultSet rs1 = ps.executeQuery();
                    while(rs1.next()){
                        days.add(rs1.getTimestamp("Date"));
                        Service f = new Service();
                        f.setId(rs1.getInt("id_service"));
                        f.setName(rs1.getString("Name"));
                        f.setDescription(rs1.getString("Description"));
                        f.setPrice(rs1.getInt("Price"));
                        list_service1.add(f);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                c.setDate(days);
                c.setList_service(list_service1);
                list_service.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list_service;
    }
    
    public static ArrayList<Use_service> SelectAll_withoutGroupBy(){
        ArrayList<Use_service> list_service = new ArrayList<>();
        String sql = "SELECT * FROM `use_service`; ";
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Use_service c = new Use_service();
                c.setId(rs.getInt("id"));
                c.setCustomer(Customer.SelectbyId(rs.getInt("id_customer")));
                c.setService(Service.SelectbyId(rs.getInt("id_service")));
                c.setTimestamp(rs.getTimestamp("Date"));
                list_service.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list_service;
    }
    
    
    public static Use_service SelectbyIdCustomer(int id){
        Use_service c = null;
        String sql = "SELECT *,b.id id_booking FROM `use_service` us INNER JOIN booking b ON us.id_customer=b.id_customer where us.id_customer = ? GROUP BY us.id_customer; ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                c = new Use_service();
                c.setId(rs.getInt("id"));
                c.setCustomer(Customer.SelectbyId(rs.getInt("id_customer")));
                c.setBooking(Booking.SelectbyId(rs.getInt("id_booking")));
                ArrayList<Timestamp> days = new ArrayList<>();
                ArrayList<Service> list_service = new ArrayList<>();
                String sql1 = "SELECT * FROM `use_service` us INNER JOIN service s on us.id_service = s.id WHERE us.id_customer=?;";
                try {
                    PreparedStatement ps1 = conn.prepareStatement(sql1);
                    ps1.setInt(1, id);
                    ResultSet rs1 = ps1.executeQuery();
                    while(rs1.next()){
                        days.add(rs1.getTimestamp("Date"));
                        Service f = new Service();
                        f.setId(rs1.getInt("id_service"));
                        System.out.println(id);
                        f.setName(rs1.getString("Name"));
                        System.out.println("2");
                        f.setDescription(rs1.getString("Description"));
                        f.setPrice(rs1.getInt("Price"));
                        list_service.add(f);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                c.setDate(days);
                c.setList_service(list_service);
            }           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return c ;
    }
    
    
    public static Use_service SelectbyId(int id){
        Use_service c = new Use_service();
        String sql = "SELECT * FROM use_service where id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            c.setId(rs.getInt("id"));
                c.setCustomer(Customer.SelectbyId(rs.getInt("id_customer")));
                ArrayList<Service> list_service = new ArrayList<>();
                ArrayList<Timestamp> days = new ArrayList<>();
                String sql1 = "SELECT * FROM `use_service` us INNER JOIN service s on us.id_service = s.id WHERE us.id_customer=?;";
                try {
                    PreparedStatement ps1 = conn.prepareStatement(sql1);
                    ResultSet rs1 = ps1.executeQuery();
                    while(rs1.next()){
                        days.add(rs1.getTimestamp("Date"));
                        Service f = new Service();
                        f.setId(rs1.getInt("id_service"));
                        f.setName(rs1.getString("Name"));
                        f.setDescription(rs1.getString("Description"));
                        f.setPrice(rs1.getInt("Price"));
                        list_service.add(f);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                c.setDate(days);
                c.setList_service(list_service);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return c;
    }
    
    
    
    public static boolean addUse_service(Use_service s){
        for (int i = 0; i < s.getList_service().size(); i++) {
            String sql = "INSERT INTO `use_service`(`id_customer`,`id_service`)"
                + "VALUES(?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, s.getCustomer().getId());
                ps.setInt(2, s.getList_service().get(i).getId());
            
                return ps.executeUpdate() > 0;
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    public static boolean editUse_service(Use_service s,Customer customer,Service service,int days){
        
        String sql = "UPDATE `use_service` SET `id_customer`=?,`id_service`=? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,customer.getId() );
            ps.setInt(1,service.getId() );
            ps.setInt(4, s.getId());
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean deleteUse_serviceALL(Use_service s){
        
        String sql = "DELETE FROM use_service where id_customer = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, s.getCustomer().getId());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean deleteByid_customer(int id){
        
        String sql = "DELETE FROM use_service where id_customer = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean deleteUse_service(Use_service s,int id, Timestamp time){
        
        String sql = "DELETE FROM use_service where id_customer = ? and id_service= ? and Date = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, s.getCustomer().getId());
            ps.setInt(2, id);
            ps.setTimestamp(3, time);
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
