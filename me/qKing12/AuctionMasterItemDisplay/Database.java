package me.qKing12.AuctionMasterItemDisplay;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.qKing12.AuctionMasterItemDisplay.TopElements.TopDisplay;
import me.qKing12.AuctionMasterItemDisplay.TopElements.TopHolder;
import org.bukkit.Bukkit;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class Database {

    public static void saveToFile(AuctionMasterItemDisplay plugin){
        Bukkit.getScheduler().runTaskAsynchronously(AuctionMasterItemDisplay.plugin, () -> {
            try {

                GsonBuilder builder = new GsonBuilder();

                builder.serializeNulls();

                Gson gson = builder.create();

                BufferedWriter writer = new BufferedWriter(new FileWriter(plugin.getDataFolder() + "/display.txt"));

                ArrayList<String> transTop = new ArrayList<>();

                for (TopDisplay element : TopHolder.top) {
                    transTop.add(String.valueOf(element.getPosition()));
                    transTop.add(utils.locationToBase64(element.getLocation()));
                }

                writer.write(gson.toJson(transTop));

                writer.close();
            }catch (Exception x){
                x.printStackTrace();
            }
        });
    }

    public static void loadFromFile(AuctionMasterItemDisplay plugin) throws IOException {
        TopHolder.top = new ArrayList<>();

        File database = new File(plugin.getDataFolder(), "display.txt");
        if(!database.exists()) {
            database.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(plugin.getDataFolder()+"/display.txt"));
            writer.write("[]");
            writer.close();
        }
        else{
            GsonBuilder builder = new GsonBuilder();

            builder.serializeNulls();

            Gson gson = builder.create();

            BufferedReader reader = new BufferedReader(new FileReader(plugin.getDataFolder() + "/display.txt"));

            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> transitTop = gson.fromJson(reader.readLine(), type);
            Iterator<String> it = transitTop.iterator();
            while(it.hasNext()){
                TopHolder.top.add(new TopDisplay(Integer.parseInt(it.next()), utils.locationFromBase64(it.next())));
            }

            reader.close();
        }

    }

}
