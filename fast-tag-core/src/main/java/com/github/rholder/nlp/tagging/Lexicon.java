/*
 * Modifications: Copyright 2015 Ray Holder
 * Original source: Copyright 2003-2008 Mark Watson (markw@markwatson.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rholder.nlp.tagging;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lexicon {

    public static Map<String, String[]> fromClasspath(String classpath) {
        return fromInputStream(Lexicon.class.getResourceAsStream(classpath));
    }

    public static Map<String, String[]> fromInputStream(InputStream inputStream) {
        Map<String, String[]> lexicon = new HashMap<String, String[]>();
        try {
            if (inputStream == null) {
                throw new RuntimeException("Could not find lexicon file on classpath");
            }
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter(System.getProperty("line.separator"));
            while (scanner.hasNext()) {
                String line = scanner.next();
                int count = 0;
                for (int i = 0, size = line.length(); i < size; i++) {
                    if (line.charAt(i) == ' ') {
                        count++;
                    }
                }
                if (count == 0) {
                    continue;
                }
                String[] ss = new String[count];
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(" ");
                String word = lineScanner.next();
                count = 0;
                while (lineScanner.hasNext()) {
                    ss[count++] = lineScanner.next();
                }
                lineScanner.close();
                lexicon.put(word, ss);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableMap(lexicon);
    }
}
