package repository;

import model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>
{
    List<Video> findByTitle(String title);
    List<Video> findByFloor(int floor);
    List<Video> findByDate(LocalDate date);
}
