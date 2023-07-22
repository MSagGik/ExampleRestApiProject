package com.msaggik.examplerestapiproject.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.msaggik.examplerestapiproject.R;
import com.msaggik.examplerestapiproject.network.HttpsHelper;
import com.msaggik.examplerestapiproject.view.activities.MainActivity;
import com.msaggik.examplerestapiproject.viewmodel.adapters.AdapterProducts;

import java.util.Objects;

public class SettingsFragment extends Fragment implements Runnable{

    private SharedPreferences settings; // поле настроек приложения
    private SharedPreferences.Editor editor; // поле для добавления новых данных в настройки
    private final String NAME_SETTING = "RestApiApp"; // константа названия настроек
    private final String CONNECTION_SETTING = "Connection"; // константа названия настройки URL соединения
    private String urlConnection; // URL адрес сервера
    private int request;
    private EditText urlServerSetting;
    private TextView resultConnectionCheck, infoSetting;
    private Button buttonSetting, buttonCheck;
    private Handler handler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // создание объекта работы с настройками приложения
        settings = Objects.requireNonNull(getActivity()).getSharedPreferences(NAME_SETTING, Context.MODE_PRIVATE);
        urlConnection = settings.getString(CONNECTION_SETTING, "NoUrl"); // получение настроек

        handler = new Handler(Looper.getMainLooper()); // создание объекта обработчика сообщений
        new Thread(this).start();

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        urlServerSetting = view.findViewById(R.id.urlServerSetting);
        resultConnectionCheck = view.findViewById(R.id.resultConnectionCheck);
        infoSetting = view.findViewById(R.id.infoSetting);
        buttonSetting = view.findViewById(R.id.buttonSetting);
        buttonCheck = view.findViewById(R.id.buttonCheck);

        urlServerSetting.setText(urlConnection); // вывод настроек на экран приложения
        infoSetting.append("\nАдрес сайта должен быть\nhttps://dummyjson.com/" +
                "\n200 - соединение успешное\n3хх - перенаправление\n4хх - ошибка URL\n5xx - ошибка сервера");

        return view;

    }

    @Override
    public void run() {
        request = new HttpsHelper().connectionCheck(getActivity());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonSetting.setOnClickListener(listener);
                buttonCheck.setOnClickListener(listener);
            }
        },0);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.buttonSetting:
                    // если пользователь ничего не ввёл, то остаёмся в этой же активности
                    if (urlConnection.equals("")) {
                        // сообщение пользователю об отсутствии введённого города
                        Toast toast = Toast.makeText(getActivity(), "URL сервера отсутствует", Toast.LENGTH_SHORT);
                        toast.show();
                    } else { // иначе сохраняем (пересохраняем) эти данные и переключаемся в активность прогноза погоды
                        urlConnection = urlServerSetting.getText().toString();
                        // запись новой настройки
                        editor = settings.edit(); // создание объекта для доступа к изменению настроек
                        editor.putString(CONNECTION_SETTING, urlConnection); // запись настроек
                        editor.apply(); // сохранение настроек
                    }
                    break;
                case R.id.buttonCheck:
                    resultConnectionCheck.setText("Ответ на GET запрос " + request);
                    break;
            }
        }
    };
}