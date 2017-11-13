/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.management.system;

import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
/**
 * This class create JTable from Database table.
 * User program needs to specify database connection and corresponding atable name.
 * @author Hemraj
 */
public class books1{
    //private String table;
    private Connection con;
    public books1(Connection con){
        this.con=con;
    }
    /**
     * This method return JTable object created from Database table having same data asn structure
     * as in original table into database.
     * @param table Name of the database table to be coverted to JTable
     * @return JTable object that consist of data and structure of Database table
     * @throws java.lang.Exception Original object is deferent, e.i either SQLException or NullPointerException
     */
    public JTable getTable(String table)throws Exception{
        JTable t1=new JTable();
        DefaultTableModel dm=new DefaultTableModel();
        Statement st=con.createStatement();   
        ResultSet rs=st.executeQuery("select * from "+table);
        ResultSetMetaData rsmd=rs.getMetaData();
        //Coding to get columns-
        int cols=rsmd.getColumnCount();
        String c[]=new String[cols];
        for(int i=0;i<cols;i++){
            c[i]=rsmd.getColumnName(i+1);
            dm.addColumn(c[i]);
        }
        //get data from rows
        Object row[]=new Object[cols];
        while(rs.next()){
             for(int i=0;i<cols;i++){
                    row[i]=rs.getString(i+1);
                }
            dm.addRow(row);
        }
        t1.setModel(dm);
        con.close();
        return t1;
    }
    /**
     * This method return JTable object created from Database table having selected data and structure
     * as in original table into database.
     * @param table Name of the database table to be coverted to JTable
     * @param query Select query to specify selected columns and data to extracted from database table
     * @return JTable object that consist of selected data and structure of Database table
     * @throws java.lang.Exception Original object is deferent, e.i either SQLException or NullPointerException
     */
    public JTable getTable(String table,String query)throws Exception{
        JTable t1=new JTable();
        DefaultTableModel dm=new DefaultTableModel();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        ResultSetMetaData rsmd=rs.getMetaData();
        //Coding to get columns-
        int cols=rsmd.getColumnCount();
        String c[]=new String[cols];
        for(int i=0;i<cols;i++){
            c[i]=rsmd.getColumnName(i+1);
            dm.addColumn(c[i]);
        }


        //get data from rows
        Object row[]=new Object[cols];
        while(rs.next()){
             for(int i=0;i<cols;i++){
                    row[i]=rs.getString(i+1);
                }
            dm.addRow(row);
        }
        t1.setModel(dm);
        con.close();
        return t1;
    }


    public static void main(String ar[])throws Exception{
        JFrame f=new JFrame("Books Table");
        String dataSourceName = "login";
        String dbURL = "jdbc:odbc:" + dataSourceName;
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection con=DriverManager.getConnection(dbURL, "ify","ify123");
        library.management.system.books1 obj = new library.management.system.books1(con);
        JScrollPane sp=new JScrollPane(obj.getTable("books"));
        f.add(sp);
        f.setBounds(200,200,1200,300);
        f.setVisible(true);       
    }
}
