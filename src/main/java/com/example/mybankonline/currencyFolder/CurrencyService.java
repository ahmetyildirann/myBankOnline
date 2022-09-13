package com.example.mybankonline.currencyFolder;

import com.example.mybankonline.accountFolder.model.Account;
import com.example.mybankonline.customerFolder.repository.CustomerRepository;
import org.json.JSONObject;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CurrencyService {

    private final CustomerRepository customerRepository;

    public CurrencyService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public double setAccountBalancer(double amount, Account sender, Account receiver) throws IOException {

        String getUrl = "https://api.exchangerate.host/latest?base="+sender.getCurrentType()+"&symbols="+receiver.getCurrentType();
        URL url = new URL(getUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        int responseCode = httpURLConnection.getResponseCode();

        if (!(responseCode == HttpURLConnection.HTTP_OK)) System.out.println("curreny api succes");

            BufferedReader in =new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }in.close();


            JSONObject jsonpObject = new JSONObject(response.toString());

            Double exchangeRate = jsonpObject.getJSONObject("rates").getDouble(receiver.getCurrentType());

            Double accountBalance = amount*exchangeRate;

            return accountBalance;

    }





}


