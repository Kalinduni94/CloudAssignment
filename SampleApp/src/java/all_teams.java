import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class all_teams extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        MyDb db = new MyDb();
        Connection con = db.getCon();
           
        String req_option = request.getParameter("select_option");
        PrintWriter out = response.getWriter();  
         response.setContentType("text/html");  
         out.println("<html><body>");  
         
         try 
         {  
               
             Statement stmt = con.createStatement();  
               
             
             if(req_option.equals("All Teams")){
             ResultSet rs = stmt.executeQuery("select * from teams");
             out.println("<h1>All Teams </h1>");
             out.println("<table border=1 width=50% height=10>");  
             out.println("<tr><th>ID</th><th>Team Name</th><th>Year Founded </th><tr>");  
             int count = 1;
             while (rs.next()) 
             {  
                 String n = rs.getString("id");  
                 String nm = rs.getString("team");  
                 int s = rs.getInt("year_founded");   
                 out.println("<tr><td>" + count + "</td><td>" + nm + "</td><td>" + s + "</td></tr>");   
                 count++;
             }  
             }else if(req_option.equals("Top five oldest teams")){
                ResultSet rs = stmt.executeQuery("SELECT id,team,year_founded FROM `teams` ORDER BY year_founded ASC");
                out.println("<h1>Top five oldest teams </h1>");
                out.println("<table border=1 width=50% height=10>");  
                out.println("<tr><th>ID</th><th>Team Name</th><th>Year Founded </th><tr>");
                int count = 1;
                while (rs.next()&&count<6) {  
                 String n = rs.getString("id");  
                 String nm = rs.getString("team");  
                 int s = rs.getInt("year_founded");   
                 out.println("<tr><td>" + count + "</td><td>" + nm + "</td><td>" + s + "</td></tr>"); 
                 count++;
                }      
   
             }else if(req_option.equals("Most recently founded teams")){
                ResultSet rs = stmt.executeQuery("SELECT id,team,year_founded FROM `teams` ORDER BY year_founded DESC"); 
                 
                out.println("<h1>Most recently founded teams</h1>");
                out.println("<table border=1 width=50% height=10>");  
                out.println("<tr><th>ID</th><th>Team Name</th><th>Year Founded </th><tr>");
                int count = 1;
                while (rs.next()&&count<6) {  
                 String n = rs.getString("id");  
                 String nm = rs.getString("team");  
                 int s = rs.getInt("year_founded");   
                 out.println("<tr><td>" + count + "</td><td>" + nm + "</td><td>" + s + "</td></tr>");
                 count++;
                }  
             }
             out.println("</table>");  
             out.println("</html></body>");  
             con.close();  
            }  
             catch (Exception e) 
            {  
             out.println("error");  
         }  
         //}
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
