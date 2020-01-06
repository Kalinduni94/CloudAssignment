<%-- Document   : populateselect.jsp --%>

<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Scanner"%>
<%
     
    try
    {
        //accessing the database uspresidents
       //parameters used to access the db
        String database = "jdbc:mysql://localhost:3306/nbastat";
        String user = "root";
        String password = "";
        
        //loading jdbc driver
        Class.forName("com.mysql.jdbc.Driver");
        
        //creating an object of class Connection
        Connection conndb = DriverManager.getConnection(database, user, password);
        
        //creating a SQL command to select all records in the DB
        String SQLselect = "select * from country_table";
        
        //to run above command we need to have two objects: Statement and ResultSet
        //1. Object of Class Statement
        Statement mystat = conndb.createStatement();
        
        //2. Object of Class ResultSet
        ResultSet results = mystat.executeQuery(SQLselect);
        
        //sending back to HTML the start of the select element
        out.println("<select id='countryList' onchange='getinfo()'>");
        out.println("<option value=''>Select a Country</option>");
        
        //extracting the cocerned table fields from object results
        while(results.next())
        {
            String countryName = results.getString ("country");
            String capitalName = results.getString ("capital");
            String disMiles = results.getString ("miles");
            String disKilometers = results.getString ("kilometers");
            
            //Let's send back to HTML this option: user sees country but the value is 
            //capital,distance
            out.println("<option value='"+capitalName+","+disMiles+","+disKilometers+"'>"+
                        countryName+"</option>");
        }
        //closing the select element
        out.println("</select>");
        
        out.println("Connected");
        
    }
    catch(Exception e){}



%>