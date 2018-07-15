package com.maxorator.vcmp.java.mode;

import com.maxorator.vcmp.java.plugin.integration.generic.Colour;
import com.maxorator.vcmp.java.plugin.integration.player.Player;
import com.maxorator.vcmp.java.plugin.integration.server.Server;
import com.maxorator.vcmp.java.tools.commands.CommandController;
import com.maxorator.vcmp.java.tools.timers.TimerHandle;
import com.maxorator.vcmp.java.tools.commands.Command;
import com.maxorator.vcmp.java.tools.timers.TimerRegistry;

public class TimerDemoCommands implements CommandController {
    protected final static Colour RESPONSE_COLOUR = new Colour(255, 200, 200);

    protected final Server server;
    protected final TimerRegistry timerRegistry;

    public TimerDemoCommands(Server server, TimerRegistry timerRegistry) {
        this.server = server;
        this.timerRegistry = timerRegistry;
    }

    @Override
    public boolean checkAccess(Player player) {
        return true;
    }

    @Command
    public void pingMe(final Player player) {
        TimerHandle handle = player.getData("pinger", TimerHandle.class);

        if (handle != null) {
            server.sendClientMessage(player, RESPONSE_COLOUR, "You are already being pinged.");
        } else {
            handle = timerRegistry.register(true, 5000, new Runnable() {
                @Override
                public void run() {
                    server.sendClientMessage(player, RESPONSE_COLOUR, "PING!");
                }
            });

            player.setData("pinger", handle);

            server.sendClientMessage(player, RESPONSE_COLOUR, "Pinging you every 5 seconds.");
        }
    }

    @Command
    public void stopPing(Player player) {
        TimerHandle handle = player.getData("pinger", TimerHandle.class);

        if (handle == null) {
            server.sendClientMessage(player, RESPONSE_COLOUR, "You are not being pinged.");
        } else {
            handle.cancel();

            server.sendClientMessage(player, RESPONSE_COLOUR, "Stopping your pings.");
        }
    }
}
