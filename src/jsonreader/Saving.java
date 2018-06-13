/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonreader;

import java.io.File;
import java.util.ArrayList;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author guojef18
 */
public class Saving {

    public static void Export(String myHelpers[]) {
        ArrayList<String> listdata = new ArrayList<String>();     
        JSONArray jArray=(JSONArray)jsonobject; 
        if (jArray != null) { 
            for (int i=0;i<jArray.length();i++){ 
                listdata.add(jArray.getString(i));
            } 
        } 
        String jsonString = "{\"infile\": [{\"field1\": 11,\"field2\": 12,\"field3\": 13},{\"field1\": 21,\"field2\": 22,\"field3\": 23},{\"field1\": 31,\"field2\": 32,\"field3\": 33}]}";
        JSONObject output;
        try {
            output = new JSONObject(jsonString);

            JSONArray docs = output.getJSONArray("infile");

            File file = new File("/tmp2/fromJSON.csv");
            String csv = CDL.toString(docs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
