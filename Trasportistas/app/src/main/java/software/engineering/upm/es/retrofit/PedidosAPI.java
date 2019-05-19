package software.engineering.upm.es.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import software.engineering.upm.es.objetos.parceables.Pedido;

public interface PedidosAPI {
    @POST("https://beer-company2019.herokuapp.com/damePedidosTransportistaListo/")
    Call<Object> getPedidosL();

    @POST("https://beer-company2019.herokuapp.com/damePedidosTransportistaRecogido/")
    Call<Object> getPedidosR();

    @POST("https://beer-company2019.herokuapp.com/damePedidosTransportistaEntregado/")
    Call<Object> getPedidosE();

    @GET("{id}")
    Call<JSONArray> getPedidos(@Path("id") String id);


    @POST("ejemplo")
    Call<JSONArray> insertPedido(@Body Pedido pedido);

    @FormUrlEncoded
    @PUT("https://beer-company2019.herokuapp.com/recogidaOrden")
    Call<Object> updatePedidoR( @Field("json") JsonObject JsonObject);

    @FormUrlEncoded
    @PUT("https://beer-company2019.herokuapp.com/entregadaOrden")
    Call<Object> updatePedidoE(@Field("json")  JsonObject JsonObject);

    @DELETE("ejemplo/{id}")
    Call<JSONArray> deletePedido(@Path("id") String id);

    //@POST("{user}/connect/{pass}")
    //Call<RespuestaAPI> getConnection(@Path("user") String user, @Path("pass") String pass);
}
