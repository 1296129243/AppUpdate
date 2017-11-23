package zq.com.appupdate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;

/**
 * Created by Administrator on 2017/11/2.
 * *共通机能-版本检测/更新
 *
 * @version 1.0
 */

public class AppInnerDownLoader {
    public final static String SD_FOLDSER = Environment.getExternalStorageDirectory() + "/VersionChecker/";
    private static final String TAG = AppInnerDownLoader.class.getSimpleName();

    /**
     * 从服务器中下载APK
     */
    public static void downLoadApk(Context mContext,String downURL,String appName) {
        ProgressDialog pd ;// 进度条对话框
        pd = new ProgressDialog(mContext);
        pd.setCancelable(false);// 必须一直下载完，不可取消
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载安装包，请稍后");
        pd.setTitle("版本升级");
        pd.show();

    }
}
