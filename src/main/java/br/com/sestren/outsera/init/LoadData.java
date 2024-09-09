package br.com.sestren.outsera.init;

import br.com.sestren.outsera.service.LoadDataService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoadData {
    private final LoadDataService service;

    @Autowired
    public LoadData(LoadDataService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() throws IOException {
        this.service.loadData();
    }
}
