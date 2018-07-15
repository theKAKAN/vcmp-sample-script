package com.maxorator.vcmp.java.mode;

import com.maxorator.vcmp.java.tools.commands.CommandController;
import com.maxorator.vcmp.java.plugin.integration.generic.Colour;
import com.maxorator.vcmp.java.plugin.integration.player.Player;
import com.maxorator.vcmp.java.plugin.integration.server.Server;
import com.maxorator.vcmp.java.tools.commands.Command;
import com.maxorator.vcmp.java.tools.commands.PartialMatch;
import com.maxorator.vcmp.java.tools.commands.NullIfNotFound;

public class AnnotationDemoCommands implements CommandController {
    protected final static Colour RESPONSE_COLOUR = new Colour(200, 255, 150);

    protected final Server server;

    public AnnotationDemoCommands(Server server) {
        this.server = server;
    }

    @Override
    public boolean checkAccess(Player player) {
        return true;
    }

    @Command(name = "renamed")
    public void methodName(Player player) {
        // Command name is usually the same as method name (in lowercase), but this can be changed like done above
        server.sendClientMessage(player, RESPONSE_COLOUR, "This command has a custom name that differs from method name.");
    }

    @Command(usage = "<random number>")
    public void lottery(Player player, int number) {
        // A basic usage text is generated automatically, but a custom one is probably better
        server.sendClientMessage(player, RESPONSE_COLOUR, String.format("Good job on finally entering the correct parameters. Oh and %d is not a winning number", number));
    }

    @Command
    public void findDefault(Player player, Player target) {
        // By default, finding a player requires the format #<id> or <exact name>
        server.sendClientMessage(player, RESPONSE_COLOUR, String.format("Player '%s' found.", target.getName()));
    }

    @Command
    public void findNoError(Player player, @NullIfNotFound Player target) {
        // By default, an error is automatically shown if the entity is not found, with this annotation you will instead get null
        if (target == null) {
            server.sendClientMessage(player, RESPONSE_COLOUR, "No such guy lives here.");
        } else {
            server.sendClientMessage(player, RESPONSE_COLOUR, String.format("Yep, found %s.", target.getName()));
        }
    }

    @Command
    public void findPartial(Player player, @PartialMatch Player target) {
        // This annotation does not require an exact match, but gives an error when there are several matches
        server.sendClientMessage(player, RESPONSE_COLOUR, String.format("The dude called %s seems close enough.", target.getName()));
    }
}
