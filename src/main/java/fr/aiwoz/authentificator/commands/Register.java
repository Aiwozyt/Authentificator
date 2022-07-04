package fr.aiwoz.authentificator.commands;

import fr.aiwoz.authentificator.Main;
import fr.aiwoz.authentificator.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Register implements CommandExecutor {

    private final Main main;

    public Register(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player)sender;

            if(this.main.waiting.contains(player.getName())) {
                if(this.main.configuration.contains(player.getUniqueId().toString())) {
                    player.sendMessage(Utils.color(this.main.config.getString("already-registered")));
                } else {
                    if(args.length == 2) {
                        if(args[0].equals(args[1])) {
                            this.main.configuration.set(player.getUniqueId().toString(), args[0]);
                            this.main.saveConfiguration();
                            this.main.waiting.remove(player.getName());
                            player.sendMessage(Utils.color(this.main.config.getString("successful-register")));
                        } else {
                            player.sendMessage(Utils.color(this.main.config.getString("different-password")));
                        }
                    } else {
                        player.sendMessage(Utils.color(this.main.config.getString("register-error")));
                    }
                }
            } else {
                player.sendMessage(Utils.color(this.main.config.getString("already-logged")));
            }
        }
        return false;
    }

}
