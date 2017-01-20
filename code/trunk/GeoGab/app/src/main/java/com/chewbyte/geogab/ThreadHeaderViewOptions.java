package com.chewbyte.geogab;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.widget.TextView;

import com.chewbyte.geogab.common.Category;
import com.chewbyte.geogab.common.User;
import com.mapbox.mapboxsdk.annotations.BaseMarkerViewOptions;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chris on 11/09/2016.
 */
public class ThreadHeaderViewOptions extends BaseMarkerViewOptions<ThreadHeaderView, ThreadHeaderViewOptions> {

    String threadTitle;
    String snippet;
    int profileImage;
    User owner;
    Category category;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public Category getCategory() {
        return category;
    }

    public User getOwner() {
        return owner;
    }

    public ThreadHeaderViewOptions() {

    }

    public ThreadHeaderViewOptions(Parcel in) {
        position((LatLng) in.readParcelable(LatLng.class.getClassLoader()));
        snippet(in.readString());
        title(in.readString());
        flat(in.readByte() != 0);
        anchor(in.readFloat(), in.readFloat());
        selected = in.readByte() != 0;
        rotation(in.readFloat());
        if (in.readByte() != 0) {
            // this means we have an icon
            String iconId = in.readString();
            Bitmap iconBitmap = in.readParcelable(Bitmap.class.getClassLoader());
            Icon icon = IconFactory.recreate(iconId, iconBitmap);
            icon(icon);
        }
    }

    @Override
    public ThreadHeaderViewOptions getThis() {
        return this;
    }

    @Override
    public ThreadHeaderView getMarker() {
        return new ThreadHeaderView(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(getPosition(), flags);
        out.writeString(getSnippet());
        out.writeString(getTitle());
        out.writeByte((byte) (isFlat() ? 1 : 0));
        out.writeFloat(getAnchorU());
        out.writeFloat(getAnchorV());
        out.writeFloat(getInfoWindowAnchorU());
        out.writeFloat(getInfoWindowAnchorV());
        out.writeByte((byte) (selected ? 1 : 0));
        out.writeFloat(getRotation());
        Icon icon = getIcon();
        out.writeByte((byte) (icon != null ? 1 : 0));
        if (icon != null) {
            out.writeString(getIcon().getId());
            out.writeParcelable(getIcon().getBitmap(), flags);
        }
    }

    public static final Creator<ThreadHeaderViewOptions> CREATOR =
            new Creator<ThreadHeaderViewOptions>() {
                public ThreadHeaderViewOptions createFromParcel(Parcel in) {
                    return new ThreadHeaderViewOptions(in);
                }

                public ThreadHeaderViewOptions[] newArray(int size) {
                    return new ThreadHeaderViewOptions[size];
                }
            };
}
