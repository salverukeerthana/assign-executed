import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FileDownload extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/wtpro";


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int fileId = Integer.parseInt(request.getParameter("fileId"));

        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL);

            // Retrieve the file from the database
            String sql = "SELECT name, content FROM files WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, fileId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String fileName = result.getString("name");
                InputStream fileContent = result.getBinaryStream("content");

                // Set response headers for file download
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

                // Write file content to response output stream
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileContent.read(buffer)) != -1) {
                    response.getOutputStream().write(buffer, 0, bytesRead);
                }

                // Close the database connection and input stream
                result.close();
                statement.close();
                fileContent.close();
            } else {
                response.getWriter().println("File not found.");
            }

            // Close the database connection
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error retrieving file.");
        }
    }
}