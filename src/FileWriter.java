import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileWriter {


    public void writeFile(ArrayList<String> input) {
        File workDir = new File(System.getProperty("user.dir"));
        String fileName = "tagOutputFile";
        Path file = Paths.get(workDir.getPath() + "\\src\\" + fileName + ".txt");

        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for (String rec : input) {
                writer.write(rec);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}