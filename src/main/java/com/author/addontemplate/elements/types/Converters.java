package com.author.addontemplate.elements.types;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.data.BukkitClasses;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Converters {

    static {

        register(Number.class, Integer.class, number -> number.intValue());

        register(String.class, UUID.class, new Converter<String, UUID>() {
            @Override
            public @Nullable UUID convert(String string) {
                if (string.matches(BukkitClasses.UUID_PATTERN.pattern())) {
                    return UUID.fromString(string);
                }
                return null;
            }
        });
    }

    // Shortcut method due to same name as Skript's
    private static <F, T> void register(Class<F> from, Class<T> to, Converter<F, T> converter) {
        // Don't register useless converters that are already added to skript or from an addon
        if (ch.njol.skript.registrations.Converters.converterExists(from, to)) return;
        ch.njol.skript.registrations.Converters.registerConverter(from, to, converter);
    }

    // Shortcut method due to same name as Skript's
    private static <F, T> void register(Class<F> from, Class<T> to, Converter<F, T> converter, int options) {
        // Don't register useless converters that are already added to skript or from an addon
        if (ch.njol.skript.registrations.Converters.converterExists(from, to)) return;
        ch.njol.skript.registrations.Converters.registerConverter(from, to, converter, options);
    }

}
