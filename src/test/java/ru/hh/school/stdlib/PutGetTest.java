package ru.hh.school.stdlib;

import java.io.*;
import java.net.Socket;
import org.junit.Assert;
import org.junit.Test;

public class PutGetTest extends BaseFunctionalTest {
  @Test
  public void putGet() throws IOException {
    Socket s = null;
    Writer out = null;
    BufferedReader in = null;

    s = connect();
    out = new PrintWriter(s.getOutputStream());
    out.append("PUT K1 one\n").flush();
    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    Assert.assertEquals("OK", in.readLine());
    s.close();

    s = connect();
    out = new PrintWriter(s.getOutputStream());
    out.append("GET K1 one\n").flush();
    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    Assert.assertEquals("VALUE", in.readLine());
    Assert.assertEquals("one", in.readLine());
    s.close();

    s = connect();
    out = new PrintWriter(s.getOutputStream());
    out.append("PUT keys ${K1} ${K2}\n").flush();
    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    Assert.assertEquals("OK", in.readLine());
    s.close();

    s = connect();
    out = new PrintWriter(s.getOutputStream());
    out.append("GET keys\n").flush();
    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    Assert.assertEquals("VALUE", in.readLine());
    Assert.assertEquals("one ", in.readLine());
    s.close();

    s = connect();
    out = new PrintWriter(s.getOutputStream());
    out.append("PUT K2 two\n").flush();
    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    Assert.assertEquals("OK", in.readLine());
    s.close();

    s = connect();
    out = new PrintWriter(s.getOutputStream());
    out.append("SET SLEEP 1\n").flush();
    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    Assert.assertEquals("OK", in.readLine());
    s.close();

    s = connect();
    out = new PrintWriter(s.getOutputStream());
    out.append("GET keys\n").flush();
    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    Assert.assertEquals("VALUE", in.readLine());
    Assert.assertEquals("one two", in.readLine());
    s.close();
  }
}
