package org.elasticsearch.index.analysis;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;



public class KimbaLtStemmer {

  public KimbaLtStemmer() {
  }
  
  private final String[] wordEndings 
  = {
  "ias", "io", "iui", "ią", "iu", "yje", "y", "iai", "ių", "iams", "ius", "iais", "iuose", "iai",
  "jas", "jo", "jui", "ją", "ju", "juje", "jau", "jai", "jų", "jams", "jus", "jais", "juose", "jai",
  "ius", "iaus", "iui", "ių", "iumi", "iuje", "iau", "iai", "ių", "iams", "ius", "iais", "iuose", "iai",
  "jus", "jaus", "jui", "jų", "jumi", "juje", "jau", "jai", "jų", "jams", "jus", "jais", "juose", "jai",
  "dis", "džio", "džiui", "dį", "džiu", "dyje", "di", "džiai", "džių", "džiams", "džius", "džiais", "džiuose", "džiai",
  "dys", "džio", "džiui", "dį", "džiu", "dyje", "dy", "džiai", "džių", "džiams", "džius", "džiais", "džiuose", "džiai",
  "tis", "čio", "čiui", "tį", "čiu", "tyje", "ti", "čiai", "čių", "čiams", "čius", "čiais", "čiuose", "čiai",
  "tis", "ties", "čiai", "tį", "timi", "tyje", "tie", "tys", "čių", "tims", "tis", "timis", "tyse", "tys",
  "tys", "čio", "čiui", "tį", "čiu", "tyje", "ty", "čiai", "čių", "čiams", "čius", "čiais", "čiuose", "čiai",
  "čia", "čios", "čiai", "čią", "čia", "čioje", "čia", "čios", "čių", "čioms", "čias", "čiomis", "čiose", "čios",
  "as", "o", "ui", "ą", "u", "e", "e", "ai", "ų", "ams", "us", "ais", "uose", "ai",
  "ys", "io", "iui", "į", "iu", "yje", "y", "iai", "ių", "iams", "ius", "iais", "iuose", "iai",
  "ia", "ios", "iai", "ią", "ia", "ioje", "ia", "ios", "ių", "ioms", "ias", "iomis", "iose", "ios",
  "ja", "jos", "jai", "ją", "ja", "joje", "ja", "jos", "jų", "joms", "jas", "jomis", "jose", "jos",
  "us", "aus", "ui", "ų", "umi", "uje", "au", "ūs", "ų", "ums", "us", "umis", "uose", "ūs",
  "is", "io", "iui", "į", "iu", "yje", "i", "iai", "ių", "iams", "ius", "iais", "iuose", "iai",
  "is", "ies", "iai", "į", "imi", "yje", "ie", "ys", "ių", "ims", "is", "imis", "yse", "ys",
  "uo", "ens", "eniui", "enį", "eniu", "enyje", "enie", "enys", "enų", "enims", "enis", "enimis", "enyse", "enys",
  "tė", "tės", "tei", "tę", "te", "tėje", "te", "tės", "čių", "tėms", "tes", "tėmis", "tėse", "tės",
  "dė", "dės", "dei", "dę", "de", "dėje", "de", "dės", "džių", "dėms", "des", "dėmis", "dėse", "dės",
  "ti", "čios", "čiai", "čią", "čia", "čioje", "čia", "čios", "čių", "čioms", "čias", "čiomis", "čiose", "čios",
  "a", "os", "ai", "ą", "a", "oje", "a", "os", "ų", "oms", "as", "omis", "ose", "os",
  "i", "ios", "iai", "ią", "ia", "ioje", "ia", "ios", "ių", "ioms", "ias", "iomis", "iose", "ios",
  "ė", "ės", "ei", "ę", "e", "ėje", "e", "ės", "ių", "ėms", "es", "ėmis", "ėse", "ės",

  "esnis", "esnio", "esniam", "esnį", "esniu", "esniame", "esni", "esni", "esnių", "esniems", "esnius", "esniais", "esniuose", "esni",
  "elis", "elio", "eliam", "elį", "eliu", "eliame", "eli", "eli", "elių", "eliems", "elius", "eliais", "eliuose", "eli",
  "inis", "inio", "iam", "inį", "iniu", "iniame", "ini", "iniai", "inių", "iniams", "inius", "iniais", "iniuose", "iniai",
  "asis", "ojo", "ąjam", "ąjį", "uoju", "ąjame", "asis", "ieji", "ųjų", "iesiems", "uosius", "aisiais", "uosiuose", "ieji",
  "oji", "osios", "ąjai", "ąją", "ąja", "ojoje", "oji", "osios", "ųjų", "osioms", "ąsias", "osiomis", "osiose", "osios",
  "ias", "io", "iam", "ią", "iu", "iame", "ias", "i", "ių", "iems", "ius", "iais", "iuose", "i",
  "tis", "čio", "čiam", "tį", "čiu", "čiame", "tis", "tys", "čių", "tiems", "čius", "čiais", "čiuose", "tys",
  "as", "o", "am", "ą", "u", "ame", "as", "i", "ų", "iems", "us", "ais", "uose", "i",
  "is", "io", "iam", "į", "iu", "iame", "i", "iai", "ių", "iams", "ius", "iais", "iuose", "iai",
  "ys", "io", "iam", "į", "iu", "iame", "y", "iai", "ių", "iems", "ius", "iais", "iuose", "iai",
  "us", "aus", "iam", "ų", "iu", "iame", "us", "ūs", "ių", "iems", "ius", "iais", "iuose", "ūs",
  "ti", "čios", "čiai", "čią", "čia", "čioje", "ti", "čios", "čių", "čioms", "čias", "čiomis", "čiose", "čios",
  "a", "os", "ai", "ą", "a", "oje", "a", "os", "ų", "oms", "as", "omis", "ose", "os",
  "i", "ios", "iai", "ią", "ia", "ioje", "i", "ios", "ių", "ioms", "ias", "iomis", "iose", "ios",
  "a", "os", "ai", "ą", "a", "oje", "a", "os", "ų", "oms", "as", "omis", "ose", "os",
  "ė", "ės", "ei", "ę", "e", "ėje", "e", "ės", "ių", "ėms", "es", "ėmis", "ėse", "ės",

  "asis", "ojo", "ąjam", "ąjį", "uoju", "ąjame", "asis", "ieji", "ųjų", "iesiems", "uosius", "aisiais", "uosiuose", "ieji",
  "oji", "osios", "ąjai", "ąją", "ąja", "ojoje", "oji", "osios", "ųjų", "osioms", "ąsias", "osiomis", "osiose", "osios"
  };
  
  public class MyComparator implements Comparator<String>{
	    @Override
	    public int compare(String o1, String o2) {  
	      if (o1.length() > o2.length()) {
	         return 1;
	      } else if (o1.length() < o2.length()) {
	         return -1;
	      }
	      return o1.compareTo(o2);
	    }
	}
  
  	public int stem(char s[], int len) {
	  
	  Arrays.sort(wordEndings, new MyComparator());
    
	  if (len < 3)
		  return len;
	  
	  for(int i = 0; i < this.wordEndings.length; i++){
		  String ending = this.wordEndings[i];
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
}
