import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class FileUpload extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/wtpro";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
Connection con=null;
        try {
            // Connect to the database
        Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/wtpro","root","");

            // Retrieve the uploaded file from the request
            Part filePart = request.getPart("file");
            String fileName = filePart.getSubmittedFileName();

            // Store the file in the database
            String sql = "INSERT INTO files (name, content) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, fileName);
            statement.setBinaryStream(2, filePart.getInputStream());
            statement.executeUpdate();
            statement.close();

            // Close the database connection
            con.close();

            response.getWriter().println("File uploaded successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error uploading file.");
        }
    }
}