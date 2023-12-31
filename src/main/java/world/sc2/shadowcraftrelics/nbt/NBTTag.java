package world.sc2.shadowcraftrelics.nbt;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

/**
 * Represents all data stored within an NBT Tag. This data includes the {@link NamespacedKey} of the NBTTag, the
 * {@link PersistentDataType} of the NBT Tag, and the actual Data stored within that NBT Tag.
 */
public class NBTTag<T, Z> {

    private final NamespacedKey namespacedKey;

    private final PersistentDataType<T, Z> persistentDataType;
    private final Z defaultData;

    /**
     * Creates a new NBTTag Object without starting data.
     * @param namespacedKey The {@link NamespacedKey} of this NBT Tag.
     * @param persistentDataType The data type of this NBT Tag.
     */
    public NBTTag(NamespacedKey namespacedKey, PersistentDataType<T, Z> persistentDataType) {
        this.namespacedKey = namespacedKey;
        this.persistentDataType = persistentDataType;
        defaultData = null;
    }

    /**
     * Creates a new NBTTag Object with initial stored data.
     * @param namespacedKey The {@link NamespacedKey} of this NBT Tag.
     * @param persistentDataType The data type of this NBT Tag.
     * @param defaultData The data initially stored within this NBT Tag.
     */
    public NBTTag(NamespacedKey namespacedKey, PersistentDataType<T, Z> persistentDataType, Z defaultData) {
        this.namespacedKey = namespacedKey;
        this.persistentDataType = persistentDataType;
        this.defaultData = defaultData;
    }

    /**
     * @return The {@link NamespacedKey} of this NBT Tag.
     */
    public NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }

    /**
     * @return The data type of this NBT Tag.
     */
    public PersistentDataType<T, Z> getPersistentDataType() {
        return persistentDataType;
    }

    /**
     * @return The data initially stored within this NBT Tag.
     */
    public Z getDefaultData() {
        return defaultData;
    }

    /**
     * Applies this NBT Tag to the {@link ItemStack} given.
     * @param item The {@link ItemStack} to apply this NBT Tag to.
     * @throws IllegalStateException if called when default data was not supplied to the NBT Tag.
     */
    public void applyTag(ItemStack item) {
        if (defaultData == null) {
            throw new IllegalStateException("Attempted to apply NBT Tag " + namespacedKey + ", which does not have a " +
                    "set default value, to an item without supplying a value.");
        }
        ItemMeta itemMeta = item.getItemMeta();
        applyTag(itemMeta);
        item.setItemMeta(itemMeta);
    }

    /**
     * Applies this NBT Tag to the {@link PersistentDataHolder} given.
     * @param persistentDataHolder The {@link PersistentDataHolder} to apply this NBT Tag to.
     * @throws IllegalStateException if called when default data was not supplied to the NBT Tag.
     */
    public void applyTag(PersistentDataHolder persistentDataHolder) {
        if (defaultData == null) {
            throw new IllegalStateException("Attempted to apply NBT Tag " + namespacedKey + ", which does not have a " +
                    "set default value, to a PersistentDataHolder without supplying a value.");
        }
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        dataContainer.set(namespacedKey, persistentDataType, defaultData);
    }

    /**
     * Applies this NBT Tag to the {@link ItemStack} with the data given.
     * @param item The {@link ItemStack} to apply this NBT Tag to.
     */
    public void applyTag(ItemStack item, Z data) {
        ItemMeta itemMeta = item.getItemMeta();
        applyTag(itemMeta, data);
        item.setItemMeta(itemMeta);
    }

    /**
     * Applies this NBT Tag to the {@link PersistentDataHolder} with the data given.
     * @param persistentDataHolder The {@link PersistentDataHolder} to apply this NBT Tag to.
     */
    public void applyTag(PersistentDataHolder persistentDataHolder, Z data) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        dataContainer.set(namespacedKey, persistentDataType, data);
    }
}
