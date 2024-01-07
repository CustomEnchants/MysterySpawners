package MysterySpawners;

import MysterySpawners.Commands.MysterySpawners;
import MysterySpawners.Listeners.PlayerListener;
import MysterySpawners.Utils.FileUtil;
import MysterySpawners.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MysterySpawnersPlugin extends JavaPlugin {

    private static MysterySpawnersPlugin instance;
    private FileUtil fileutil;
    private Util util;

    public static MysterySpawnersPlugin getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        fileutil = new FileUtil();
        util = new Util();
        getFileUtil().setup(getDataFolder());
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        getCommand("MysterySpawners").setExecutor(new MysterySpawners());
        getCommand("MysterySpawners").setTabCompleter(new MysterySpawners());
    }

    public void onDisable() {
        getFileUtil().mysterySpawnerMobs.clear();
        instance = null;
    }

    public Util getUtil(){
        return util;
    }

    public FileUtil getFileUtil() {
        return fileutil;
    }

}
