package photo.droid;

import java.util.ArrayList;

import photo.droid.ImageSearchRSCursorPage;

public class ImageSearchRSCursor {

    public ArrayList<ImageSearchRSCursorPage> pages;

    public ImageSearchRSCursor() {
        pages = new ArrayList<ImageSearchRSCursorPage>();
    }

    public ArrayList<ImageSearchRSCursorPage> addImageSearchRSCursorPage
        (ImageSearchRSCursorPage irp) {
        pages.add(irp);
        return pages;
    }
}
