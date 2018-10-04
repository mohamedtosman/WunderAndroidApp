package com.example.tikawy.wunder;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest extends Model{

    /**
     * Unit test case for testing populateCarListFromJson by passing in a single JSON
     * string that contains a single car to test how the function populates the arrayList
     * carList
     */
    @Test
    public void testJsonParsingForFirstCar() {
        ArrayList<HashMap<String, String>> carList;
        String jsonStr = "{\"placemarks\":[{\"address\":\"Lesserstraße 170, 22049 Hamburg\",\"coordinates\":[10.07526,53.59301,0],\"engineType\":\"CE\",\"exterior\":\"UNACCEPTABLE\",\"fuel\":42,\"interior\":\"UNACCEPTABLE\",\"name\":\"HH-GO8522\",\"vin\":\"WME4513341K565439\"}]}";

        Model model = new Model();
        model.populateCarListFromJson(jsonStr);
        carList = model.getCarList();

        assertEquals(carList.get(0).get("address"),"Lesserstraße 170, 22049 Hamburg");
        assertEquals(carList.get(0).get("lat"),"53.59301");
        assertEquals(carList.get(0).get("lang"),"10.07526");
        assertEquals(carList.get(0).get("engineType"),"CE");
        assertEquals(carList.get(0).get("exterior"),"UNACCEPTABLE");
        assertEquals(carList.get(0).get("fuel"),"42");
        assertEquals(carList.get(0).get("interior"),"UNACCEPTABLE");
        assertEquals(carList.get(0).get("name"),"HH-GO8522");
        assertEquals(carList.get(0).get("vin"),"WME4513341K565439");

    }
}