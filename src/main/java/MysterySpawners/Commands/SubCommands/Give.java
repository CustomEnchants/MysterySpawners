package MysterySpawners.Commands.SubCommands;

import MysterySpawners.Objects.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Give extends SubCommand {

    public Give() {
        super("Give", "Give a mystery spawner to a player", "<player> <amount>", "MysterySpawners.give");
    }

    @Override
    public void run(CommandSender cs, String[] args) {
        if(!cs.hasPermission(getPermission())){
            instance.getFileUtil().commandMysterySpawnersGiveNoPermission.forEach(cs::sendMessage);
            return;
        }
        if(args.length < 2){
            instance.getFileUtil().commandMysterySpawnersGiveNotEnoughArguments.forEach(cs::sendMessage);
            return;
        }

        Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
        if (targetPlayer == null) {
            instance.getFileUtil().commandMysterySpawnersGivePlayerNotOnline.forEach(cs::sendMessage);
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            instance.getFileUtil().commandMysterySpawnersGiveInvalidAmount.forEach(cs::sendMessage);
            return;
        }
        ItemStack itemStack = instance.getUtil().createMysterySpawner(amount);
        targetPlayer.getInventory().addItem(itemStack);
        targetPlayer.updateInventory();
        instance.getFileUtil().commandMysterySpawnersGiveGiven.stream().map(string -> string.replace("%amount%", String.valueOf(amount)).replace("%player%", args[0])).forEach(cs::sendMessage);
    }

}
