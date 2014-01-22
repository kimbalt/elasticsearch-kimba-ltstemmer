package org.elasticsearch.index.analysis;

import org.elasticsearch.index.analysis.AnalysisModule.AnalysisBinderProcessor;

public class KimbaLtStemmerBinderProcessor extends AnalysisBinderProcessor {
	
	@Override
	public void processTokenFilters(TokenFiltersBindings tokenFiltersBindings) {
		tokenFiltersBindings.processTokenFilter("kimba_lt_stemmer", KimbaLtStemmerTokenFilterFactory.class);
	}


}
