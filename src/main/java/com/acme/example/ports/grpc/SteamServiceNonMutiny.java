package com.acme.example.ports.grpc;

import com.acme.example.GameBySearchDto;
import com.acme.example.GetGamesByNameRequest;
import com.acme.example.GetGamesByNameResponse;
import com.acme.example.StoreEnum;
import com.acme.example.core.domain.GameFetch;
import com.acme.example.core.infrastructure.clients.dtos.games_by_name.GamesByNameResponse;
import io.grpc.stub.StreamObserver;
import io.smallrye.common.annotation.Blocking;

import javax.inject.Inject;
import java.util.List;

//@GrpcService
public class SteamServiceNonMutiny {
    //extends GameServiceGrpc.GameServiceImplBase
    @Inject
    GameFetch gameFetch;

    @Blocking
    public void getGamesByName(GetGamesByNameRequest request, StreamObserver<GetGamesByNameResponse> responseObserver) {
        List<GamesByNameResponse> gameBySearch = gameFetch.getGameBySearch(request.getSearchString());

        responseObserver.onNext(GetGamesByNameResponse.newBuilder()
                .addAllGames(() -> gameBySearch.stream()
                        .map(gbsr ->
                                GameBySearchDto.newBuilder()
                                        .setAppid(gbsr.appid())
                                        .setName(gbsr.name())
                                        .setStoreEnum(StoreEnum.STEAM)
                                        .build()
                        ).iterator())
                .build());
        responseObserver.onCompleted();
    }
}
