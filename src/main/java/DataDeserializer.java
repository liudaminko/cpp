import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;

public class DataDeserializer {
    public static List<User> deserializeReaders(String filePath) {
        Gson gson = new Gson();
        List<User> usersList = null;

        // deserialize from JSON file
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            Type listType = new TypeToken<List<User>>() {}.getType();
            usersList = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public static List<Book> deserializeBooks(String filePath) {
        Gson gson = new Gson();
        List<Book> booksList = null;

        // deserialize from JSON file
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            Type listType = new TypeToken<List<Book>>() {}.getType();
            booksList = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return booksList;
    }

    public static Admin deserializeAdmin(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null && line.contains(",")) {
                String[] parts = line.split(",");
                String email = parts[0];
                String password = parts[1];
                return new Admin(email, password);
            } else {
                System.err.println("invalid file format. expected format: email,password");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}