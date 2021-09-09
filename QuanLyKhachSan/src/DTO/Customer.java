package DTO;

import DAL.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Customer {
    private static PreparedStatement ps;
    private static Connection conn = new Database().conn;
    private int id;
    private String name,sdt,cmnd,address;

    public Customer() {
    }

    public Customer(String name, String sdt, String cmnd, String address) {
        this.name = name;
        this.sdt = sdt;
        this.cmnd = cmnd;
        this.address = address;
    }

    public Customer(int id, String name, String sdt, String cmnd, String address) {
        this.id = id;
        this.name = name;
        this.sdt = sdt;
        this.cmnd = cmnd;
        this.address = address;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public static ArrayList<Customer> SelectAll(){
        ArrayList<Customer> list_staff = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("Name"));
                c.setSdt(rs.getString("Sdt"));
                c.setCmnd(rs.getString("Cmnd"));
                c.setAddress(rs.getString("Address"));
                list_staff.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list_staff;
    }
    
    public static Customer SelectbyId(int id){
        Customer c = new Customer();
        String sql = "SELECT * FROM customer where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("Name"));
            c.setSdt(rs.getString("Sdt"));
            c.setCmnd(rs.getString("Cmnd"));
            c.setAddress(rs.getString("Address"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return c;
    }
    
    public static int addCustomer(Customer s){
        
        String sql = "INSERT INTO `customer`(`Name`,`Sdt`, `Cmnd`, `Address`)"
                + "VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getSdt());
            ps.setString(3, s.getCmnd());
            ps.setString(4, s.getAddress());
            ps.executeUpdate();
            sql = "SELECT * FROM customer ORDER BY id DESC  LIMIT 1";
            try {
                ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                rs.next();
                return rs.getInt("id");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public static boolean editCustomer(Customer s,String name,String sdt,String cmnd,String address){
        
        String sql = "UPDATE `customer` SET `Name`=?,`Sdt`=?,`Cmnd`=?,`Address`=? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, sdt);
            ps.setString(3, cmnd);
            ps.setString(4, address);
            ps.setInt(5, s.getId());
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean deleteCustomer(Customer s){
        
        String sql = "DELETE FROM customer where id = ?";
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
