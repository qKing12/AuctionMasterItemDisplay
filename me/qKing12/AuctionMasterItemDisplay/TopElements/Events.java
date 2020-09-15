package me.qKing12.AuctionMasterItemDisplay.TopElements;

import me.qKing12.AuctionMaster.API.Events.AuctionCreateEvent;
import me.qKing12.AuctionMaster.API.Events.PlaceBidEvent;
import me.qKing12.AuctionMaster.AuctionMaster;
import me.qKing12.AuctionMaster.AuctionObjects.Auction;
import me.qKing12.AuctionMasterItemDisplay.Database;
import me.qKing12.AuctionMasterItemDisplay.Heads;
import me.qKing12.AuctionMasterItemDisplay.AuctionMasterItemDisplay;
import me.qKing12.AuctionMasterItemDisplay.utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Comparator;

public class Events implements Listener {
    private static AuctionMasterItemDisplay plugin;


    public Events(AuctionMasterItemDisplay plugin){
        this.plugin=plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHeadPlace(BlockPlaceEvent e){
        if(!e.getItemInHand().hasItemMeta() || !e.getItemInHand().getItemMeta().hasDisplayName() || !e.getItemInHand().getItemMeta().hasLore())
            return;
        if(e.getItemInHand().getItemMeta().getDisplayName().equals(Heads.removalItem.getItemMeta().getDisplayName()) && e.getItemInHand().getItemMeta().getLore().equals(Heads.removalItem.getItemMeta().getLore())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(utils.chat("&cThis is for removing a display not for placing, silly."));
        }
        else if(utils.getPosition(e.getItemInHand())!=0) {
            e.setCancelled(true);
            for(Entity et : e.getBlock().getWorld().getNearbyEntities(e.getBlock().getLocation(), 1, 3, 1))
                if(et.getType().equals(EntityType.ARMOR_STAND)){
                    e.getPlayer().sendMessage(utils.chat("&cThis location is too close to an armorstand."));
                    return;
                }
            Location loc = e.getBlock().getLocation();
            loc.add(0.5, -1.35, 0.5);
            TopHolder.top.add(new TopDisplay(utils.getPosition(e.getItemInHand())-1, loc));
            try {
                Database.saveToFile(plugin);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPickUp(PlayerPickupItemEvent e){
        if(!e.isCancelled()){
            ItemStack item = e.getItem().getItemStack();
            if(item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("AuctionMasterItemDisplay")){
                AuctionMasterItemDisplay.plugin.getLogger().info("An display item was picked up. Forcefully removing the item for player "+e.getPlayer().getName());
                e.getPlayer().getInventory().remove(item);
            }
        }
    }

    @EventHandler
    public void onArmorStandClick(PlayerInteractAtEntityEvent e){
        if(e.getRightClicked() instanceof ArmorStand){
            ArmorStand am = (ArmorStand)e.getRightClicked();
            if(AuctionMasterItemDisplay.verifyAngle(am.getLeftArmPose()) || AuctionMasterItemDisplay.verifyAngle(am.getRightArmPose())){
                e.setCancelled(true);
                TopDisplay display = TopHolder.getTopDisplay(e.getRightClicked().getLocation());
                if(display!=null){
                    try {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(Heads.removalItem.getItemMeta().getDisplayName()) && e.getPlayer().getItemInHand().getItemMeta().getLore().equals(Heads.removalItem.getItemMeta().getLore())) {
                            display.remove();
                        } else {
                            display.clickTrigger(e.getPlayer());
                        }
                    }
                    catch (Exception x){
                        display.clickTrigger(e.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onAuctionCreate(AuctionCreateEvent e){
        if(AuctionMaster.auctionsHandler.global==null){
            TopHolder.globalAuctions.add(e.getAuction());
            TopHolder.globalAuctions.sort(Collections.reverseOrder(Comparator.comparing(Auction::getCoins)));
        }
    }

    @EventHandler
    public void onBidCreate(PlaceBidEvent e){
        if(AuctionMaster.auctionsHandler.global==null)
            Bukkit.getScheduler().runTaskLaterAsynchronously(AuctionMasterItemDisplay.plugin, () -> TopHolder.globalAuctions.sort(Collections.reverseOrder(Comparator.comparing(Auction::getCoins))), 5);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        try {
            if (Bukkit.getOnlinePlayers().size() == 1) {
                for (TopDisplay display : TopHolder.top) {
                    display.checkStatusAuction();
                }
            }
        }catch(Exception x){

        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        if(Bukkit.getOnlinePlayers().size()==1){
            for(TopDisplay display : TopHolder.top)
                display.despawn();
        }
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onChunkLoad(ChunkLoadEvent e){
        for(TopDisplay display : TopHolder.top)
            if(display.isDisplayChunk(e.getChunk().getX(), e.getChunk().getZ())) {
                display.checkStatusAuction();
            }
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onChunkUnLoad(ChunkUnloadEvent e){
        for(TopDisplay display : TopHolder.top)
            if(e.getWorld().equals(display.getLocation().getWorld()) && display.isDisplayChunk(e.getChunk().getX(), e.getChunk().getZ())) {
                display.despawn();
            }
    }
}
