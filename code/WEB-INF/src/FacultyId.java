import javax.servlet.*;
import java.io.*;
import java.sql.*;
public class  FacultyId extends GenericServlet
{
    public void service(ServletRequest req,ServletResponse res) throws ServletException,IOException
    {
        res.setContentType("text/html");
        PrintWriter out=res.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>FACULTY LOGIN</title>");
        out.println("<style>");
        out.println("body{font-family: Times New Roman;background-color: #f5f5f5}");
        out.println("div{width: 400px;margin: 0px auto;box-shadow: 0px 1px 10px #a8a8a8;background-color: #fff;padding-bottom: 10px;position: absolute;top: 50%;left: 50%;transform: translate(-50%,-50%)}");
        out.println("h1{background-color: #d1a134;text-align: center;margin-top: 0;padding: 20px;color: #fff}");
 out.println("h3{background-color: #d1a134;text-align: center;margin-top: 0;padding: 20px;color: #fff}");
        out.println("input{display: block; margin:auto; font-size: 20px}");
        out.println("p{text-align: center;font-size: 20px;color: #82a28a;margin;0}");
        out.println("input{width: 70%;border: 1px solid #34cbd1;background-color: #fff;outline: none;border-radius: 5px;height:40px;padding: 10px}");
        out.println("input[type='submit']:hover{background-color: #34cbd1;border: 0}");
        out.println("input[type='submit']{background-color: #4cce56;color: #fff;cursor: pointer};");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div>");
       
        Connection con=null;
        Statement stm=null;
        ResultSet rs=null;
        String FacultyId=req.getParameter("FacultyId");
      
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/wtpro","root","");
            String query = "select * from facultyid where FacultyId='"+FacultyId+"'";
            stm= con.createStatement();
            rs=stm.executeQuery(query);
            if(rs.next()){
                out.println("<h1>Welcome</h1>");
                out.println("<p>FacultyName - "+rs.getString("FacultyName")+"</p>");
                    out.println("<p>FacultyId - "+rs.getString("FacultyId")+"</p>");
out.println("<h3 style='background-color: #66c35a'><p><a href='FileDownload.html'>List Of Executed Assignments With Student RollNo</a></p></h3>");
            }
            else
                out.println("<h1 style='background-color: #eb1616'>Invalid FacultyName !</h1>");
        }
        catch(ClassNotFoundException e){
            out.println(e);
        }
        catch(SQLException e){
            out.println(e);
        }
        finally
        {
            try
            {
                rs.close();
                stm.close();
                con.close();
            }
            catch(SQLException e){
            out.println(e);
            }
        }
        
        out.println("<p><a href='FacultyId.html'>Back</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}