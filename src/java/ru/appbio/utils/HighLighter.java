package ru.appbio.utils;

import org.codehaus.groovy.grails.plugins.codecs.HTMLCodec;

import java.util.Collections;
import java.util.List;

/**
 * Used for highlighting search results.
 */
public class HighLighter {

    /**
     * Highlight and HTML-encode search result.
     * Works case-insensitive by default.
     *
     * Will optionally truncate the text if limit is provided.
     *
     * @param src string to be highlighted
     * @param terms search terms to highlight; when empty, only truncation is performed.
     * @param prefix prefix tag
     * @param suffix suffix tag
     * @param truncationSuffix see more string to be appended when string is truncated.
     * @param limit original string limit, -1 to ignore
     * @return "highlighted" version
     */
    public static CharSequence highlight(String src, List<String> terms, String prefix, String suffix, String truncationSuffix, int limit) {
        if (terms == null || terms.isEmpty()) {
            if (limit > 0 && src.length() > limit) {
                return HTMLCodec.encode(src.substring(0, limit) + truncationSuffix);
            } else {
                return HTMLCodec.encode(src).toString();
            }
        }
        if (limit == -1 || limit > src.length()) {
            limit = src.length();
        }
        StringBuilder result = new StringBuilder();
        String scanString = src.toLowerCase();
        int chars = 0;
        int pos = 0;
        while (true) {
            boolean found = false;
            // todo choose biggest match first.
            for(String term : terms) {
                String scanTerm = term.trim().toLowerCase();
                if (scanTerm.length() == 0) {
                    continue;           // in case of stupid params, ignore.
                }
                pos = scanString.indexOf(scanTerm, chars);
                if (pos >= 0) {
                    found = true;
                    if (pos > chars) {
                        result.append(HTMLCodec.encode(src.substring(chars, pos)));
                    }
                    result.append(prefix);
                    int endPoint = pos + scanTerm.length();
                    if (endPoint > limit) {
                        endPoint = limit;
                    }
                    result.append(HTMLCodec.encode(src.substring(pos, endPoint)));
                    result.append(suffix);
                    chars = endPoint;
                    break;
                }
            }
            if (!found) {
                break;
            }
        }
        if (chars < limit) {
            result.append(HTMLCodec.encode(src.substring(chars, limit)));
        }
        if (src.length() > limit) {
            result.append(truncationSuffix);
        }
        return result.toString();
    }

    /**
     * Highlight and HTML-encode search result.
     * Works case-insensitive by default.
     * @param src string to be highlighted
     * @param terms search terms
     * @param prefix prefix tag
     * @param suffix suffix tag
     * @return "highlighted" version
     */
    public static CharSequence highlight(String src, List<String> terms, String prefix, String suffix) {
        return highlight(src, terms, prefix, suffix, null, -1);
    }

    /**
     * Highlight and HTML-encode search result.
     * Works case-insensitive by default.
     * @param src string to be highlighted
     * @param term search term
     * @param prefix prefix tag
     * @param suffix suffix tag
     * @return "highlighted" version
     */
    public static CharSequence highlight(String src, String term, String prefix, String suffix) {
        return highlight(src, term == null || term.trim().length() == 0 ? null : Collections.singletonList(term), prefix, suffix, null, -1);
    }
}
