package world.sc2.shadowcraftrelics.relics.on_interact;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import world.sc2.shadowcraftrelics.ShadowcraftRelics;
import world.sc2.shadowcraftrelics.config.Config;
import world.sc2.shadowcraftrelics.relics.Relic;
import world.sc2.shadowcraftrelics.relics.on_attack.TriggerOnAttackRelic;
import world.sc2.shadowcraftrelics.util.ItemUtils;

public class Purger extends Relic implements TriggerOnInteractRelic, TriggerOnAttackRelic {

    private final NamespacedKey purgerStoredItemKey;
    public Purger(String name, Config config, ShadowcraftRelics plugin) {
        super(name, config);

        purgerStoredItemKey = new NamespacedKey(plugin, "storedPurgerItem");
    }
    @Override
    public void onInteract(PlayerInteractEvent event) {
        ItemStack currentPurger = event.getPlayer().getInventory().getItemInMainHand();
        switchCurrentPurger(event.getPlayer(), currentPurger);
    }

    @Override
    public void onAttack(EntityDamageByEntityEvent event) {
        Player player = ((Player) event.getDamager());
        ItemStack currentPurger = player.getEquipment().getItemInMainHand();
        switchCurrentPurger(player, currentPurger);
    }

    private void switchCurrentPurger(Player player, ItemStack currentPurger) {
        ItemMeta currentPurgerMeta = currentPurger.getItemMeta();
        PersistentDataContainer currentPurgerPDC = currentPurgerMeta.getPersistentDataContainer();

        if (!currentPurgerPDC.has(purgerStoredItemKey, PersistentDataType.BYTE_ARRAY)) {
            Bukkit.getLogger().severe("Purger cannot switch because Purger does not have purgerStoredItemKey!");
            return;
        }

        // Create stored Purger ItemStack
        ItemStack nextPurger = ItemUtils.deserializeItemStack(
                    currentPurgerPDC.get(purgerStoredItemKey, PersistentDataType.BYTE_ARRAY));

        ItemMeta nextPurgerMeta = nextPurger.getItemMeta();
        PersistentDataContainer nextPurgerPDC = nextPurgerMeta.getPersistentDataContainer();

        // remove next Purger's data from the current Purger's PDC
        currentPurgerPDC.remove(purgerStoredItemKey);
        currentPurger.setItemMeta(currentPurgerMeta);

        // Serialize and store current Purger inside the next Purger
        nextPurgerPDC.set(purgerStoredItemKey, PersistentDataType.BYTE_ARRAY, ItemUtils.serializeItemStack(currentPurger));

        nextPurger.setItemMeta(nextPurgerMeta);

        // Replace the current Purger with the next Purger
        player.getInventory().setItemInMainHand(nextPurger);

        // todo  a d d  D o p a m i n e  s o u n d

    }

}