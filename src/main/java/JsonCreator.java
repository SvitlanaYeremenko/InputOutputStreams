import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonCreator {
    private static final String ABSOLUTE_PATH_TEST_FILE =
            "C:\\Users\\Svitlana\\IdeaProjects\\goit-student-projects\\InputOutputStreams\\src\\main\\resources\\file_task2.txt";
    private static final String ABSOLUTE_PATH_JSON =
            "C:\\Users\\Svitlana\\IdeaProjects\\goit-student-projects\\InputOutputStreams\\src\\main\\resources\\user.json";

    public static void main(String[] args) {

        List<User> users = writeToUserList(createTestFile());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);
        createJsonFile(json);

        System.out.println(json);
    }

    private static List<User> writeToUserList(File file) {
        List<User> userList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while (line != null) {
                boolean result = line.matches("[a-zA-Z]*\\s\\d*");
                if (result) {
                    String[] fields = line.split(" ");
                    User user = new User(fields[0], Integer.parseInt(fields[1]));
                    userList.add(user);
                }
                line = reader.readLine();
            }
            System.out.println(userList);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return userList;
    }

    private static File createTestFile() {
        File file = new File(ABSOLUTE_PATH_TEST_FILE);
        checkIfFileAvailable(file);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            bufferedWriter.write("name age\n" +
                    "alice 21\n" +
                    "ryan 30\n");

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return file;
    }

    private static File createJsonFile(String json) {
        File file = new File(ABSOLUTE_PATH_JSON);
        checkIfFileAvailable(file);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            bufferedWriter.write(json);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return file;
    }

    private static void checkIfFileAvailable(File file) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();

            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}