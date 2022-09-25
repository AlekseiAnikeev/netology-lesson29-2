package ru.agentche.goroda.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 25.09.2022
 */

public class Server {
    private static final List<String> city = new ArrayList<>();


    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            System.out.println("Сервер запущен и ждет подключения");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {
                    if (initialize(out, in)) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean initialize(PrintWriter out, BufferedReader in) throws IOException {
        if (city.size() == 0) {
            out.println("???");
        } else {
            out.println(city.get(city.size() - 1));
        }
        String name = in.readLine();
        if ("end".equals(name)) {
            System.out.println("Сервер остановлен");
            return true;
        } else if (city.size() == 0) {
            city.add(name);
            out.println("OK");
        } else {
            String lastCity = city.get(city.size() - 1);
            if (name.startsWith(String.valueOf(lastCity.charAt(lastCity.length() - 1)))) {
                city.add(name);
                out.println("OK");
            } else {
                out.println("NOT OK");
            }
        }
        return false;
    }
}