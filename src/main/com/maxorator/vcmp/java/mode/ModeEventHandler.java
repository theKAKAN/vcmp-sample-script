package com.maxorator.vcmp.java.mode;

import com.maxorator.vcmp.java.plugin.integration.RootEventHandler;
import com.maxorator.vcmp.java.plugin.integration.player.Player;
import com.maxorator.vcmp.java.plugin.integration.server.Server;
import com.maxorator.vcmp.java.plugin.integration.vehicle.Vehicle;
import com.maxorator.vcmp.java.tools.commands.CommandRegistry;
import com.maxorator.vcmp.java.tools.events.DelegatingEventHandler;
import com.maxorator.vcmp.java.tools.timers.TimerRegistry;

public class ModeEventHandler extends RootEventHandler {
    public static final int COLOUR_YELLOWISH = 0xFFFF5500;

    protected DelegatingEventHandler eventHandler;

    public ModeEventHandler(Server server) {
        super(server);

        eventHandler = new DelegatingEventHandler(server);
    }

    @Override
    public boolean onServerInitialise() {
        System.out.println("Server started!");

        return true;
    }

    @Override
    public void onServerLoadScripts() {
        System.out.println("Scripts loaded!");

        eventHandler.add(this);
        eventHandler.add(new TimerDemoCommands(server, eventHandler.timers));
        eventHandler.add(new SlowDemoCommands(server));
        eventHandler.add(new AnnotationDemoCommands(server));
        eventHandler.add(new RestrictedDemoCommands(server));
        eventHandler.add(new OtherCommands(server));
        eventHandler.takeOver();
    }

    @Override
    public void onServerUnloadScripts() {
        System.out.println("Scripts unloaded!");
    }

    @Override
    public String onIncomingConnection(String name, String password, String ip) {
        return name + "!";
    }

    @Override
    public void onPlayerConnect(Player player) {
        server.sendClientMessage(null, COLOUR_YELLOWISH, String.format("Player %s joined.", player.getName()));
    }

    @Override
    public void onPlayerDisconnect(Player player, int reason) {
        server.sendClientMessage(null, COLOUR_YELLOWISH, String.format("Player %s disconnected.", player.getName()));
    }

    @Override
    public void onPlayerEnterVehicle(Player player, Vehicle vehicle, int slot) {
        server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Player %s entered vehicle %d at slot %d.", player.getName(), vehicle.getId(), slot));
    }

    @Override
    public void onPlayerExitVehicle(Player player, Vehicle vehicle) {
        server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Player %s exited vehicle %d.", player.getName(), vehicle.getId()));
    }

    @Override
    public void onVehicleExplode(Vehicle vehicle) {
        server.sendClientMessage(null, COLOUR_YELLOWISH, String.format("Vehicle %d exploded.", vehicle.getId()));
    }
}
