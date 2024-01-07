package MysterySpawners.Commands.SubCommands;

import MysterySpawners.Objects.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class GiveAll extends SubCommand {

    public GiveAll() {
        super("GiveAll", "Give everyone a mystery spawner", "<amount>", "MysterySpawners.give.all");
    }

    @Override
    public void run(CommandSender cs, String[] args) {
        if(!cs.hasPermission(getPermission())){
            instance.getFileUtil().commandMysterySpawnersGiveAllNoPermission.forEach(cs::sendMessage);
            return;
        }
        if(args.length < 1){
            instance.getFileUtil().commandMysterySpawnersGiveAllNotEnoughArguments.forEach(cs::sendMessage);
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            instance.getFileUtil().commandMysterySpawnersGiveAllInvalidAmount.forEach(cs::sendMessage);
            return;
        }
        ItemStack itemStack = instance.getUtil().createMysterySpawner(amount);
        Bukkit.getServer().getOnlinePlayers().forEach(targetPlayer -> {
            targetPlayer.getInventory().addItem(itemStack);
            targetPlayer.updateInventory();
        });
        instance.getFileUtil().commandMysterySpawnersGiveAllGiven.forEach(cs::sendMessage);
    }
}
