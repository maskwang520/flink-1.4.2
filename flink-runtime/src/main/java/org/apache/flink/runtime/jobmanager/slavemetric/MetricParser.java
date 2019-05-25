package org.apache.flink.runtime.jobmanager.slavemetric;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maskwang on 2019/1/2.
 * Parser the String to data and sort
 */
public class MetricParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(MetricParser.class);

	public static List<DataMetric> getSoredMetrics() throws Exception {

		List<DataMetric> list = new ArrayList<>();
		//TODO okhttp的实现
		OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
		Request request = new Request.Builder()
			.url("http://127.0.0.1:8999/getInstantData")//请求接口。如果需要传参拼接到接口后面。
			.build();//创建Request 对象
		Response response1 = null;
		response1 = client.newCall(request).execute();//得到Response 对象
		String data = response1.body().string();
		LOGGER.info("parse data is {}", data);
		String regex = "(\\d+.\\d+.\\d+.\\d+)\":\\[\"(\\d.\\d+):(\\d.\\d+):(\\d+.\\d+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		while (matcher.find()) {
			//System.out.println( matcher.group(1) + " --> " + matcher.group(2)+"-->"+matcher.group(3)+"-->"+matcher.group(4));
			//if ip is 127.0.0.1,skip the instance
			if (matcher.group(1).trim().equals("127.0.0.1")) {
				continue;
			}
			DataMetric temp = new DataMetric(matcher.group(1), Float.parseFloat(matcher.group(2)), Float.parseFloat(matcher.group(3)));
			list.add(temp);
		}
		Collections.sort(list, new Comparator<DataMetric>() {
			@Override
			public int compare(DataMetric o1, DataMetric o2) {
				if (o1.getCpuData() * 0.9 + o1.getMemData() * 0.1 == o2.getCpuData() * 0.9 + o2.getMemData() * 0.1) {
					return 0;
				} else {
					if (o1.getCpuData() * 0.9 + o1.getMemData() * 0.1 < o2.getCpuData() * 0.9 + o2.getMemData() * 0.1) {
						return 1;
					} else {
						return -1;
					}
				}
			}
		});
		return list;
	}

}
