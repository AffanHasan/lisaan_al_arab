/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class EntryPoint {

    public static void main(String... args) {
//        Create directory app-inteface
        Path app_interface_path = Paths.get("app-interface");
        File app_interface_dir = new File(app_interface_path.toString());

        if (!app_interface_dir.exists()) {
            try {
                Files.createDirectories(app_interface_path);
                System.out.println("Directory \'app-interface\' created");
            } catch (IOException ex) {
                Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Directory \'app-interface\' already exists");
        }
//        Creating File commands.json
        Path commands_json_file_path = Paths.get("app-interface/commands.json");
        File commands_json_file = new File(commands_json_file_path.toString());
        if (!commands_json_file.exists()) {
            try {
                Files.createFile(commands_json_file_path);
                System.out.println("File \'app-interface\\commands.json\' created");
            } catch (IOException ex) {
                Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("File \'app-interface\\commands.json\' already exists");
        }

        try (FileWriter fw = new FileWriter(commands_json_file)) {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/commands_template.json");
            int character = 0;
            char abc;
            String fileContent = null;
            while ((character = is.read()) != -1) {
                abc = (char) character;
                fileContent += abc;
            }
            System.out.println(fileContent);
//            System.out.println(EntryPoint.class.getClassLoader().getResource("templates/commands_template.json").getFile());
//            File temp_file = new File(EntryPoint.class.getClassLoader().getResource("templates/commands_template.json").getFile());
//            FileReader fr =new FileReader(temp_file);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
