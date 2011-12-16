package ru.hh.school.stdlib;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substitutor3000 {
  public void put(String key, String value) {
    map.put(key, value);
  }

  public String get(String key) {
    String value = map.get(key);
    return (value == null) ? "" : replace(value);
  }

  String replace(String s) {
    Pattern p = Pattern.compile("\\$\\{[^\\}]*\\}");
    for (;;)
    {
      Matcher m = p.matcher(s);
      if (!m.find()) {
        break;
      }

      String key = m.group(0);
      key = key.substring(2, key.length() - 1);
      String r = map.get(key);
      if (r == null)
        r = "";

      s = m.replaceFirst(r);
    }

    return s;
  }

  protected Map<String, String> map = new HashMap<String, String>();
}
