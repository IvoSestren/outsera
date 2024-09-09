package br.com.sestren.outsera.controller;

import br.com.sestren.outsera.dto.AwardsDTO;
import br.com.sestren.outsera.service.WinnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class IndexController {

    private final WinnerService service;

    public IndexController(
            WinnerService service
    ) {
        this.service = service;
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<AwardsDTO> winners() {
        return ResponseEntity.ok(service.getWinners());
    }
}
