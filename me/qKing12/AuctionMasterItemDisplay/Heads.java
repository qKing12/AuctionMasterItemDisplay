package me.qKing12.AuctionMasterItemDisplay;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Heads {
    public static ItemStack top1;
    public static ItemStack top2;
    public static ItemStack top3;
    public static ItemStack top4;
    public static ItemStack top5;
    public static ItemStack top6;
    public static ItemStack top7;
    public static ItemStack top8;
    public static ItemStack top9;
    public static ItemStack top10;
    public static ItemStack top11;
    public static ItemStack top12;
    public static ItemStack top13;
    public static ItemStack top14;
    public static ItemStack top15;
    public static ItemStack top16;
    public static ItemStack top17;
    public static ItemStack top18;
    public static ItemStack top19;
    public static ItemStack top20;
    public static ItemStack topMore;

    public static ItemStack removalItem;

    public static String displayName = utils.chat("&e#%position% Place Tool &6(Right Click Block)");

    public static ItemStack getHead(int position){
        switch(position){
            case 1:
                return top1;
            case 2:
                return top2;
            case 3:
                return top3;
            case 4:
                return top4;
            case 5:
                return top5;
            case 6:
                return top6;
            case 7:
                return top7;
            case 8:
                return top8;
            case 9:
                return top9;
            case 10:
                return top10;
            case 11:
                return top11;
            case 12:
                return top12;
            case 13:
                return top13;
            case 14:
                return top14;
            case 15:
                return top15;
            case 16:
                return top16;
            case 17:
                return top17;
            case 18:
                return top18;
            case 19:
                return top19;
            case 20:
                return top20;
            default:
                return topMore;
        }
    }

    public Heads(){
        top1=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU1ZmMyYzFiYWU4ZTA4ZDNlNDI2YzE3YzQ1NWQyZmY5MzQyMjg2ZGZmYTNjN2MyM2Y0YmQzNjVlMGMzZmUifX19");
        top2=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM2MWIwNGUxMmE4Nzk3NjdiM2I3MmQ2OTYyN2YyOWE4M2JkZWI2MjIwZjVkYzdiZWEyZWIyNTI5ZDViMDk3In19fQ");
        top3=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyM2Y3NzU1OGNhNjA2MGI2ZGM2YTRkNGIxZDg2YzFhNWJlZTcwODE2NzdiYmMzMzZjY2I5MmZiZDNlZSJ9fX0");
        top4=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTFiOWM0ZDZmNzIwOGIxNDI0Zjg1OTViZmMxYjg1Y2NhYWVlMmM1YjliNDFlMGY1NjRkNGUwYWNhOTU5In19fQ");
        top5=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmMxNDE1OTczYjQyZjgyODZmOTQ4ZTIxNDA5OTJiOWEyOWQ4MDk2NTU5M2IxNDU1M2Q2NDRmNGZlYWZiNyJ9fX0");
        top6=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZmNWZiZmJjODk0NGE1MDc3NzExMzc5OGU5ZmUzYWVhYzJlMzk2NDg5NDdiNzBjYzEwM2RlYjZjOWU4NjYxIn19fQ");
        top7=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYzYWNkNmRmZDViY2JmYTlmYzg0ZGYzZDcwNGJiZDlkMTQ4N2VhODdjZjU2NDQyN2JiOGVjOTVjNjUyMjcifX19");
        top8=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGViMTE1ZmE0N2E5ZDJkNmQ0MWU1YWE3YmU0ZTEzNjdmZjkyYzRlOTQ2NTg5N2Q1ZDYyYTc2NWVmOTI0ZjQifX19");
        top9=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWFiOGEyN2FlZTQ3NzlmMDQzYTY2OGM5ZTkyN2EzNTNlNjNhYzc5MWVkM2Y1NmEyNGY2MWQ5NDM5Y2NmZDUxNSJ9fX0");
        top10=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM1NzE5NDQ1NjNlNjFhNzY1ZTk1ZWU4NGM4Y2Q3OWJmYjU4YTA3OGFhZDMzYmFiM2ExY2JhMzdlOGUzNDUwIn19fQ");
        top11=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFjZjM0NWIyMjIzYzA2NmU4Y2I2OTkyMDc3OWE0ODdmZDg5YmE5N2YxNWNlN2E5Yjc0NzIxNTc2NDkifX19");
        top12=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdhNDlkMzUxMTZmNjM3ZjJmNmZkN2FiZjlkNTUzMTU0M2U2Y2ZiMzA5YWYyNmRlNTUzZjZmM2Y3YmVmYzAifX19");
        top13=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTFhMzI0OTRhOWJhODI1NzliYzE4OTBhYjVkNTI5Y2U5ZjRhOTUzYzhjY2U0YmE2ZDFhMzdlZmViNjBjNjJlIn19fQ");
        top14=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTU1M2Q1ZmQ2OWYxZTgzY2IzODE4NDg3ODg5MjYyNzQ1NDI3NjQ2ZTRmOTg5ODI4ZTA3NDRlOTY2MjVmZGI5YSJ9fX0");
        top15=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGNkMTU4MWM3Mzk1NDc4NjM3NzhkNGYxMjQ5OWI3ZDA2OTMyZmYxZGUyYzU3MmRhM2YzMDNkNTg3MDE5YjYifX19");
        top16=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjljN2Q4Zjg2MWYzNTVhZjVhMmNmNjRiZjYyMTc3ZTVkYTE5NGMyOTU1YjFhZDYzMDRlMmQ3OWU0N2I3YSJ9fX0");
        top17=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzlkYmIzMjdhZjM0NTA0NmQwOTE2YzM4YWNhMmQ0Y2MzYTkyODQ4MWYzODcyNjBhNTViMDVhYzgxMDI1YzAifX19");
        top18=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2M2ZTU1MzMzYjhmOGNhNWVjYzNiNDFiM2ZhMmRlZWJhYTk4OGZhNWZjN2IwYzdhZDNhNGE4ODI2NTM4NWE3In19fQ");
        top19=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTA5MTIwZDc5MTg4ZmRlNjU5ZjRmYmQzZGQyZmI3MTA4N2I1YThiOWJjNjBiNDk2MjcyNzlhNjdmZDY1NWY3In19fQ");
        top20=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBlMDFmMmM2YWYzZDI5NzhiYTFhMTFmMTAyNWZmNjJhNTRjN2FkMjRmODU2YTQ5Yjg0NTJkYzRmZGQ3ZjkifX19");
        topMore=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzUxMTM3ZTExNDQzYThmYmIwNWZjZDNjY2MxYWY5YmQyMzAzOTE4ZjM1NDQ4MTg1ZTNlZDk2ZWYxODRkYSJ9fX0");

        removalItem=utils.setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");

        ItemMeta meta = removalItem.getItemMeta();
        meta.setDisplayName(utils.chat("&cRemoval Tool"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(utils.chat("&7Click on a display"));
        lore.add(utils.chat("&7to remove it."));
        meta.setLore(lore);
        removalItem.setItemMeta(meta);

    }
}
