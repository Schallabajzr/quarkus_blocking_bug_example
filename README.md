# An example of BlockingNotAllowedException with mutiny style GrpcService

To call the endpoint I was using built in http client with bundled gRPC extensions in Intellij Ultimate

```http request
GRPC localhost:9000/game.search.GameService/GetGamesByName

{
  "search_string" : "Witcher 3"
}
```

## Broken service with Mutiny

com.acme.example.ports.grpc.SteamService.getGamesByName 

that fails with
```text
[io.qua.grp.stu.ServerCalls] (vert.x-eventloop-thread-0) gRPC service threw an exception other than StatusRuntimeException: org.jboss.resteasy.reactive.common.core.BlockingNotAllowedException
	at org.jboss.resteasy.reactive.client.impl.InvocationBuilderImpl.unwrap(InvocationBuilderImpl.java:210)
	at org.jboss.resteasy.reactive.client.impl.InvocationBuilderImpl.method(InvocationBuilderImpl.java:329)
	at com.acme.example.core.infrastructure.clients.SteamCommunityService$$QuarkusRestClientInterface.getGameByName(Unknown Source)
	at com.acme.example.core.infrastructure.clients.SteamCommunityService$$CDIWrapper.getGameByName(Unknown Source)
	at com.acme.example.core.domain.GameFetch.getGameBySearch(GameFetch.java:27)
	at com.acme.example.core.domain.GameFetch_ClientProxy.getGameBySearch(Unknown Source)
	at com.acme.example.ports.grpc.SteamService.getGamesByName(SteamService.java:34)
	at com.acme.example.GameServiceBean.getGamesByName(GameServiceBean.java:19)
	at io.quarkus.grpc.stubs.ServerCalls.oneToOne(ServerCalls.java:30)
	at com.acme.example.MutinyGameServiceGrpc$MethodHandlers.invoke(MutinyGameServiceGrpc.java:88)
	at io.grpc.stub.ServerCalls$UnaryServerCallHandler$UnaryServerCallListener.onHalfClose(ServerCalls.java:182)
	at io.grpc.PartialForwardingServerCallListener.onHalfClose(PartialForwardingServerCallListener.java:35)
	at io.grpc.ForwardingServerCallListener.onHalfClose(ForwardingServerCallListener.java:23)
	at io.grpc.ForwardingServerCallListener$SimpleForwardingServerCallListener.onHalfClose(ForwardingServerCallListener.java:40)
	at io.quarkus.grpc.runtime.supports.context.GrpcRequestContextGrpcInterceptor$1.onHalfClose(GrpcRequestContextGrpcInterceptor.java:83)
	at io.quarkus.grpc.runtime.supports.context.GrpcDuplicatedContextGrpcInterceptor$ListenedOnDuplicatedContext$1.handle(GrpcDuplicatedContextGrpcInterceptor.java:119)
	at io.quarkus.grpc.runtime.supports.context.GrpcDuplicatedContextGrpcInterceptor$ListenedOnDuplicatedContext$1.handle(GrpcDuplicatedContextGrpcInterceptor.java:111)
```

## Working example by extending GameServiceGrpc.GameServiceImplBase
*Note: uncomment code to switch the implementation*

com.acme.example.ports.grpc.SteamServiceNonMutiny.getGamesByName

that succeeds with

```json
{
  "games": [{
    "name": "The Witcher 3: Wild Hunt",
    "appid": "292030"
  }, {
    "name": "The Witcher 3: Wild Hunt - Game of the Year Edition",
    "appid": "499450"
  }]
}

```

