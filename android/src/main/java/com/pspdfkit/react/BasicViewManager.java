package main.java.com.pspdfkit.react;

import android.graphics.Color;
import android.view.View;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ReactImageView;
import androidx.annotation.Nullable;

public class BasicViewManager extends SimpleViewManager<View> {
    ReactApplicationContext mCallerContext;

    public BasicViewManager(ReactApplicationContext reactContext) {
        mCallerContext = reactContext;
    }

    @Override
    public View createViewInstance(ThemedReactContext context) {
        View v = new View(context);
        v.setBackgroundColor(Color.RED);
        return v;
    }

    @ReactProp(name = "backgroundColor")
    public void setSrc(ReactImageView view, @Nullable String color) {
        view.setBackgroundColor(color);
    }

    @Override
    public String getName() {
        return "RCTBasicView";
    }
}

