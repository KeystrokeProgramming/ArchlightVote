package me.Dustin.com;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class VoteShopGUI
        implements Listener
{
    private static Inventory inv;
    private static Inventory inv2;
    VoteMain plugin;
    String Price = ChatColor.GREEN + "" + ChatColor.BOLD + "Price: ";
    String Buy = ChatColor.RED + " -";
    String Prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "ArclightVote" + ChatColor.WHITE + "]";
    private VoteAPI api;

    public VoteShopGUI(VoteMain instance)
    {
        this.plugin = instance;
        this.api = new VoteAPI(instance);
        setup(instance);
    }

    public void setup(Plugin p)
    {
        inv = Bukkit.getServer().createInventory(null, 18, ChatColor.WHITE + "[" + ChatColor.GOLD + "Token Shop" + ChatColor.WHITE + "]");

        ItemStack b = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta bm = b.getItemMeta();
        bm.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Buy =>");
        b.setItemMeta(bm);

        ItemStack np = new ItemStack(Material.EMERALD);
        ItemMeta npm = np.getItemMeta();
        npm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Next Page =>");
        np.setItemMeta(npm);

        ItemStack vk1 = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta vk1m = vk1.getItemMeta();
        vk1m.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Vote Kit" + ChatColor.YELLOW + " (Protection 1)");
        vk1m.setLore(Arrays.asList(new String[] { this.Price + ChatColor.RED + "12 Tokens", ChatColor.AQUA + "",
                ChatColor.AQUA + "Diamond Helmet",
                ChatColor.AQUA + "Diamond Chestplate",
                ChatColor.AQUA + "Diamond Leggings",
                ChatColor.AQUA + "Diamond Boots",
                ChatColor.AQUA + "Diamond Sword (Sharpness 3)",
                ChatColor.AQUA + "Diamond Axe",
                ChatColor.AQUA + "Splash Health 2 (x2)",
                ChatColor.AQUA + "Bow (Power 2)",
                ChatColor.AQUA + "Arrows (x64)",
                ChatColor.AQUA + "Golden Apples (x5)" }));
        vk1.setItemMeta(vk1m);

        inv.setItem(0, b);
        inv.setItem(1, vk1);
        inv.setItem(9, b);

        inv.setItem(17, np);

        Bukkit.getServer().getPluginManager().registerEvents(this, p);
    }

    public static ItemStack createItem1(Material material, String displayname) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayname);
        item.setItemMeta(meta);
        return item;
    }

    public void show(Player p) {
        p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return;
        if (e.getCurrentItem().getItemMeta() == inv) return;

        Player p = (Player)e.getWhoClicked();
        if ((e.getCurrentItem().equals(null)) ||
                (e.getCurrentItem().getType().equals(Material.AIR)) ||
                (!e.getCurrentItem().hasItemMeta())) {
            p.closeInventory();
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Buy =>")) {
            p.sendMessage(this.Prefix + ChatColor.RED + " Error: You cannot buy this Item!");
            p.closeInventory();
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&a&lNext Page =>"))) {
            p.openInventory(inv2);
        }
        if (VoteMain.cooldown1.get(p.getName()) <= 0) {
        	// This will then set the cooldown to 10 seconds
        	VoteMain.cooldown1.put(p.getName(), 10);
        	// Do all of your stuff here
        	}getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Vote Kit" + ChatColor.YELLOW + " (Protection 1)"))
        {
            ItemStack dah = new ItemStack(Material.DIAMOND_HELMET);
            ItemMeta dahm = dah.getItemMeta();
            dahm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            dah.setItemMeta(dahm);

            ItemStack dch = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemMeta dchm = dch.getItemMeta();
            dchm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            dch.setItemMeta(dchm);

            ItemStack dlh = new ItemStack(Material.DIAMOND_LEGGINGS);
            ItemMeta dlhm = dlh.getItemMeta();
            dlhm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            dlh.setItemMeta(dlhm);

            ItemStack dbh = new ItemStack(Material.DIAMOND_BOOTS);
            ItemMeta dbhm = dbh.getItemMeta();
            dbhm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            dbh.setItemMeta(dbhm);

            ItemStack testEnchant = new ItemStack(Material.BOW, 1);
            ItemMeta testEnchantMeta = testEnchant.getItemMeta();
            testEnchantMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
            testEnchant.setItemMeta(testEnchantMeta);

            ItemStack dsh = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta dshm = dsh.getItemMeta();
            dshm.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
            dsh.setItemMeta(dshm);

            if (this.api.doTransaction(p, 12)) {
                p.sendMessage(this.Prefix + ChatColor.GREEN + " You have sucessfully purchased" + ChatColor.YELLOW + " \n Vote Kit (Protection 1)!");
                p.getInventory().addItem(dah);
                p.getInventory().addItem(dch);
                p.getInventory().addItem(dlh);
                p.getInventory().addItem(dbh);
                p.getInventory().addItem(dsh);
                p.getInventory().addItem(testEnchant);
                p.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE));
                p.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 5));
                p.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
                p.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
                p.sendMessage(this.Prefix + ChatColor.RED + " You have " + ChatColor.GREEN + this.api.getBalance(p) + ChatColor.RED + " tokens left!");
                p.closeInventory();
            } else {
                p.sendMessage(this.Prefix + ChatColor.RED + " You don't have enough tokens to buy this!");
                p.closeInventory();
            }
        }
    }
}