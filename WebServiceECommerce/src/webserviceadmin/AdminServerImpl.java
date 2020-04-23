/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserviceadmin;

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
@WebService(endpointInterface = "webserviceadmin.AdminServer")
public class AdminServerImpl implements AdminServer {
    private final String url = "jdbc:postgresql://192.168.50.69:5432/postgres";
    private final String user = "usuario";
    private final String password = "postgres";
    Connection conn = null;
    PreparedStatement stmt = null;
    /**
     *
     */
    public AdminServerImpl(){
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
        resposta = "id: " +rs.getInt(1) + " item: " + rs.getString(2) + " estoque: " + rs.getInt(3) + " vendidos: "+ rs.getInt(4)+ "\n";
        
        
        }catch (SQLException ex) {
            Logger.getLogger(AdminServerImpl.class.getName()).log(Level.SEVERE, null, ex);
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
            resposta += "id: " +rs.getInt(1) + " item: " + rs.getString(2) + " estoque: " + rs.getInt(3) + " vendas: "+ rs.getInt(4)+ "\n";
        }
        
        }catch (SQLException ex) {
            Logger.getLogger(AdminServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resposta;
    }

    @Override
    public boolean inserirIten(int id,String nome, int quantidade, int vendas) {
        String sql = "INSERT INTO itens (id, nome, quantidade, vendas) VALUES(?, ?, ?, ?)";
       
        try { 
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, nome.toUpperCase());
            stmt.setInt(3, quantidade);
            stmt.setInt(4, vendas);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(AdminServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;

    }

    @Override
    public String listarCompras() {
        String resposta = "";
        String sql = "SELECT id, nome, vendas FROM itens ORDER BY id";
        try { 
        stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            resposta += "id: " +rs.getInt(1) + " item: " + rs.getString(2) + " vendas: " + rs.getInt(3) + "\n";
        }
        
        }catch (SQLException ex) {
            Logger.getLogger(AdminServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resposta;
    }

    @Override
    public boolean excluirIten(int id) {
        String sql = "DELETE FROM itens WHERE id = " + id;
        try { 
        stmt = conn.prepareStatement(sql);
        stmt.execute();
       
        }catch (SQLException ex) {
            Logger.getLogger(AdminServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
        return true;
        
    }

    @Override
    public boolean alterarNomeIten(int id, String nome) {
        String sql = "UPDATE itens SET nome = ? WHERE id = ?";
        try { 
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, nome.toUpperCase());
        stmt.setInt(2, id);
        stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(AdminServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean alterarQuantidadeIten(int id, int quantidade) {
        String sql = "UPDATE itens SET quantidade = " + quantidade + "WHERE id = " + id;
        try { 
        stmt = conn.prepareStatement(sql);
        stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(AdminServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean alterarVendasIten(int id, int vendas) {
        String sql = "UPDATE itens SET vendas = " + vendas + "WHERE id = " + id;
        
        try { 
        stmt = conn.prepareStatement(sql);
        stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(AdminServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
