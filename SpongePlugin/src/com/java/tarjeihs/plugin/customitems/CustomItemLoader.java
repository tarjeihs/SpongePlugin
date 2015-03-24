package com.java.tarjeihs.plugin.customitems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.customitems.sub.BackpackItem;
import com.java.tarjeihs.plugin.customitems.sub.SuperAxeItem;

public class CustomItemLoader {

	private JPlugin plugin;
	
	private static final HashMap<String,Integer> data = new HashMap<String, Integer>();;
	
	public CustomItemLoader(JPlugin instance) 
	{
		this.plugin = instance;
		
	}
	
	public void addSuperAxeRecipe() {
		List<String> lore = new ArrayList<String>();
		
		lore.add("Super Axe");
		lore.add("Kutter trær på ett slag");
		lore.add("Ikke anbefalt for mindreåringer");
				
		ItemStack item = new ItemStack(Material.IRON_AXE); // Look of the item
		
		String name  = "Super Axe#"; // Name of the item
		
		addData(name); // Adding the MetaData to HashMap
		
		CustomItem superAxe = new SuperAxeItem(plugin, item, name + getID(name), lore, null); // Creating a new instance of our new Item
		
		superAxe.createCustomItemMeta(); // Loading all defaults for ItemMetaData
	}
	
	public void addBackpackRecipe() {
		List<String> lore = new ArrayList<String>();
		List<Enchantment> enchantment = new ArrayList<Enchantment>();
		
		lore.add("-----------");
		lore.add("Ryggsekk som kan bære over 32 itemslots");
		lore.add("NB: Reduserer projektilskade med 20%");
				
		enchantment.add(Enchantment.PROTECTION_PROJECTILE);
		
		ItemStack item = new ItemStack(Material.ENDER_CHEST); // Look of the item
		
		String name  = "Backpack#"; // Name of the item
		
		addData(name); // Adding the MetaData to HashMap
		
		CustomItem backpack = new BackpackItem(plugin, item, name + getID(name), lore, null); // Creating a new instance of our new Item
		
		backpack.createCustomItemMeta(); // Loading all defaults for ItemMetaData	
	}
	
	public static Integer getID(String name) {
		return (!(data.isEmpty()) ? data.get(name) : null);
	}

	private static void addData(String name) {
		int randomId = new Random().nextInt(5000-1000)+1000;
		
		data.put(name, randomId);
	}
	
	public static HashMap<String, Integer> getIDs() {
		if (data == null) {
			return null;
		}
		
		return data;
	}
}