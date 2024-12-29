package com.ecommerce.ecommerce_remake.web.controller;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.dto.enums.ResponseCode;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.Response;
import com.ecommerce.ecommerce_remake.common.factory.CrudServiceFactory;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factory")
@Slf4j
public class CrudController {

    private final CrudServiceFactory serviceFactory;

    public CrudController(CrudServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @PostMapping("/{module}")
    public ResponseEntity<Model> save(@PathVariable Module module, @Valid @RequestBody String jsonRequest){
        CrudService service = getService(module);
        Response response = service.create(jsonRequest);
        log.info("CrudService.create() response code={}", response.getResponseCode());

        if(response.getResponseCode().equals(ResponseCode.RESP_SUCCESS)) {
            log.info("POST Response 201 - {}", response.getResponseDescription());
            Model models = (Model) response.getResponseObject();
            return new ResponseEntity<>(models, HttpStatus.CREATED);
        }else {
            log.error("POST Response: 500 - Internal server error (failed to execute request)");
            throw new RuntimeException("An unexpected error occurred while processing the request.");
        }
    }

    @GetMapping("/{module}")
    public ResponseEntity<GetAllResponse> getAll(@PathVariable Module module,
                                                 @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                 @RequestParam(defaultValue = "createdDate", required = false) String sortBy){
        CrudService service = getService(module);
        Response response = service.retrieveAll(pageNo, pageSize, sortBy);
        log.info("CrudService.retrieve() response code={}", response.getResponseCode());
        if (response.getResponseCode().equals(ResponseCode.RESP_SUCCESS)) {
            GetAllResponse getAllResponse;
            try {
                getAllResponse = (GetAllResponse) response.getResponseObject();
            } catch (ClassCastException e) {
                throw new RuntimeException(String.format("Failed to execute GET request, encountered exception with message: '%s'", e.getMessage()));
            }

            log.info("GET Response: 200 - Returning {} records", getAllResponse.getModels().size());
            return new ResponseEntity<>(getAllResponse, HttpStatus.OK);

        } else if (response.getResponseCode().equals(ResponseCode.RESP_NOT_FOUND)) {
            log.warn("GET Response: 200 - {}", "No data found");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            log.error("GET Response: 500 - Internal server error (failed to execute request)");
            throw new RuntimeException("An unexpected error occurred while processing the request.");
        }
    }

    @GetMapping("/{module}/{id}")
    public ResponseEntity<Model> getOne(@PathVariable Module module,@PathVariable String id){
        CrudService service = getService(module);
        Response response = service.retrieve(id);
        log.info("CrudService.retrieve() response code={}", response.getResponseCode());
        if(response.getResponseCode().equals(ResponseCode.RESP_SUCCESS)) {
            Model responseObject = (Model) response.getResponseObject();
            log.info("GET Response: {} - {}, returning {}",response.getResponseCode().getValue(), response.getResponseDescription(), responseObject);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } else if (response.getResponseCode().equals(ResponseCode.RESP_NOT_FOUND)){
            log.warn("GET Response: {} - {}",response.getResponseCode().getValue(), response.getResponseDescription());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else {
            log.error("GET Response: 500 - Internal server error (failed to execute request)");
            throw new RuntimeException("An unexpected error occurred while processing the request.");
        }
    }

    @PutMapping("/{module}")
    public ResponseEntity<String> update(@PathVariable Module module){
        CrudService service = getService(module);
        return new ResponseEntity<>(service.update(), HttpStatus.OK);
    }

    @DeleteMapping("/{module}")
    public ResponseEntity<String> delete(@PathVariable Module module){
        CrudService service = getService(module);
        return new ResponseEntity<>(service.delete(), HttpStatus.OK);
    }

    private CrudService getService(Module module) {
        return serviceFactory.getService(module);
    }
}
