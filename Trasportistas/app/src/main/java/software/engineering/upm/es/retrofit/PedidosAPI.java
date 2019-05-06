package software.engineering.upm.es.retrofit;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import software.engineering.upm.es.objetos.parceables.Pedido;

public interface PedidosAPI {
    @GET("https://beer-company2019.herokuapp.com/damePedidosTransportistas")
    Call<JSONArray> getPedidos();

    @GET("{id}")
    Call<JSONArray> getPedidos(@Path("id") String id);

    @POST("ejemplo")
    Call<JSONArray> insertPedido(@Body Pedido pedido);

    @PUT("ejemplo")
    Call<JSONArray> updatePedido(@Body Pedido pedido);

    @DELETE("ejemplo/{id}")
    Call<JSONArray> deletePedido(@Path("id") String id);

    //@POST("{user}/connect/{pass}")
    //Call<RespuestaAPI> getConnection(@Path("user") String user, @Path("pass") String pass);
}
