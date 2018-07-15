package com.maxorator.vcmp.java.mode;

import com.maxorator.vcmp.java.tools.commands.CommandController;
import com.maxorator.vcmp.java.plugin.integration.generic.Colour;
import com.maxorator.vcmp.java.plugin.integration.player.Player;
import com.maxorator.vcmp.java.plugin.integration.server.Server;
import com.maxorator.vcmp.java.plugin.integration.server.SyncBlock;
import com.maxorator.vcmp.java.tools.commands.Command;

import java.io.IOException;

public class SlowDemoCommands implements CommandController {
    protected final static Colour RESPONSE_COLOUR = new Colour(200, 255, 200);

    protected final Server server;

    public SlowDemoCommands(Server server) {
        this.server = server;
    }

    @Override
    public boolean checkAccess(Player player) {
        return true;
    }

    private void doSomethingSlow() {
        // Do not actually use this as timers, use it when running something like an SQL query that might not be very fast
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    @Command
    public void slow(final Player player) {
        server.sendClientMessage(player, RESPONSE_COLOUR, "Running a slow thing.");

        // In a different thread, you cannot normally call server methods (including player/vehicle/etc)
        new Thread(new Runnable() {
            @Override
            public void run() {
                doSomethingSlow();

                // In this block, you can call server functions (server main thread pauses while this is running)
                try (SyncBlock ignored = server.sync()) {
                    if (player.isValid()) {
                        server.sendClientMessage(player, RESPONSE_COLOUR, "The slow thing has finished.");
                    } else {
                        server.sendClientMessage(null, RESPONSE_COLOUR, "The guy whose query I was running has left.");
                    }
                } catch (Exception ignored) {
                    System.err.println("Slow job finished when scripts were unloaed.");
                }
            }
        }).start();
    }
}
