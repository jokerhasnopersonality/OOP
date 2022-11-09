package io.github.jokerhasnopersonality;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.min;

public class StringSearch {
    int z_function(String string, BufferedReader reader) throws IOException {
        int len = string.length();

        ArrayList<Integer> ZZ = new ArrayList<>(len);

        if (len == 0) {
            return 0;
        }

        ZZ.set(0, len);

        String line = reader.readLine();
        StringBuilder zLine = new StringBuilder();
        while (!line.isEmpty()) {
            zLine.append(string);
            zLine.append(line);
            ArrayList<Integer> Z = z_function(zLine.toString());
            ZZ.addAll(Z);
        }
        int res = 0;
        for (Integer i : ZZ) {
            if(i.equals(len)) {
                res++;
            }
        }
        return res;
    }

    ArrayList<Integer> z_function(String line) {
        int n = line.length();
        ArrayList<Integer> Z = new ArrayList<>(n);
        for (int i=1, l=0, r=0; i<n; ++i) {
            if (i <= r) {
                Z.set(i, min(r - i + 1, Z.get(i - l)));
            }
            while (i + Z.get(i) < n && line.charAt(Z.get(i)) == line.charAt(i+Z.get(i))) {
                Z.set(i, Z.get(i)+1);
            }
            if (i+Z.get(i)-1 > r) {
                l = i;
                r = i + Z.get(i) - 1;
            }
        }
        return  Z;
    }
}
