package ec.edu.tecnologicoloja.listapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ec.edu.tecnologicoloja.listapplication.adapter.ListAdapter;
import ec.edu.tecnologicoloja.listapplication.database.Persona;
import ec.edu.tecnologicoloja.listapplication.database.PersonaLab;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListAdapter listItemAdapter;
    private ArrayList<Persona> listaNombres = new ArrayList<>();
    private ListView listView;
    private PersonaLab mPersonaLab;
    private Persona mPersona;
    private TextView guardar,apellido,direccion;
    private Button bguardar,blimpiar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPersonaLab=new PersonaLab(this);

       listView = findViewById(R.id.list);
        guardar = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        direccion = findViewById(R.id.txtDireccion);

        bguardar = findViewById(R.id.buttonGuardar);
        blimpiar = findViewById(R.id.buttonLimpiar);
        bguardar.setOnClickListener(this);
        blimpiar.setOnClickListener(this);

        getAllPersonas();
        listItemAdapter=new ListAdapter(this,listaNombres);
        listView.setAdapter(listItemAdapter);

        listView.setOnItemClickListener ( new AdapterView.OnItemClickListener ( ) {
                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent info  =  new Intent(MainActivity.this,Activity_info.class);
                                                  info.putExtra("id", listaNombres.get(position));
                                                    startActivity(info);
                                              }

            } ) ;
        }
    /**
     * GUARDA EN LA BASE DE DATOS
     */
    public void insertPersonas() {
        mPersona=new Persona();
        mPersona.setNombre(guardar.getText().toString());
        mPersona.setApellido((apellido.getText().toString()));
        mPersona.setDireccion((direccion.getText().toString()));
        mPersonaLab.addPersona(mPersona);
        guardar.setText(" ");
        apellido.setText(" ");
        direccion.setText(" ");

    }
// CONSULTA A LA BASE DE DATOS
    public void getAllPersonas(){
        listaNombres.clear();
        listaNombres.addAll(mPersonaLab.getPersonas());

    }

// ACCION DE LOS BOTONES
    @Override
    public void onClick(View v) {
        if (v==blimpiar){
            mPersonaLab.deleteAllPersona();
            listaNombres.clear();
            listItemAdapter.notifyDataSetChanged();
        }
        if (v==bguardar){
            insertPersonas();
            getAllPersonas();
            listItemAdapter.notifyDataSetChanged();
        }
    }

}