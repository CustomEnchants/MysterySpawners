package MysterySpawners.Commands;

import MysterySpawners.Commands.SubCommands.Give;
import MysterySpawners.Commands.SubCommands.GiveAll;
import MysterySpawners.Commands.SubCommands.Reload;
import MysterySpawners.MysterySpawnersPlugin;
import MysterySpawners.Objects.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MysterySpawners implements CommandExecutor, TabCompleter {

    private final MysterySpawnersPlugin instance = MysterySpawnersPlugin.getInstance();
    public final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public MysterySpawners() {
        subCommands.add(new Give());
        subCommands.add(new GiveAll());
        subCommands.add(new Reload());
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lab, String[] args) {
        if (cmd.getName().equalsIgnoreCase("MysterySpawners")) {
            if (args.length < 1) {
                sendHelp(cmd, cs, 1);
                return false;
            }
            Optional<SubCommand> subCommandOptional = subCommands.stream().filter(subCommand -> subCommand.getName().equalsIgnoreCase(args[0])).findFirst();
            if(!subCommandOptional.isPresent()){
                sendHelp(cmd, cs, 1);
                return false;
            }
            ArrayList<String> a = new ArrayList<>(Arrays.asList(args));
            a.remove(0);
            subCommandOptional.get().run(cs, a.toArray(new String[0]));
            return false;
        }
        return false;
    }

    public void sendHelp(Command cmd, CommandSender cs,int page) {
        new BukkitRunnable() {
            public void run() {
                cs.sendMessage(instance.getUtil().fixColour("&6___________.[ &2MysterySpawners Page &c%page% &6].___________".replace("%page%", "" + page)));
                int perPage = 10;
                int maxPage = subCommands.size() == 0 ? 1 : Math.max((int) Math.ceil((double) subCommands.size() / perPage), 1);
                int actualPage = Math.min(page, maxPage);
                int min = actualPage == 1 ? 0 : actualPage * perPage - perPage;
                int max = actualPage == 1 ? perPage : min + perPage;
                ArrayList<SubCommand> subCommandsForPlayer = subCommands.stream().filter(subCommand -> subCommand.hasAccess(cs)).collect(Collectors.toCollection(ArrayList::new));
                for (int i = min; i < max; i++) {
                    if (subCommandsForPlayer.size() <= i) break;
                    SubCommand subCommand = subCommandsForPlayer.get(i);
                    cs.sendMessage(instance.getUtil().fixColour("&b/" + cmd.getName() + " &7" + subCommand.getName() + " &7" + subCommand.getArgs() + " &7"+subCommand.getDescription()));
                }
                cancel();
            }
        }.runTaskAsynchronously(instance);
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String s, String[] strings) {
        if(cmd.getName().equalsIgnoreCase("MysterySpawners")){
            return subCommands.stream().filter(subCommand -> subCommand.hasAccess(cs)).map(SubCommand::getName).collect(Collectors.toCollection(ArrayList::new));
        }
        return null;
    }


}
