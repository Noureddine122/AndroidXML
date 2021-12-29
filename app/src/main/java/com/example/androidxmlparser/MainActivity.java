package com.example.androidxmlparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.ListV);
        List<String> items = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        InputStream source = null;
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        int event = 0;
        try {
            //Ouvrir le fichier xml
            source = getAssets().open("Personnes.xml");
            // Instancier factory avant le pullparser
            factory = XmlPullParserFactory.newInstance();
            assert factory != null;
            parser = factory.newPullParser();
            assert parser != null;
            // Start parsing
            parser.setInput(source,null);
            event = parser.getEventType();
            parser.next();
            String result;
            while(event != XmlPullParser.END_DOCUMENT){
                if (event == XmlPullParser.END_TAG && parser.getName().equals("personne")) {
                    // add element to items list and get notifiing the adapter about that change
                    result = ": "+ parser.getAttributeValue(0);
                    result += " \t|| : " + parser.getAttributeValue(1);
                    result += " \t|| : " + parser.getAttributeValue(2);
                    result += " \t|| : " + parser.getAttributeValue(3);

                    items.add(result);
                    adapter.notifyDataSetChanged();
                    event = parser.next();// next tag <personne/>
                }
                else{
                    event = parser.next();
                }

            }
            //message de finir le parsing
            Toast.makeText(this,"Finish reading the xml file",Toast.LENGTH_SHORT).show();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();//si une exception se declence
        }
    }
}

