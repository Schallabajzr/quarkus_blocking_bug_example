package com.acme.example.ports.grpc;

import com.acme.example.GameBySearchDto;
import com.acme.example.GameService;
import com.acme.example.GetGamesByNameRequest;
import com.acme.example.GetGamesByNameResponse;
import com.acme.example.StoreEnum;
import com.acme.example.core.domain.GameFetch;
import com.acme.example.core.infrastructure.clients.dtos.games_by_name.GamesByNameResponse;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import java.util.List;


@GrpcService
public class SteamService implements GameService {

    @Inject
    GameFetch gameFetch;

    @Blocking
    public Uni<GetGamesByNameResponse> getGamesByName(GetGamesByNameRequest request) {
        //this is okay
        try {
            Thread.sleep(1000);  // block for 1 second
        } catch (InterruptedException e) {
            // handle exception
        }

        //but the rest client fails even though it works in com.acme.example.ports.grpc.SteamServiceNonMutiny.GetGamesByName
        List<GamesByNameResponse> gameBySearch = gameFetch.getGameBySearch(request.getSearchString());

        return Uni.createFrom().item(() ->
                GetGamesByNameResponse.newBuilder()
                        .addAllGames(() -> gameBySearch.stream()
                                .map(gbsr ->
                                        GameBySearchDto.newBuilder()
                                                .setAppid(gbsr.appid())
                                                .setName(gbsr.name())
                                                .setStoreEnum(StoreEnum.STEAM)
                                                .build()
                                ).iterator())
                        .build()
        );
    }
}

