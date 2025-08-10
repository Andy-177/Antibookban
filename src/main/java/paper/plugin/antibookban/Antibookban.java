package paper.plugin.antibookban;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Antibookban extends JavaPlugin implements Listener {
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, (Plugin)this);
  }
  
  public void onDisable() {}
  
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Block block = event.getClickedBlock();
    if (block != null && block.getType() == Material.getMaterial("SHULKER_BOX")) {
      ShulkerBox shulkerBox = (ShulkerBox)block.getState();
      checkShulkerBox(shulkerBox.getInventory());
    } 
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    if (event.getInventory().getType() == InventoryType.SHULKER_BOX)
      checkShulkerBox(event.getInventory()); 
  }
  
  @EventHandler
  public void onInventoryOpen(InventoryOpenEvent event) {
    if (event.getInventory().getType() == InventoryType.SHULKER_BOX)
      checkShulkerBox(event.getInventory()); 
  }
  
  @EventHandler
  public void onItemSpawn(ItemSpawnEvent event) {
    if (event.getEntity().getItemStack().getType() == Material.getMaterial("SHULKER_BOX"))
      checkShulkerBox(event.getEntity().getItemStack()); 
  }
  
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    if (event.getBlock().getType() == Material.getMaterial("SHULKER_BOX")) {
      BlockState blockState = event.getBlock().getState();
      if (blockState instanceof ShulkerBox) {
        ShulkerBox shulkerBox = (ShulkerBox)blockState;
        checkShulkerBox(shulkerBox.getInventory());
      } 
    } 
  }
  
  @EventHandler
  public void onPlayerPickupItem(EntityPickupItemEvent event) {
    checkShulkerBox(event.getItem().getItemStack());
  }
  
  @EventHandler
  public void onInventoryMoveItem(InventoryMoveItemEvent event) {
    if (event.getSource().getType() == InventoryType.HOPPER) {
      ItemStack item = event.getItem();
      if (item != null && item.getType() == Material.WRITTEN_BOOK) {
        event.setCancelled(true);
      } else {
        event.setCancelled(false);
      } 
    } 
  }
  
  private void checkShulkerBox(Inventory inventory) {
    for (int i = 0; i < inventory.getSize(); i++) {
      ItemStack item = inventory.getItem(i);
      if (item != null && item.getType() == Material.WRITTEN_BOOK)
        inventory.clear(i); 
    } 
  }
  
  private void checkShulkerBox(ItemStack itemStack) {
    if (itemStack.getType() == Material.getMaterial("SHULKER_BOX")) {
      Inventory inventory = Bukkit.createInventory(null, 27);
      checkShulkerBox(inventory);
    } 
  }
}
