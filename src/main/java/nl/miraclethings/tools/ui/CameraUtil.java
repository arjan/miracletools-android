package nl.miraclethings.tools.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import nl.miraclethings.tools.data.DateUtil;

/**
 * Created by arjan on 7-3-16.
 */
public class CameraUtil {

    public static List<ImageMetadata> getPicturesTaken(Context context, Date from, Date to) {
        List<ImageMetadata> result = new LinkedList<>();

        if (!hasExternalStoragePermission(context)) {
            return result;
        }

        // Find the last picture
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE
        };
        final Cursor cursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                        null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");


        // Put it in the image view
        assert cursor != null;

        if (cursor.moveToFirst()) {
            while (true) {
                String imageLocation = cursor.getString(1);
                long date = cursor.getLong(2);
                String mime = cursor.getString(3);

                if (from.getTime() < date && date < to.getTime() && mime != null && mime.startsWith("image/")) {
                    //System.out.println(date + " " + mime + " " + imageLocation);
                    File file = new File(imageLocation);
                    if (file.canRead()) {
                        result.add(new ImageMetadata(file, mime, new Date(date)));
                    }
                }

                if (!cursor.moveToNext()) {
                    break;
                }
            }
        }
        return result;
    }

    public static boolean hasExternalStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static class ImageMetadata implements Comparable<ImageMetadata>, Serializable {
        public File file;
        public String mime;
        public Date dateTaken;
        public int groupIndex;

        public ImageMetadata(File file, String mime, Date dateTaken) {
            this.file = file;
            this.mime = mime;
            this.dateTaken = dateTaken;
        }

        public void setGroupIndex(int groupIndex) {
            this.groupIndex = groupIndex;
        }

        @Override
        public int compareTo(ImageMetadata another) {
            return dateTaken.compareTo(another.dateTaken);
        }

        public Date getBaseDate() {
            return DateUtil.getBaseDate(dateTaken, Calendar.DATE);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ImageMetadata that = (ImageMetadata) o;

            if (!file.equals(that.file)) return false;
            if (!mime.equals(that.mime)) return false;
            return dateTaken.equals(that.dateTaken);

        }

        @Override
        public int hashCode() {
            int result = file.hashCode();
            result = 31 * result + mime.hashCode();
            return result;
        }
    }
}
