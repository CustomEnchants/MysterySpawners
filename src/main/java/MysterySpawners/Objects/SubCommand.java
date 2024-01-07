package MysterySpawners.Objects;

import MysterySpawners.MysterySpawnersPlugin;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public final MysterySpawnersPlugin instance = MysterySpawnersPlugin.getInstance();
    private final String name;
    private final String desc;
    private final String args;
    private String permission;

    public SubCommand(String name, String desc, String args, String permission) {
        this.name = name;
        this.desc = desc;
        this.args = args;
        this.setPermission(permission);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return desc;
    }

    public String getArgs() {
        return args;
    }

    public abstract void run(CommandSender cs, String[] args);

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isPermissionRequired(){
        return !getPermission().isEmpty();
    }

    public boolean hasAccess(CommandSender cs){
        return getPermission().isEmpty() || cs.hasPermission(getPermission());
    }
}