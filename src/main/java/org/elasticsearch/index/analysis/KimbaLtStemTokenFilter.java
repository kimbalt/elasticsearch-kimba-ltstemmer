package org.elasticsearch.index.analysis;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

/**
 * A {@link TokenFilter} that applies {@link KimbaLtStemmer} to stem Lithuanian
 * words.
 * <p>
 * NOTE: Input is expected to be casefolded for Lithuanian (including folding of final
 * sigma to sigma), and with diacritics removed. This can be achieved by using
 * either ICUFoldingFilter before LtStemFilter.
 *
 * Exported from @lucene.experiment and modified in order to use the Kimba
 * Lithuanian stemmer.
 */
public class KimbaLtStemTokenFilter extends TokenFilter {
    
  private final KimbaLtStemmer stemmer;
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);

	public KimbaLtStemTokenFilter(TokenStream input,
	    CharArraySet stopwords)
	{
		super(input);
		this.stemmer = new KimbaLtStemmer(stopwords);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			if (!keywordAttr.isKeyword()) {
				final int newlen = stemmer.stem(termAtt.buffer(), termAtt.length());
				termAtt.setLength(newlen);
			}
			return true;
		} else {
			return false;
		}
	}
}
