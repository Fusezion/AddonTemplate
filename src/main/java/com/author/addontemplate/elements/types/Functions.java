package com.author.addontemplate.elements.types;

import ch.njol.skript.lang.function.JavaFunction;
import ch.njol.skript.lang.function.Parameter;
import ch.njol.skript.lang.function.SimpleJavaFunction;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.DefaultClasses;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Functions {

    static {

        register(new SimpleJavaFunction<OfflinePlayer>("player", new Parameter[]{
                new Parameter<>("nameOrUUID", DefaultClasses.STRING, true, null)

        }, Classes.getExactClassInfo(OfflinePlayer.class), true) {
            @Override
            @Nullable
            public OfflinePlayer[] executeSimple(Object[][] params) {
                String nameOrUUID = (String) params[0][0];
                UUID uuid = null;
                try {
                    uuid = UUID.fromString(nameOrUUID);
                } catch (Exception ignore) {}
                return CollectionUtils.array(uuid != null ? Bukkit.getOfflinePlayer(uuid) : Bukkit.getOfflinePlayer(nameOrUUID));
            }
        }
                .description("Retrieve an offline player object without needing to use ExprParse")
                .examples(
                        "on load:",
                        "\tset {_playerFromName} to player(\"Fusezion\")",
                        "\tset {_playerFromUUID} to player(uuid of {_playerFromName})"
                ).since("INSERT VERSION"));
    }

    // Shortcut method as skript used Functions too
    private static <T> void register(JavaFunction<T> function) {
        ch.njol.skript.lang.function.Functions.registerFunction(function);
    }

}
