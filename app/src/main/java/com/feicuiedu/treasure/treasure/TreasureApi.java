package com.feicuiedu.treasure.treasure;

import com.feicuiedu.treasure.treasure.detail.TreasureDetail;
import com.feicuiedu.treasure.treasure.detail.TreasureDetailResult;
import com.feicuiedu.treasure.treasure.detail.TreasureFind;
import com.feicuiedu.treasure.treasure.detail.TreasureFindResult;
import com.feicuiedu.treasure.treasure.hide.HideTreasure;
import com.feicuiedu.treasure.treasure.hide.HideTreasureResult;
import com.feicuiedu.treasure.treasure.home.Area;
import com.feicuiedu.treasure.treasure.home.Treasure;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public interface TreasureApi {

    @POST("/Handler/TreasureHandler.ashx?action=show")
    Call<List<Treasure>> getTreasureInArea(@Body Area body);

    @POST("/Handler/TreasureHandler.ashx?action=hide")
    Call<HideTreasureResult> hideTreasure(@Body HideTreasure body);

    @POST("/Handler/TreasureHandler.ashx?action=tdetails")
    Call<List<TreasureDetailResult>> getTreasureDetail(@Body TreasureDetail body);

    @POST("/Handler/TreasureHandler.ashx?action=find")
    Call<TreasureFindResult> findTreasure(@Body TreasureFind body);
}