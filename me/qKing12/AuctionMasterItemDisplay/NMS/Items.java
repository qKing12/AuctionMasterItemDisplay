package me.qKing12.AuctionMasterItemDisplay.NMS;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {
    public ItemStack glass;
    public ItemStack skull;
    public ItemStack skullItem;

    public Items(){
        String version = Bukkit.getServer().getVersion();
        if(version.contains("1.8") || version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
            glass = new ItemStack(Material.GLASS, 1);
            skull = new ItemStack(Material.getMaterial("SKULL"), 1, (short) 3);
            skullItem = new ItemStack(Material.getMaterial("SKULL_ITEM"), 1, (short) 3);
        }
        else{
            glass= new ItemStack(Material.GLASS, 1);
            skull = new ItemStack(Material.PLAYER_HEAD, 1);
            skullItem = new ItemStack(Material.PLAYER_HEAD, 1);
        }
    }
}
