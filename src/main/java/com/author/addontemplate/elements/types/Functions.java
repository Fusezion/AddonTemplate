package com.author.addontemplate.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.data.BukkitClasses;
import ch.njol.skript.classes.data.DefaultFunctions;
import ch.njol.skript.lang.function.FunctionEvent;
import ch.njol.skript.lang.function.JavaFunction;
import ch.njol.skript.lang.function.Parameter;
import ch.njol.skript.lang.function.SimpleJavaFunction;
import ch.njol.skript.lang.util.SimpleLiteral;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.DefaultClasses;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.regex.Pattern;

public class Functions {

    static {

        Parameter<?>[] stringParam = new Parameter[]{new Parameter<>("string", DefaultClasses.STRING, true, null)};

        register(new SimpleJavaFunction<>("player", new Parameter[]{
                new Parameter("nameOrUUID", DefaultClasses.STRING, true, null)

        }, Classes.getExactClassInfo(OfflinePlayer.class),true) {
            @Override
            public @Nullable OfflinePlayer[] executeSimple(Object[][] params) {
                String nameOrUUID = (String) params[0][0];
                UUID uuid = null;
                try {
                    uuid = UUID.fromString(nameOrUUID);
                } catch (Exception ignore) {}
                return CollectionUtils.array(uuid != null ? Bukkit.getOfflinePlayer(uuid) : Bukkit.getOfflinePlayer(nameOrUUID));
            }
        }
                .description("")
                .examples("")
                .since(""));
    }

    // Shortcut method as skript used Functions too
    private static void register(JavaFunction<?> function) {
        ch.njol.skript.lang.function.Functions.registerFunction(function);
    }

}
