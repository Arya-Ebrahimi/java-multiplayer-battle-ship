package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    static Socket socket = null;

    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(1380);

        socket = serverSocket.accept();



        System.out.println("Player 1 connected");
        Thread firstPlayerThread = new ServerHandler(socket, "player1");
        firstPlayerThread.start();


        socket = null;
        socket = serverSocket.accept();

        System.out.println("Player 2 connected");


        Thread secondPlayerThread = new ServerHandler(socket, "player2");
        secondPlayerThread.start();
    }
}
