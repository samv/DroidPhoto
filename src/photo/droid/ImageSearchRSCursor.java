package photo.droid;

import java.util.ArrayList;

import photo.droid.ImageSearchRSCursorPage;

public class ImageSearchRSCursor {

    public int currentPageIndex;
    public ArrayList<ImageSearchRSCursorPage> pages;

    public ImageSearchRSCursor() {
        pages = new ArrayList<ImageSearchRSCursorPage>();
    }

    public ArrayList<ImageSearchRSCursorPage> addImageSearchRSCursorPage
        (ImageSearchRSCursorPage irp) {
        pages.add(irp);
        return pages;
    }

    public int next_start() {
        if ((pages.size() == currentPageIndex) ||
            ((currentPageIndex + 1) == pages.size())) {
            return -1;
        }
        else {
            return Integer.parseInt(pages.get(currentPageIndex + 1).start);
        }
    }
}
