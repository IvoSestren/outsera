package br.com.sestren.outsera.controller;

import br.com.sestren.outsera.dto.AwardsDTO;
import br.com.sestren.outsera.service.WinnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IndexControllerTest {

    @Autowired
    private WinnerService winnerService;

    @Test
    void winners() throws Exception {
        AwardsDTO awards = this.winnerService.getWinners();
        Assertions.assertNotNull(awards);
        Assertions.assertNotNull(awards.getMin());
        Assertions.assertNotNull(awards.getMax());
    }
}