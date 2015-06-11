package com.java.tarjeihs.plugin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class SerializableItemStack implements Serializable {

	private static final long serialVersionUID = 44826028401213288L;

	private final int amount, typeId;
	private final byte data;
	private final short durability;
	private final String name;
	private final Map<Enchantment, Integer> enchantmentList;
	private final List<String> lore;
	
	@SuppressWarnings("deprecation")
	public SerializableItemStack(final ItemStack itemStack) {
		this.amount = itemStack.getAmount();
		this.typeId = itemStack.getTypeId();
		this.data = itemStack.getData().getData();
		this.durability = itemStack.getDurability();
		this.name = itemStack.getItemMeta().getDisplayName();
		this.lore = itemStack.getItemMeta().getLore();
		this.enchantmentList = itemStack.getEnchantments();
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack toItemStack() {
		ItemStack itemStack = new ItemStack(typeId, amount);
		
		itemStack.setData(new MaterialData(typeId, data));
		itemStack.setDurability(durability);
		itemStack.addEnchantments(enchantmentList);
	
		ItemMeta meta = itemStack.getItemMeta();
		
		meta.setLore(lore);
		meta.setDisplayName(name);
		
		itemStack.setItemMeta(meta);
		
		return itemStack;
	}
}
