package world.sc2.shadowcraftrelics.relics.on_move;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import world.sc2.shadowcraftrelics.relics.Relic;

/**
 * An interface that represents a {@link Relic} with functionality that triggers whenever a {@link Player} wearing
 * this {@link Relic} moves in a {@link EntityMoveEvent}.
 */
public interface TriggerOnMoveRelic {

    /**
     * This method is called whenever it is determined that a {@link Player} has moved while wearing this
     * {@link Relic}.
     * @param event The instance of {@link PlayerMoveEvent} generated by the player's movement.
     */
    void onMove(PlayerMoveEvent event);

}