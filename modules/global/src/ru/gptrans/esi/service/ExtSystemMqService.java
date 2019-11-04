package ru.gptrans.esi.service;

import org.apache.activemq.artemis.jms.client.ActiveMQObjectMessage;

public interface ExtSystemMqService {
    String NAME = "esi_ExtSystemMqService";

    public void produce(String request);
}