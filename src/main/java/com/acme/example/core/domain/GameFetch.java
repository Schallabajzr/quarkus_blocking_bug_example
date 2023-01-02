package com.acme.example.core.domain;

import com.acme.example.core.infrastructure.clients.SteamCommunityService;
import com.acme.example.core.infrastructure.clients.dtos.games_by_name.GamesByNameResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.arc.Arc;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class GameFetch {

    @RestClient
    SteamCommunityService steamCommunityService;

    @PostConstruct
    void init() {
        ObjectMapper objectMapper = Arc.container().instance(ObjectMapper.class).get();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    public List<GamesByNameResponse> getGameBySearch(String searchString) {
        return steamCommunityService.getGameByName(searchString);
    }
}
