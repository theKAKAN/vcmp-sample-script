package com.maxorator.vcmp.java.mode;

import com.maxorator.vcmp.java.plugin.integration.generic.Vector;
import com.maxorator.vcmp.java.plugin.integration.player.Player;
import com.maxorator.vcmp.java.plugin.integration.server.Server;
import com.maxorator.vcmp.java.plugin.integration.vehicle.Vehicle;
import com.maxorator.vcmp.java.plugin.integration.vehicle.VehicleColours;
import com.maxorator.vcmp.java.plugin.integration.vehicle.VehicleDamage;
import com.maxorator.vcmp.java.tools.commands.CommandController;
import com.maxorator.vcmp.java.tools.commands.Command;

public class OtherCommands implements CommandController {
    public static final int COLOUR_YELLOWISH = 0xFFFF5500;

    protected final Server server;

    public OtherCommands(Server server) {
        this.server = server;
    }

    @Override
    public boolean checkAccess(Player player) {
        return true;
    }

    @Command
    public void createVehicle(Player player, int modelId) {
        Vector position = player.getPosition();
        position.x += 5.0f;

        Vehicle vehicle = server.createVehicle(modelId, player.getWorld(), position, 0.0f, new VehicleColours(1, 1));

        if (vehicle != null) {
            server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Vehicle %d created! YAY!", vehicle.getId()));
        } else {
            server.sendClientMessage(player, COLOUR_YELLOWISH, "Could not create vehicle.");
        }
    }

    @Command
    public void getWorld(Player player) {
        server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Your world is %d.", player.getWorld()));
    }

    @Command
    public void setWorld(Player player, int world) {
        player.setWorld(world);

        server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Set your world to %d.", world));
    }

    @Command
    public void getPlayerVehicle(Player player, Player target) {
        Vehicle vehicle = target.getVehicle();

        if (vehicle != null) {
            server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Player %s is in vehicle %d.", target.getName(), vehicle.getId()));
        } else {
            server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Player %s is not in a vehicle.", target.getName()));
        }
    }

    @Command
    public void getVehicleHealth(Player player, Vehicle vehicle) {
        server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Vehicle %d health: %f.", vehicle.getId(), vehicle.getHealth()));
    }

    @Command(usage = "<player> <vehicle> <slot> <makeroom> <warp>")
    public void putPlayerInVehicle(Player player, Player target, Vehicle vehicle, int slot, boolean makeRoom, boolean warp) {
        target.putInVehicle(vehicle, slot, makeRoom, warp);

        server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Sending player %s into vehicle %d.", target.getName(), vehicle.getId()));
    }

    @Command
    public void getVehicleOccupant(Player player, Vehicle vehicle, int slot) {
        Player occupant = vehicle.getOccupant(slot);

        if (occupant == null) {
            server.sendClientMessage(player, COLOUR_YELLOWISH, "That vehicle slot is not occupied.");
        } else {
            server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("That slot is occupied by %s (%d).", occupant.getName(), occupant.getId()));
        }
    }

    @Command
    public void getVehiclePosition(Player player, Vehicle vehicle) {
        Vector position = vehicle.getPosition();

        server.sendClientMessage(player, COLOUR_YELLOWISH, String.format("Vehicle %d is at (%f, %f, %f).", vehicle.getId(), position.x, position.y, position.z));
    }

    @Command
    public void breakCar(Player player, Vehicle vehicle) {
        VehicleDamage damage = vehicle.getDamage();
        damage.setDoorStatus(VehicleDamage.Door.Bonnet, VehicleDamage.DoorStatus.Flapping);
        damage.setTyreStatus(VehicleDamage.Tyre.LeftRear, VehicleDamage.TyreStatus.Flat);
        damage.setPanelStatus(VehicleDamage.Panel.Windscreen, VehicleDamage.PanelStatus.Damaged);
        vehicle.setDamage(damage);

        server.sendClientMessage(player, COLOUR_YELLOWISH, "Broke that car.");
    }

    @Command
    public void gibAdmin(Player player) {
        player.setAdmin(true);

        server.sendClientMessage(player, 0xFFFFFFFF, "Gabe admin");
    }
}
