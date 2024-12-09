import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private final HashMap<String, String> countriesMap = new HashMap<>();
    private final Scanner consoleScanner = new Scanner(System.in);
    private int score = 0;

    public Game(String filePath) {
        loadCountries(filePath);
    }

    private void loadCountries(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String country = parts[0].trim();
                    String capital = parts[1].trim();

                    countriesMap.put(country, capital);
                } else {
                    System.out.println("Formato incorrecto en la línea: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public void play() {
        System.out.println("Por favor, introduce tu nombre: ");
        String userName = consoleScanner.nextLine();
        System.out.println("¡Hola, " + userName + "!");

        List<String> keys = new ArrayList<>(countriesMap.keySet());
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String randomCountry = keys.get(random.nextInt(keys.size()));
            String correctCapital = countriesMap.get(randomCountry);

            System.out.println("¿Cuál es la capital de " + randomCountry + "?");
            String userAnswer = consoleScanner.nextLine().trim();

            if (userAnswer.equalsIgnoreCase(correctCapital)) {
                System.out.println("¡Correcto!");
                score++;
            } else {
                System.out.println("Incorrecto. La capital es: " + correctCapital);
            }
        }

        System.out.println("\nJuego terminado, " + userName + ". Tu puntuación final es: " + score + " de 10.");
        guardarPuntuacion(userName, score);
    }

    private void guardarPuntuacion(String userName, int score) {
        var archivo = new File("classificacio.txt");

        try (var salida = new PrintWriter(new FileWriter(archivo, true))) {
            salida.println(userName + ": " + score + " puntos");
            System.out.println("Puntuación guardada en classificacio.txt.");
        } catch (IOException e) {
            System.out.println("Error al guardar la puntuación: " + e.getMessage());
        }
    }
}
