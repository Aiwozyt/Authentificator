package fr.aiwoz.authentificator.events;

import fr.aiwoz.authentificator.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

public class Events implements Listener {

    private final Main main;

    public Events(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(this.main.waiting.contains(e.getPlayer().getName())) {
            e.getPlayer().teleport(e.getFrom());
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e) {
        if(this.main.waiting.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
        if(this.main.waiting.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent e) {
        if(this.main.waiting.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        if(this.main.waiting.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(this.main.waiting.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void bucketFill(PlayerBucketFillEvent e) {
        if(this.main.waiting.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void bucketEmpty(PlayerBucketEmptyEvent e) {
        if(this.main.waiting.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void editBook(PlayerEditBookEvent e) {
        if(this.main.waiting.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        if(this.main.waiting.contains(e.getPlayer().getName())) {
            String[] args = e.getMessage().split(" ", 2);
            if(args[0].equals("/login") || args[0].equals("/register")) {} else {
                e.setCancelled(true);
            }
        }
    }

}
