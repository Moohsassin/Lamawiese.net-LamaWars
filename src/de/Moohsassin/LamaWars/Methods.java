package de.Moohsassin.LamaWars;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class Methods {

	public static void sendTitle(Player p, String title, String subtitle, int time, int fadein, int fadeout) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\": \"" + title + "\"}"), time, fadein, fadeout));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\": \"" + subtitle + "\"}"), time, fadein, fadeout));
	}
	
	public static void sendToHub(Player p) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF("LW-HUB-1");
		p.sendPluginMessage(LamaWars.instance, "BungeeCord", out.toByteArray());
	}
}
