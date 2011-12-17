package ru.hh.school.stdlib;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public Server(InetSocketAddress addr) throws IOException {
    this.addr = addr;
    System.out.println("Address: " + addr.getAddress());
    System.out.println("Port: " + addr.getPort());
    System.out.println("HostName: " + addr.getHostName());

    //throw new UnsupportedOperationException();
  }

  public void run() throws IOException {
    ServerSocket ss = new ServerSocket(getPort());
    for (;;) {
      Socket cs = null;

      if (result.value != 0) {
        if (cs != null) {
          cs.close();
        }
        break;
      }
      else {
        cs = ss.accept();
        synchronized (subst) {
          synchronized (sleep) {
            synchronized (result) {
              new Thread(new ServerRunnable(cs, subst, sleep, result)).start();
            }
          }
        }
      }
    }
    throw new UnsupportedOperationException();
  }

  public int getPort() {
    return addr.getPort();
    //throw new UnsupportedOperationException();
  }

  protected final Substitutor3000 subst = new Substitutor3000();
  protected final Integer2 sleep = new Integer2();
  protected final Integer2 result = new Integer2();
  protected InetSocketAddress addr;
}
