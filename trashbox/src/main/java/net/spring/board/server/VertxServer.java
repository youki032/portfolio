package net.spring.board.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonObject;

import com.nhncorp.mods.socket.io.SocketIOServer;
import com.nhncorp.mods.socket.io.SocketIOSocket;
import com.nhncorp.mods.socket.io.impl.DefaultSocketIOServer;

@Component
public class VertxServer extends DefaultEmbeddableVerticle {
  protected Logger log = LogManager.getLogger(VertxServer.class);
  
  private SocketIOServer io;

  
  @Override
  public void start(Vertx vertx) {
       
    HttpServer server = vertx.createHttpServer()
        .setSSL(true)   // vertx서버 https 사용설정
//        .setKeyStorePath("C:/javaide/.keystore") //localhost환경 keystore Path
        .setKeyStorePath("/etc/letsencrypt/live/trashbox.loan/keystore.p12") //aws환경 keystore Path
        .setKeyStorePassword("romeo777");
    
    io = new DefaultSocketIOServer(vertx, server);
    io.sockets().onConnection(new Handler<SocketIOSocket>() {
      public void handle(final SocketIOSocket socket) {
        socket.emit("welcome");
        
        socket.on("echo", new Handler<JsonObject>() {
          public void handle(JsonObject msg) {
            socket.emit("echo", msg);
          }
        });
      }
    });
    
    server.listen(19999);
  }
  

  public SocketIOServer getIo() {
    return io;
  }
}

