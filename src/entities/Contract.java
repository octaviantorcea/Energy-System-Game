package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class that contains data about a contract and specific methods
 */
public final class Contract {
    private int consumerId;
    private int price;
    private int remainedContractMonths;
    /**
     * the consumer to whom the contract belongs
     */
    @JsonIgnore
    private Consumer consumer;
    /**
     * the distributor to whom the contract belongs
     */
    @JsonIgnore
    private Distributor distributor;
}
