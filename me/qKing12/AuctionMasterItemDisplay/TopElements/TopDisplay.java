package me.qKing12.AuctionMasterItemDisplay.TopElements;

import me.qKing12.AuctionMaster.AuctionMaster;
import me.qKing12.AuctionMaster.AuctionObjects.Auction;
import me.qKing12.AuctionMaster.Menus.ViewAuctionMenu;
import me.qKing12.AuctionMasterItemDisplay.Database;
import me.qKing12.AuctionMasterItemDisplay.AuctionMasterItemDisplay;
import me.qKing12.AuctionMasterItemDisplay.utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class TopDisplay implements Listener {

    private Auction auction;
    private Item item;
    private Location location;
    private int position;
    private int chunkX;
    private int chunkZ;
    private boolean isCleared;
    private int bidCache;
    private BukkitTask check;
    private BukkitTask checkEntities;
    private boolean isDespawned=false;

    public Location getLocation(){
        return location;
    }

    private void setItem(ItemStack item){
        ItemStack toSet=item.clone();
        toSet.setAmount(1);
        ItemMeta meta=toSet.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("AuctionMasterItemDisplay");
        lore.add(UUID.randomUUID().toString());
        meta.setLore(lore);
        toSet.setItemMeta(meta);
        Bukkit.getScheduler().runTask(AuctionMasterItemDisplay.plugin, () -> {
            if (this.item == null || this.item.isDead()) {
                Item temporary = getItem(location);
                if(temporary!=null)
                    this.item=temporary;
                else {
                    this.item = this.location.getWorld().dropItemNaturally(this.location.clone().add(0, 1.6, 0), toSet);
                    this.item.setVelocity(new Vector(0, 0, 0));
                    this.item.setPickupDelay(999999999);
                    this.item.teleport(this.location.clone().add(0, 1.6, 0));
                }
            } else
                this.item.setItemStack(toSet);
        });
    }

    private Item getItem(Location loc){
        for(Entity ent : loc.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
            if (ent.getType().equals(EntityType.DROPPED_ITEM)) {
                Item display = (Item) ent;
                try {
                    if (display.getItemStack().getItemMeta().getLore().get(0).equalsIgnoreCase("AuctionMasterItemDisplay")) {
                        return display;
                    }
                }catch(Exception x){

                }
            }
        }
        return null;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPickUp(PlayerPickupItemEvent e){
        if(item==null || item.isDead())
            return;
        if(e.getItem().equals(item))
            e.setCancelled(true);
    }

    public void handleEntities(){
        if(checkEntities!=null)
            checkEntities.cancel();
        checkEntities=Bukkit.getScheduler().runTaskTimerAsynchronously(AuctionMasterItemDisplay.plugin, () -> {
            if(item==null || item.isDead()){
                if(auction!=null)
                    setItem(auction.getItemStack());
            }
            Bukkit.getScheduler().runTask(AuctionMasterItemDisplay.plugin, () -> {
                if (utils.getArmorStandDown(location) == null)
                    placeDisplay();
            });
        }, 20, 40);
    }

    public TopDisplay(int position, Location loc){
        location=loc;
        this.position=position;
        chunkX=loc.getBlockX()/16;
        chunkZ=loc.getBlockZ()/16;
        checkStatusAuction();
        Bukkit.getPluginManager().registerEvents(this, AuctionMasterItemDisplay.plugin);
    }

    public int getPosition(){
        return position;
    }

    public boolean isDisplayChunk(int chunkX, int chunkZ){
        return chunkX==this.chunkX && chunkZ==this.chunkZ;
    }

    public void despawn(){
        if(!utils.isLoadedChunk(location) || check==null)
            return;
        isCleared=true;
        ArmorStand amDown=utils.getArmorStandDown(location);
        if(amDown!=null) {
            amDown.remove();
            ArmorStand amUp = utils.getArmorStandUp(location.clone().add(0, 0.4, 0));
            if(amUp!=null)
                amUp.remove();
        }
        if(item!=null)
            item.remove();
        check.cancel();
        checkEntities.cancel();
        isDespawned=true;
    }

    public void remove(){
        despawn();
        TopHolder.top.remove(this);
        Database.saveToFile(AuctionMasterItemDisplay.plugin);
        HandlerList.unregisterAll(this);
    }

    public void clickTrigger(Player player){
        if(isCleared){
            for (String line : AuctionMasterItemDisplay.plugin.getConfig().getStringList("no-auction-display-click-message"))
                player.sendMessage(utils.chat(line));
        }
        else
            new ViewAuctionMenu(player, auction, "Close", 0);
    }

    public void checkStatusAuction(){
        if(Bukkit.getOnlinePlayers().size()>0 && utils.isLoadedChunk(location)) {
            if(check!=null)
                check.cancel();
            check = Bukkit.getScheduler().runTaskTimerAsynchronously(AuctionMasterItemDisplay.plugin, () -> {
                try {
                    if (auction == null || auction.getId() == null || !auction.getId().equalsIgnoreCase(TopHolder.auctionsList.getAuction(position).getId())) {
                        auction = TopHolder.auctionsList.getAuction(position);
                        try {
                            bidCache = auction.getBids().getNumberOfBids();
                            placeDisplay();
                            findSigns();
                        }catch(NullPointerException x){
                            TopHolder.globalAuctions.remove(auction);
                            auction=null;
                            clearDisplay();
                            bidCache=-1;
                        }
                    } else if (auction.isEnded()) {
                        TopHolder.removeAuctionFromList(auction);
                        auction = null;
                        clearDisplay();
                        bidCache=-1;
                    } else if (auction.getBids().getNumberOfBids() != bidCache) {
                        bidCache++;
                        placeDisplay();
                        findSigns();
                    }
                    else if (isDespawned)
                        placeDisplay();
                } catch (Exception x) {
                    if(auction!=null) {
                        auction = null;
                        clearDisplay();
                        bidCache = -1;
                    }
                }
            }, 0, 20);
            handleEntities();
        }
    }

    public void findSigns() {
        Bukkit.getScheduler().runTask(AuctionMasterItemDisplay.plugin, () -> {
            final Block loc = this.location.clone().add(0,1, 0).getBlock();
            if (loc.getRelative(BlockFace.EAST).getType().equals(Material.WALL_SIGN)) {
                this.updateSign((Sign) loc.getRelative(BlockFace.EAST).getState());
            }
            if (loc.getRelative(BlockFace.WEST).getType().equals(Material.WALL_SIGN)) {
                this.updateSign((Sign) loc.getRelative(BlockFace.WEST).getState());
            }
            if (loc.getRelative(BlockFace.SOUTH).getType().equals(Material.WALL_SIGN)) {
                this.updateSign((Sign) loc.getRelative(BlockFace.SOUTH).getState());
            }
            if (loc.getRelative(BlockFace.NORTH).getType().equals(Material.WALL_SIGN)) {
                this.updateSign((Sign) loc.getRelative(BlockFace.NORTH).getState());
            }
        });
    }

    public void updateSign(final Sign toUpdate) {
        if (auction==null) {
            toUpdate.setLine(0, "");
            toUpdate.setLine(1, "");
            toUpdate.setLine(2, "");
            toUpdate.setLine(3, "");
        }
        else {
            toUpdate.setLine(0, utils.chat(AuctionMasterItemDisplay.plugin.getConfig().getString("sign-line-1").replace("%position%", String.valueOf(this.position+1)).replace("%coins%", utils.formatSignCoins(auction.getCoins())).replace("%bids%", String.valueOf(bidCache))));
            toUpdate.setLine(1, utils.chat(AuctionMasterItemDisplay.plugin.getConfig().getString("sign-line-2").replace("%position%", String.valueOf(this.position+1)).replace("%coins%", utils.formatSignCoins(auction.getCoins())).replace("%bids%", String.valueOf(bidCache))));
            toUpdate.setLine(2, utils.chat(AuctionMasterItemDisplay.plugin.getConfig().getString("sign-line-3").replace("%position%", String.valueOf(this.position+1)).replace("%coins%", utils.formatSignCoins(auction.getCoins())).replace("%bids%", String.valueOf(bidCache))));
            toUpdate.setLine(3, utils.chat(AuctionMasterItemDisplay.plugin.getConfig().getString("sign-line-4").replace("%position%", String.valueOf(this.position+1)).replace("%coins%", utils.formatSignCoins(auction.getCoins())).replace("%bids%", String.valueOf(bidCache))));
        }
        toUpdate.update();
    }

    private void clearDisplay(){
        findSigns();
        isCleared=true;
        if(this.item!=null)
            this.item.remove();
        Bukkit.getScheduler().runTask(AuctionMasterItemDisplay.plugin, () -> {
            ArmorStand amDownTest = utils.getArmorStandDown(location);
            if (amDownTest != null) {
                ArmorStand amUp;
                amUp = utils.getArmorStandUp(location.clone().add(0, 0.4, 0));
                amDownTest.setCustomNameVisible(false);
                if (amUp != null) {
                    amUp.setCustomNameVisible(false);
                }
            } else {
                ArmorStand amDown = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                amDown.setHelmet(AuctionMasterItemDisplay.items.glass.clone());
                amDown.setVisible(false);
                amDown.setRemoveWhenFarAway(false);
                amDown.setGravity(false);
                amDown.setArms(true);
                amDown.setLeftArmPose(AuctionMasterItemDisplay.verify);
                amDown.setBasePlate(false);

                ArmorStand amUp = (ArmorStand) location.getWorld().spawnEntity(location.clone().add(0, 0.4, 0), EntityType.ARMOR_STAND);
                amUp.setVisible(false);
                amUp.setRemoveWhenFarAway(false);
                amUp.setGravity(false);
                amUp.setArms(true);
                amUp.setRightArmPose(AuctionMasterItemDisplay.verify);
                amUp.setBasePlate(false);
            }
        });
    }

    private void placeDisplay(){
        if(auction==null){
            clearDisplay();
            return;
        }
        isDespawned=false;
        setItem(auction.getItemStack());
        isCleared=false;
        Bukkit.getScheduler().runTask(AuctionMasterItemDisplay.plugin, () -> {
            ArmorStand amDownTest = utils.getArmorStandDown(location);
            if (amDownTest != null) {
                ArmorStand amUp = utils.getArmorStandUp(location.clone().add(0, 0.4, 0));
                amDownTest.setCustomName(utils.chat(AuctionMasterItemDisplay.plugin.getConfig().getString("title-line-2")).replace("%top-position%", String.valueOf(this.position+1)).replace("%auction-name%", auction.getDisplayName()).replace("%seller-display-name%", auction.getSellerDisplayName()));
                amDownTest.setCustomNameVisible(true);
                if (amUp != null) {
                    amUp.setCustomName(utils.chat(AuctionMasterItemDisplay.plugin.getConfig().getString("title-line-1")).replace("%top-position%", String.valueOf(this.position+1)).replace("%auction-name%", auction.getDisplayName()).replace("%seller-display-name%", auction.getSellerDisplayName()));
                    amUp.setCustomNameVisible(true);
                }
            } else {
                    ArmorStand amDown = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                    amDown.setHelmet(AuctionMasterItemDisplay.items.glass.clone());
                    amDown.setVisible(false);
                    amDown.setRemoveWhenFarAway(false);
                    amDown.setGravity(false);
                    amDown.setArms(true);
                    amDown.setLeftArmPose(AuctionMasterItemDisplay.verify);
                    amDown.setBasePlate(false);
                    amDown.setCustomName(utils.chat(AuctionMasterItemDisplay.plugin.getConfig().getString("title-line-2")).replace("%top-position%", String.valueOf(this.position + 1)).replace("%auction-name%", auction.getDisplayName()).replace("%seller-display-name%", auction.getSellerDisplayName()));
                    amDown.setCustomNameVisible(true);

                    ArmorStand amUp = (ArmorStand) location.getWorld().spawnEntity(location.clone().add(0, 0.4, 0), EntityType.ARMOR_STAND);
                    amUp.setVisible(false);
                    amUp.setRemoveWhenFarAway(false);
                    amUp.setGravity(false);
                    amUp.setArms(true);
                    amUp.setRightArmPose(AuctionMasterItemDisplay.verify);
                    amUp.setBasePlate(false);
                    amUp.setCustomName(utils.chat(AuctionMasterItemDisplay.plugin.getConfig().getString("title-line-1")).replace("%top-position%", String.valueOf(this.position + 1)).replace("%auction-name%", auction.getDisplayName()).replace("%seller-display-name%", auction.getSellerDisplayName()));
                    amUp.setCustomNameVisible(true);
            }
        });
    }
}
