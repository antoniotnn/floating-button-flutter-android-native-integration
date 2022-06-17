package br.com.tnn.floating_button;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import android.annotation.SuppressLint;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.Screen;


import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;


public class MainActivity extends FlutterActivity {

    private static final String CHANNEL = "floating_button";

    MethodChannel channel;

    @Override
    public void configureFlutterEngine(@Nullable FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        channel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GeneratedPluginRegistrant.registerWith(Objects.requireNonNull(this.getFlutterEngine()));


        //GeneratedPluginRegistrant.registerWith(this.getFlutterEngine());

        //MethodChannel channel = new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(), CHANNEL);

        channel.setMethodCallHandler(
            (call, result) -> {

                switch (call.method) {
                    case "create":

                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.plus);

                        FloatWindow.with(getApplicationContext()).setView(imageView)
                                .setWidth(Screen.width, 0.15f)
                                .setHeight(Screen.width, 0.15f)
                                .setX(Screen.width, 0.8f)
                                .setY(Screen.height, 0.3f)
                                .setDesktopShow(true)
                                .build();

                        imageView.setOnClickListener(view -> {

                            channel.invokeMethod("touch", null);
                            Toast.makeText(MainActivity.this, "+1", Toast.LENGTH_SHORT).show();
                        }
                        );

                        break;
                    case "show":
                        FloatWindow.get().show();
                        break;
                    case "hide":
                        FloatWindow.get().hide();
                        break;
                    case "isShowing":
                        result.success(FloatWindow.get().isShowing());
                        break;
                    default:
                        result.notImplemented();
                }

            }
        );

    }

    @Override
    protected void onDestroy() {
        FloatWindow.destroy();
        super.onDestroy();
    }
}
