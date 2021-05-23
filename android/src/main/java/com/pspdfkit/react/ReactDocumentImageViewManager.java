
package com.pspdfkit.react;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import android.graphics.Color;
import android.util.Log;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.image.ReactImageView;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.pspdfkit.views.PdfView;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;

public class ReactDocumentImageViewManager extends SimpleViewManager<ReactImageView> {

    ReactApplicationContext mCallerContext;
    private ImgStartListener imgStartListener;

    /* Interface Listener to start loading the image if the source is set */
    private interface ImgStartListener {
        void startLoading(String imgUrl);
    }

    public ReactDocumentImageViewManager(ReactApplicationContext reactContext) {
        mCallerContext = reactContext;
    }

    @Override
    public ReactImageView createViewInstance(ThemedReactContext context) {
        ReactImageView reactImageView = new ReactImageView(context, Fresco.newDraweeControllerBuilder(), null, mCallerContext);
        reactImageView.setBackgroundColor(Color.BLUE);

        final Handler handler = new Handler();
        imgStartListener = new ImgStartListener() {
            @Override
            public void startLoading(String imgUrl) {
                startDownloading(imgUrl, handler, reactImageView);
            }
        };
        return reactImageView;
    }

    private void startDownloading(final String imgUrl, final Handler handler, final ReactImageView reactImageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imgUrl);
                    final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    setImage(bmp, handler, reactImageView);
                } catch (Exception e) {
                    Log.e("ReactImageManager", "Error : " + e.getMessage());
                }
            }
        }).start();
    }

    private void setImage(final Bitmap bmp, Handler handler, final ReactImageView reactImageView) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                reactImageView.setImageBitmap(bmp);
            }
        });
    }

    @ReactProp(name = "src")
    public void setSrc(ReactImageView view, String uri) {
        Log.i("ReactDocumentImageViewManager", uri);

        imgStartListener.startLoading(uri);
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
