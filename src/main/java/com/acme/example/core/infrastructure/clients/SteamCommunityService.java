package com.acme.example.core.infrastructure.clients;

import com.acme.example.core.infrastructure.clients.dtos.games_by_name.GamesByNameResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@RegisterRestClient(configKey = "steam-community")
public interface SteamCommunityService {

    @GET
    @Path("/{searchString}")
    List<GamesByNameResponse> getGameByName(@PathParam("searchString") String name);

}