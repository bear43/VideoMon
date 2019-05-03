package service;

import lombok.Data;
import model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.VideoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Data
public class VideoService
{
    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository)
    {
        this.videoRepository = videoRepository;
    }

    public void createNewVideo(Video video) throws Exception
    {
        videoRepository.saveAndFlush(video);
    }

    public void createNewVideo(Video video, String originalFilename) throws Exception
    {
        video.setOriginalFilename(originalFilename);
        videoRepository.saveAndFlush(video);
    }

    public void deleteVideo(Video video) throws Exception
    {
        videoRepository.delete(video);
    }

    public List<Video> findByTitle(String title)
    {
        return videoRepository.findByTitle(title);
    }

    public List<Video> findByFloor(int floor)
    {
        return videoRepository.findByFloor(floor);
    }

    public List<Video> findByDate(LocalDate date)
    {
        return videoRepository.findByDate(date);
    }
}
