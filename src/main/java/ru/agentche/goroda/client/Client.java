package ru.agentche.goroda.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 25.09.2022
 */

public class Client {
    private static final int PORT = 8989;
    private static final String SERVER_IP = "localhost";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Socket clientSocket = new Socket(SERVER_IP, PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String city = in.readLine();
            System.out.println(city);
            if("???".equals(city)){
                System.out.println("Ты начинаешь, введи название города!");
                out.println(scanner.nextLine());
            } else {
                System.out.println("Введи название города начинающегося с буквы: " + city.charAt(city.length()-1));
                out.println(scanner.nextLine());
            }
            System.out.println(in.readLine());
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}


