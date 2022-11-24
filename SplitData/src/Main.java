import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {

    private static void moveFile(Path from, Path to){
        try {
            Files.createDirectories(to);
            Files.move(from, to, REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final double TRAIN_PERCENTAGE = 0.75d;
        final double TEST_PERCENTAGE = 0.15d;
        //final double VALIDATION_PERCENTAGE = 0.1d;
        File dataset_directory = new File("data/training_data_final");
        for(File directory : dataset_directory.listFiles()){
            System.out.println(directory + " has " + directory.listFiles().length + " files");
            for(File element : directory.listFiles()){
                double indicator = Math.random();
                StringBuilder dest_path = new StringBuilder();
                dest_path.append("data/");
                if(indicator > TRAIN_PERCENTAGE && indicator < TRAIN_PERCENTAGE + TEST_PERCENTAGE){ //included in the test set
                    dest_path.append("test/");
                } else if(indicator > TRAIN_PERCENTAGE + TEST_PERCENTAGE) { //included in the validation set
                    dest_path.append("validation/");
                } else { //included in the training set
                    dest_path.append("training/");
                }
                dest_path.append(element.getParentFile().getName());
                List<String> dont_touch = new ArrayList<>();
                dont_touch.add("training");
                dont_touch.add("test");
                dont_touch.add("validation");
                if(!dont_touch.contains(element.getName()))
                    moveFile(
                            element.toPath(),
                            Paths.get(dest_path.toString() + "/" + element.getName())
                    );
            }
        }
    }
}
