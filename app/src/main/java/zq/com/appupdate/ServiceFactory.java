package zq.com.appupdate;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Administrator on 2017/10/31.
 * 通过工厂模式来创建ApiService
 */

public class ServiceFactory {
    public static final String BASEURL = "http://192.168.0.106:8081/";

    public static <T> T createServiceFrom(final Class<T> serviceClass) {
        Retrofit adapter = new Retrofit.Builder().baseUrl(BASEURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())// 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create())// 添加Gson转换器
                .build();
        return adapter.create(serviceClass);
    }
}
