package org.elasticsearch.index.analysis;

import org.testng.Assert;
import org.testng.annotations.Test;

public class KimbaLtStemmerTest {
  private final KimbaLtStemmer stemmer = new KimbaLtStemmer();

  /**
   * a sample of lithuanian words that should be stemmed by the Kimba Lithuanian stemmer.
   */
  private static final String[] words = { };

  /**
   * the stems that should be returned by the stemmer for the above words.
   */
  private static final String[] stems = { };

  private static final String[] stopwords = { };

  private char[] token;
  private String stem;
  private int tokenLength, stemLength;

  @Test
  public void testKimbaLtStemmer() {
    for (int i = 0; i < words.length; i++) {
      token = words[i].toCharArray();
      tokenLength = words[i].length();
      stemLength = stemmer.stem(token, tokenLength);
      stem = new String(token, 0, stemLength);

      Assert.assertEquals(stem, stems[i]);
    }
  }

  @Test
  public void testKimbaLtStemmerStopwords() {
    for(String stopword : stopwords) {
      token = stopword.toCharArray();
      tokenLength = stopword.length();
      stemLength = stemmer.stem(token, tokenLength);
      stem = new String(token, 0, stemLength);

      Assert.assertEquals(stopword, stem);
    }
  }
}
