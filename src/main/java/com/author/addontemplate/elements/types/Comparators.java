package com.author.addontemplate.elements.types;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Comparator;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Comparators {

    static {

        // Simple Is/Is Not Comparator
        register(Entity.class, UUID.class, new Comparator<Entity, UUID>() {
            @Override
            @NotNull
            public Relation compare(Entity entity, UUID uuid) {
                return Relation.get(entity.getUniqueId().equals(uuid));
            }

            @Override
            public boolean supportsOrdering() {
                return false;
            }
        });

        // Snippet taken from Skript's own source code this is a pain to code.
        register(Number.class, Number.class, new Comparator<Number, Number>() {
            @Override
            @NotNull
            public Relation compare(Number number1, Number number2) {
                if (number1 instanceof Long && number2 instanceof Long)
                    return Relation.get(number1.longValue() - number2.longValue());
                Double double1 = number1.doubleValue(),
                       double2 = number2.doubleValue();
                if (double1.isNaN() || double2.isNaN()) return Relation.SMALLER;
                else if (double1.isInfinite() || double2.isInfinite())
                    return double1 > double2 ? Relation.GREATER : double1 < double2 ? Relation.SMALLER : Relation.EQUAL;

                double diff = double1 - double2;
                if (Math.abs(diff) < Skript.EPSILON)
                    return Relation.EQUAL;
                return Relation.get(diff);
            }

            @Override
            public boolean supportsOrdering() {
                return true;
            }
        });



    }

    private static <T1,T2> void register(Class<T1> type1, Class<T2>type2, Comparator<T1,T2> comparator) {
        // Don't register an extra comparator if there exists one already registered
        if (ch.njol.skript.registrations.Comparators.getComparator(type1, type2) != null) return;
        ch.njol.skript.registrations.Comparators.registerComparator(type1,type2,comparator);
    }
}
