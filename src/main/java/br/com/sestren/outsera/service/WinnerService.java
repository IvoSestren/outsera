package br.com.sestren.outsera.service;

import br.com.sestren.outsera.database.entity.ProducerMax;
import br.com.sestren.outsera.database.entity.ProducerMin;
import br.com.sestren.outsera.database.repository.ProducerRepository;
import br.com.sestren.outsera.dto.AwardsDTO;
import br.com.sestren.outsera.dto.ProducerIntervalDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WinnerService {

    private final ProducerRepository producerRepository;

    public WinnerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public AwardsDTO getWinners() {
        AwardsDTO awards = new AwardsDTO();

        awards.setMin(new ArrayList<>());
        awards.setMax(new ArrayList<>());

        awardsMin(awards);
        awardsMax(awards);

        return awards;
    }

    private void awardsMin(AwardsDTO awards) {
        int minInterval = Integer.MAX_VALUE;

        // Get all producer has more than one movie winner
        List<ProducerMin> producerMin = this.producerRepository.findMinInterval();

        // Encontrar primeiro a menor quantidade de anos dos produtores
        ProducerMin previousProducer = null;
        for (ProducerMin producer : producerMin) {
            if ((previousProducer == null) || (!previousProducer.getId().equals(producer.getId()))) {
                previousProducer = producer;
                continue;
            }

            if (producer.getYearMovie() - previousProducer.getYearMovie() < minInterval) {
                minInterval = producer.getYearMovie() - previousProducer.getYearMovie();
            }
            previousProducer = producer;
        }

        // Agora que ja tenho a menor quantidade de anos entre um premio e outro, vamos achar os produtores que tem esta quantidade de anos encontrada
        previousProducer = null;
        for (ProducerMin producer : producerMin) {
            if ((previousProducer == null) || (!previousProducer.getId().equals(producer.getId()))) {
                previousProducer = producer;
                continue;
            }

            if ((producer.getYearMovie() - previousProducer.getYearMovie()) == minInterval) {
                ProducerIntervalDTO interval = new ProducerIntervalDTO();
                interval.setProducer(producer.getName());
                interval.setInterval(minInterval);
                interval.setPreviousWin(previousProducer.getYearMovie());
                interval.setFollowingWin(producer.getYearMovie());
                awards.getMin().add(interval);
            }

            previousProducer = producer;
        }
    }

    private void awardsMax(AwardsDTO awards) {
        Integer maxInterval = Integer.MIN_VALUE;

        // Get all producer has more than one movie winner and difference from max and min year ordered by descendent interval
        List<ProducerMax> producerMax = this.producerRepository.findMaxInterval();

        for (ProducerMax producer : producerMax) {
            if (producer.getQtdYearsMovie() > maxInterval) {
                maxInterval = producer.getQtdYearsMovie();
                ProducerIntervalDTO interval = new ProducerIntervalDTO();
                interval.setProducer(producer.getName());
                interval.setInterval(maxInterval);
                interval.setPreviousWin(producer.getYearFirstMovie());
                interval.setFollowingWin(producer.getYearLastMovie());
                awards.getMax().add(interval);
            }
        }
    }
}
