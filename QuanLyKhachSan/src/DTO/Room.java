package DTO;

import DAL.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Room {
    private static PreparedStatement ps;
    private static Connection conn = new Database().conn;
    private int id;
    private String name;
    private int bed,price;
    private boolean vip,booked;

    public Room() {
    }

    public Room(String name, int bed, int price, boolean vip, boolean booked) {
        this.name = name;
        this.bed = bed;
        this.price = price;
        this.vip = vip;
        this.booked = booked;
    }

    public Room(int id, String name, int bed, int price, boolean vip, boolean booked) {
        this.id = id;
        this.name = name;
        this.bed = bed;
        this.price = price;
        this.vip = vip;
        this.booked = booked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
    public static ArrayList<Room> SelectAll(){
        ArrayList<Room> list_staff = new ArrayList<>();
        String sql = "SELECT * FROM room";
        try {
            Statement s = conn.createStatement();
//          PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Room c = new Room();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("Name"));
                c.setBed(rs.getInt("Bed"));
                c.setPrice(rs.getInt("Price"));
                c.setVip(rs.getBoolean("Vip"));
                c.setBooked(rs.getBoolean("Booked"));
                list_staff.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list_staff;
    }
    public static Room SelectbyId(int id){
        Room c = new Room();
        String sql = "SELECT * FROM room where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("Name"));
            c.setBed(rs.getInt("Bed"));
            c.setPrice(rs.getInt("Price"));
            c.setVip(rs.getBoolean("Vip"));
            c.setBooked(rs.getBoolean("Booked"));
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return c;
    }
    
    public static boolean addRoom(Room s){
        
        String sql = "INSERT INTO `room`(`Name`,`Bed`, `Price`, `Vip`,`Booked`)"
                + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setInt(2, s.getBed());
            ps.setInt(3, s.getPrice());
            ps.setBoolean(4, s.isVip());
            ps.setBoolean(5, s.isBooked());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean editRoom(Room s,String name,int bed,int price,boolean vip,boolean booked){
        
        String sql = "UPDATE `room` SET `Name`=?,`Bed`=?,`Price`=?,`Vip`=? , `Booked`=? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, bed);
            ps.setInt(3, price);
            ps.setBoolean(4, vip);
            ps.setBoolean(5, booked);
            ps.setInt(6, s.getId());
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean deleteRoom(Room s){
        
        String sql = "DELETE FROM room where id = ?";
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
