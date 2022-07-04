package fr.aiwoz.authentificator.commands;

import fr.aiwoz.authentificator.Main;
import fr.aiwoz.authentificator.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePassword implements CommandExecutor {

    private final Main main;

    public ChangePassword(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player)sender;

            if(this.main.configuration.contains(player.getUniqueId().toString())) {
                if(args.length == 3) {
                    if(this.main.configuration.get(player.getUniqueId().toString()).equals(args[0])) {
                        if(args[1].equals(args[2])) {
                            this.main.configuration.set(player.getUniqueId().toString(), args[1]);
                            this.main.saveConfiguration();
                            player.sendMessage(Utils.color(this.main.config.getString("successful-changed-password")));
                        } else {
                            player.sendMessage(Utils.color(this.main.config.getString("new-different-password")));
                        }
                    } else {
                        player.sendMessage(Utils.color(this.main.config.getString("incorrect-currently-password")));
                    }
                } else {
                    player.sendMessage(Utils.color(this.main.config.getString("changepw-error")));
                }
            } else {
                player.sendMessage(Utils.color(this.main.config.getString("not-found")));
            }

        }
        return false;
    }
}