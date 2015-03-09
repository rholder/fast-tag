/*
 * Copyright 2015 Ray Holder
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

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.rholder.nlp.tagging.Tokenizer.wordsToList;

public class FastTagTest {

    private static final FastTag FAST_TAG = new FastTag(Lexicon.fromClasspath("/com/github/rholder/nlp/tagging/fast-tag-lexicon.txt"));

    @Test
    public void happyPath() {
        List<String> words = wordsToList("The ball rolled down the street.");
        List<String> expectedTags = Arrays.asList("DT", "NN", "VBD", "RB", "DT", "NN", ".");
        checkFor(expectedTags, words);
    }

    @Test
    public void duchess() {
        // this is intentionally ambiguous, it gets pretty close
        List<String> words = wordsToList("The Duchess was entertaining last night.");
        List<String> expected = Arrays.asList("DT", "NNP", "VBD", "VBG", "JJ", "NN", ".");
        checkFor(expected, words);
    }

    @Test
    public void fireTruck() {
        // this is intentionally ambiguous
        List<String> words = wordsToList("the big green fire truck");
        List<String> expected = Arrays.asList("DT", "JJ", "JJ", "NN", "NN");
        checkFor(expected, words);
    }

    @Test
    public void blue() {
        List<String> words = wordsToList("the word \"blue\" has 4 letters.");
        List<String> expected = Arrays.asList("DT", "NN", "JJ", "VBZ", "4^", "NNS", ".");
        checkFor(expected, words);
    }

    @Ignore("jumps is being incorrectly identified as NNS here, should be VBZ")
    @Test
    public void someFoxes() {
        List<String> words = wordsToList("The quick brown fox jumps over the lazy dog.");
        List<String> expected = Arrays.asList("DT", "JJ", "JJ", "NN", "VBZ", "IN", "DT", "JJ", "NN", ".");
        checkFor(expected, words);
    }

    private void checkFor(List<String> expectedTags, List<String> words) {
        List<String> tags = FAST_TAG.tag(words);
        for (int i = 0; i < words.size(); i++) {
            String expectedTag = expectedTags.get(i);
            String tag = tags.get(i);
            String word = words.get(i);
            //System.out.println(words.get(i) + " / " + tags.get(i));
            Assert.assertEquals("Unexpected tag for: " + word, expectedTag, tag);
        }
    }
}
