package com.java.tarjeihs.plugin.customitems;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.customitems.custom.CoinItem;

public class CustomItemLoader {

	private JPlugin plugin;
	
	private static final HashMap<String, Integer> data = new HashMap<String, Integer>();
	
	public CustomItemLoader(JPlugin instance) 
	{
		this.plugin = instance;
		
	}
	
	public void addCoinItem() {
		ItemStack item = new ItemStack(Material.DOUBLE_PLANT);
		
		String name = "Kroner";
		
		CustomItem coin = new CoinItem(plugin, item, name)
		.setLore("Kan legges inn ved en bank")
		.setLore("som finnes rundt på kartet.")
		.setLore("Finnes ingen virtuell bank.")
		.setLore("Statens Penger")
		.addEnchantment(Enchantment.LUCK);
		
		coin.createCustomItemMeta();
	}
	
	public static Integer getID(String name) {
		return (!(data.isEmpty()) ? data.get(name) : null);
	}

	public static void addData(String name) {
		int randomId = new Random().nextInt(5000-1000)+1000;
		
		data.put(name, randomId);
	}
	
	public static HashMap<String, Integer> getData() {
		if (data == null) {
			return null;
		}
		
		return data;
	}
}