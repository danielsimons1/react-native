
package com.pspdfkit.react;

import com.pspdfkit.views.PdfView;

import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.react.MainApplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;

import android.graphics.Color;
import android.util.Log;

import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.react.bridge.ReadableArray;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.java.com.pspdfkit.react.helper.PDFDocumentHelper;
import com.pspdfkit.views.PdfReactImageView;

import com.pspdfkit.document.PdfDocumentLoader;

import androidx.annotation.Nullable;

import java.net.URL;

public class ReactDocumentImageViewManager extends SimpleViewManager<PdfReactImageView> {

    ReactApplicationContext mCallerContext;

    /* Interface Listener to start loading the image if the source is set */
    private interface ImgStartListener {
        void startLoading();
    }

    public ReactDocumentImageViewManager(ReactApplicationContext reactContext) {
        mCallerContext = reactContext;
        Log.e("Initializing ReactDocumentImageViewManager", "it is time to init the react document image view manager!!!");
    }

    @Override
    public PdfReactImageView createViewInstance(ThemedReactContext context) {
        PdfReactImageView reactImageView = new PdfReactImageView(context, Fresco.newDraweeControllerBuilder(), mCallerContext);

        return reactImageView;
    }

    @ReactProp(name = "documentPath")
    public void setDocument(PdfReactImageView view, String documentPath) {
        Log.i("ReactDocumentImageViewManager", documentPath);

        PDFDocumentHelper.getInstance(mCallerContext).getDocument(documentPath)
                .subscribeOn(Schedulers.io())
                .subscribe(pdfDoc -> {
                    Log.e("Found Document", "document was initialized");
                    view.load(pdfDoc);
                }, throwable -> {
                    Log.e("PDFDocumentHelper", "throwing: $throwable" + documentPath + " : " + throwable.getMessage());
                });


    }

    @ReactProp(name = "pageIndex")
    public void setPageIndex(PdfReactImageView view, int pageIndex) {
        view.setPageIndex(pageIndex);
    }

    @ReactProp(name = "scaleType")
    public void setResizeMode(PdfReactImageView view, @Nullable String resizeMode) {
        view.setScaleType(ImageResizeMode.toScaleType(resizeMode));
    }

    @Override
    public String getName() {
        return "RCTDocumentImageView";
    }
}
