package conexion;

import java.sql.*;
import javax.swing.JOptionPane;

public class MySQLClass {

    private Connection conn;
    private ResultSet rs;
    private Statement instruc;
    private String ip, db_name, user, pass;

    public MySQLClass(String pIp, String pDb_name, String pUser, String pPass) {

        ip = pIp;
        db_name = pDb_name;
        user = pUser;
        pass = pPass;
    }

    public void connect() throws ClassNotFoundException, SQLException {
           
        Class.forName("org.gjt.mm.mysql.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + db_name,
                    user,
                    pass);

            System.out.println("Driver org.gjt.mm.mysql.Driver in use");
            System.out.println("");

            instruc = (Statement) conn.createStatement();


    }

    public void disconnect() {

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException sqlEx) {
            System.out.println("Error: disconnect");
            JOptionPane.showMessageDialog(null, "Error: disconnect");
        }

        try {
            if (instruc != null) {
                instruc.close();
            }
        } catch (SQLException sqlEx) {
            System.out.println("Error: disconnect");
            JOptionPane.showMessageDialog(null, "Error: disconnect");
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqlEx) {
            System.out.println("Error: disconnect");
            JOptionPane.showMessageDialog(null, "Error: disconnect");
        }

    }

    public ResultSet execQuery(String StrSql) throws SQLException {
        
        
        System.out.println("execute query "+StrSql);
        rs = (ResultSet) instruc.executeQuery(StrSql);
        return rs;
    }

    public void execInstruc(String pInstruc) throws SQLException {
        System.out.println("execute instrucr "+pInstruc);
        instruc.execute(pInstruc);

    }

    public PreparedStatement getPrepareStatement(String pStatement) throws SQLException {

        return conn.prepareStatement(pStatement);

    }
//    public ResultSet execConsola( String StrSql )
//    {
//       try{
//           System.out.println("--------------------------------------------");
//           System.out.print("( " + StrSql + " )");
//           System.out.print(" Only First and Last Rows ");
//           System.out.println("  ");
//
//           rs = (ResultSet) instruc.executeQuery(StrSql);
//
//           ResultSetMetaData rsMetaData = rs.getMetaData();
//           int numberOfColumns = rsMetaData.getColumnCount();
//
//
//            System.out.println("--------------------------------------------");
//
//            for (int i = 1; i < numberOfColumns + 1; i++)
//            {
//              String columnName = rsMetaData.getColumnName(i);
//              System.out.print("- " + columnName + " ");
//            }
//
//            System.out.print("-");
//            System.out.println("");
//            System.out.println("--------------------------------------------");
//
//
//
//
//            while(rs.next())
//                  {
//                        for (int i = 1; i < numberOfColumns + 1; i++)
//                        {
//                        String columnName = rsMetaData.getColumnName(i);
//                        System.out.print( rs.getString(columnName) + " ");
//                        }
//
//                   rs.last();
//                   System.out.println("");
//
//                        for (int i = 1; i < numberOfColumns + 1; i++)
//                        {
//                        String columnName = rsMetaData.getColumnName(i);
//                        System.out.print( rs.getString(columnName) + " ");
//                        }
//                  }
//
//            System.out.println("");
//            System.out.println("--------------------------------------------");
//
//          }
//       catch(Exception e)
//         {
//           System.out.println("Error en la base de datos");
//           System.out.println(e.getMessage());
//         }
//
//       return rs;
//      }
}
