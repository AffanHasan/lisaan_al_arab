/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

/**
 *
 * @author root
 */
public class EntryPoint {

    public static void main(String... args) {
//        Create directory app-inteface
        Path app_interface_path = Paths.get("app-interface");
        final File app_interface_dir = new File(app_interface_path.toString());
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
//        Writing the commands to file commands.json
        try (FileWriter fw = new FileWriter(commands_json_file)) {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/commands_template.json");
            int characterIntValue = 0;
            char character;
            String fileContent = new String();
            while ((characterIntValue = is.read()) != -1) {
                character = (char) characterIntValue;
                fileContent += character;
            }
            fw.write(fileContent);
            fw.flush();

//        Registering the directory 'app-interface' for WatchService
            WatchService ws = FileSystems.getDefault().newWatchService();
            app_interface_path.register(ws, ENTRY_MODIFY);//Registering the directory for file modification
//            Processing file change events
            WatchKey wk;
            for (;;) {
                wk = ws.take();
                for (WatchEvent<?> event : wk.pollEvents()) {
                    WatchEvent.Kind eventKind = event.kind();

                    if (eventKind == OVERFLOW) {
                        System.out.println("PVERFLOE");
                        continue;
                    } else if (eventKind == ENTRY_MODIFY) {
//                            The filename is the context of the event.
                        Path commandsJsonFilePath = ((WatchEvent<Path>) event).context();
                        System.out.println("Abs Path: " + commands_json_file.getAbsolutePath());
                        try (FileReader fr = new FileReader(commands_json_file)) {
                            fileContent = new String();
                            while ((characterIntValue = fr.read()) != -1) {
                                character = (char) characterIntValue;
                                fileContent += character;
                            }
                            System.out.println("Command file content" + fileContent);
                            JsonParser jp = new JsonParser();
                            JsonObject jo = (JsonObject) jp.parse(fileContent);
                            System.out.println("Command is: " + jo.get("current_command").getAsJsonObject());
                        } catch (IOException ioe) {
                            Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ioe);
                        }

                    }
                }
                boolean valid = wk.reset();
                if (!valid) {
                    System.out.println("The commands.json is no longer valid to watch");
                    break;
                }
            }
        } catch (IOException ioe) {
            Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ioe);
        } catch (InterruptedException ex) {
            Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
