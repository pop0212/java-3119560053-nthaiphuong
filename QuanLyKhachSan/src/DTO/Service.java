
package DTO;

import DAL.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Service {
    private static PreparedStatement ps;
    private static Connection conn = new Database().conn;
    private int id;
    private String name,description;
    private int price;

    public Service() {
    }

    public Service(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Service(int id, String name, String description, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public static ArrayList<Service> SelectAll(){
        ArrayList<Service> list_staff = new ArrayList<>();
        String sql = "SELECT * FROM service";
        try {
            Statement s = conn.createStatement();
//          PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Service c = new Service();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("Name"));
                c.setDescription(rs.getString("Description"));
                c.setPrice(rs.getInt("Price"));
                list_staff.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list_staff;
    }
    
    public static Service SelectbyId(int id){
        Service c = new Service();
        String sql = "SELECT * FROM service where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("Name"));
                c.setDescription(rs.getString("Description"));
                c.setPrice(rs.getInt("Price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return c;
    }
    public static boolean addService(Service s){
        
        String sql = "INSERT INTO `service`(`Name`,`Description`, `Price`)"
                + "VALUES(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getDescription());
            ps.setInt(3, s.getPrice());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean editService(Service s,String name,String description,int price){
        
        String sql = "UPDATE `service` SET `Name`=?,`Description`=?,`Price`=? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, price);
            ps.setInt(4, s.getId());
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean deleteService(Service s){
        
        String sql = "DELETE FROM service where id = ?";
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
