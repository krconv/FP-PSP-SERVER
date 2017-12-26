package py.org.fundacionparaguaya.pspserver.web.rest;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.common.exceptions.NotFoundException;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.Snapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicators;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigovillalba on 10/5/17.
 */
@RestController
@RequestMapping(value = "/api/v1/snapshots")
@io.swagger.annotations.Api(description = "The snapshots resource returns snapshots for various inputs. Snapshots are instances of a filled out survey.")
public class SnapshotController {

    private final SnapshotService snapshotService;

    public SnapshotController(SnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @io.swagger.annotations.ApiOperation(value = "Retrieves all snapshots for a  survery", notes = "A `GET` request with a survey parameter will return a list of snapshots for the that survey.", response = List.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "List of available surveys", response = Snapshot.class, responseContainer = "List") })
    public ResponseEntity getSnapshots(@RequestParam("survey_id") Long surveyId,
            @RequestParam(value = "family_id", required = false) Long familiyId) {
        List<Snapshot> snapshots = snapshotService.find(surveyId, familiyId);
        return ResponseEntity.ok(snapshots);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path="/filter")
    @io.swagger.annotations.ApiOperation(value = "Retrieves a filtered set of snapshots", notes = "A `GET` request with filter parameters will return a list of snapshots matching that criteria.", response = List.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Snapshots matching filter criteria.", response = Snapshot.class, responseContainer="List")
    })
    public ResponseEntity filterSnapshots(@RequestParam(value = "indicators", required = false) String indicators,
                                          @RequestParam(value = "organizationId", required = false) Long organizationId,
                                          @RequestParam(value = "applicationId", required = false) Long applicationId,
                                          @RequestParam(value = "countryId", required = false) Long countryId,
                                          @RequestParam(value = "cityId", required = false) Long cityId){
        Map<String, List<String>> indicatorsMap = new HashMap<String, List<String>>();
        if (indicators != null) {
            indicatorsMap = new Gson().fromJson(indicators, new TypeToken<Map<String, List<String>>>(){}.getType());
        }
        List<Snapshot> snapshots = snapshotService.filter(indicatorsMap, organizationId, applicationId, countryId, cityId);

        return ResponseEntity.ok(snapshots);
    }

    @GetMapping(produces = "text/csv", path="/filter/csv")
    @io.swagger.annotations.ApiOperation(value = "Retrieves a filtered set of snapshots", notes = "A `GET` request with filter parameters will return a list of snapshots matching that criteria.", response = List.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Snapshots matching filter criteria.", response = Snapshot.class)
    })
    public String filterSnapshotsCSV(@RequestParam(value = "indicators", required = false) String indicators,
                                     @RequestParam(value = "organizationId", required = false) Long organizationId,
                                     @RequestParam(value = "applicationId", required = false) Long applicationId,
                                     @RequestParam(value = "countryId", required = false) Long countryId,
                                     @RequestParam(value = "cityId", required = false) Long cityId) {
        Map<String, List<String>> indicatorsMap = new HashMap<String, List<String>>();
        if (indicators != null) {
            indicatorsMap = new Gson().fromJson(indicators, new TypeToken<Map<String, List<String>>>(){}.getType());
        }

        return snapshotService.filterCSV(indicatorsMap, organizationId, applicationId, countryId, cityId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @io.swagger.annotations.ApiOperation(value = "Create Snapshot", notes = "A `POST` request will create new snapshot for a particular survey.", response = Snapshot.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 201, message = "The created snapshot", response = Snapshot.class) })
    public ResponseEntity addSnapshot(
            @ApiParam(value = "The snapshot", required = true) @RequestBody NewSnapshot snapshot)
            throws NotFoundException, URISyntaxException {
        Snapshot data = snapshotService.addSurveySnapshot(snapshot);
        URI surveyLocation = new URI("/snapshots/" + data.getSurveyId());
        return ResponseEntity.created(surveyLocation).body(data);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/indicators")
    @io.swagger.annotations.ApiOperation(value = "Retrieves all snapshots indicators for a  survery", notes = "A `GET` request with a survey parameter will return a list of snapshots indicators for the that survey.", response = List.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "List of available surveys", response = SnapshotIndicators.class) })
    public ResponseEntity getSnapshotIndicators(@RequestParam("snapshot_id") Long snapshotId) {

        SnapshotIndicators snapshot = snapshotService.getSnapshotIndicators(snapshotId);
        return ResponseEntity.ok(snapshot);
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path="/family")
    @io.swagger.annotations.ApiOperation(value = "Retrieves all snapshots indicators related with a family", 
    	notes = "A `GET` request with a survey parameter will return a list of snapshots indicators for the that family.", response = List.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "List of available snapshots", response = Snapshot.class, responseContainer="List")
    })
    public ResponseEntity<List<SnapshotIndicators>> getSnapshotsIndicatorsByFamily(@RequestParam(value = "family_id", required = false) Long familiyId) {
        List<SnapshotIndicators> snapshots = snapshotService.getSnapshotIndicatorsByFamily(familiyId);
        return ResponseEntity.ok(snapshots);
    }
    

}
