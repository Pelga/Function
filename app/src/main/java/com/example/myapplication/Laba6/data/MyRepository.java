package com.example.myapplication.Laba6.data;

import android.util.Log;

import com.example.myapplication.Laba6.app.App;
import com.example.myapplication.Laba6.data.api.MyAPI;
import com.example.myapplication.Laba6.data.retrofit.MyRepositoryCallback;
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

public class MyRepository {
    private static final String BASE_URL = "https://run.mocky.io/";
    private MyDateNumbers data;

    public void createTabulatedFunctionByRequest(MyRepositoryCallback myRepositoryCallback) {
        //1)при вызове чужого класса результат получаем в callback в аргументах callback
        //2)тк мы получаем результат в callback, мы не можем использовать return, тк callback работает ассинхронно
        //2.1)все callback работаю ассинхронно и ни в каком нельзя использовать ретерн
        //3)делаем метод void и передаем результат с помощью нового callback

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //создание объекта ретрофит для работы с сетью
        MyAPI apiService = retrofit.create(MyAPI.class);
        //экземпляр апи
        Call<MyDateNumbers> call = apiService.getMyData();
        //вызов метода апи для получения данных
        call.enqueue(new Callback<MyDateNumbers>() {
            //метод enqueue для асинхронного запроса
            //в качестве параметра метода передается экзампрял нового анонимного класса,
            // которые определяет поведение в случае успешного ответа или ошибки
            @Override
            public void onResponse(Call<MyDateNumbers> call, Response<MyDateNumbers> response) {
                Log.d("a", "aaa");
                ArrayTabulatedFunction arrayTabulatedFunction;
                //переменная atf
                if (response.isSuccessful()) { //
                    Log.d("a", "ccc");
                    //проверка на успешность ответа от сервера
                    data = response.body();
                    //если успешно, сохраняется в data
                    assert data != null;
                    //проверяется что data не равна налл
                    arrayTabulatedFunction = new ArrayTabulatedFunction(data.getNumbers());
                    //создается atf на основе данных из data
                    myRepositoryCallback.onSuccess(arrayTabulatedFunction);
                    //вызывает метода onSuccess  из callback maRepisitoryCallback передавая ему созданных ебъект atf
                }
            }
            //в случае успешного ответа создает arraytabfun на основе полученных данных

            @Override
            public void onFailure(Call<MyDateNumbers> call, Throwable t) {
                Log.d("a", "bbb");
                call.cancel();
                myRepositoryCallback.onFailure(t);
                //в случае ошибки вызывает метод onFailure из callback maRepisitoryCallback, передавая ему информацию об ошибке
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
