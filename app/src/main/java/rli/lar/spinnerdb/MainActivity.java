package rli.lar.spinnerdb;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerPays;

    String ip, db, user, mdp;
    Connection connect;
    PreparedStatement stmt;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip = "192.168.1.24:3306";
        user = "professeur";
        mdp = "professeur";
        db = "monde";

        spinnerPays = (Spinner) findViewById(R.id.spinnerPays);

        String query = "select pays from country";

        try {
            connect = connexion(user, mdp, db, ip);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            while (rs.next()) {
                String pays = rs.getString("pays");
                data.add(pays);
            }
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            spinnerPays.setAdapter(NoCoreAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        spinnerPays.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spinnerPays.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressLint("NewApi")
    private Connection connexion(String user, String pass, String db, String ip) {
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.
                Builder().
                permitAll().
                build();

        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            ConnURL = "jdbc:mysql://" + this.ip + "/" + this.db + "";
            conn = DriverManager.getConnection(ConnURL, this.user, this.mdp);
            Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_LONG).show();

        } catch (SQLException se) {
            Toast.makeText(MainActivity.this, "Echec connexion", Toast.LENGTH_LONG).show();
            Log.e("ERRO", se.getMessage());

        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());

        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }

}

