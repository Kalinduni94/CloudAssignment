//
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import static javax.servlet.SessionTrackingMode.URL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Acer
 */
public class Uploader {
    
    //method to run on startup
    static void display(){
        System.out.println("----This is when startup----");
    }
    
    public static void importData()
    {   
        String filename = "C:/Users/Acer/Documents/NetBeansProjects/SampleApp/src/java/nba.csv";
        Statement stmt;
        String query;
 
        try{
            MyDb db = new MyDb();
            Connection connection = db.getCon();
            
            stmt = connection.createStatement(
            ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
 
            query = "LOAD DATA INFILE '"+filename+"' INTO TABLE teams  FIELDS TERMINATED BY ';' (team,year_founded)";
 
            stmt.execute(query);
                 
        }
        catch(Exception e)
        {
            e.printStackTrace();
            stmt = null;
        }
    }
    
    static void upload(){
    
        Connection connection = null;
        String csvFilePath = "C:/Users/Acer/Documents/NetBeansProjects/SampleApp/src/java/nba.csv";
        int batchSize = 20;
        
        File file = new File(csvFilePath);
        
        try {
            MyDb db = new MyDb();
            connection = db.getCon();
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
            
            
            
            int id = 1;
            String sql = "INSERT INTO `teams`(`ID`, `team`, `year_founded`) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            //Scanner inputStream = new Scanner(file);
            
 
            int count = 0;
            System.out.println("Connnection #######:"+csvReader);
             // skip header line
             //inputStream.next();
             count++;
             String row = csvReader.readLine();
             System.out.println(row);
             
//             if(row.equals("Team;Year Founded")){
//                 row = csvReader.readLine();
//             }
             //connection.setAutoCommit(false);
             
             while((row = csvReader.readLine()) != null){
            //while (inputStream.hasNext()) {
                //String[] data = inputStream.next().split(";");
                String[] data = row.split(";");
                
                String team = data[0];
                String year = data[1];
 
                statement.setInt(1, id++);
                statement.setString(2, team);
                statement.setInt(3,Integer.parseInt(year));
                   System.out.println(row);
                
                   
                   //statement.execute(sql);
                statement.addBatch();
                
                id++;
//                if (++id %  6== 0) {
//                    //System.out.println("Succes :"+id);
//                    statement.executeBatch();
//                    
//                    connection.commit();
//                    System.out.println("Succes :"+id);
//                    //connection.clearBatch();
//                }
            }
 
            //inputStream.close();
              csvReader.close();
            // execute the remaining queries
            statement.executeBatch();
 
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
            
            
 
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
 
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
    }
}


//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Scanner;
//
//
//
//public class Uploader {
//    
//    //method to run on startup
//    static void display(){
//        System.out.println("----This is when startup----");
//    }
//    
//    static void upload() throws IOException{
//    
//        
//        String csvFilePath = "C:/Users/Acer/Documents/NetBeansProjects/SampleApp/src/java/nba.csv";
//        int batchSize = 20;
//        
//        File file = new File(csvFilePath);
//        
//        //File myFile = new File("word.txt");
//        System.out.println("Attempting to read from file in: "+file.getCanonicalPath());
//
//        
//        
//        try {
//           
//            BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
//            
//            
//            
//            int id = 2;
//            
//            
//            //Scanner inputStream = new Scanner(file);
//            String lineText = null;
// 
//            int count = 0;
//            //System.out.println("Connnection #######:"+connection);
//             // skip header line
//             //inputStream.next();
//             String row = csvReader.readLine();
//             while((row = csvReader.readLine()) != null){
//            //while (inputStream.hasNext()) {
//                //String[] data = inputStream.next().split(";");
//                
//                System.out.println(row);
// 
//                
// 
//                
//            
// 
//       
// }
//            
// 
//        } catch (IOException ex) {
//            System.err.println(ex);
//       
//    }
//}
//}
