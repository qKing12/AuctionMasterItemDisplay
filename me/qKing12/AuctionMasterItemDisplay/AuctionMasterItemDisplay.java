package me.qKing12.AuctionMasterItemDisplay;

import me.qKing12.AuctionMasterItemDisplay.NMS.Items;
import me.qKing12.AuctionMasterItemDisplay.TopElements.Events;
import me.qKing12.AuctionMasterItemDisplay.TopElements.TopDisplay;
import me.qKing12.AuctionMasterItemDisplay.TopElements.TopHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;

public class AuctionMasterItemDisplay extends JavaPlugin {
    public static Items items;
    public static AuctionMasterItemDisplay plugin;
    public static EulerAngle verify = new EulerAngle(1, 5, 3);
    public static boolean verifyAngle(EulerAngle toVerify){
        if(Math.floor(verify.getX())!=Math.floor(toVerify.getX()))
            return false;
        if(Math.floor(verify.getY())!=Math.floor(toVerify.getY()))
            return false;
        if(Math.floor(verify.getZ())!=Math.floor(toVerify.getZ()))
            return false;
        return true;
    }

    @Override
    public void onEnable() {
        this.plugin = this;
        saveDefaultConfig();
        //File ah = new File(Bukkit.getPluginManager().getPlugin("AuctionMaster").getDataFolder(), "config.yml");
        //FileConfiguration ahCfg = YamlConfiguration.loadConfiguration(ah);
        items = new Items();
        new Events(this);
        new Heads();
        new Commands(this);
        new TopHolder();
        try {
            Database.loadFromFile(AuctionMasterItemDisplay.plugin);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    @Override
    public void onDisable(){
        for(TopDisplay element : TopHolder.top)
            element.despawn();
    }
}
