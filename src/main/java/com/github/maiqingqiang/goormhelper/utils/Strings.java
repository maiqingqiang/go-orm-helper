package com.github.maiqingqiang.goormhelper.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class Strings {

    public static @NotNull String toSnakeCase(@NotNull String s) {
        return s.replaceAll("(?<g1>.)(?<g2>[A-Z][a-z]+)", "${g1}_${g2}").
                replaceAll("(?<g1>[a-z0-9])(?<g2>[A-Z])", "${g1}_${g2}").
                toLowerCase();
    }

    public static @NotNull String clearQuote(@NotNull String s) {
        if (s.startsWith("`") && s.endsWith("`")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    public static @NotNull String clearSingleQuotn(@NotNull String s) {
        if (s.startsWith("'") && s.endsWith("'")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    public static boolean endsWithIgnoreCaseAny(CharSequence sequence, CharSequence... searchStrings) {
        if (!StringUtils.isEmpty(sequence) && !ArrayUtils.isEmpty(searchStrings)) {
            for (CharSequence searchString : searchStrings) {
                if (StringUtils.endsWithIgnoreCase(sequence, searchString)) {
                    return true;
                }
            }
        }
        return false;
    }
}
