package ru.gptrans.esi.service;

public interface ClientSystemMqService {
    String NAME = "esi_ClientSystemMqService";

    boolean produce(String message);
}