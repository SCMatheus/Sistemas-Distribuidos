/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserviceecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;

/**
 *
 * @author mathe
 */
@WebService(endpointInterface = "webserviceecommerce.ClientServer")
public class ClientServerImpl implements ClientServer {
    private final String url = "jdbc:postgresql://192.168.50.69:5432/postgres";
    private final String user = "usuario";
    private final String password = "postgres";
    Connection conn = null;
    PreparedStatement stmt = null;
    /**
     *
     */
    public ClientServerImpl(){
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public String listarIten(int id) {
        String resposta = "";
        String sql = "SELECT * FROM itens WHERE id = " + id;
        try { 
        stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        resposta = rs.getInt(1) + ": " + rs.getString(2) + ": " + rs.getInt(3) + ": "+ rs.getInt(4)+ "\n";
        
        
        }catch (SQLException ex) {
            Logger.getLogger(ClientServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resposta;
    }

    @Override
    public String listarTodosOsItens() {
        String resposta = "";
        String sql = "SELECT * FROM itens ORDER BY id";
        try { 
        stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            resposta += rs.getInt(1) + ": " + rs.getString(2) + ": " + rs.getInt(3) + ": "+ rs.getInt(4)+ "\n";
        }
        
        }catch (SQLException ex) {
            Logger.getLogger(ClientServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resposta;
    }

    @Override
    public boolean comprarIten(int id, int qnt) {
        String resposta = "";
        String sql = "SELECT quantidade, vendas FROM itens WHERE id = " + id;
        try { 
        stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int quantidade = rs.getInt(1);
        int vendas = rs.getInt(2);
        
        
        if (quantidade - qnt >= 0 ){
            quantidade -= qnt;
            
            sql = "UPDATE itens SET quantidade = " + quantidade + "WHERE id = " + id;
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            vendas += qnt; 
            sql = "UPDATE itens SET vendas = " + vendas + "WHERE id = " + id;
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }else
        {
            return false;
        }
        }catch (SQLException ex) {
            Logger.getLogger(ClientServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
