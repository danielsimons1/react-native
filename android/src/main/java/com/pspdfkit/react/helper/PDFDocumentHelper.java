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

import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.react.MainApplication;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.io.File;

public class PDFDocumentHelper {

        private static PDFDocumentHelper INSTANCE = null;

        // other instance variables can be here
        public PdfDocument document = null;
        private Disposable documentOpeningDisposable;

        private PDFDocumentHelper() {
            setDocument("file:///android_asset/FullBook_V9.pdf");
        };

        public static PDFDocumentHelper getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new PDFDocumentHelper();
            }
            return(INSTANCE);
        }

    public void setDocument(@Nullable String documentPath) {
        if (documentPath == null) {
            this.document = null;
            return;
        }

        if (Uri.parse(documentPath).getScheme() == null) {
            // If there is no scheme it might be a raw path.
            try {
                File file = new File(documentPath);
                documentPath = Uri.fromFile(file).toString();
            } catch (Exception e) {
                documentPath = FILE_SCHEME + document;
            }
        }
        if (documentOpeningDisposable != null) {
            documentOpeningDisposable.dispose();
        }

        documentOpeningDisposable = PdfDocumentLoader.openDocumentAsync(com.pspdfkit.react.MainApplication.getContext(), Uri.parse(documentPath))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pdfDocument -> {
                    com.pspdfkit.react.helper.PDFDocumentHelper.this.document = pdfDocument;
                }, throwable -> {
                    com.pspdfkit.react.helper.PDFDocumentHelper.this.document = null;
                });
    }

}
