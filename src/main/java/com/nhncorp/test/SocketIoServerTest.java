package com.nhncorp.test;

import com.nhncorp.mods.socket.io.SocketIOServer;
import com.nhncorp.mods.socket.io.SocketIOSocket;
import com.nhncorp.mods.socket.io.impl.DefaultSocketIOServer;
import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.impl.VertxInternal;
import org.vertx.java.core.json.JsonObject;

/**
 * @author Keesun Baik
 */
public class SocketIoServerTest extends BusModBase {

	@Override
	public void start() {
		super.start();
		HttpServer server = vertx.createHttpServer();
		SocketIOServer io = new DefaultSocketIOServer((VertxInternal) vertx, server);
		io.sockets().onConnection(new Handler<SocketIOSocket>() {
			@Override
			public void handle(final SocketIOSocket socket) {
				System.out.println("Somebody comes in.");
				socket.on("timer", new Handler<JsonObject>() {
					@Override
					public void handle(JsonObject event) {
						socket.emit("timer", event);
					}
				});
				socket.onDisconnect(new Handler<JsonObject>() {
					@Override
					public void handle(JsonObject event) {
						System.out.println("somebody went out");
					}
				});
			}
		});
		server.listen(9090);
	}
}
