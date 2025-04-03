import java.util.*;

class BigVigenere{
    static int[] key;
    String numericKey;
    char[][] alphabet;
    final String ALPHABET = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; //Tamaño 64

    public BigVigenere(int[] contrasena, char[][] alfabeto) {
        key = contrasena;
        alphabet = alfabeto;
    }
    public BigVigenere(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese su contraseña: ");
        this.numericKey = sc.nextLine();

        int N = ALPHABET.length();
        alphabet = new char[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                alphabet[i][j] = ALPHABET.charAt((i + j) % N);
            }
        }
        key = new int[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            char c = numericKey.charAt(i);
            key[i] = ALPHABET.indexOf(c);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BigVigenere bigVigenere = new BigVigenere();
        System.out.println(Arrays.toString(bigVigenere.key));
    }
}