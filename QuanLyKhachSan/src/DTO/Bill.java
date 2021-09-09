
package DTO;

import java.sql.Timestamp;
import DAL.Database;
import DTO.Booking;
import DTO.Bill;
import DTO.Staff;
import java.util.ArrayList;
import java.sql.*;

public class Bill {
    private static PreparedStatement ps;
    private static Connection conn = new Database().conn;
    int id;
    Booking booking;
    Use_service use_service;
    Staff staff;
    int price;
    Timestamp timestamp;
    public Bill() {
    }

    public Bill(int id, Booking booking, Use_service use_service, Staff staff, int price) {
        this.id = id;
        this.booking = booking;
        this.use_service = use_service;
        this.staff = staff;
        this.price = price;
    }

    public Bill(Booking booking, Use_service use_service, Staff staff, int price) {
        this.booking = booking;
        this.use_service = use_service;
        this.staff = staff;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Use_service getUse_service() {
        return use_service;
    }

    public void setUse_service(Use_service use_service) {
        this.use_service = use_service;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public static ArrayList<Bill> SelectAll(){
        ArrayList<Bill> list_staff = new ArrayList<>();
        String sql = "SELECT * FROM bill";
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Bill c = new Bill();
                c.setId(rs.getInt("id"));
                c.setBooking(Booking.SelectbyId(rs.getInt("id_booking")));
                c.setPrice(rs.getInt("Price"));
                c.setStaff(Staff.SelectbyId(rs.getInt("id_staff")));
                c.setTimestamp(rs.getTimestamp("Date"));
                list_staff.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list_staff;
    }
    
    public static Bill SelectbyId(int id){
        Bill c = new Bill();
        String sql = "SELECT * FROM bill where id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
                c.setId(rs.getInt("id"));
                c.setBooking(Booking.SelectbyId(rs.getInt("id_booking")));
                c.setPrice(rs.getInt("Price"));
                c.setStaff(Staff.SelectbyId(rs.getInt("id_staff")));
                c.setTimestamp(rs.getTimestamp("Date"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return c;
    }
    
    public static boolean addBill(Bill s){
        
        String sql = "INSERT INTO `bill`(`id_booking`,`Price`,`id_staff`)"
                + "VALUES(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, s.getBooking().getId());
            ps.setInt(2, s.getPrice());
            ps.setInt(3, s.getStaff().getId());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean editBill(Bill s,Booking booking,int price,Staff staff){
        
        String sql = "UPDATE `bill` SET `id_booking`=?,`Price`=?,`id_staff`=? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,booking.getId() );
            ps.setInt(2, price);
            ps.setInt(3, staff.getId());
            ps.setInt(4, s.getId());
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean deleteBill(Bill s){
        
        String sql = "DELETE FROM bill where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, s.getId());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
