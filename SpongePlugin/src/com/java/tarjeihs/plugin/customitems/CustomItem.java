package com.java.tarjeihs.plugin.customitems;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.java.tarjeihs.plugin.JPlugin;

public abstract class CustomItem {
	
	private JPlugin plugin;
	
	private ItemStack itemStack;
	
	private String name;
	
	private List<String> lore;
	
	private List<Enchantment> enchantment;
	
	public CustomItem(JPlugin plugin, ItemStack item, String name, List<String> lore, List<Enchantment> enchantment) {
		this.plugin = plugin;
		this.itemStack = item;
		this.name = name;
		this.lore = lore;
		this.enchantment = enchantment;
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
	
	protected void setLore(List<String> lore) {
		this.lore = lore;
	}
	
	public List<String> getLore() {
		return this.lore;
	}
	
	protected void addEnchantment(List<Enchantment> e) {
		this.enchantment = e;
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
				meta.addEnchant(enchantment, 0, true);
			}
		}
		
		itemStack.setItemMeta(meta);
		
		Bukkit.getServer().addRecipe(newShapedRecipe());
	}
	
	public JPlugin getPlugin() {
		return plugin;
	}
	
	public abstract ShapedRecipe newShapedRecipe();
}