package com.hlxx.climber.dataTables;

import com.microsoft.windowsazure.mobileservices.table.DateTimeOffset;

public class Comments {

    @com.google.gson.annotations.SerializedName("id")
    private String mId;
    public String getId() { return mId; }
    public final void setId(String id) { mId = id; }

    @com.google.gson.annotations.SerializedName("text")
    private String mText;
    public String getText() { return mText; }
    public final void setText(String text) { mText = text; }

    @com.google.gson.annotations.SerializedName("by")
    private String mBy;
    public String getBy() { return mBy; }
    public final void setBy(String by) { mText = by; }

    @com.google.gson.annotations.SerializedName("isThumb")
    private Boolean mIsThumb;
    public Boolean isThumb() { return mIsThumb; }
    public final void setThumb(Boolean isThumb) { mIsThumb = isThumb; }

    public Comments() { }

    public Comments(String id, String text) {
        this.setId(id);
        this.setText(text);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Comments && ((Comments) o).mId == mId;
    }

    @Override
    public String toString() {
        return getText();
    }
}
