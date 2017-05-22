package ro.pub.cs.systems.eim.lab06.pheasantgame.network;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;

import ro.pub.cs.systems.eim.lab06.pheasantgame.general.Constants;
import ro.pub.cs.systems.eim.lab06.pheasantgame.general.Utilities;

public class ServerCommunicationThread extends Thread {

    private Socket socket;
    private TextView serverHistoryTextView;

    private Random random = new Random();

    private String expectedWordPrefix = new String();

    public ServerCommunicationThread(Socket socket, TextView serverHistoryTextView) {
        if (socket != null) {
            this.socket = socket;
            Log.d(Constants.TAG, "[SERVER] Created communication thread with: " + socket.getInetAddress() + ":" + socket.getLocalPort());
        }
        this.serverHistoryTextView = serverHistoryTextView;
    }

    public void run() {
        try {
            if (socket == null) {
                return;
            }
            boolean isRunning = true;
            BufferedReader requestReader = Utilities.getReader(socket);
            PrintWriter responsePrintWriter = Utilities.getWriter(socket);

            while (isRunning) {

                // TODO exercise 7a
                String line = requestReader.readLine();
                if (line.equals(Constants.END_GAME)) {
                    break;
                }

                if (Utilities.wordValidation(line)) {
                    if (line.startsWith(expectedWordPrefix)) {
                        String last2 = line.substring(line.length() - 2);
                        List<String> l = Utilities.getWordListStartingWith(last2);

                        if (l.isEmpty()) {
                            responsePrintWriter.print(Constants.END_GAME);
                        } else {
                            responsePrintWriter.println(l.get(0));
                            expectedWordPrefix = last2;
                        }
                    } else {
                        responsePrintWriter.println(line);
                    }
                } else {
                    responsePrintWriter.println(line);
                }

            }
            socket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }
}
