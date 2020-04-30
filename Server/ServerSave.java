package Server;

import javax.xml.crypto.Data;
import java.io.DataOutputStream;

public class ServerSave
{

    static String player1Username = null;
    static String player2Username = null;
    static int mapSize = 0;
    static boolean is1Connected = false;
    static boolean is2Connected = false;
    static boolean turn = false;
    static boolean[] targetedTilesPlayer1;
    static boolean[] targetedTilesPlayer2;

    static boolean[] player1Map;
    static boolean[] player2Map;

    static DataOutputStream player1dos;
    static DataOutputStream player2dos;

    static boolean doesPlayer1Won = false;
    static boolean doesPlayer2Won = false;

    //false for player1 and true for player2;

}
