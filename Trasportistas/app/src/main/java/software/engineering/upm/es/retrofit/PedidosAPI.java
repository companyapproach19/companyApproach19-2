package software.engineering.upm.es.retrofit;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import software.engineering.upm.es.Pedido;

public interface PedidosAPI {
    @GET("")
    Call<JSONArray> getPedidos();

    @GET("pedidos/{id}")
    Call<JSONArray> getPedidos(@Path("id") String id);

    @POST("pedidos")
    Call<JSONArray> insertPedido(@Body Pedido pedido);

    @PUT("pedidos")
    Call<JSONArray> updatePedido(@Body Pedido pedido);

    @DELETE("pedidos/{id}")
    Call<JSONArray> deletePedido(@Path("id") String id);

    //@POST("{user}/connect/{pass}")
    //Call<RespuestaAPI> getConnection(@Path("user") String user, @Path("pass") String pass);
}
