package com.wetravel.BackEnd;

import android.content.Context;
import android.os.AsyncTask;

import com.wetravel.Utils.Utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class GetJSON extends AsyncTask<String,String,String> {
    Context mContext;
    String templateName = "";
    String result = "";

    public abstract void response(String response);

    public GetJSON(Context mContext,String templateName){
        this.mContext = mContext;
        this.templateName = templateName;
    }

    @Override
    protected String doInBackground(String... str) {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(mContext.getAssets().open("JSON/"+templateName),"UTF-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            closeReader(bufferedReader);
            e.printStackTrace();
        }
        closeReader(bufferedReader);
        result = stringBuilder.toString();

//        Utility.LOG(result);

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        response(s);
    }

    public void closeReader(BufferedReader bufferedReader){
        if(bufferedReader!=null) {
            try {
                bufferedReader.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
