/*
 * Copyright (c) 2018.
 */

package weatherapp.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import weatherapp.domain.dbmodel.RealTimeWeatherUpdate;
import weatherapp.domain.restmodel.RealTimeWeatherUpdateRest;
import weatherapp.services.RealTimeWeatherUpdateService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequestMapping(value = "/realtimeWeatherUpdate", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class RealTimeWeatherUpdateController {
    RealTimeWeatherUpdateService realTimeWeatherUpdateService;
    private Logger logger = LoggerFactory.getLogger(RealTimeWeatherUpdateController.class);

    public RealTimeWeatherUpdateController(RealTimeWeatherUpdateService realTimeWeatherUpdateService) {
        this.realTimeWeatherUpdateService = realTimeWeatherUpdateService;
    }

    @PostMapping(value = "/post-weather-update")
    @ResponseStatus(HttpStatus.CREATED)
    public RealTimeWeatherUpdateRest saveRealTimeWeatherUpdate(@RequestBody RealTimeWeatherUpdateRest realTimeWeatherUpdateRest) {
        RealTimeWeatherUpdate realTimeWeatherUpdate = realTimeWeatherUpdateRest.toRealTimeWeatherUpdate();
        RealTimeWeatherUpdate savedRealTimeWeatherUpdate = realTimeWeatherUpdateService.saveRealTimeWeatherUpdate(realTimeWeatherUpdate);
        return RealTimeWeatherUpdateRest.fromReadTimeWeatherUpdate(savedRealTimeWeatherUpdate);
    }

    @GetMapping(value = "/get-weather-update/{city}", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<RealTimeWeatherUpdateRest> getRealTimeWeatherUpdate(@PathVariable(value = "city") String city) {
        if (Objects.isNull(city) || city.isBlank() || city.isEmpty()) {
            throw new RuntimeException("City not sent.");
        }
        List<RealTimeWeatherUpdate> realTimeWeatherUpdate = realTimeWeatherUpdateService.getRealTimeWeatherUpdate(city);
        List<RealTimeWeatherUpdateRest> collect = realTimeWeatherUpdate.stream().map(RealTimeWeatherUpdateRest::fromReadTimeWeatherUpdate).sorted(Comparator.comparing(RealTimeWeatherUpdateRest::getZonedDateTimeRest)).collect(Collectors.toList());
        return collect;
    }
}
