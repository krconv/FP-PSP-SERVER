package py.org.fundacionparaguaya.pspserver.web.rest;

import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.common.exceptions.NotFoundException;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.Snapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotFilterDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicators;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;

import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE, 
        path = "/filter"
    )
    @io.swagger.annotations.ApiOperation(
        value = "Retrieves a filtered set of snapshots",
        notes = "A `GET` request with filter parameters will return a"
                + " list of snapshots matching that criteria.",
        response = List.class,
        tags = {}
    )
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(
                code = 200,
                message = "Snapshots matching filter criteria.",
                response = Snapshot.class,
                responseContainer = "List"
            ) 
        })
    /**
     * Filters snapshots by the given criteria.
     * @param indicators A JSON formated string with the indicators to look for, 
     *      along with their possible, or null to exclude this parameter.
     * @param organizationId The ID of the organization to look for, or null
     *      to exclude this parameter.
     * @param applicationId The ID of the application to look for, or null to
     *      exclude this parameter.
     * @param countryId The ID of the country to look for, or null to exclude
     *      this parameter.
     * @param cityId The ID of the city to look for, or null to exclude this
     *      parameter.
     * @return The snapshots which match all of the included criteria.
     */
    public ResponseEntity<List<Snapshot>> filterSnapshots(
            @RequestParam(value = "indicators", required = false) 
                final String indicators,
            @RequestParam(value = "organizationId", required = false) 
                final Long organizationId,
            @RequestParam(value = "applicationId", required = false) 
                final Long applicationId,
            @RequestParam(value = "countryId", required = false) 
                final Long countryId,
            @RequestParam(value = "cityId", required = false) 
                final Long cityId
        ) {
        List<Snapshot> snapshots = snapshotService.filter(
            new SnapshotFilterDTO(
                indicators, organizationId, applicationId, countryId, cityId
            )
        );
        return ResponseEntity.ok(snapshots);
    }

    @GetMapping(produces = "text/csv", path = "/filter/csv")

    @io.swagger.annotations.ApiOperation(
        value = "Retrieves a filtered set of snapshots in CSV format",
        notes = "A `GET` request with filter parameters will return a"
                + " list of snapshots matching that criteria.",
        response = List.class,
        tags = {}
    )
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(
                code = 200,
                message = "Snapshots matching filter criteria.",
                response = Snapshot.class,
                responseContainer = "List"
            ) 
        })
    /**
     * Filters snapshots by the given criteria and returns them in CSV format.
     * @param indicators A JSON formated string with the indicators to look for, 
     *      along with their possible, or null to exclude this parameter.
     * @param organizationId The ID of the organization to look for, or null
     *      to exclude this parameter.
     * @param applicationId The ID of the application to look for, or null to
     *      exclude this parameter.
     * @param countryId The ID of the country to look for, or null to exclude
     *      this parameter.
     * @param cityId The ID of the city to look for, or null to exclude this
     *      parameter.
     * @return The snapshots which match all of the included criteria in CSV
     *      format.
     */
    public String filterSnapshotsCSV(
        @RequestParam(value = "indicators", required = false) 
            final String indicators,
        @RequestParam(value = "organizationId", required = false) 
            final Long organizationId,
        @RequestParam(value = "applicationId", required = false) 
            final Long applicationId,
        @RequestParam(value = "countryId", required = false) 
            final Long countryId,
        @RequestParam(value = "cityId", required = false) 
            final Long cityId
    ) {
        List<Snapshot> snapshots = snapshotService.filter(
            new SnapshotFilterDTO(
                indicators, organizationId, applicationId, countryId, cityId
            )
        );
        List<String> headers = new ArrayList<String>();
        headers.add("id");
        headers.add("createdAt");
        List<Map<String, String>> rows = new LinkedList<Map<String, String>>();

        for (Snapshot snapshot : snapshots) {
            Map<String, String> row = new HashMap<String, String>();
            row.put("id", snapshot.getSurveyId().toString());
            row.put("createdAt", snapshot.getCreatedAt());

            row.putAll(toRow(headers, snapshot.getPersonalSurveyData()));
            row.putAll(toRow(headers, snapshot.getEconomicSurveyData()));
            row.putAll(toRow(headers, snapshot.getIndicatorSurveyData()));

            rows.add(row);
        }

        StringWriter buffer = new StringWriter();
        headers.stream().forEachOrdered((h) -> buffer.write(h + ","));
        buffer.append('\n');

        for (Map<String, String> row : rows) {
            headers.stream().forEachOrdered(
                (h) -> buffer.write(row.getOrDefault(h, "") + ",")
            );
            buffer.append('\n');
        }

        return buffer.toString();
    }

    private Map<String, String> toRow(List<String> headers, SurveyData data) {
        if (data == null) {
            return new HashMap<String, String>();
        }

        Map<String, String> row = new HashMap<String, String>();
        Set<String> cols = data.keySet();
        for (String col : cols) {
            if (!headers.contains(col)) {
                headers.add(col);
            }
            Object value = data.get(col);
            row.put(col, value != null ? StringEscapeUtils.escapeCsv(value.toString()) : "");
        }
        return row;
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
