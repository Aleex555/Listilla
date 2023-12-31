package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    class Record {
        public int intents;
        public String nom;

        public int imageResource;


        public Record(int _intents, String _nom, int _imageResource) {
            intents = _intents;
            nom = _nom;
            imageResource = _imageResource;

        }

        public int getImageResource(){
            return imageResource;
        }

        public String getNom() {
            return nom;
        }

        public int getIntents() {
            return intents;
        }
    }

    // Model = Taula de records: utilitzem ArrayList
    static ArrayList<Record> records;
    static ArrayList<String> noms;

    static ArrayList<String> fotos;

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] fotos = {"baixa","baixa1","baixa2","baixa4"};
        noms = new ArrayList<>(Arrays.asList(
                 "Manuel", "Raquel", "Antonio", "Elena", "David",
                 "Silvia", "Joaquín", "Marta", "Javier", "Cristina",
                 "Francisco", "Patricia", "Alejandro", "Natalia", "Roberto",
                 "Isabel", "Luis", "Lucía", "Sergio", "Alicia"
        ));

        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        records.add(new Record(33, "Manolo",getResources().getIdentifier("baixa", "drawable", getPackageName())));
        records.add(new Record(12, "Pepe",getResources().getIdentifier("baixa1", "drawable", getPackageName())));
        records.add(new Record(42, "Laura",getResources().getIdentifier("baixa4", "drawable", getPackageName())));

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>(this, R.layout.list_item, records) {
            @Override
            public View getView(int pos, View convertView, ViewGroup container) {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if (convertView == null) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                ImageView imageView = convertView.findViewById(R.id.imageView);
                imageView.setImageResource(getItem(pos).imageResource);
                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int indiceAleatorio = rand.nextInt(noms.size());
                int numeroAleatorio = rand.nextInt(100) + 1;
                int posicion = rand.nextInt(fotos.length);
                String imagen = fotos[posicion];
                int imageResource = getResources().getIdentifier(imagen, "drawable", getPackageName());
                    records.add(new Record(numeroAleatorio, noms.get(indiceAleatorio),imageResource));
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });

        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(records, new Comparator<Record>() {
                    @Override
                    public int compare(Record record1, Record record2) {
                        return Integer.compare(record2.getIntents(), record1.getIntents());
                    }
                });
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });
    }
}
