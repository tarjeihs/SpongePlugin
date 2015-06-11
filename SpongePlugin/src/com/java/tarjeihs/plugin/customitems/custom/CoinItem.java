package com.java.tarjeihs.plugin.customitems.custom;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.customitems.CustomItem;

public class CoinItem extends CustomItem {

	public CoinItem(JPlugin plugin, ItemStack item, String name) {
		super(plugin, item, name);
	}

	@Override
	public ShapedRecipe newShapedRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getItem());
		return recipe.shape("GG", "GG")
				.setIngredient('G', Material.GOLD_NUGGET);
	}

	@Override
	public ShapelessRecipe newShapelessRecipe() {
		return null;
	}
}
