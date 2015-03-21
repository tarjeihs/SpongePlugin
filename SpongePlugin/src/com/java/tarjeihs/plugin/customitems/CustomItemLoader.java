package com.java.tarjeihs.plugin.customitems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.java.tarjeihs.plugin.JPlugin;

public class CustomItemLoader {

	private static JPlugin plugin;
	
	private static HashMap<String,Integer> data = new HashMap<String, Integer>();;
	
	public CustomItemLoader(JPlugin instance) 
	{
		plugin = instance;
		
	}
	
	@SuppressWarnings("deprecation")
	public static void addSuperAxeRecipe() {
		List<String> lore = new ArrayList<String>();
		List<Enchantment> enchantment = new ArrayList<Enchantment>();
		
		lore.add("Super Pick Axe");
		lore.add("One shot trees");
		lore.add("The pussy magnet");
		lore.add("Micheal Jackson's exwife");
		
		enchantment.add(Enchantment.WATER_WORKER);
		
		ItemStack item = new ItemStack(Material.IRON_AXE);
		
		CustomItem customItem = new CustomItem(plugin, item, "Super Pick Axe", lore, enchantment) {
			
			@Override
			public ShapedRecipe newShapedRecipe() {
				ShapedRecipe recipe = new ShapedRecipe(getItem());					
				return recipe.shape(new String[]{"DD ", "DR ", " R "}).setIngredient('D', Material.DIAMOND_BLOCK).setIngredient('R', Material.BLAZE_ROD);
			}
		};
		
		customItem.createCustomItemMeta();
	
		String name  = "SuperPickAxe";
		
		addData(name, item.getTypeId());
	}
	
	public static Integer getID(String name) {
		return (!(data.isEmpty()) ? data.get(name) : null);
	}

	private static void addData(String name, int typeId) {
		data.put(name, typeId);
	}
	
	public static HashMap<String, Integer> getIDs() {
		if (data == null) {
			return null;
		}
		
		return data;
	}
}