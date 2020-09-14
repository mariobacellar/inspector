package br.com.mcb.inspector.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Reading a web page in Java: http://zetcode.com/articles/javareadwebpage/
 * @author mario.bacellar
 *
 */
public class ReadWebPageEx {

    public static void main(String[] args) throws MalformedURLException, IOException {

        BufferedReader br = null;

        try {

            URL url = new URL("http://www.something.com");
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }

            System.out.println(sb);
        } finally {

            if (br != null) {
                br.close();
            }
        }
    }
}
