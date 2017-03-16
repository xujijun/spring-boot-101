package com.xjj.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by XuJijun on 2017-03-16.
 */
public class RuntimeTask {

	public static void main(String[] args) {
		Process process;
		//一次性执行多个命令
		String cmd = "cmd /c \"E: && dir && cd gitBackup && dir && pullAll.bat\"";

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
			}

			is.close();
			isr.close();
			br.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
