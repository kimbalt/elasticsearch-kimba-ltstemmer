package org.elasticsearch.plugin.analysis.kimbaltstemmer;

import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.KimbaLtStemmerBinderProcessor;

public class KimbaLtStemmerPlugin extends AbstractPlugin {

	@Override
	public String description() {
		return "Lithuanian stemmer customized for the needs of www.kimba.lt";
	}

	@Override
	public String name() {
		return "kimba-lt-stemmer";
	}

	public void onModule(AnalysisModule module) {
		module.addProcessor(new KimbaLtStemmerBinderProcessor());
	}

}
