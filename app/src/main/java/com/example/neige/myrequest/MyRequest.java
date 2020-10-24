/*
 * Copyright (c) Salah-Eddine ET-TALEBY, CESBIO 2020
 */

package com.example.neige.myrequest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.neige.traitements.Formulaire;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyRequest {

    private Context context;
    private RequestQueue queue;
    private String URL_SERVEUR = "http://osr-cesbio.ups-tlse.fr/sdk/"; // URL du serveur


    public MyRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    public void register(final String pseudo, final String email, final String password, final String password2, final RegisterCallback callback) {
        String url = URL_SERVEUR + "register.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Map<String, String> errors = new HashMap<>();
            try {
                JSONObject json = new JSONObject(response);
                Boolean error = json.getBoolean("error");
                if (!error) {
                    callback.onSuccess("Vous êtes bien inscrit !");

                } else {
                    JSONObject messages = json.getJSONObject("message");
                    if (messages.has("pseudo")) {
                        errors.put("pseudo", messages.getString("pseudo"));
                    }
                    if (messages.has("email")) {
                        errors.put("email", messages.getString("email"));
                    }
                    if (messages.has("password")) {
                        errors.put("password", messages.getString("password"));
                    }
                    callback.inputErrors(errors);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    callback.onError("NetworkError : Impossible de se connecter !");
                } else if (error instanceof VolleyError) {
                    callback.onError("VolleyError : Une erreur s'est produite...");
                }
            }
        }) {
            // Envoi des paramètres que l'on veut tester dans le fichier register.php
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("pseudo", pseudo); // $_POST["pseudo"]
                map.put("email", email);
                map.put("password", password);
                map.put("password2", password2);
                return map;
            }
        };
        queue.add(request);
    }

    public void update_password(final String password, final String password2, final int id_user, final UpdateCallback callback) {
        String url = URL_SERVEUR + "updatePassword.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Map<String, String> errors = new HashMap<>();
            try {
                JSONObject json = new JSONObject(response);
                Boolean error = json.getBoolean("error");
                if (!error) {
                    callback.onSuccess("Mot de passe modifié avec succès !");

                } else {
                    JSONObject messages = json.getJSONObject("message");
                    if (messages.has("password")) {
                        errors.put("password", messages.getString("password"));
                    }
                    callback.inputErrors(errors);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    callback.onError("NetworkError : Impossible de se connecter !");
                } else if (error instanceof VolleyError) {
                    callback.onError("VolleyError : Une erreur s'est produite...");
                }
            }
        }) {
            // Envoi des paramètres que l'on veut tester dans le fichier register.php
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id_user", id_user + "");
                map.put("password", password);
                map.put("password2", password2);
                return map;
            }
        };
        queue.add(request);
    }

    public void reset_password(final String email, final ResetCallback callback) {
        String url = URL_SERVEUR + "resetPassword.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Map<String, String> errors = new HashMap<>();
            try {
                JSONObject json = new JSONObject(response);
                Boolean error = json.getBoolean("error");
                if (!error) {
                    callback.onSuccess("", "");

                } else {
                    JSONObject messages = json.getJSONObject("message");
                    if (messages.has("password")) {
                        errors.put("password", messages.getString("password"));
                    }
                    callback.inputErrors(errors);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    callback.onError("NetworkError : Impossible de se connecter !");
                } else if (error instanceof VolleyError) {
                    callback.onError("VolleyError : Une erreur s'est produite...");
                }
            }
        }) {
            // Envoi des paramètres que l'on veut tester dans le fichier
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                return map;
            }
        };
        queue.add(request);
    }

    public void login(final String pseudo, final String password, final LoginCallback callback) {
        String url = URL_SERVEUR + "login.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {
                    Log.d("RESPONSE", response);
                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error) {
                        String id = json.getString("id");
                        String pseudo = json.getString("pseudo");
                        callback.onSuccess(id, pseudo);
                    } else {
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    callback.onError("Une erreur s'est produite...");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    callback.onError("NetworkError : Impossible de se connecter ! " + error.toString());
                } else if (error instanceof VolleyError) {
                    callback.onError("VolleyError : Une erreur s'est produite... " + error.toString());
                }
            }
        }) {
            // Envoi des paramètres que l'on veut tester dans le fichier register.php
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("pseudo", pseudo); // $_POST["pseudo"]
                map.put("password", password);
                return map;
            }
        };
        queue.add(request);
    }

    public void insertionFormulaire(final Formulaire formulaire, final InsertionFormCallback callback) {
        String url = URL_SERVEUR + "insertForm.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Map<String, String> errors = new HashMap<>();
                try {
                    JSONObject json = new JSONObject(response);
                    Log.d("RESPONSE", response);
                    Boolean error = json.getBoolean("error");
                    if (!error) {
                        callback.onSuccess("Le formulaire a bien été sauvegardé dans la base de données !");

                    } else {
                        JSONObject messages = json.getJSONObject("message");
                        if (messages.has("req")) {
                            errors.put("req", messages.getString("req"));
                        }
                        callback.inputErrors(errors);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    callback.onError("NetworkError : Impossible de se connecter ! " + error.toString());
                } else if (error instanceof VolleyError) {
                    callback.onError("VolleyError : Une erreur s'est produite... " + error.toString());
                }
            }
        }) {
            // Envoi des paramètres que l'on veut tester dans le fichier insertionForm.php
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("latitude", formulaire.getLatitude() + ""); // $_POST["latitude"]
                map.put("longitude", formulaire.getLongitude() + "");
                map.put("accuracy", formulaire.getAccurracy() + "");
                map.put("altitude", formulaire.getAltitude() + "");
                map.put("pourcentageNeige", formulaire.getPourcentageNeige() + "");
                map.put("date", formulaire.getDate());
                map.put("id_user", formulaire.getIdUser() + "");
                return map;
            }
        };
        queue.add(request);
    }

    public void insertionFormulaireHL(final ArrayList<Formulaire> selectedForms, final InsertionFormCallback callback) {
        String url = URL_SERVEUR + "insertFormHL.php";
        String longitudes = "";
        String latitudes = "";
        String dates = "";
        String accuracies = "";
        String pourcentagesNeige = "";
        String altitudes = "";
        int id_user = 0;

        for (int i = 0; i < selectedForms.size(); i++) {
            longitudes += selectedForms.get(i).getLongitude();
            latitudes += selectedForms.get(i).getLatitude();
            dates += selectedForms.get(i).getDate();
            accuracies += selectedForms.get(i).getAccurracy();
            pourcentagesNeige += selectedForms.get(i).getPourcentageNeige();
            altitudes += selectedForms.get(i).getAltitude();
            id_user = selectedForms.get(i).getIdUser();
            if (i != selectedForms.size() - 1) {
                longitudes += ",";
                latitudes += ",";
                dates += ",";
                accuracies += ",";
                pourcentagesNeige += ",";
                altitudes += ",";
            }
        }

        final String finalAccuracies = accuracies;
        final String finalLatitudes = latitudes;
        final String finalLongitudes = longitudes;
        final String finalAltitudes = altitudes;
        final String finalPourcentagesNeige = pourcentagesNeige;
        final String finalDates = dates;
        final int finalId_user = id_user;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Map<String, String> errors = new HashMap<>();
                try {
                    JSONObject json = new JSONObject(response);
                    Log.d("RESPONSE", response);
                    Boolean error = json.getBoolean("error");
                    if (!error) {
                        callback.onSuccess("Formulaire(s) envoyé(s) dans la base de données !");

                    } else {
                        JSONObject messages = json.getJSONObject("message");
                        if (messages.has("req")) {
                            errors.put("req", messages.getString("req"));
                        }
                        callback.inputErrors(errors);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    callback.onError("NetworkError : Impossible de se connecter ! " + error.toString());
                } else if (error instanceof VolleyError) {
                    callback.onError("VolleyError : Une erreur s'est produite... " + error.toString());
                }
            }
        }) {
            // Envoi des paramètres que l'on veut tester dans le fichier insertionFormHL.php
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("latitudes", finalLatitudes); // $_POST["latitudes"]
                map.put("longitudes", finalLongitudes);
                map.put("accuracies", finalAccuracies);
                map.put("altitudes", finalAltitudes);
                map.put("pourcentagesNeige", finalPourcentagesNeige);
                map.put("dates", finalDates);
                map.put("id_user", finalId_user + "");
                return map;
            }
        };
        queue.add(request);
    }

    public interface RegisterCallback {
        void onSuccess(String message);

        void inputErrors(Map<String, String> errors);

        void onError(String message);
    }

    public interface UpdateCallback {
        void onSuccess(String message);

        void inputErrors(Map<String, String> errors);

        void onError(String message);
    }

    public interface ResetCallback {
        void onSuccess(String newPassword, String message);

        void inputErrors(Map<String, String> errors);

        void onError(String message);
    }

    public interface LoginCallback {
        void onSuccess(String id, String pseudo);

        void onError(String message);
    }

    public interface InsertionFormCallback {
        void onSuccess(String message);

        void inputErrors(Map<String, String> errors);

        void onError(String message);
    }
}
