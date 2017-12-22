package py.org.fundacionparaguaya.pspserver.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.system.dtos.ActivityDTO;
import py.org.fundacionparaguaya.pspserver.system.services.ActivityService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/activities")
public class ActivityController {

    private ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping()
    public ResponseEntity<ActivityDTO> addActivity(@Valid @RequestBody ActivityDTO activityDTO) throws URISyntaxException {
        ActivityDTO result = activityService.addActivity(activityDTO);
        return ResponseEntity.created(new URI("/api/v1/activities" + result.getActivityId()))
                .body(result);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable("activityId") Long activityId) {
        ActivityDTO dto = activityService.getActivityById(activityId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<ActivityDTO> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }
}
