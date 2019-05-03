package model;

import lombok.Getter;

public enum VideoFormat
{
    AVI("avi"),
    WMV("wmv"),
    MPEG("mpeg"),
    MOV("mov"),
    MKV("mkv"),
    THREEGP("3gp"),
    FLV("flv"),
    SWF("swf"),
    MP4("mp4"),
    M4V("m4v"),
    MPG("mpg");

    @Getter
    private String title;

    VideoFormat(String title)
    {
        this.title = title;
    }

    public static boolean isVideoFormat(String format)
    {
        format = format.toLowerCase();
        for(VideoFormat fmt : VideoFormat.values())
        {
            if(format.equals(fmt.getTitle())) return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        return title;
    }
}
