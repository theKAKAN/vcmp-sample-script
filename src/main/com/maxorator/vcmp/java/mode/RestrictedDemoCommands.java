package com.maxorator.vcmp.java.mode;

import com.maxorator.vcmp.java.tools.commands.CommandController;
import com.maxorator.vcmp.java.plugin.integration.player.Player;
import com.maxorator.vcmp.java.plugin.integration.server.Server;
import com.maxorator.vcmp.java.tools.commands.Command;

public class RestrictedDemoCommands implements CommandController {
    public static final int COLOUR_YELLOWISH = 0xFFFF5500;

    protected final Server server;

    public RestrictedDemoCommands(Server server) {
        this.server = server;
    }

    @Override
    public boolean checkAccess(Player player) {
        if (!player.isAdmin()) {
            server.sendClientMessage(player, COLOUR_YELLOWISH, "You need to be an admin for this (/gibadmin).");
            return false;
        }

        return true;
    }

    @Command
    public void getServerName(Player player) {
        server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Server name: %s", server.getServerName()));
    }

    @Command
    public void setServerName(Player player, String serverName) {
        server.setServerName(serverName);
        server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Server name changed to: %s", serverName));
    }

    @Command
    public void reload(Player player) {
        server.reloadScript();
    }
}
