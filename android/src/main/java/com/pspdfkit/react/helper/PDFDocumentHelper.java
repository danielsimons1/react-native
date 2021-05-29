/*
 * PDFDocumentHelper.java
 *
 *   PSPDFKit
 *
 *   Copyright © 2021 PSPDFKit GmbH. All rights reserved.
 *
 *   THIS SOURCE CODE AND ANY ACCOMPANYING DOCUMENTATION ARE PROTECTED BY INTERNATIONAL COPYRIGHT LAW
 *   AND MAY NOT BE RESOLD OR REDISTRIBUTED. USAGE IS BOUND TO THE PSPDFKIT LICENSE AGREEMENT.
 *   UNAUTHORIZED REPRODUCTION OR DISTRIBUTION IS SUBJECT TO CIVIL AND CRIMINAL PENALTIES.
 *   This notice may not be removed from this file.
 */

package main.java.com.pspdfkit.react.helper;

import android.net.Uri;
import android.util.Log;
import android.content.Context;

import io.reactivex.Single;

import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.react.MainApplication;
import com.facebook.react.bridge.ReactApplicationContext;
import com.pspdfkit.document.PdfDocumentLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import javax.annotation.Nullable;
import java.io.File;

public class PDFDocumentHelper {

        private static PDFDocumentHelper INSTANCE = null;
        private Context reactAppContext = null;

        private static final String FILE_SCHEME = "file:///";

        private String documentPath = "file:///android_asset/FullBook_V9.pdf";

    // other instance variables can be here
        public PdfDocument document = null;

        private PDFDocumentHelper(Context context) {
            this.reactAppContext = context;
        };

        public static PDFDocumentHelper getInstance(Context reactAppContext) {
            if (INSTANCE == null) {
                INSTANCE = new PDFDocumentHelper(reactAppContext);
            }
            return(INSTANCE);
        }

    public static PDFDocumentHelper getInstance() {
        return(INSTANCE);
    }

    public void setDocument() {

        if (Uri.parse(documentPath).getScheme() == null) {
            // If there is no scheme it might be a raw path.
            try {
                File file = new File(documentPath);
                documentPath = Uri.fromFile(file).toString();
            } catch (Exception e) {
                documentPath = FILE_SCHEME + document;
            }
        }

        PdfDocumentLoader.openDocumentAsync(reactAppContext, Uri.parse(documentPath))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pdfDocument -> {
                    Log.e("Found Document", "document was initialized");
                    this.document = pdfDocument;
                }, throwable -> {
                    Log.e("PDFDocumentHelper", "throwing: $throwable");
                });
    }

    public Single<PdfDocument> getDocument() {
        if (this.document != null) {
            return Single.just(this.document);
        }

        return PdfDocumentLoader.openDocumentAsync(reactAppContext, Uri.parse(this.documentPath))
    }

}
