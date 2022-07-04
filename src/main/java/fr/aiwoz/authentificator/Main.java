package fr.aiwoz.authentificator;

import fr.aiwoz.authentificator.commands.ChangePassword;
import fr.aiwoz.authentificator.commands.Login;
import fr.aiwoz.authentificator.commands.Register;
import fr.aiwoz.authentificator.events.*;
import fr.aiwoz.authentificator.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends JavaPlugin implements Listener {

    public ArrayList<String> waiting = new ArrayList<>();
    public FileConfiguration config = getConfig();
    public File data = new File(this.getDataFolder(), "players.yml");
    public YamlConfiguration configuration = YamlConfiguration.loadConfiguration(data);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(this, (Plugin) this);
        Bukkit.getPluginManager().registerEvents(new Events(this), (Plugin) this);

        this.getCommand("login").setExecutor(new Login(this));
        this.getCommand("register").setExecutor(new Register(this));
        this.getCommand("changepassword").setExecutor(new ChangePassword(this));
    }

    @Override
    public void onDisable() {
        if(config.getBoolean("plugin-stop.enable")) {
            for (String allPlayer : waiting) {
                Bukkit.getPlayer(allPlayer).kickPlayer(config.getString("plugin-stop.message"));
            }
        }
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if(config.getBoolean("join-message.enable")) {
            player.sendMessage(config.getString("join-message.message"));
        }

        if(config.getBoolean("op-bypass")) {
            if(player.isOp()) {
            } else if(player.hasPermission("authentificator.bypass")){
            } else {
                waiting.add(e.getPlayer().getName());

                if(configuration.contains(player.getUniqueId().toString())) {

                    player.sendMessage(Utils.color(config.getString("login-message")));
                } else {
                    player.sendMessage(Utils.color(config.getString("register-message")));
                }
            }
        } else {
            if(player.hasPermission("authentificator.bypass")) {
            } else {
                waiting.add(e.getPlayer().getName());
                if(configuration.contains(player.getUniqueId().toString())) {
                    player.sendMessage(Utils.color(config.getString("login-message")));
                } else {
                    player.sendMessage(Utils.color(config.getString("register-message")));
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(waiting.contains(e.getPlayer().getName())) {
           waiting.remove(e.getPlayer().getName());
        }
    }

    public void saveConfiguration() {
        try {
            configuration.save(data);
        } catch (IOException iOException) {}
    }

}
