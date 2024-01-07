package MysterySpawners.Listeners;

import MysterySpawners.MysterySpawnersPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerListener implements Listener {

    private final MysterySpawnersPlugin instance = MysterySpawnersPlugin.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR) return;
        Player p = event.getPlayer();
        if (event.getItem() == null) return;
        if(!event.getItem().isSimilar(instance.getFileUtil().mysterySpawnerItem)) return;
        int rand = new Random().nextInt(instance.getFileUtil().mysterySpawnerMobs.size());
        String mob = instance.getFileUtil().mysterySpawnerMobs.get(rand);
        if (mob == null) return;
        if (event.getItem().getAmount() == 1) {
            p.setItemInHand(null);
        } else {
            ItemStack newItemStack = event.getItem().clone();
            newItemStack.setAmount(newItemStack.getAmount() - 1);
            p.setItemInHand(newItemStack);
        }
        p.updateInventory();
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("CustomsStackedSpawners") || Bukkit.getServer().getPluginManager().isPluginEnabled("CustomsSilkSpawners")){
            String cmd = "css givespawner %name% %mob% 1".replace("%name%", p.getName()).replace("%mob%", mob);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            event.setCancelled(true);
            event.setUseInteractedBlock(Result.DENY);
            p.updateInventory();
            return;
        }
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("AdvancedSpawners")){
            String cmd = "as give %name% %mob% 1 1".replace("%name%", p.getName()).replace("%mob%", mob);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            event.setCancelled(true);
            event.setUseInteractedBlock(Result.DENY);
            p.updateInventory();
            return;
        }
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("WildStacker")){
            String cmd = "stacker give %name% spawner %mob% 1".replace("%name%", p.getName()).replace("%mob%", mob);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            event.setCancelled(true);
            event.setUseInteractedBlock(Result.DENY);
            p.updateInventory();
            return;
        }
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("SilkSpawners")){
            String cmd = "ss give %name% %mob% 1".replace("%name%", p.getName()).replace("%mob%", mob);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            event.setCancelled(true);
            event.setUseInteractedBlock(Result.DENY);
            p.updateInventory();
        }
    }


}
