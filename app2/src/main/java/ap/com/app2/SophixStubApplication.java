package ap.com.app2;

import android.app.Application;
import android.util.Log;

import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class SophixStubApplication extends Application {

    //获取版本号+
    public static String appVersion;

    public interface MsgDisplayListener {
        void handle(String msg);
    }

    public static MsgDisplayListener msgDisplayListener = null;
    public static StringBuilder cacheMsg = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
        //初始ali热修复
        initHotfix();
    }

    //初始ali热修复
    private void initHotfix() {
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            Log.i("appVersion", appVersion);
        } catch (Exception e) {
            appVersion = "1.0.0";
        }

        // initialize必须放在attachBaseContext最前面，初始化代码直接写在Application类里面，切勿封装到其他类。
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                //.setAesKey("0123456789123456")
                .setEnableDebug(false)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        String msg = new StringBuilder("").append("Mode:").append(mode)
                                .append(" Code:").append(code)
                                .append(" Info:").append(info)
                                .append(" HandlePatchVersion:").append(handlePatchVersion).toString();
                        if (msgDisplayListener != null) {
                            msgDisplayListener.handle(msg);
                        } else {
                            cacheMsg.append("\n").append(msg);
                        }
                    }
                }).initialize();

        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
