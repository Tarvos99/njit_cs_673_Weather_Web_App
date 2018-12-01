/*
 * Copyright (c) 2018.
 */

package weatherapp.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/secured-endpoints", consumes = MediaType.ALL_VALUE,
        produces = MediaType.ALL_VALUE)
@RestController
public class SecuredEndpoints {

    @GetMapping(value = "/sample-secured", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getSampleAutenticatedResponse() {
        return "Hello, your token is valid.";
    }
}
