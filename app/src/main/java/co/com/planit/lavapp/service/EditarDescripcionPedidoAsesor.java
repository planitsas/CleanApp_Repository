package co.com.planit.lavapp.service;

import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Usuario_TO;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;

/**
 * Created by Jose on 24/10/2016.
 */
public interface EditarDescripcionPedidoAsesor {
//    @FormUrlEncoded
//    @PUT("/editarDescripcionPedidoAsesor/")
//    void editarDescripcionPedidoAsesor(@Field("idDescripcionPedido") int idDescripcionPedido,
//                       @Field("idEstado") int idEstado,
//                       @Field("observacionAsesor") String observacionAsesor,
//                       @Field("idColor") int idColor,
//                       @Field("foto1") String foto1,
//                       @Field("foto2") String foto2,
//                       @Field("foto3") String foto3,
//                                       @Field("codigo") String codigo,
//                                       @Field("nombrefoto1") String nombrefoto1,
//                                       @Field("nombrefoto2") String nombrefoto2,
//                                       @Field("nombrefoto3") String nombrefoto3,
//                       Callback<String> callback);

    @FormUrlEncoded
    @PUT("/editarDescripcionPedidoAsesor/")
    void editarDescripcionPedidoAsesor(@Field(value ="idDescripcionPedido", encodeValue = true , encodeName = true) int idDescripcionPedido,
                                       @Field(value ="idEstado", encodeValue = true , encodeName = true) int idEstado,
                                       @Field(value ="observacionAsesor", encodeValue = true , encodeName = true) String observacionAsesor,
                                       @Field(value ="idColor", encodeValue = true , encodeName = true) int idColor,
                                       @Field(value ="foto1", encodeValue = true , encodeName = true) String foto1,
                                       @Field(value ="foto2", encodeValue = true , encodeName = true) String foto2,
                                       @Field(value ="foto3", encodeValue = true , encodeName = true) String foto3,
                                       @Field(value ="codigo", encodeValue = true , encodeName = true) String codigo,
                                       @Field(value ="nombrefoto1", encodeValue = true , encodeName = true) String nombrefoto1,
                                       @Field(value ="nombrefoto2", encodeValue = true , encodeName = true) String nombrefoto2,
                                       @Field(value ="nombrefoto3", encodeValue = true , encodeName = true) String nombrefoto3,
                                       Callback<String> callback);
}
