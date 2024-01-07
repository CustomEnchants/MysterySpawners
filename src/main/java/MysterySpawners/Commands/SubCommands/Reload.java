package MysterySpawners.Commands.SubCommands;

import MysterySpawners.Objects.SubCommand;
import org.bukkit.command.CommandSender;

public class Reload extends SubCommand {

    public Reload() {
        super("Reload", "Reload the configuration file", "", "MysterySpawners.reload");
    }

    @Override
    public void run(CommandSender cs, String[] args) {
        if(!cs.hasPermission(getPermission())){
            instance.getFileUtil().commandMysterySpawnersReloadNoPermission.forEach(cs::sendMessage);
            return;
        }
        instance.getFileUtil().load(true);
        instance.getFileUtil().commandMysterySpawnersReloadReloaded.forEach(cs::sendMessage);
    }
}
