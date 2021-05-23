
package com.pspdfkit.react;

import android.util.Log;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.image.ReactImageView;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.pspdfkit.views.PdfView;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;

public class ReactDocumentImageViewManager extends SimpleViewManager<ReactImageView> {

    ReactApplicationContext mCallerContext;

    public ReactDocumentImageViewManager(ReactApplicationContext reactContext) {
        mCallerContext = reactContext;
    }

    @Override
    public ReactImageView createViewInstance(ThemedReactContext context) {
        return new ReactImageView(context, Fresco.newDraweeControllerBuilder(), null, mCallerContext);
    }

    @ReactProp(name = "src")
    public void setSrc(ReactImageView view, @Nullable ReadableArray sources) {
        Log.i("ReactDocumentImageViewManager", sources.toString());

        view.setSource(sources);
    }

    @ReactProp(name = "pageIndex")
    public void setPageIndex(PdfView view, int pageIndex) {
        view.setPageIndex(pageIndex);
    }

    @ReactProp(name = "scaleType")
    public void setResizeMode(ReactImageView view, @Nullable String resizeMode) {
        view.setScaleType(ImageResizeMode.toScaleType(resizeMode));
    }

    @Override
    public String getName() {
        return "RCTDocumentImageView";
    }
}
