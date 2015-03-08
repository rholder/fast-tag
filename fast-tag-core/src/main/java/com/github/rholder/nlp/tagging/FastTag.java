/*
 * Copyright 2003-2008 Mark Watson (markw@markwatson.com)
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FastTag {

	private final Map<String, String[]> lexicon;

    public FastTag() {
        this.lexicon = buildLexicon("fast-tag-lexicon.txt");
    }

	/**
	 * 
	 * @param words
	 *            list of strings to tag with parts of speech
	 * @return list of strings for part of speech tokens
	 */
	public List<String> tag(List<String> words) {
		List<String> ret = new ArrayList<String>(words.size());
		for (int i = 0, size = words.size(); i < size; i++) {
			String[] ss = lexicon.get(words.get(i));
			// 1/22/2002 mod (from Lisp code): if not in hash, try lower case:
			if (ss == null)
				ss = lexicon.get(words.get(i).toLowerCase());
			if (ss == null && words.get(i).length() == 1)
				ret.add(words.get(i) + "^");
			else if (ss == null)
				ret.add("NN");
			else
				ret.add(ss[0]);
		}
		/**
		 * Apply transformational rules
		 **/
		for (int i = 0; i < words.size(); i++) {
			String word = ret.get(i);
			// rule 1: DT, {VBD | VBP} --> DT, NN
			if (i > 0 && ret.get(i - 1).equals("DT")) {
				if (word.equals("VBD") || word.equals("VBP") || word.equals("VB")) {
					ret.set(i, "NN");
				}
			}
			// rule 2: convert a noun to a number (CD) if "." appears in the word
			if (word.startsWith("N")) {
				if (words.get(i).indexOf(".") > -1) {
					ret.set(i, "CD");
				}
				try {
					Float.parseFloat(words.get(i));
					ret.set(i, "CD");
				} catch (Exception e) { // ignore: exception OK: this just means
										// that the string could not parse as a
										// number
				}
			}
			// rule 3: convert a noun to a past participle if words.get(i) ends with "ed"
			if (ret.get(i).startsWith("N") && words.get(i).endsWith("ed"))
				ret.set(i, "VBN");
			// rule 4: convert any type to adverb if it ends in "ly";
			if (words.get(i).endsWith("ly"))
				ret.set(i, "RB");
			// rule 5: convert a common noun (NN or NNS) to a adjective if it ends with "al"
			if (ret.get(i).startsWith("NN") && words.get(i).endsWith("al"))
				ret.set(i, "JJ");
			// rule 6: convert a noun to a verb if the preceeding work is "would"
			if (i > 0 && ret.get(i).startsWith("NN")
					&& words.get(i - 1).equalsIgnoreCase("would"))
				ret.set(i, "VB");
			// rule 7: if a word has been categorized as a common noun and it ends with "s",
			// then set its type to plural common noun (NNS)
			if (ret.get(i).equals("NN") && words.get(i).endsWith("s"))
				ret.set(i, "NNS");
			// rule 8: convert a common noun to a present participle verb (i.e., a gerand)
			if (ret.get(i).startsWith("NN") && words.get(i).endsWith("ing"))
				ret.set(i, "VBG");
		}
		return ret;
	}

	private Map<String, String[]> buildLexicon(String classpath) {
		Map<String, String[]> lexicon = new HashMap<String, String[]>();
		try {
			InputStream ins = FastTag.class.getClassLoader().getResourceAsStream(classpath);
			if (ins == null) {
				throw new RuntimeException("Could not find lexicon file on classpath");
			}
			Scanner scanner = new Scanner(ins);
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
