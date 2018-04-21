package me.Dustin.com;

import org.bukkit.entity.Player;

public class VoteAPI
{
    private static VoteMain main;

    public VoteAPI(VoteMain main)
    {
        main = VoteAPI.main;
    }

    public void Withdraw(Player player, int balance) {
        main.getConfig().set("players.tokens." + player.getUniqueId().toString(), Integer.valueOf(balance));
    }

    public int getBalance(Player player) {
        return main.getConfig().getInt("players.tokens." + player.getUniqueId().toString(), 0);
    }

    public boolean doTransaction(Player player, int price) {
        int balance = getBalance(player);
        if (balance >= price) {
            setBalance(player, balance - price);
            main.saveConfig();
            return true;
        }
        return false;
    }

	private void setBalance(Player player, int i) {
		// TODO Auto-generated method stub
		
	}
}