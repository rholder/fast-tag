[![Build Status](http://img.shields.io/travis/rholder/fast-tag.svg)](https://travis-ci.org/rholder/fast-tag) [![Latest Version](http://img.shields.io/badge/latest-0.1.0-brightgreen.svg)](https://github.com/rholder/fast-tag/releases/tag/v0.1.0) [![License](http://img.shields.io/badge/license-apache%202-brightgreen.svg)](https://github.com/rholder/fast-tag/blob/master/LICENSE)

## What is this?
This is a fork of [Mark Watson](http://markwatson.com)'s Brill-style part of speech
tagger implementation and a couple of lexicons. You can find the original source
it was based on [here](https://github.com/mark-watson/fasttag_v2). It currently
doesn't handle all of the Brill contextual/lexical rules, but it implements a
nice chunk of them, and it's small, speedy, and includes its own tokenizer
without any external dependencies.

## How do I use it?
This project has been deployed to Maven Central to make it easy to integrate into your
own projects. Here are examples of how to add it to your own Maven or Gradle-based
project build:

### Maven
```xml
<dependency>
  <groupId>com.github.rholder.nlp</groupId>
  <artifactId>fast-tag-core</artifactId>
  <version>0.1.0</version>
</dependency>

<dependency>
  <groupId>com.github.rholder.nlp</groupId>
  <artifactId>fast-tag-lexicon</artifactId>
  <version>0.1.0</version>
</dependency>
```

### Gradle
```groovy
compile 'com.github.rholder.nlp:fast-tag-core:0.1.0'
compile 'com.github.rholder.nlp:fast-tag-lexicon:0.1.0'
```

## Quickstart
Add both `fast-tag-core` and `fast-tag-lexicon` to your project. Here's a
snippet that demonstrates some of the tagger's functionality:

```java
String lexiconPath = "/com/github/rholder/nlp/tagging/fast-tag-lexicon.txt";
FastTag fastTag = new FastTag(Lexicon.fromClasspath(lexiconPath));

List<String> words = Tokenizer.wordsToList("The ball rolled down the street.");
List<String> tags = fastTag.tag(words);
for(int i = 0; i < words.size(); i++) {
    System.out.println(words.get(i) + " / " + tags.get(i));
}
```

This produces the following output:

```
The / DT
ball / NN
rolled / VBD
down / RB
the / DT
street / NN
. / .
```

## Lexicon Tag Definitions:
Here are the tag definitions and some examples used in the `fast-tag-lexicon` module:

```
CC Coord Conjuncn           and,but,or
NN Noun, sing. or mass      dog
CD Cardinal number          one,two
NNS Noun, plural            dogs
DT Determiner               the,some
NNP Proper noun, sing.      Edinburgh
EX Existential there        there
NNPS Proper noun, plural    Smiths
FW Foreign Word             mon dieu
PDT Predeterminer           all, both
IN Preposition              of,in,by
POS Possessive ending       Õs
JJ Adjective                big
PP Personal pronoun         I,you,she
JJR Adj., comparative       bigger
PP$ Possessive pronoun      my,oneÕs
JJS Adj., superlative       biggest
RB Adverb                   quickly
LS List item marker         1,One
RBR Adverb, comparative     faster
MD Modal                    can,should
RBS Adverb, superlative     fastest
RP Particle                 up,off
WP$ Possessive-Wh           whose
SYM Symbol                  +,%,&
WRB Wh-adverb               how,where
TO ÒtoÓ                     to
$ Dollar sign               $
UH Interjection             oh, oops
# Pound sign                #
VB verb, base form          eat
" quote                     "
VBD verb, past tense        ate
VBG verb, gerund            eating
( Left paren                (
VBN verb, past part         eaten
) Right paren               )
VBP Verb, present           eat
, Comma                     ,
VBZ Verb, present           eats
. Sent-final punct          . ! ?
WDT Wh-determiner           which,that
: Mid-sent punct.           : ; Ñ
WP Wh pronoun               who,what
```

## Lexicon from Medpost Tag Definitions:
Here are the tag definitions and some examples used in the `fast-tag-lexicon-medical` module:

```
CC coordinating conjunction
CS subordinating conjunction
CSN comparative conjunction (than)
CST complementizer (that)
DB predeterminer
DD determiner
EX existential there
GE genitive marker Õs
II preposition
JJ adjective
JJR comparative adjective
JJT superlative adjective
MC number or numeric
NN noun
NNP proper noun
NNS plural noun
PN pronoun
PND determiner as pronoun
PNG genitive pronoun
PNR relative pronoun
RR adverb 
RRR comparative adverb
RRT superlative adverb
SYM symbol
TO infinitive marker to
VM modal
VBB base be, am, are
VBD past was, were
VBG participle being
VBI infinitive be
VBN participle been
VBZ 3rd pers. sing. is
VDB base do
VDD past did
VDG participle doing
VDI infinite do
VDN participle done
VDZ 3rd pers. sing. does
VHB base have
VHD past had
VHG participle having
VHI infinitive have
VHN participle had
VHZ 3rd pers. sing. has
VVB base form lexical verb
VVD past tense
VVG present part.
VVI infinitive lexical verb
VVN past part.
VVZ 3rd pers. sing.
VVNJ prenominal past part.
VVGJ prenominal present part.
VVGN nominal gerund
( left parenthesis
) right parenthesis
, comma
. end-of-sentence period
: dashes, colons
 ? ? right quo
```

## Building from source
The `fast-tag` project uses a [Gradle](http://gradle.org)-based build system. In the instructions
below, [`./gradlew`](http://vimeo.com/34436402) is invoked from the root of the source tree and serves as
a cross-platform, self-contained bootstrap mechanism for the build. The only
prerequisites are [Git](https://help.github.com/articles/set-up-git) and JDK 1.6+.

### check out sources
`git clone git://github.com/rholder/fast-tag.git`

### compile and test, build all jars
`./gradlew build`

### install all jars into your local Maven cache
`./gradlew install`

## Acknowledgements
* Based on source code from Mark Watson (http://markwatson.com) licensed under the Apache 2 license.
* Eric Brill for his lexicon and trained rule set: http://www.cs.jhu.edu/~brill/
* Medpost team for their tagging lexicon: http://mmtx.nlm.nih.gov/MedPost_SKR.shtml
* Brant Chee for bug reports and bug fixes

## License
The `fast-tag` module is released under version 2.0 of the
[Apache License](http://www.apache.org/licenses/LICENSE-2.0). The FastTag,
Lexicon, and Tokenizer classes contain code imported from the fasttag_v2 library
distributed under the terms of the Apache 2 license. Original source:
```
Copyright 2003-2008 Mark Watson (markw@markwatson.com)
```
Modifications:
```
Copyright 2015 Ray Holder
```
