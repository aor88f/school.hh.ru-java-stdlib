package ru.hh.school.stdlib;

import java.io.*;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.Map;

public class ServerRunnable implements Runnable
{
  public ServerRunnable(Socket cs, Substitutor3000 subst, Integer2 sleep, Integer2 result)
  {
    this.cs = cs;
    this.subst = subst;
    this.sleep = sleep;
    this.result = result;
  }

  public void run() {
    String text = null;
    try {
      text = acceptText();
    } catch (Exception e) {
      return;
    }

    try {
      Map.Entry<String, String> textEntry = parse(text);
      Map.Entry<String, String> argsEntry = parse(textEntry.getValue());
      String command = textEntry.getKey();
      String key = argsEntry.getKey();
      String value = argsEntry.getValue();

      Thread.sleep(sleep.value);

      if (command.equals("PUT")) {
        subst.put(key, value);
        sendText("OK\n");
        cs.close();
      }
      else if (command.equals("GET")) {
        sendText("VALUE\n");
        sendText(subst.get(key) + '\n');
        cs.close();
      }
      else if (command.equals("SET")) {
        if (key.equals("SLEEP")) {
          try {
            sleep.value = Integer.parseInt(value);
            sendText("OK\n");
            cs.close();
          }
          catch (NumberFormatException e) {
            error();
          }
        }
        else {
          error();
        }
      }
      else {
        error();
      }
    }
    catch (Exception e) {
      try {
        cs.close();
      } catch (IOException e1) {
        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      result.value = -1;
    }
  }

  String acceptText() throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
    return in.readLine();
  }

  void sendText(String text) throws IOException {
    Writer out = new PrintWriter(cs.getOutputStream());
    out.append(text).flush();
  }

  protected Map.Entry<String, String> separateText(final String text) throws Exception {
    int i = text.indexOf(' ');

    String s1 = null;
    String s2 = null;

    if (i == -1) {
      s1 = text;
      s2 = "";
    }
    else {
      s1 = text.substring(0, i);
      s2 = text.substring(i + 1);
    }
 
    return new AbstractMap.SimpleEntry<String, String>(s1, s2);
  }

  protected Map.Entry<String, String> parse(final String text) throws Exception {
    return separateText(text);
  }

  protected Map.Entry<String, String> getEntry(String args) throws Exception {
    return separateText(args);
  }

  void error() throws UnsupportedEncodingException {
    throw new UnsupportedOperationException();
  }

  protected Substitutor3000 subst;
  protected Socket cs = null;
  protected Integer2 sleep = null;
  protected Integer2 result = null;
}
