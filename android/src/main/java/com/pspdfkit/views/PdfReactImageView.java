package com.pspdfkit.views;

import android.content.Context;

import com.facebook.react.views.image.ReactImageView;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.GenericDraweeView;

import java.util.LinkedList;

import androidx.annotation.Nullable;

public class PdfReactImageView extends ReactImageView {
    public int pageIndex = 0;

    public PdfReactImageView(
            Context context,
            AbstractDraweeControllerBuilder draweeControllerBuilder,
            @Nullable Object callerContext) {
        super(context, draweeControllerBuilder, null, callerContext);
    }
}
