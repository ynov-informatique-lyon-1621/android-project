package com.example.travail.esparel.HTTP;

import android.util.Log;

import com.example.travail.esparel.model.AnnonceModel;


import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HTTP {

    public static String httpConnection(String... strings) {
        InputStream inputStream = null;
        HttpURLConnection conn = null;

        String stringUrl = strings[0];
        try {
            URL url = new URL(stringUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            int response = conn.getResponseCode();
            if (response != 200) {
                return null;
            }

            inputStream = conn.getInputStream();
            if (inputStream == null) {
                return null;
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }

            return new String(buffer);
        } catch (IOException e) {
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }


    public static String createNewAnnonce(AnnonceModel perso) {
        Map<String, String> data = new HashMap<>();
        data.put("image", perso.getImage());
        data.put("titre", perso.getTitre());
        data.put("email", perso.getEmail());
        data.put("password", perso.getPassword());
        data.put("prix", perso.getPrix());
        data.put("categorie", perso.getCategorie());
        data.put("nom", perso.getVendeur());
        data.put("description", perso.getDescription());

        HttpURLConnection urlConnection;


        DataOutputStream request;
        final String boundary =  "*****";
        final String crlf = "\r\n";
        final String twoHyphens = "--";

        URL url;

        String content = null;
        try {


            // creates a unique boundary based on time stamp
            url = new URL("http://139.99.98.119:8080/saveAnnonce");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Cache-Control", "no-cache");
            httpConn.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);
            request =  new DataOutputStream(httpConn.getOutputStream());

            String string = "{\n" +
                    "      \n" +
                    "        \"nomVendeur\": \"toto\",\n" +
                    "        \"email\": \"asskicker@ynov.com\",\n" +
                    "        \"mdp\": \"asskicker\",\n" +
                    "        \"titre\": \"Image DBZ\",\n" +
                    "        \"localisation\": \"Lyon\",\n" +
                    "        \"categorie\": \"VÃªtements\",\n" +
                    "        \"prix\": 35,\n" +
                    "        \"description\": \"Bonjour,\\nJe vends des polos Lacoste neufs.\\n35 euros l'unitÃ©.\\nBonne journÃ©e.\"\n" +
                    "        }";

            /*String name = "annonce";
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" + name + "\""+ crlf);
            request.writeBytes("Content-Type: application/json; charset=UTF-8" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(string+ crlf);
            request.flush();
            File uploadFile = new File("C:\\Users\\Travail\\Documents\\1.jpg");
            String fileName = "test.png";
            String fieldName = "annonce";
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    fieldName + "\";filename=\"" +
                    fileName + "\"" + crlf);
            request.writeBytes(crlf);

            byte[] bytesArray = new byte[(int) uploadFile.length()];

            FileInputStream fis = new FileInputStream(uploadFile);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
            byte[] bytes = bytesArray;
            request.write(bytes);*/

            String response ="";
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary +
                    twoHyphens + crlf);
            request.flush();
            request.close();
            // checks server's status code first
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                InputStream responseStream = new
                        BufferedInputStream(httpConn.getInputStream());
                BufferedReader responseStreamReader =
                        new BufferedReader(new InputStreamReader(responseStream));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                responseStreamReader.close();
                response = stringBuilder.toString();
                httpConn.disconnect();
            } else {
                throw new IOException("Server returned non-OK status: " + status);
            }
            content = response;


           /* url = new URL("http://139.99.98.119:8080/saveAnnonce");
            // Create the urlConnection
            urlConnection = (HttpURLConnection) url.openConnection();

            HttpPost post = new HttpPost("http://139.99.98.119:8080/saveAnnonce");
            FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
            StringBody stringBody = new StringBody("{\n" +
                    "      \n" +
                    "        \"nomVendeur\": \"toto\",\n" +
                    "        \"email\": \"asskicker@ynov.com\",\n" +
                    "        \"mdp\": \"asskicker\",\n" +
                    "        \"titre\": \"Image DBZ\",\n" +
                    "        \"localisation\": \"Lyon\",\n" +
                    "        \"categorie\": \"VÃªtements\",\n" +
                    "        \"prix\": 35,\n" +
                    "        \"description\": \"Bonjour,\\nJe vends des polos Lacoste neufs.\\n35 euros l'unitÃ©.\\nBonne journÃ©e.\"\n" +
                    "        }", ContentType.MULTIPART_FORM_DATA);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file", fileBody);
            builder.addPart("annonce", stringBody);

            HttpEntity entity = builder.build();

            post.setEntity(entity);
            HttpResponse response = client.execute(post);



            InputStream in = response.getEntity().getContent();

            StringBuilder sb = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((content = reader.readLine()) != null) {
                sb.append(content);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }
}


    // Push data to the server
      /*  try {

            url = new URL("http://139.99.98.119:8080/saveAnnonce");
            // Create the urlConnection
            urlConnection = (HttpURLConnection) url.openConnection();
            /*urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);*/
         /*   urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");


            if (!data.isEmpty()) {

                // Transform Map data to JSON
                JSONObject jsonPostData = new JSONObject(data);

                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(jsonPostData.toString());
                writer.flush();
            }

            InputStream in = urlConnection.getInputStream();

            StringBuilder sb = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((content = reader.readLine()) != null) {
                sb.append(content);
            }

            content = sb.toString();
            Log.i("Save entry ", content);

            return "true";
        } catch (Exception ex) {
            Log.e("Save entry error", "Failed to reach the web service server", ex);
        }
        return "false";

    }
}*/
