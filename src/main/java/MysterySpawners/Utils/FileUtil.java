package MysterySpawners.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtil {

    public File conf;

    public ItemStack mysterySpawnerItem;
    public ArrayList<String> mysterySpawnerMobs = new ArrayList<>();

    public ArrayList<String> commandMysterySpawnersGiveNoPermission = new ArrayList<>();
    public ArrayList<String> commandMysterySpawnersGiveGiven = new ArrayList<>();
    public ArrayList<String> commandMysterySpawnersGiveNotEnoughArguments = new ArrayList<>();
    public ArrayList<String> commandMysterySpawnersGivePlayerNotOnline = new ArrayList<>();
    public ArrayList<String> commandMysterySpawnersGiveInvalidAmount = new ArrayList<>();

    public ArrayList<String> commandMysterySpawnersGiveAllNoPermission = new ArrayList<>();
    public ArrayList<String> commandMysterySpawnersGiveAllGiven = new ArrayList<>();
    public ArrayList<String> commandMysterySpawnersGiveAllNotEnoughArguments = new ArrayList<>();
    public ArrayList<String> commandMysterySpawnersGiveAllInvalidAmount = new ArrayList<>();


    public ArrayList<String> commandMysterySpawnersReloadNoPermission = new ArrayList<>();
    public ArrayList<String> commandMysterySpawnersReloadReloaded = new ArrayList<>();

    public String fixColour(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public ArrayList<String> fixColours(List<String> input) {
        ArrayList<String> result = new ArrayList<>();
        input.forEach(string -> result.add(fixColour(string)));
        return result;
    }

    public void load(boolean reload) {
        if (reload) {
            mysterySpawnerMobs.clear();
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(conf);
        mysterySpawnerItem = config.getItemStack("MysterySpawners.item");
        mysterySpawnerMobs = (ArrayList<String>) config.getStringList("MysterySpawners.mobs");

        commandMysterySpawnersGiveNoPermission = fixColours(config.getStringList("Commands.MysterySpawners.Give.no-permission"));
        commandMysterySpawnersGiveNotEnoughArguments = fixColours(config.getStringList("Commands.MysterySpawners.Give.not-enough-arguments"));
        commandMysterySpawnersGiveGiven = fixColours(config.getStringList("Commands.MysterySpawners.Give.given"));
        commandMysterySpawnersGivePlayerNotOnline = fixColours(config.getStringList("Commands.MysterySpawners.Give.player-not-online"));
        commandMysterySpawnersGiveInvalidAmount = fixColours(config.getStringList("Commands.MysterySpawners.Give.invalid-amount"));

        commandMysterySpawnersGiveAllNoPermission = fixColours(config.getStringList("Commands.MysterySpawners.GiveAll.no-permission"));
        commandMysterySpawnersGiveAllNotEnoughArguments = fixColours(config.getStringList("Commands.MysterySpawners.GiveAll.not-enough-arguments"));
        commandMysterySpawnersGiveAllGiven = fixColours(config.getStringList("Commands.MysterySpawners.GiveAll.given"));
        commandMysterySpawnersGiveAllInvalidAmount = fixColours(config.getStringList("Commands.MysterySpawners.GiveAll.invalid-amount"));

        commandMysterySpawnersReloadNoPermission = fixColours(config.getStringList("Commands.MysterySpawners.Reload.no-permission"));
        commandMysterySpawnersReloadReloaded = fixColours(config.getStringList("Commands.MysterySpawners.Reload.reloaded"));
    }

    public void setup(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        conf = new File(dir + File.separator + "Config.yml");
        if (!conf.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(dir);
            config.set("MysterySpawners.item",createDefaultMysterySpawner());
            config.set("MysterySpawners.mobs", Arrays.asList("Enderman", "PigZombie", "Cow", "Creeper", "Blaze", "IronGolem","MagmaCube", "Ghast", "Skeleton", "Zombie"));

            config.set("Commands.MysterySpawners.Give.not-enough-arguments", Collections.singletonList("&b&l(!) &bMysterySpawners &7/mspawner give <name> <amount>"));
            config.set("Commands.MysterySpawners.Give.player-not-online",Collections.singletonList("&b&l(!) &bMysterySpawners &7Player is not online."));
            config.set("Commands.MysterySpawners.Give.given", Collections.singletonList("&b&l(!) &bMysterySpawners &7You have given &c%player% &c%amount% &7MysterySpawners"));
            config.set("Commands.MysterySpawners.Give.no-permission",Collections.singletonList("&b&l(!) &bMysterySpawners &cYou do not have permission to execute this command!"));
            config.set("Commands.MysterySpawners.Give.invalid-amount",Collections.singletonList("&b&l(!) &bMysterySpawners &cYou have specified a invalid amount!"));

            config.set("Commands.MysterySpawners.GiveAll.not-enough-arguments", Collections.singletonList("&b&l(!) &bMysterySpawners &7/MysterySpawners giveall <amount>"));
            config.set("Commands.MysterySpawners.GiveAll.given", Collections.singletonList("&b&l(!) &bMysterySpawners &7You have given everyone &c%amount% &7MysterySpawners"));
            config.set("Commands.MysterySpawners.GiveAll.no-permission",Collections.singletonList("&b&l(!) &bMysterySpawners &cYou do not have permission to execute this command!"));
            config.set("Commands.MysterySpawners.GiveAll.invalid-amount",Collections.singletonList("&b&l(!) &bMysterySpawners &cYou have specified a invalid amount!"));

            config.set("Commands.MysterySpawners.Reload.reloaded",Collections.singletonList("&b&l(!) &bMysterySpawners &7You have reloaded the configuration"));
            config.set("Commands.MysterySpawners.Reload.no-permission",Collections.singletonList("&b&l(!) &bMysterySpawners &cYou do not have permission to execute this command!"));
            try {
                config.save(conf);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        load(false);
    }

    private ItemStack createDefaultMysterySpawner(){
        ItemStack itemStack = Material.getMaterial("MOB_SPAWNER") == null ? new ItemStack(Material.getMaterial("SPAWNER")) : new ItemStack(Material.getMaterial("MOB_SPAWNER"));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(fixColour("&f&l* &6&l&nMystery Mob Spawner&f&l *"));
        itemMeta.setLore(fixColours(Arrays.asList("&7Right-click in hand to reveal one", "&7of the following mob spawner types:", "", "&6✖ &eEnderman", "&6✖ &ePigZombie", "&6✖ &eCow", "&6✖ &eCreeper", "&6✖ &eBlaze", "&6✖ &eIronGolem", "&6✖ &eMagmaCube", "&6✖ &eGhast", "&7", "&7Purchase these mystery spawners from", "&6&nhttps://Shop.Server.net/")));
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        itemStack.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        return itemStack;
    }

}
