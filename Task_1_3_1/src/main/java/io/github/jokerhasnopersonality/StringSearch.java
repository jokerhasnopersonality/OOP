package io.github.jokerhasnopersonality;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * String Search class for searching substrings in text files.
 * Search methods are case-sensitive.
 */
public class StringSearch {
    /**
     * Searches the given substring in the given file.
     * For each line builds z function and adds substring occurrences to an array of indexes.
     *
     * @param substring substring to look for
     * @param in input file
     * @return list of all the occurrences of a given substring
     */
    public static List<Integer> search(String substring, InputStream in)
            throws NullPointerException, IOException {
        if (substring == null || in == null) {
            throw new NullPointerException();
        }
        int len = substring.length();

        if (len == 0) {
            return null;
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8));
        int[] zline = zfunction(substring.toCharArray(), substring.toCharArray(), new int [len], len);
        int pos = max(len, 512);
        char[] curr = new char[pos];
        int off = 0;
        int f = reader.read(curr, off, pos);
        int[] zz = zfunction(curr, substring.toCharArray(), zline, f);

        List<Integer> indexes = new ArrayList<>();
        int cnt = 0;
        for (int i = 0; i < zz.length; i++) {
            if (zz[i] == len) {
                indexes.add(cnt, i);
                cnt++;
            }
        }

        off += pos;
        char[] prev = new char[len];
        System.arraycopy(curr, curr.length - len, prev, 0, len);
        f = reader.read(curr, 0, pos);
        char[] str;
        while (f > 0) {
            str = (String.valueOf(prev) + String.valueOf(curr)).toCharArray();
            zz = zfunction(str, substring.toCharArray(), zline, f);
            System.arraycopy(curr, curr.length - len, prev, 0, len);

            for (int i = 0; i < zz.length; i++) {
                if (zz[i] == len && !indexes.contains(i + off - len)) {
                    indexes.add(cnt, i + off - len);
                    cnt++;
                }
            }

            off += pos;
            f = reader.read(curr, 0, pos);
        }
        return indexes;
    }

    /**
     * Builds the z function of a line using z function of the given substring.
     *
     * @param line line to build z function of
     * @param substr substring to look for
     * @param zline z function of a substring
     * @param n number of characters read
     * @return array of integers representing z function of a line
     */
    private static int[] zfunction(char[] line, char[] substr, int[] zline, int n) {
        int[] zz = new int[line.length];
        int l = -1;
        int r = -1;
        for (int i = 0; i < n; i++) {
            if (i < r) {
                zz[i] = zline[i - l];
            } else {
                zz[i] = 0;
            }
            // zz[i] < zline.length
            while (zz[i] < substr.length
                    && i + zz[i] < line.length && substr[zz[i]] == line[i + zz[i]]) {
                zz[i]++;
            }
            if (i + zz[i] > r) {
                l = i;
                r = i + zz[i];
            }
        }
        return zz;
    }
}