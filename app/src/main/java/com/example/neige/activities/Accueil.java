package com.example.neige.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neige.R;
import com.example.neige.traitements.SessionManager;

/**
 * @author Salah-Eddine ET-TALEBY
 * Classe liée à l'activité principale...
 * L'utilisateur peut saisir un nouveau formulaire, consulter ses formulaires envoyés/sauvegardés, et voir ses statistiques
 */
public class Accueil extends AppCompatActivity {

    // Variables nécessaires
    private SessionManager sessionManager;
    private Button btn_listeformulaires_horsligne, btn_listeformulaires_bd, btn_nouveauformulaire, btn_deconnexion, btn_aide, btn_statistiques;
    private String pseudo;
    private int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Instanciation des variables
        sessionManager = new SessionManager(this);
        btn_listeformulaires_horsligne = findViewById(R.id.btn_listeformulaires_horsligne);
        btn_listeformulaires_bd = findViewById(R.id.btn_listeformulaires_bd);
        btn_nouveauformulaire = findViewById(R.id.btn_nouveauformulaire);
        btn_deconnexion = findViewById(R.id.btn_deconnexion);
        btn_aide = findViewById(R.id.btn_aide);
        btn_statistiques = findViewById(R.id.btn_statistiques);

        // Si l'utilisateur est loggé, on récupère les informations
        if (sessionManager.isLogged()) {
            pseudo = sessionManager.getPseudo();
            id_user = Integer.parseInt(sessionManager.getId());
        }

        // Ouvrir la liste de formulaires sauvegardés hors-ligne
        btn_listeformulaires_horsligne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FormulairesHorsLigne.class);
                i.putExtra("pseudo", pseudo);
                i.putExtra("id_user", id_user);
                startActivity(i);
            }
        });

        // Ouvrir la liste des formulaires sauvegardés dans la base de données
        btn_listeformulaires_bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FormulairesBD.class);
                i.putExtra("pseudo", pseudo);
                i.putExtra("id_user", id_user);
                startActivity(i);
            }
        });


        // Ouvrir la fenêtre de localisation afin de saisir un nouveau formulaire
        btn_nouveauformulaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Localisation.class);
                i.putExtra("pseudo", pseudo);
                i.putExtra("id_user", id_user);
                startActivity(i);
            }
        });

        // Déconnecter l'utilisateur lorsqu'il clique sur le bouton "Déconnexion"
        btn_deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                Intent i = new Intent(getApplicationContext(), Bienvenue.class);
                startActivity(i);
                finish();
            }
        });

        // Ouvrir la fenêtre d'aide
        btn_aide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(getApplicationContext(), Aide.class);
                // startActivity(i);
                // finish();
                Toast.makeText(Accueil.this, "Clic !", Toast.LENGTH_SHORT).show();
            }
        });

        // Ouvrir la fenêtre de statistiques
        btn_statistiques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Statistiques.class);
                i.putExtra("pseudo", pseudo);
                i.putExtra("id_user", id_user);
                startActivity(i);
                finish();
            }
        });
    }
}