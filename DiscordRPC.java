package net.sqizy;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordEventHandlers.Builder;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {
	
	private boolean running = true;
	private long created = 0;
	
	//Enter your project id here
	private String CLIEND_ID = "";
	
	public void start() {
		
		this.created = System.currentTimeMillis();
		
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
			
			@Override
			public void apply(DiscordUser user) {
				System.out.println("Welcome " + user.username + "#" + user.discriminator + ".");
				update("Loading DiscordRPC...", " Client by Sqizyyyy");
				//Startig Message
			}
			
		}).build();
		
		DiscordRPC.discordInitialize("CLIEND_ID", handlers, true);
		
		new Thread("Discord RPC Callback") {
			public void run() {
				while(running) {
					DiscordRPC.discordRunCallbacks();
				}
			}
		}.start();
		
		
	}
	//On Shutdown
	public void shutdown() {
		running = false;
		DiscordRPC.discordShutdown();
		
		
	}
	//On Update
	public void update(String firstLine, String secondLine) {
		DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
		b.setBigImage("large", "");
		b.setDetails(firstLine);
		b.setStartTimestamps(created);
		
		DiscordRPC.discordUpdatePresence(b.build());
		
	}
	
}
