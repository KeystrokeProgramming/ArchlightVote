package me.Dustin.com;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VoteMain extends JavaPlugin
        implements Listener
{
    public static Economy economy = null;
    String Prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "ArclightVote" + ChatColor.WHITE + "]";
    private VoteShopGUI menu;
    private VoteAPI api;
    public static Permission permission = null;

    public void onEnable()
    {
        this.api = new VoteAPI(this);
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        this.menu = new VoteShopGUI(this);
        setupPermissions();
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName() }));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        if (!e.getPlayer().hasPlayedBefore()) 
            this.api.setBalance(Bukkit.getServer().getPlayer(e.getPlayer().getName()), 0);
    }

    @EventHandler
    public void onPlayerVote(VotifierEvent e)
    {
        Vote h = e.getVote();
        Bukkit.getServer().broadcastMessage(this.Prefix + ChatColor.GOLD + " " + h.getUsername() + ChatColor.GREEN + " has voted, and received" + ChatColor.RED + " One Voting Token!");
        Player p = Bukkit.getServer().getPlayer(h.getUsername());
        if (p == null) {
            return;
        }
        this.api.setBalance(p, this.api.getBalance(p) + 1);
        p.sendMessage(this.Prefix + ChatColor.YELLOW + " Thanks for voting! You have" + ChatColor.RED + this.api.getBalance(Bukkit.getServer().getPlayer(h.getUsername())) + ChatColor.YELLOW + " Tokens!");
        p.sendMessage(this.Prefix + ChatColor.GOLD + " Type /tokens shop to Spend Them!");
    }

    private boolean setupEconomy()
    {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = (Economy)rsp.getProvider();
        return economy != null;
    }

    public void onDisable() {
        saveConfig();
    }

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = (Permission)permissionProvider.getProvider();
        }
        return permission != null;
    }

    public void addPerm(String perm, Player player) {
        permission.playerAdd(player, perm);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        Player p = (Player)sender;

        if (commandLabel.equalsIgnoreCase("tokens")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + ChatColor.BOLD + "=======" + this.Prefix + ChatColor.YELLOW + ChatColor.STRIKETHROUGH + ChatColor.BOLD + "======");
                p.sendMessage(this.Prefix + ChatColor.RED + " You have " + ChatColor.GREEN + this.api.getBalance(p) + ChatColor.RED + " Tokens!" + ChatColor.GOLD +
                        "\n Type /tokens shop to Spend Them!");
                p.sendMessage(ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + ChatColor.BOLD + "=======================");

            }
            if (args.length == 1) {
                this.menu.show(p);
            }
        }
        if (commandLabel.equalsIgnoreCase("tokensadd")) {
            if (permission.playerHas(p, "tokenshop.admin")) {
                p.sendMessage(this.Prefix + ChatColor.GOLD + " You have been given 5 tokens!");
                this.api.setBalance(p, this.api.getBalance(p) + 5);
            } else {
                p.sendMessage(this.Prefix + ChatColor.RED + " You don't have permission to use this command!");
            }
        }
        return false;
    }
}