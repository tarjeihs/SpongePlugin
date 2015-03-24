package com.java.tarjeihs.plugin.customitems.sub;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.customitems.CustomItem;

public class BackpackItem extends CustomItem {

	public BackpackItem(JPlugin plugin, ItemStack item, String name,
			List<String> lore, List<Enchantment> enchantment) {
		super(plugin, item, name, lore, enchantment);
	}

	@Override
	public ShapedRecipe newShapedRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getItem());					
		return recipe.shape(new String[]{"DDD", "DCD", "DDD"})
				.setIngredient('D', Material.DIAMOND)
				.setIngredient('C', Material.CHEST);
	}
}
