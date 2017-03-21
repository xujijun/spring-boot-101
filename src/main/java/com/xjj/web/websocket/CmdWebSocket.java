package com.xjj.web.websocket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by XuJijun on 2017-03-21.
 */
@ServerEndpoint("/cmd-websocket")
@Component
public class CmdWebSocket {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static int onlineCount = 0;
	private static CopyOnWriteArraySet<CmdWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

	private Session session;

	@OnOpen
	public void onOpen(Session session) throws IOException{
		this.session = session;
		webSocketSet.add(this);
		if(getOnlineCount()>0){
			this.sendMessage("对不起，此服务只允许有一个连接。请稍后再试。");
			session.close();
			return;
		}

		incrOnlineCount();

		logger.info("new connection...current online count: {}", getOnlineCount());
	}

	@OnClose
	public void onClose() throws IOException{
		webSocketSet.remove(this);
		decOnlineCount();
		logger.info("one connection closed...current online count: {}", getOnlineCount());
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException, InterruptedException {
		logger.info("message received: {}", message);

		if(message.equals("execute cmd")){
			//执行命令，然后返回结果
			Process process;
			//一次性执行多个命令
			String cmd = "cmd /c \"E: && cd gitBackup && pullAll.bat\"";

			try {
				Runtime runtime = Runtime.getRuntime();

				System.out.println("开始执行。。。");
				process = runtime.exec(cmd);

				//打印执行的输出结果
				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "gbk"); //gbk：解决输出乱码
				BufferedReader br = new BufferedReader(isr);
				String line;
				while ((line = br.readLine()) != null){
					System.out.println(line);
					if(StringUtils.isNotBlank(line.trim())) {
						this.sendMessage(line);
					}
					//Thread.sleep(500);
				}

				is.close();
				isr.close();
				br.close();
			}catch (IOException e){
				e.printStackTrace();
			}

		}else {
			this.sendMessage("不支持的命令。");
		}

	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	public static synchronized int getOnlineCount(){
		return CmdWebSocket.onlineCount;
	}

	public static synchronized void incrOnlineCount(){
		CmdWebSocket.onlineCount++;
	}

	public static synchronized void decOnlineCount(){
		CmdWebSocket.onlineCount--;
	}
}
