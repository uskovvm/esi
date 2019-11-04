package ru.gptrans.esi.service;

import org.springframework.stereotype.Service;
import ru.gptrans.esi.core.ExtSystemMqBean;

import javax.inject.Inject;
import javax.jms.JMSException;

@Service(ExtSystemMqService.NAME)
public class ExtSystemMqServiceBean implements ExtSystemMqService {

    @Inject
    ExtSystemMqBean extSystemMqBean;


    @Override
    public void produce(String request) {
        try {
            extSystemMqBean.produce(request);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}