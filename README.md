KimbaLtStemmer plugin for ElasticSearch
===========================================


In order to build KimbaLtStemmer plugin just clone this git repository and initiate maven package build from scratch with this command: mvn clean package.

It's in development stage. (official released version doesn't exist at a time)

Examples:

    -----------------------------------------
    | Word                 | KimbaLtStemmer |
    -----------------------------------------
    | taves (singular)     | tav            |
    -----------------------------------------
    | mūsų (plural)        | mus            |
    -----------------------------------------
    | namas (singular)     | nam            |
    -----------------------------------------
    | giedraičiai (plural) | giedraic       |
    -----------------------------------------
    | geriausias (singular)| geriaus        |
    -----------------------------------------
    | didysis (singular)   | didys          |
    -----------------------------------------


In order to install the latest version of the plugin, simply run:

    sudo bin/plugin -url file:elasticsearch-kimba-ltstemmer-0.0.1.zip  -install kimba-ltstemmer

Example usage:

	index:
	  analysis:
	    analyzer:
	      lt_analyzer:
	        filter: icu_folding, stem_lt
	    filter:
	      stem_lt:
	        type: kimba_stem_lt


Warning
-------

Input is expected to to be casefolded for Lithuanian, and with diacritics removed. This can be achieved with ICU_FOLDING.
