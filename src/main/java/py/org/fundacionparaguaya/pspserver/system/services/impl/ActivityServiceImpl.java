package py.org.fundacionparaguaya.pspserver.system.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.system.dtos.ActivityDTO;
import py.org.fundacionparaguaya.pspserver.system.entities.ActivityEntity;
import py.org.fundacionparaguaya.pspserver.system.mapper.ActivityMapper;
import py.org.fundacionparaguaya.pspserver.system.repositories.ActivityRepository;
import py.org.fundacionparaguaya.pspserver.system.services.ActivityService;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class ActivityServiceImpl implements ActivityService {

    private ActivityRepository activityRepository;

    private ActivityMapper activityMapper;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public ActivityDTO getActivityById(Long activityId){
        checkArgument(activityId > 0, "Argument was %s but expected non negative", activityId);

        return Optional.ofNullable(activityRepository.findOne(activityId))
                .map(activityMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Activity does not exist"));
    }

    @Override
    public ActivityDTO addActivity(ActivityDTO activityDTO) {
        ActivityEntity activity = new ActivityEntity();
        BeanUtils.copyProperties(activityDTO, activity);
        ActivityEntity newActivity = activityRepository.save(activity);
        return activityMapper.entityToDto(newActivity);
    }

    @Override
    public List<ActivityDTO> getAllActivities() {
        List<ActivityEntity> activities = activityRepository.findAll();
        return activityMapper.entityListToDtoList(activities);
    }



}
