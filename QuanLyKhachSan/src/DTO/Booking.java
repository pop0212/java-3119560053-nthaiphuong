package DTO;
import DAL.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import  java.sql.Timestamp;
import java.util.ArrayList;


public class Booking {
    private static PreparedStatement ps;
    private static Connection conn = new Database().conn;
    int id;
    Customer customer;
    Room room;
    Staff staff;
    Timestamp timestamp;
    Boolean payed;

    public Booking(Customer customer, Room room, Staff staff, Timestamp timestamp, Boolean payed) {
        this.customer = customer;
        this.room = room;
        this.staff = staff;
        this.timestamp = timestamp;
        this.payed = payed;
    }

    public Booking(Customer customer, Room room, Staff staff, Boolean payed) {
        this.customer = customer;
        this.room = room;
        this.staff = staff;
        this.payed = payed;
    }

    public Booking() {
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }
    
    public static ArrayList<Booking> SelectAll(){
        ArrayList<Booking> list_staff = new ArrayList<>();
        String sql = "SELECT * FROM booking";
        try {
            Statement s = conn.createStatement();
//          PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Booking c = new Booking();
                c.setId(rs.getInt("id"));
                c.setCustomer(Customer.SelectbyId(rs.getInt("id_customer")));
                c.setRoom(Room.SelectbyId(rs.getInt("id_room")));
                c.setTimestamp(rs.getTimestamp("Date"));
                c.setStaff(Staff.SelectbyId(rs.getInt("id_staff")));
                c.setPayed(rs.getBoolean("Payed"));
                list_staff.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list_staff;
    }
    
    public static Booking SelectbyId(int id){
        Booking c = new Booking();
        String sql = "SELECT * FROM booking where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            c.setId(rs.getInt("id"));
                c.setCustomer(Customer.SelectbyId(rs.getInt("id_customer")));
                c.setRoom(Room.SelectbyId(rs.getInt("id_room")));
                c.setTimestamp(rs.getTimestamp("Date"));
                c.setStaff(Staff.SelectbyId(rs.getInt("id_staff")));
                c.setPayed(rs.getBoolean("Payed"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return c;
    }
    public static ArrayList<Booking> SelectbyKeyword(String keyword){
        ArrayList<Booking> list_staff = new ArrayList<>();
        String sql = "SELECT * FROM `booking` b INNER JOIN customer c on b.id_customer=c.id INNER JOIN room r on b.id_room=r.id INNER JOIN staff s ON b.id_staff=s.id WHERE c.Name LIKE ? OR c.Sdt LIKE ? OR c.Cmnd LIKE ? OR c.Address LIKE ? OR r.Name LIKE ? OR s.Name LIKE ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ps.setString(4, "%" + keyword + "%");
            ps.setString(5, "%" + keyword + "%");
            ps.setString(6, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Booking c = new Booking();
                c.setId(rs.getInt("id"));
                c.setCustomer(Customer.SelectbyId(rs.getInt("id_customer")));
                c.setRoom(Room.SelectbyId(rs.getInt("id_room")));
                c.setTimestamp(rs.getTimestamp("Date"));
                c.setStaff(Staff.SelectbyId(rs.getInt("id_staff")));
                c.setPayed(rs.getBoolean("Payed"));
                list_staff.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list_staff;
    }
    
    public static boolean addBooking(Booking s){
        int id = Customer.addCustomer(s.getCustomer());
        String sql = "INSERT INTO `booking`(`id_customer`, `id_room`,`id_staff`,`Payed`)"
                + "VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, s.getRoom().getId());
            ps.setInt(3, s.getStaff().getId());
            ps.setBoolean(4, s.getPayed());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean editBooking(Booking s,Customer customer , Boolean payed){
        Customer.editCustomer(s.getCustomer(), customer.getName(), customer.getSdt(), customer.getCmnd(), customer.getAddress());
        String sql = "UPDATE `booking` SET `Payed`=? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1,payed);
            ps.setInt(2, s.getId());
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean deleteBooking(Booking s){
        Use_service.deleteByid_customer(s.getCustomer().getId());
        String sql = "DELETE FROM booking where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, s.getId());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Customer.deleteCustomer(s.getCustomer());
        
        return false;
    }
}
