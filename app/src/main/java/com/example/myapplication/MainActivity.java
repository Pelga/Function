package com.example.myapplication;

import static com.example.myapplication.Constants.ARRAY;
import static com.example.myapplication.Constants.NULL;
import static com.example.myapplication.Laba6.MyString.toStr;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.Laba6.App;
import com.example.myapplication.Laba6.ArrayTabulatedFunction;
import com.example.myapplication.Laba6.FunctionPoint;
import com.example.myapplication.Laba6.MyAPI;
import com.example.myapplication.Laba6.MyDao;
import com.example.myapplication.Laba6.MyDatabase;
import com.example.myapplication.Laba6.MyDateNumbers;
import com.example.myapplication.Laba6.MyEntity;
import com.example.myapplication.Laba6.MyFragment;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Serializable {
    private boolean isClosed = false;
    private static final String MY_KEY_CLOSED = "myKeyClosed";
    private static final String BASE_URL = "https://run.mocky.io/";
    private MyDateNumbers data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView cardView = findViewById(R.id.card);
        EditText leftDomainBorder = findViewById(R.id.right_domain_border);
        EditText rightDomainBorder = findViewById(R.id.left_domain_border);
        EditText pointsCount = findViewById(R.id.points_count);
        MaterialButton materialButton = findViewById(R.id.material_button);
        MaterialButton materialButtonGenerate = findViewById(R.id.material_button_generate);
        MaterialButton materialButtonDownload = findViewById(R.id.material_button_download);
        if (savedInstanceState != null) {
            isClosed = savedInstanceState.getBoolean(MY_KEY_CLOSED);
            if (isClosed) {
                cardView.setVisibility(View.INVISIBLE);
            } else {
                cardView.setVisibility(View.VISIBLE);
            }
        }
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toStr(rightDomainBorder).trim().equals(NULL) || toStr(leftDomainBorder).equals(NULL) || toStr(pointsCount).equals(ARRAY)) {
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                } else {
                    closeKeyboard(view);
                    cardView.setVisibility(View.INVISIBLE);
                    isClosed = true;
                    double l = Double.parseDouble(toStr(leftDomainBorder));
                    double r = Double.parseDouble(toStr(rightDomainBorder));
                    int p = Integer.parseInt(toStr(pointsCount));
                    ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(l, r, p);
                    openFragment(arrayTabulatedFunction);
                }
            }
        });

        materialButtonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard(view);
                cardView.setVisibility(View.INVISIBLE);
                isClosed = true;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                MyAPI apiService = retrofit.create(MyAPI.class);
                Call<MyDateNumbers> call = apiService.getMyData();
                call.enqueue(new Callback<MyDateNumbers>() {
                    @Override
                    public void onResponse(Call<MyDateNumbers> call, Response<MyDateNumbers> response) {
                        if (response.isSuccessful()) {
                            data = response.body();
                            assert data != null;
                            ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(data.getNumbers());
                            openFragment(arrayTabulatedFunction);
                        }
                    }

                    @Override
                    public void onFailure(Call<MyDateNumbers> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

        materialButtonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClosed = true;
                closeKeyboard(view);
                cardView.setVisibility(View.INVISIBLE);
                MyDatabase db = App.getInstance().getDatabase();
                MyDao dao = db.myDao();
                List<MyEntity> entity = dao.getAll();
                openFragment(new ArrayTabulatedFunction(toFunctionArray(entity)));
                for (int i = 0; i < entity.size(); i++) {
                    dao.delete(entity.get(i));
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(MY_KEY_CLOSED, isClosed);
    }

    private void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void openFragment(ArrayTabulatedFunction arrayTabulatedFunction) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyFragment fragment = new MyFragment(arrayTabulatedFunction);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private FunctionPoint toFunctionPoint(MyEntity entity) {
        FunctionPoint fp = new FunctionPoint();
        fp.setX(entity.getX());
        fp.setY(entity.getY());
        return fp;
    }

    private FunctionPoint[] toFunctionArray(List<MyEntity> entity) {
        FunctionPoint[] array = new FunctionPoint[entity.size()];
        for (int i = 0; i < entity.size(); i++) {
            array[i] = toFunctionPoint(entity.get(i));
        }
        return array;
    }
}



