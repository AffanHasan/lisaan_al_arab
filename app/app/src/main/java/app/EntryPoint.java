/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.File;
import java.io.IOException;
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
        File commcommands_json_file = new File(commands_json_file_path.toString());
        if (!commcommands_json_file.exists()) {
            try {
                Files.createFile(commands_json_file_path);
                System.out.println("File \'app-interface\\commands.json\' created");
            } catch (IOException ex) {
                Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("File \'app-interface\\commands.json\' already exists");
        }
    }

}
