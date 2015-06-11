package com.java.tarjeihs.plugin.customitems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.java.tarjeihs.plugin.JPlugin;

public abstract class CustomItem {
	
	private JPlugin plugin;
	
	private ItemStack itemStack;
	
	private String name;
	
	private List<String> lore;
	
	private List<Enchantment> enchantment;
	
	public CustomItem(JPlugin plugin, ItemStack item, String name) {
		this.plugin = plugin;
		this.itemStack = item;
		this.name = name;
		this.lore = new ArrayList<String>();
		this.enchantment = new ArrayList<Enchantment>();
	}
	
	public ItemStack getItem() {
		return this.itemStack;
	}
	
	protected void setDisplayName(String name) {	
		this.name = name;
	}
	
	public String getDisplayName() {
		return this.name;
	}
	
	protected CustomItem setLore(String lore) {
		this.lore.add(lore);
		
		return this;
	}
	
	public List<String> getLore() {
		return this.lore;
	}
	
	protected CustomItem addEnchantment(Enchantment e) {
		this.enchantment.add(e);
		
		return this;
	}
	
	public List<Enchantment> getEnchantments() {
		return this.enchantment;
	}
	
	
	public final void createCustomItemMeta() {
		ItemMeta meta = itemStack.getItemMeta();
		
		meta.setDisplayName(name);
		
		meta.setLore(lore);
		
		if (enchantment != null) {
			for (Enchantment enchantment : enchantment) {
				meta.addEnchant(enchantment, 2, true);
			}
		}
		
		itemStack.setItemMeta(meta);
			
		Bukkit.getServer().addRecipe(newShapedRecipe() == null ? 
				newShapelessRecipe() : newShapedRecipe());
				
	}
	
	public JPlugin getPlugin() {
		return plugin;
	}
	
	public abstract ShapedRecipe newShapedRecipe();
	
	public abstract ShapelessRecipe newShapelessRecipe();
}