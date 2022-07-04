package fr.aiwoz.authentificator.commands;

import fr.aiwoz.authentificator.Main;
import fr.aiwoz.authentificator.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Login implements CommandExecutor {

    private final Main main;

    public Login(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            if(this.main.waiting.contains(player.getName())) {
                if(this.main.configuration.contains(player.getUniqueId().toString())) {
                    if(args.length == 1) {
                        if(this.main.configuration.get(player.getUniqueId().toString()).equals(args[0])) {
                            player.sendMessage(Utils.color(this.main.config.getString("sucessful-login")));
                            this.main.waiting.remove(player.getName());
                        } else {
                            player.sendMessage(Utils.color(this.main.config.getString("incorrect-password")));
                        }
                    } else {
                        player.sendMessage(Utils.color(this.main.config.getString("login-error")));
                    }
                } else {
                    player.sendMessage(Utils.color(this.main.config.getString("not-found")));
                }
            } else {
                player.sendMessage(Utils.color(this.main.config.getString("already-logged")));
            }
        }
        return false;
    }


}
