package ru.gptrans.esi.service;

import org.springframework.stereotype.Service;
import ru.gptrans.esi.core.ClientSystemMqBean;

import javax.inject.Inject;
import javax.jms.JMSException;

@Service(ClientSystemMqService.NAME)
public class ClientSystemMqServiceBean implements ClientSystemMqService {

    @Inject
    ClientSystemMqBean clientSystemMqBean;


    @Override
    public boolean produce(String message) {
        try {
            clientSystemMqBean.produce(message);
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }
}