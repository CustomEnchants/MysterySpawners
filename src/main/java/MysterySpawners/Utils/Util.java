package MysterySpawners.Utils;

import MysterySpawners.MysterySpawnersPlugin;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private final MysterySpawnersPlugin instance = MysterySpawnersPlugin.getInstance();

    public String fixColour(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public ArrayList<String> fixColours(List<String> input) {
        ArrayList<String> result = new ArrayList<>();
        input.forEach(string -> result.add(fixColour(string)));
        return result;
    }

    public ItemStack createMysterySpawner(int amount){
        ItemStack itemStack = instance.getFileUtil().mysterySpawnerItem.clone();
        itemStack.setAmount(amount);
        return itemStack;
    }
}
