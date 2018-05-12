package ap.com.app2;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * Sophix应用地址：https://emas.console.aliyun.com/?spm=5176.2020520001.aliyun_topbar.111.69864bd3FtSMm3#/productList
     */

    private String mStatusStr = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_msg = findViewById(R.id.tv_msg);
        tv_msg.setText("AliSophix Success\n版本 v1_" + SophixStubApplication.appVersion);

        SophixStubApplication.msgDisplayListener = new SophixStubApplication.MsgDisplayListener() {
            @Override
            public void handle(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateConsole(msg);
                    }
                });
            }
        };
    }

    private void updateConsole(String msg) {
        mStatusStr += msg + "\n";
        Log.i("Sophix", mStatusStr);
    }
}
