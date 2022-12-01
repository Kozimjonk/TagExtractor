import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileChooser {

    public static Set<String> noiseWords = new TreeSet<>();
    public static Set<String> keySet = new TreeSet<>();



    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        File chosenFile;
        String rec = "";
        File noiseWordFile;
        Map<String, Integer> freqMap = new TreeMap<>();


        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { //file for tag extraction

            chosenFile = chooser.getSelectedFile();
            Path file = chosenFile.toPath();
            //Got file, now need to get stop word file


            try {
                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                    noiseWordFile = chooser.getSelectedFile();
                    Path file2 = noiseWordFile.toPath();
                    //Both files are retrieved, now can extract tags.
                    try(Stream<String> lines = Files.lines(file2))
                    {
                        noiseWords = lines.collect(Collectors.toSet());
                    }
                    catch (IOException e)
                    {
                        System.out.println("Failed to open the moise word file");
                        e.printStackTrace();
                    }

                    try (Stream<String> lines = Files.lines(file)) {
                        lines.forEach(l ->
                        {
                            String[] words = l.split(" ");
                            String w;
                            for (String x : words) {
                                w = x.toLowerCase().trim();
                                w = w.replaceAll("_", " ");
                                w = w.replaceAll("[\\W]", "");
                                w = w.replaceAll("[\\d]", "");

                                if (!isNoiseWord(w)) {
                                    Integer count = freqMap.get(w);
                                    if (count == null) {
                                        count = 1; }
                                    else { count = count + 1; }
                                    freqMap.put(w, count);
                                }
                            }
                        }); //remove all noise words


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    InputStream in =
                            new BufferedInputStream(Files.newInputStream(file, CREATE));
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(in));
                    while (reader.ready()) {
                        rec = reader.readLine();

                    }
                    reader.close(); // must close the file to seal it and flush buffer

                    DisplayGUI display = new DisplayGUI();
                    keySet = freqMap.keySet();
                    for(String k:keySet)
                    {
                        display.writeToTextBox("Word: " + k);
                        display.writeToTextBox("\t" + freqMap.get(k));
                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println("File not found!!!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
    public static boolean isNoiseWord(String word)
    {

        if(word.length() < 2)
        {
            return true;
        }
        else if(noiseWords.contains(word))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
