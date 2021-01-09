package strategies;

import entities.Producer;

import java.util.List;

public interface ChooseProducerStrategy {
    List<Producer> getNecessaryProducers();
}
