package controller;

import model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.StorageService;
import service.UserService;
import service.VideoService;

import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController
{
    private final UserService userService;

    private final VideoService videoService;

    private static StorageService storageService;

    static
    {
        try
        {
            storageService = new StorageService("/home/bear/prod");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Autowired
    public MainController(UserService userService, VideoService videoService)
    {
        this.userService = userService;
        this.videoService = videoService;
    }

    @PostMapping("/")
    public String main()
    {
        return main(null);
    }

    @GetMapping("/")
    public String main(Map<String, Object> model)
    {
        model.put("admin", userService.getCurrentUser().isAdmin());
        model.put("user", userService.getCurrentUser());
        return "main";
    }

    @PostMapping("/get_all")
    @ResponseBody
    public List<Video> get_all()
    {
        return videoService.getVideoRepository().findAll();
    }

    @PostMapping("/get_all_by_floor")
    @ResponseBody
    public Object get_all_by_floor(@RequestBody  Map<String, Integer> param)
    {
        try
        {
            int floor = param.get("floor");
            return videoService.findByFloor(floor);
        }
        catch (Exception ex)
        {
            return ex.getMessage();
        }
    }

    @PostMapping("/get_total_pages")
    @ResponseBody
    public int get_total_pages(@RequestBody Map<String, Integer> param)
    {
        return get_total_count()/param.get("limit");
    }

    @PostMapping("/get_total_count")
    @ResponseBody
    public int get_total_count()
    {
        return (int)videoService.getVideoRepository().count();
    }

    @PostMapping("/get_paged_list")
    @ResponseBody
    public List<Video> get_paged_list(@RequestBody  Map<String, Integer> params)
    {
        Page<Video> videos = videoService.getVideoRepository().
                findAll(PageRequest.of(params.get("page"), params.get("limit")));
        return videos.getContent();
    }

    @GetMapping("/add_video")
    public String add_video(RedirectAttributes attr)
    {
        if(userService.getCurrentUser().isAdmin())
            return "add_video";
        else
        {
            attr.addFlashAttribute("message", "Отказано в доступе");
            return "redirect:/";
        }
    }

    @PostMapping("/add_video")
    public String add_video(Video video, MultipartFile file, Map<String, Object> model)
    {
        video.setOriginalFilename(file.getOriginalFilename());
        try
        {
            if(!Video.isVideoFormat(video.getOriginalFilename()))
            {
                model.put("message", "Неверный формат файла!");
                return "add_video";
            }
            file.transferTo(storageService.createFileInstance(video.getPath()));
            videoService.createNewVideo(video);
        }
        catch (Exception ex)
        {
            model.put("message", "Ошибка: " + ex.getMessage());
            return "add_video";
        }
        model.put("message", "Видео успешно загружено!");
        return "add_video";
    }

    @PostMapping("/get_video")
    public ResponseEntity<Resource> get_video(long id)
    {
        Optional<Video> opt = videoService.getVideoRepository().findById(id);
        if(!opt.isPresent())
        {
            return null;
        }
        Video video = opt.get();
        try
        {
            if(video.getFloor() == userService.getCurrentUser().getFloor() || userService.getCurrentUser().isAdmin())
                return storageService.getHttpFile(video.getPath());
            else
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
