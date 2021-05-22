
package com.pspdfkit.react;

import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.image.ReactImageView;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.drawee.backends.pipeline.Fresco;

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
        view.setSource(sources);
    }

    @ReactProp(name = "pageIndex")
    public void setPageIndex(PdfView view, int pageIndex) {
        view.setPageIndex(pageIndex);
    }

    @Override
    public String getName() {
        return "RCTDocumentImageView";
    }
}
