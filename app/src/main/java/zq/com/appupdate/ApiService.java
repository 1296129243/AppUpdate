package zq.com.appupdate;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/31.
 */

public interface ApiService {
    //实际开发过程可能的接口方式
    @GET("update")
    Observable<UpdateAppInfo> getUpdateInfo(@Query("appname") String appname, @Query("serverVersiom") String appVersion);
    //以下方便版本更新接口测试
    @GET("update")
    Observable<UpdateAppInfo> getUpdateInfo();

}
