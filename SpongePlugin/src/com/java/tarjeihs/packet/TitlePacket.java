package com.java.tarjeihs.packet;

import java.lang.reflect.Method;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class TitlePacket extends Packet {

//	public TitlePacket(Player player, String message, DisplayColor color) {
//		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\":\"\",\"extra\":[{\"text\":\"" + message + "\",\"color\":\"" + color + "\"}]}");
//		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
//
//		((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
//	}
//	
//	public void sendSubtitle(Player player, String message, DisplayColor color) {
//		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\":\"\",\"extra\":[{\"text\":\"" + message + "\",\"color\":\"" + color + "\"}]}");
//		PacketPlayOutTitle subTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatTitle);
//
//		((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitle);
//	}
//	
//	public void clearTitle(Player player) {
//		PacketPlayOutTitle title = new PacketPlayOutTitle();
//
//		((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
//	}
	
	private String title = "";
	private String subTitle = "";
	
	private Class<?> packetTitle;
	
	private Class<?> packetActions;
	
	private Class<?> nmsChatSerializer;
	private Class<?> chatBaseComponent;
	
	private int a, b, c;
	
	private boolean ticks = true;
	
	private ChatColor titleColor = ChatColor.WHITE;
	private ChatColor subTitleColor = ChatColor.WHITE;
	
	public TitlePacket(String title) {
		this.title = title;
		
		loadNMSClasses();
	}
	
	public TitlePacket(String title, String subTitle) {
		this.title = title;
		this.subTitle = subTitle;
	
		loadNMSClasses();
	}
	
	public TitlePacket(String title, int a, int b, int c) {
		this.title = title;
		this.a = a;
		this.b = b;
		this.c = c;
		
		loadNMSClasses();
	}
	
	public TitlePacket(String title, String subTitle, int a, int b, int c) {
		this.title = title;
		this.subTitle = subTitle;
		this.a = a;
		this.b = b;
		this.c = c;
		
		loadNMSClasses();
	}
	
	public void setTitleColor(ChatColor color) {
		this.titleColor = color;
	}
	
	public void setSubtitleColor(ChatColor color) {
		this.subTitleColor = color;
	}
	
	private void loadNMSClasses() {
		packetTitle = getNMSClass("PacketPlayOutTitle");
		packetActions = getNMSClass("PacketPlayOutTitle$EnumTitleAction");
		chatBaseComponent = getNMSClass("IChatBaseComponent");
		nmsChatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer");
	}
	
	public void sendPacket(Player player) {
		if (packetTitle != null) {
			resetPlayer(player);
			try {
				Object handle = getHandle(player);
				Object connection = getField(handle.getClass(), "playerConnection").get(handle);
				
				Object[] actions = packetActions.getEnumConstants();
				Method sendPacket = getMethod(connection.getClass(), "sendPacket");
								
				Object packet = packetTitle.getConstructor(packetActions, chatBaseComponent,
						Integer.TYPE, Integer.TYPE, 
						Integer.TYPE).newInstance(actions[2], null, a * (ticks ? 1:20),
								b * (ticks ? 1:20),
								c * (ticks ? 1:20));
				if (a != -1 && b != -1 && c != -1) 
					sendPacket.invoke(connection, packet);
				
				Object serialized = getMethod(nmsChatSerializer, "a", String.class).invoke(null, "{text:\""
						+ ChatColor.translateAlternateColorCodes('&',
								title) + "\",color:"
						+ titleColor.name().toLowerCase() + "}");
				
				packet = packetTitle.getConstructor(packetActions,
						chatBaseComponent).newInstance(actions[0], serialized);
			
				sendPacket.invoke(connection, packet);
				if (subTitle != null || subTitle != "") {
					serialized = getMethod(nmsChatSerializer, "a", String.class)
							.invoke(null, "{text:\""
									+ ChatColor
									.translateAlternateColorCodes(
											'&', subTitle
							+ "\",color:"
							+ subTitleColor.name()
									.toLowerCase() + "}"));
					packet = packetTitle.getConstructor(packetActions, chatBaseComponent).newInstance(actions[1], serialized);
					
					sendPacket.invoke(connection, packet);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void resetPlayer(Player player) {
		try {
			Object handle = getHandle(player);
			Object connection = getField(handle.getClass(), "playerConnection").get(handle);
			
			Object[] actions = packetActions.getEnumConstants();
			Method sendPacket = getMethod(connection.getClass(), "sendPacket");
			Object packet = packetTitle.getConstructor(packetActions,
					chatBaseComponent).newInstance(actions[4], null);
			
			sendPacket.invoke(connection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}