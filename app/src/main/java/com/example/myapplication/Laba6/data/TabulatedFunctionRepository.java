package com.example.myapplication.Laba6.data;

import com.example.myapplication.Laba6.app.App;
import com.example.myapplication.Laba6.data.api.MyAPI;
import com.example.myapplication.Laba6.data.retrofit.TabulatedFunctionRepositoryCallback;
import com.example.myapplication.Laba6.domain.ArrayTabulatedFunction;
import com.example.myapplication.Laba6.domain.FunctionPoint;
import com.example.myapplication.Laba6.domain.MyDateNumbers;
import com.example.myapplication.Laba6.domain.entity.MyEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabulatedFunctionRepository {
    private static final String BASE_URL = "https://run.mocky.io/";
    private MyDateNumbers data;

    public void createTabulatedFunctionByRequest(TabulatedFunctionRepositoryCallback myRepositoryCallback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyAPI apiService = retrofit.create(MyAPI.class);
        Call<MyDateNumbers> call = apiService.getMyData();
        call.enqueue(new Callback<MyDateNumbers>() {
            @Override
            public void onResponse(Call<MyDateNumbers> call, Response<MyDateNumbers> response) {
                ArrayTabulatedFunction arrayTabulatedFunction;
                if (response.isSuccessful()) {
                    data = response.body();
                    assert data != null;
                    arrayTabulatedFunction = new ArrayTabulatedFunction(data.getNumbers());
                    myRepositoryCallback.onSuccess(arrayTabulatedFunction);
                } else {
                    myRepositoryCallback.onFailure(new Throwable("error response"));
                }
            }

            @Override
            public void onFailure(Call<MyDateNumbers> call, Throwable t) {
                call.cancel();
                myRepositoryCallback.onFailure(t);
            }
        });
    }

    public ArrayTabulatedFunction createTabulatedFunctionByDatabase() {
        MyDatabase db = App.getInstance().getDatabase();
        MyDao dao = db.myDao();
        List<MyEntity> entity = dao.getAll();
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(toFunctionArray(entity));
        for (int i = 0; i < entity.size(); i++) {
            dao.delete(entity.get(i));
        }
        return arrayTabulatedFunction;
    }

    private FunctionPoint[] toFunctionArray(List<MyEntity> entity) {
        FunctionPoint[] array = new FunctionPoint[entity.size()];
        for (int i = 0; i < entity.size(); i++) {
            array[i] = toFunctionPoint(entity.get(i));
        }
        return array;
    }

    private FunctionPoint toFunctionPoint(MyEntity entity) {
        FunctionPoint fp = new FunctionPoint();
        fp.setX(entity.getX());
        fp.setY(entity.getY());
        return fp;
    }

    public void buttonDatabasePressed(ArrayList<FunctionPoint> list) {
        MyDatabase database = App.getInstance().getDatabase();
        MyDao dao = database.myDao();
        ArrayList<FunctionPoint> tabList = list;
        for (int i = 0; i < tabList.size(); i++) {
            MyEntity entity = new MyEntity(tabList.get(i).getX(), tabList.get(i).getY());
            dao.insert(entity);
        }
    }
}
