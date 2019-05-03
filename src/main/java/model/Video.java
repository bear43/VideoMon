package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.UUID;

@Entity
@Data
public class Video
{
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private int floor;

    private LocalDate date;

    private String path;

    private String originalFilename;

    public Video(String title, int floor, LocalDate date, String path, String originalFilename)
    {
        this.title = title;
        this.floor = floor;
        this.date = date;
        this.path = path;
        this.originalFilename = originalFilename;
    }

    public Video(String title, int floor, LocalDate date, String path)
    {
        this(title, floor, date, path, "");
    }

    public Video(String title, int floor, String path)
    {
        this(title, floor, LocalDate.now(), path);
    }

    public Video(String title, int floor)
    {
        this(title, floor, UUID.randomUUID().toString()+LocalDate.now().getLong(ChronoField.INSTANT_SECONDS));
    }

    protected Video()
    {
        this("", 0, LocalDate.now(), UUID.randomUUID().toString(), "");
    }

    public void generateUUIDPlusMsFilename()
    {
        path = UUID.randomUUID().toString();
    }

    public static boolean isVideoFormat(String filename)
    {
        int lastDotIndex = filename.lastIndexOf(".");
        String format = filename.substring(lastDotIndex+1);
        return VideoFormat.isVideoFormat(format);
    }

    public String toString()
    {
        return String.format("%d | %s | %s", floor, title, date.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
