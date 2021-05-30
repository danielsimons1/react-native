package com.pspdfkit.views;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.facebook.react.views.image.ReactImageView;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.GenericDraweeView;

import java.util.LinkedList;

import androidx.annotation.Nullable;

import com.pspdfkit.document.PdfDocument;

public class PdfReactImageView extends ReactImageView {
    private int pageIndex = 0;

    private ImgStartListener imgStartListener;

    /* Interface Listener to start loading the image if the source is set */
    private interface ImgStartListener {
        void startLoading();
    }

    public PdfReactImageView(
            Context context,
            AbstractDraweeControllerBuilder draweeControllerBuilder,
            @Nullable Object callerContext) {
        super(context, draweeControllerBuilder, null, callerContext);

        final Handler handler = new Handler();
        imgStartListener = new ImgStartListener() {
            @Override
            public void startLoading(PdfDocument doc) {
                startDownloading(doc, handler, reactImageView);
            }
        };
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void startLoading() {
        imgStartListener.startLoading();
    }

    private void startDownloading(PdfDocument doc, final Handler handler, final PdfReactImageView reactImageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Log.e("ReactImageViewManager", "it is time to render page to bitmap for " + reactImageView.getPageIndex());
                    doc.renderPageToBitmapAsync(mCallerContext, reactImageView.getPageIndex(), 50, 100)
                            .subscribe(bmp -> {
                                Log.e("ReactImageViewManager", "setImage with the bitmap we just retrieved!!!!");
                                setImage(bmp, handler, reactImageView);
                            }, error -> {
                                //handle error
                                Log.e("ReactImageManager", "Error : " + error.getMessage());
                            });

                } catch (Exception e) {
                    Log.e("ReactImageManager", "Error : " + e.getMessage());
                }
            }
        }).start();
    }
}
