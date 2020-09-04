package me.qKing12.AuctionMasterItemDisplay.TopElements;

import me.qKing12.AuctionMaster.AuctionMaster;
import me.qKing12.AuctionMaster.AuctionObjects.Auction;
import me.qKing12.AuctionMasterItemDisplay.utils;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TopHolder {
    public static ArrayList<TopDisplay> top=new ArrayList<>();
    public static ArrayList<Auction> globalAuctions;

    public TopHolder(){
        if(AuctionMaster.auctionsHandler.global==null){
            loadElements();
            auctionsList=(position) -> globalAuctions.get(position);
        }
        else{
            auctionsList=(position) -> AuctionMaster.auctionsHandler.global.getAuctionsCoins().get(AuctionMaster.auctionsHandler.global.getAuctionsCoins().size()-1-position);
        }
    }

    public static void loadElements() {
        globalAuctions = new ArrayList<>();
        if (AuctionMaster.auctionsHandler.weapons != null)
            globalAuctions.addAll(AuctionMaster.auctionsHandler.weapons.getAuctionsCoins());
        if (AuctionMaster.auctionsHandler.armor != null)
            globalAuctions.addAll(AuctionMaster.auctionsHandler.armor.getAuctionsCoins());
        if (AuctionMaster.auctionsHandler.blocks != null)
            globalAuctions.addAll(AuctionMaster.auctionsHandler.blocks.getAuctionsCoins());
        if (AuctionMaster.auctionsHandler.consumables != null)
            globalAuctions.addAll(AuctionMaster.auctionsHandler.consumables.getAuctionsCoins());
        if (AuctionMaster.auctionsHandler.tools != null)
            globalAuctions.addAll(AuctionMaster.auctionsHandler.tools.getAuctionsCoins());
        if (AuctionMaster.auctionsHandler.others != null)
            globalAuctions.addAll(AuctionMaster.auctionsHandler.others.getAuctionsCoins());
        globalAuctions.sort(Collections.reverseOrder(Comparator.comparing(Auction::getCoins)));
    }

    public static TopDisplay getTopDisplay(Location loc){
        for(TopDisplay display : top)
            if(utils.isLoadedChunk(display.getLocation()) && (display.getLocation().equals(loc) || display.getLocation().clone().add(0,0.4,0).equals(loc)))
                return display;
            return null;
    }

    public interface AuctionsList{
        Auction getAuction(int position);
    }

    public static void removeAuctionFromList(Auction auction){
        if(AuctionMaster.auctionsHandler.global==null){
            globalAuctions.remove(auction);
        }
        else{
            AuctionMaster.auctionsHandler.removeAuctionFromBrowse(auction);
        }
    }

    public static AuctionsList auctionsList;
}
