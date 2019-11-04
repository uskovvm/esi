package ru.gptrans.esi.core;

import org.apache.activemq.artemis.jms.client.ActiveMQDestination;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQObjectMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.jms.*;

@Component(ExtSystemMqBean.NAME)
public class ExtSystemMqBean implements javax.jms.MessageListener{
    public static final String NAME = "esi_ExtSystemMqBean";


    public void produce(String request) throws JMSException {
        ConnectionFactory factory = new ActiveMQJMSConnectionFactory("tcp://localhost:61616");
        Destination destination = ActiveMQDestination.fromPrefixedName("queue://ext.system-1");
        Connection conn = factory.createConnection("admin","admin");
        try {
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(this);
            conn.start();

            ObjectMessage message = session.createObjectMessage();
            message.setObject(request);
            producer.send(message);

        } finally {
            conn.close();
        }

    }


    @Override
    public void onMessage(Message message) {
        ActiveMQObjectMessage receivedMessage = (ActiveMQObjectMessage)message;
        try {
            String response = (String) receivedMessage.getObject();
            response = response + " do else";
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = "http://localhost:8080/app/rest/api/clientmq";
            HttpEntity<String> request = new HttpEntity<>(response);
            String foo = restTemplate.postForObject(fooResourceUrl, request, String.class);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}