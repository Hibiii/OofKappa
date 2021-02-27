package net.drago.ofcapes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class ofcapes implements ClientModInitializer {

    public static boolean showPlayersOwnName = false;
    private static File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), "ofcapes.properties");
    
    @Override
    public void onInitializeClient() {
        loadConfig();
    }
    
    public static void loadConfig() {
        try {
            if(file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                do {
                    if(line.startsWith("showPlayersOwnName "))
                        showPlayersOwnName = Boolean.parseBoolean(line.substring(19));
                    line = br.readLine();
                } while (line != null);
                br.close();
            }
        }
        catch (Exception e) {
        }
    }
    public static void saveConfig() {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("showPlayersOwnName " + Boolean.toString(showPlayersOwnName) + "\n");
            writer.close();
        }
        catch (Exception e) {
        }
    }
}