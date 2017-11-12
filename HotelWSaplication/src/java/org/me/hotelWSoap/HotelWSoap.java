/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.hotelWSoap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;


@WebService(serviceName = "HotelWS")
public class HotelWSoap {

    /**
     * Web service operation
     * @param id_hotel
     * @param fecha
     * @return 
     */
    @WebMethod(operationName = "consulta_libres")
    public int consulta_libres(@WebParam(name = "id_hotel") int id_hotel, @WebParam(name = "fecha") int fecha) {

        int hab_libres = 0;
        Connection connection = null;
        try {            
            // load the sqlite-JDBC driver using the current class loader
            Class.forName("org.sqlite.JDBC");        

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/Toni/Documents/Universitat/AD/LAB/p4.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //hotel_fecha (id_hotel, fecha, num_hab_ocupadas, num_hab_libres)

            ResultSet rs = statement.executeQuery("select num_hab_libres "
                                                + "from hotel_fecha "
                                                + "where id_hotel = " + id_hotel + " and fecha = " + fecha);
            
            if (rs.next()) hab_libres = Integer.parseInt(rs.getString("num_hab_libres"));
            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return hab_libres;
    }

    /**
     * Web service operation
     * @param id_hotel
     * @param fecha
     * @return 
     */
    @WebMethod(operationName = "reserva_habitacion")
    public int reserva_habitacion(@WebParam(name = "id_hotel") int id_hotel, @WebParam(name = "fecha") int fecha) {
        
        int hab_ocupadas = -1;
        Connection connection = null;
        try {
            // load the sqlite-JDBC driver using the current class loader
            Class.forName("org.sqlite.JDBC");        

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/Toni/Documents/Universitat/AD/LAB/p4.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //hotel_fecha (id_hotel, fecha, num_hab_ocupadas, num_hab_libres)
            ResultSet rs = statement.executeQuery("select num_hab_libres "
                                                + "from hotel_fecha "
                                                + "where id_hotel = " + id_hotel + " and fecha = " + fecha);
            rs.next();
            if (Integer.parseInt(rs.getString("num_hab_libres")) != 0) {
                statement.executeUpdate("update hotel_fecha "
                                      + "set num_hab_ocupadas = (num_hab_ocupadas + 1), num_hab_libres = (num_hab_libres - 1) "
                                      + "where id_hotel = " + id_hotel + " and fecha = " + fecha);
                
                rs = statement.executeQuery("select num_hab_ocupadas "
                                          + "from hotel_fecha "
                                          + "where id_hotel = " + id_hotel + " and fecha = " + fecha);
                if (rs.next()) hab_ocupadas = Integer.parseInt(rs.getString("num_hab_ocupadas"));
                else hab_ocupadas = -2;
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return hab_ocupadas;
    }
}
