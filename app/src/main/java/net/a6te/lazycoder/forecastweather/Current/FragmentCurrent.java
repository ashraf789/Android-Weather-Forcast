package net.a6te.lazycoder.forecastweather.Current;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.a6te.lazycoder.forecastweather.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCurrent extends Fragment {
    private TextView temperatureTV,conditionTV,sunriseTV,sunsetTV,humidityPercentTV;
    private ImageView conditionIV,conditionsIV;

    private String apiTemp, apiArea,apiCountry,weatherCondition,apiSunrise,apiSunset,apiHumidity ;

    private String currentLat = null;
    private String currentLng = null;

    private final String BASE_URL = "http://api.openweathermap.org/";
    private WeatherCurentData weatherData;
    private WeatherCurrentServiceAPI weatherServiceAPI;

    public FragmentCurrent() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Toast.makeText(getActivity(), "lat: "+currentLat+", lon: "+currentLng, Toast.LENGTH_SHORT).show();

        String Url = "http://api.openweathermap.org/data/2.5/weather?q=Dhaka&appid=a226ec225f23ea5717f7fa94ce785237";

        Call<WeatherCurentData> weatherResponse = weatherServiceAPI.getWeatherResponse(Url);
        weatherResponse.enqueue(new Callback<WeatherCurentData>() {
            @Override
            public void onResponse(Call<WeatherCurentData> call, Response<WeatherCurentData> response) {
                if(response.code() == 200){
                    WeatherCurentData weatherData=response.body();
                    //Toast.makeText(MainActivity.this, "Got It", Toast.LENGTH_SHORT).show();
                    //apiTemp = weatherData.getMain().getTemp().toString();
                    apiArea = weatherData.getName().toString();
                    apiCountry = weatherData.getSys().getCountry().toString();
                    apiSunrise = weatherData.getSys().getSunrise().toString();
                    apiSunset = weatherData.getSys().getSunset().toString();
                    apiHumidity = weatherData.getMain().getHumidity().toString();
                    weatherCondition = weatherData.getWeather().get(0).getMain().toString();

                    //float convertToCelsius = Float.parseFloat(apiTemp);
                    double convertToCelsius = (weatherData.getMain().getTemp());

                    double convertedTemp = Math.round(TemperatureConverter.convertKelvinToCel(convertToCelsius));
                    // showSunTV.setText(convertedTemp);
                    int myInt = (int) convertedTemp;

                    String temperature = String.valueOf(myInt);


                    temperatureTV.setText(temperature+"\u2103");
                    sunriseTV.setText(apiSunrise);
                    sunsetTV.setText(apiSunset);
                    conditionTV.setText(weatherCondition);
                    humidityPercentTV.setText(apiHumidity);
                }else{
                    Toast.makeText(getActivity(), "response not found", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(FragmentCurrent.this, " Temperature :"+apiTemp, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WeatherCurentData> call, Throwable t) {
                //Toast.makeText(FragmentCurrent.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("weather", "onFailure: "+t.getMessage() );

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Retrofit Starts ************************************
        View view = inflater.inflate(R.layout.fragment_fragment_currrent, container, false);
        temperatureTV = (TextView) view.findViewById(R.id.temperatureValue);
        sunriseTV = (TextView) view.findViewById(R.id.sunriseTime);
        sunsetTV = (TextView) view.findViewById(R.id.sunsetTime);
        conditionTV = (TextView) view.findViewById(R.id.conditiontv);
        humidityPercentTV = (TextView) view.findViewById(R.id.humidityPercent);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherServiceAPI = retrofit.create(WeatherCurrentServiceAPI.class);

        //Retrofit Ends ************************************


        return view;
    }

}
