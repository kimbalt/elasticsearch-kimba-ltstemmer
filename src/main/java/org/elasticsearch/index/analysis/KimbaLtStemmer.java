package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.WordlistLoader;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * A stemmer for Lithuanian words, according to: <i>Development of a Stemmer for the
 * Lithuanian Language.</i> Georgios Ntais
 * <p>
 * NOTE: Input is expected to be casefolded for Lithuanian (including folding of final
 * sigma to sigma), and with diacritics removed. This can be achieved with
 * either {@link LithuanianLowerCaseFilter} or ICUFoldingFilter.
 *
 * This stemmer is based on the stemmer of the @lucene.experimental with some
 * additions.
 * <p>
 * According to: <i>Development of a Stemmer for the Lithuanian Language.</i>, the
 * original stemmer removed 158 suffixes. Eight suffixes were not handled at
 * all by this stemmer. However, four of those eight suffixes belong to the same
 * category with one of the suffixes that is actually handle by the stemmer.
 * This suffix is "ια" which is mishandled and may be the plural type of the
 * suffix "ιο".
 * <p>
 * Furthermore, the suffix "εασ" is not stemmed correctly and is not even
 * included in the original 166 suffixes that should be removed.
 * <p>
 * This custom Lithuanian stemmer adds to the current rule set some more cases about
 * the following suffixes:
 * <p>
 * <b>"ιο", "ιασ", "ιεσ", "ιοσ", "ιουσ", "ιοι", "εασ", "εα"</b>
 * <p>
 * Following the same strategy with the Lithuanian stemmer of lucene, some exceptions
 * about these suffixes are added.
 */
public class KimbaLtStemmer {
  public final static String DEFAULT_STOPWORD_FILE = "stopwords.txt";
  private final CharArraySet stopwords;

  public KimbaLtStemmer(final CharArraySet stopwords) {
    this.stopwords = stopwords;
  }

  public KimbaLtStemmer() {
    this.stopwords = KimbaLtStemmer.getDefaultStopSet();
  }

  public static final CharArraySet getDefaultStopSet(){
    return DefaultSetHolder.DEFAULT_SET;
  }

  private static class DefaultSetHolder {
    private static final CharArraySet DEFAULT_SET;

    static {
      try {
        DEFAULT_SET = loadStopwordSet(
            KimbaLtStemmer.class.getResourceAsStream(DEFAULT_STOPWORD_FILE),
            Version.LUCENE_44);
      } catch (IOException ex) {
        // default set should always be present as it is part of the
        // distribution (JAR)
        throw new RuntimeException("Unable to load default stopword set");
      }
    }
  }
  
  private final String[] singularNominativeEndings 
	= {"ias", "jas", "ius", "jus", "dis", "dys", "tis", "tis", "tys", "čia", "as"};
  
  private final String[] pluralNominativeEndings 
	= {"ys", "ia", "ja", "us", "is", "is", "uo", "tė", "dė", "ti", "a", "i", "ė"};
  
  private final String[] singularDativeEndings 
	= {"džiui", "džiui", "čiui", "čiai", "čiui", "čiai", "iui", "jui", "iui", "jui", "ui"};

  public int stem(char s[], int len) {
    
	  // Too short or a stopword
	  //if (len < 3 || stopwords.contains(s, 0, len))
		//  return len;

	  //nominative
	  for(int i = 0; i < this.singularNominativeEndings.length; i++){
		  String ending = this.singularNominativeEndings[i];
		  if(endsWith(s, len, ending)){
			  return len - ending.length();
		  }
	  }
	  for(int i = 0; i < this.pluralNominativeEndings.length; i++){
		  String ending = this.pluralNominativeEndings[i];
		  if(endsWith(s, len, ending)){
			  return len - ending.length();
		  }
	  }
	  
	  //dative
	  for(int i = 0; i < this.singularDativeEndings.length; i++){
		  String ending = this.singularDativeEndings[i];
		  if(endsWith(s, len, ending)){
			  return len - ending.length();
		  }
	  }
	  
	  return len;
  }
  
  /**
   * 
   * @param s
   * @param len
   * @param suffix
   * @return a boolean if word end matches
   */
  private boolean endsWith(char s[], int len, String suffix) {
	  final int suffixLen = suffix.length();
	  if (suffixLen > len)
		  return false;
	  for (int i = suffixLen - 1; i >= 0; i--)
		  if (s[len -(suffixLen - i)] != suffix.charAt(i))
			  return false;
	  return true;
  }
  
  

  /**
   * Creates a CharArraySet from a file.
   *
   * @param stopwords
   *          Input stream from the stopwords file
   *
   * @param matchVersion
   *          the Lucene version for cross version compatibility
   * @return a CharArraySet containing the distinct stopwords from the given
   *         file
   * @throws IOException
   *           if loading the stopwords throws an {@link IOException}
   */
  private static CharArraySet loadStopwordSet(InputStream stopwords,
      Version matchVersion) throws IOException {
    Reader reader = null;
    try {
      reader = IOUtils.getDecodingReader(stopwords, IOUtils.CHARSET_UTF_8);
      return WordlistLoader.getWordSet(reader, matchVersion);
    } finally {
      IOUtils.close(reader);
    }
  }
}
