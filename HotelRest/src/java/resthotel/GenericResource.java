/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resthotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of resthotel.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
        /**
     * Método POST para comprobar los asientos libres dados un id y una fecha
     *
     * @param id_hotel
     * @param fecha
     * @return
     */
    @Path("consultaLibres")
    @GET
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/html")
    public String consultalibres(@QueryParam("id") String id_hotel, @QueryParam("fecha") String fecha) {
        // Completar codigo
        int hab_libres = 0;
        Connection connection = null;
        try {
            // load the sqlite-JDBC driver using the current class loader
            Class.forName("org.sqlite.JDBC");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/Toni/Documents/Universitat/AD/LAB/p4.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select num_hab_libres "
                                                + "from hotel_fecha "
                                                + "where id_hotel = " + Integer.parseInt(id_hotel) + " and fecha = " + Integer.parseInt(fecha));
            
            if (rs.next()) hab_libres = Integer.parseInt(rs.getString("num_hab_libres"));
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        
                String resultado="";
        if(hab_libres < 0) resultado =  "Ha habido un error!";
        else if(hab_libres == 0)resultado =  "No hay habitaciones libres!";
        else resultado =  "El numero de habitaciones libres es de: "+ hab_libres;
             return resultado;
    }
    
    /**
     * Método POST para comprobar los asientos libres dados un id y una fecha
     *
     * @param id_hotel
     * @param fecha
     * @return
     */
    @Path("reservarHab")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/html")
    public String reserva(@FormParam("id") String id_hotel, @FormParam("fecha") String fecha) {
        // Completar codigo
        int hab_ocupadas = -1;
        Connection connection = null;
        try {
            // load the sqlite-JDBC driver using the current class loader
            Class.forName("org.sqlite.JDBC");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/Toni/Documents/Universitat/AD/LAB/p4.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("create table if not exists hotel_fecha(id_hotel integer primary key, fecha integer, num_hab_ocupadas integer, num_hab_libres integer)");
            ResultSet rs = statement.executeQuery("select * from hotel_fecha");
            boolean esta = false;
            while (rs.next()) {
                esta = true;
            }
            if (!esta) {
                statement.executeUpdate("insert into hotel_fecha values(1, 20171107, 0, 100)");
                statement.executeUpdate("insert into hotel_fecha values(2, 20171107, 0, 50)");
                statement.executeUpdate("insert into hotel_fecha values(3, 20171107, 0, 10)");
            }

            //hotel_fecha (id_hotel, fecha, num_hab_ocupadas, num_hab_libres)
            rs = statement.executeQuery("select num_hab_libres "
                                                + "from hotel_fecha "
                                                + "where id_hotel = " + Integer.parseInt(id_hotel) + " and fecha = " + Integer.parseInt(fecha));
            rs.next();
            if (Integer.parseInt(rs.getString("num_hab_libres")) != 0) {
                statement.executeUpdate("update hotel_fecha "
                                      + "set num_hab_ocupadas = (num_hab_ocupadas + 1), num_hab_libres = (num_hab_libres - 1) "
                                      + "where id_hotel = " + Integer.parseInt(id_hotel) + " and fecha = " + Integer.parseInt(fecha));
                
                rs = statement.executeQuery("select num_hab_ocupadas "
                                          + "from hotel_fecha "
                                          + "where id_hotel = " + Integer.parseInt(id_hotel) + " and fecha = " + Integer.parseInt(fecha));
                rs.next();
                hab_ocupadas = Integer.parseInt(rs.getString("num_hab_ocupadas"));

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
        String resultado= "";
        if(hab_ocupadas < 0) resultado= "-1";
        else resultado= "El numero de habiatciones ocupadas despues de la reserva es:"+hab_ocupadas;
                     return resultado;
    }
}
