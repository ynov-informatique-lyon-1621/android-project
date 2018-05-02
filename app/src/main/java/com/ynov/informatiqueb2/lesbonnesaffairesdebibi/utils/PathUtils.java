package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class PathUtils {
        private static String getRealPathFromURI_API19(Context context, Uri uri){
            String filePath = "";
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = { MediaStore.Images.Media.DATA };

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{ id }, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        }

        private static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
            String[] proj = { MediaStore.Images.Media.DATA };
            String result = null;

            CursorLoader cursorLoader = new CursorLoader(
                    context,
                    contentUri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();

            if(cursor != null){
                int column_index =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
            }
            return result;
        }


        public static String getPath(Context context, Uri contentUri) {
            String path;
            if (Build.VERSION.SDK_INT < 19) {
                path = PathUtils.getRealPathFromURI_API11to18(context, contentUri);
            }else {
                path = PathUtils.getRealPathFromURI_API19(context, contentUri);
            }
            return path;
        }
}
