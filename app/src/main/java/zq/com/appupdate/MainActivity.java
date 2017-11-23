package zq.com.appupdate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //网络检查版本是否需要更新
        CheckUpdateUtils.checkUpdate("apk", "1.0.0", new CheckUpdateUtils.CheckCallBack() {
            @Override
            public void onSuccess(UpdateAppInfo updateAppInfo) {
                String isForce = updateAppInfo.data.getLastForce();//是否需要强制更新
                String downUrl = updateAppInfo.data.getUpdateurl();//apk下载地址
                String updateinfo = updateAppInfo.data.getUpgradeinfo();//apk更新详情
                String appName = updateAppInfo.data.getAppname();
                if (isForce.equals("1") && !TextUtils.isEmpty(updateinfo)) {//强制更新
                    forceUpdate(MainActivity.this, appName, downUrl, updateinfo);
                } else {//非强制更新
                    //正常升级
                    normalUpdate(MainActivity.this, appName, downUrl, updateinfo);
                }

            }

            @Override
            public void onError() {
                noneUpdate(MainActivity.this);

            }
        });

        /**
         * 实在不想写网络也好，直接使用假想数据做相关操作如下
         */
        UpdateAppInfo.UpdateInfo info = new UpdateAppInfo.UpdateInfo();
        info.setLastForce("1");
        info.setAppname("我日你");
        info.setUpgradeinfo("whejjefjhrherkjreghgrjrgjjhrh");
        info.setUpdateurl("http://releases.b0.upaiyun.com/hoolay.apk");
        if (info.getLastForce().equals("1")) {//强制更新
            forceUpdate(MainActivity.this, info.getAppname(), info.getUpdateurl(), info.getUpgradeinfo());
        } else {//非强制更新
            //正常升级
            normalUpdate(MainActivity.this, info.getAppname(), info.getUpdateurl(), info.getUpgradeinfo());
        }
    }

    private void noneUpdate(Context context) {

    }

    private void normalUpdate(Context context, String appName, String downUrl, String updateinfo) {

    }

    /**
     * 强制更新
     *
     * @param context
     * @param appName
     * @param downUrl
     * @param updateinfo
     */
    private void forceUpdate(Context context, String appName, String downUrl, String updateinfo) {

        mDialog = new AlertDialog.Builder(context);
        mDialog.setTitle(appName + "又更新咯");
        mDialog.setMessage(updateinfo);
        mDialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!canDownloadState()) {
                    showDownloadSetting();
                    return;
                }
//                AppInnerDownLoader
            }
        });

    }

    private void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if(intentAvailable(intent)){
            startActivity(intent);
        }
    }

    private boolean intentAvailable(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List list = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private boolean canDownloadState() {
        try {
            int state = this.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
