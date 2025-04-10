import java.util.*;

class BigVigenere{
    static int[] key;
    String numericKey;
    char[][] alphabet;
    final String ALPHABET = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789"; //Tamaño 64

    public BigVigenere(String claveAleatoria) {
        this.numericKey = claveAleatoria;

        int N = ALPHABET.length();
        alphabet = new char[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                alphabet[i][j] = ALPHABET.charAt((i + j) % N);
            }
        }
        setKey();
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
        setKey();
        /*for(int i = 0; i < N; i++){     //En caso de querer ver la matriz entera impresa.
            for(int j = 0; j < N; j++){
                System.out.print(alphabet[i][j] + " ");
            }
            System.out.println(" ");
        }*/
    }


    public void setKey() {
        key = new int[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            char C = numericKey.charAt(i);
            key[i] = ALPHABET.indexOf(C);
        }
    }
    public String encrypt(String message) {
        StringBuilder encriptado = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char SrcChar = message.charAt(i);
            int messagePosition = ALPHABET.indexOf(SrcChar);
            int keyPos = key[i % key.length];

            if (messagePosition != -1) {
                encriptado.append(alphabet[messagePosition][keyPos]);
            } else {
                encriptado.append(SrcChar);
            }
        }
        return encriptado.toString();
    }
    public String decrypt(String encryptedMessage) {
        StringBuilder desencriptado = new StringBuilder();
        int N = ALPHABET.length();

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char CharEncriptado = encryptedMessage.charAt(i);
            int keyPosition = key[i % key.length];

            int messagePosition = -1;
            for (int j = 0; j < N; j++) {
                if (alphabet[j][keyPosition] == CharEncriptado) {
                    messagePosition = j;
                    break;
                }
            }

            if (messagePosition != -1) {
                desencriptado.append(ALPHABET.charAt(messagePosition));
            } else {
                desencriptado.append(CharEncriptado);
            }
        }
        return desencriptado.toString();
    }
    public void reEncrypt() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el mensaje cifrado: ");
        String encriptado = scanner.nextLine();

        String desencriptado = decrypt(encriptado);
        System.out.println("Mensaje desencriptado: " + desencriptado);

        System.out.print("Ingrese la nueva clave: ");
        this.numericKey = scanner.nextLine();
        setKey();

        String reEncriptado = encrypt(desencriptado);
        System.out.println("Nuevo mensaje cifrado: " + reEncriptado);
    }
    public char search(int position) {
        int fila = position / ALPHABET.length();
        int columna = position % ALPHABET.length();
        return alphabet[fila][columna];
    }
    public char optimalSearch(int position) {
        return ALPHABET.charAt(position % ALPHABET.length());
    }
}

public class Main {
    public static void main(String[] args) {
        BigVigenere bigVigenere = new BigVigenere();
        System.out.println(Arrays.toString(bigVigenere.key));

        Scanner mensaje = new Scanner(System.in);
        System.out.print("Ingrese el mensaje a cifrar: ");
        String Message = mensaje.nextLine();

        String encriptado = bigVigenere.encrypt(Message);
        System.out.println("El mensaje encriptado es: " + encriptado);

        bigVigenere.reEncrypt();


        System.out.println("Búsqueda en matriz (posición 10): " + bigVigenere.search(10));
        System.out.println("Búsqueda óptima (posición 102): " + bigVigenere.optimalSearch(105));


        int[] keySizes = new int[]{10, 50, 100, 500, 1000, 5000};
        String testMessage = RandomString(10000);
        for (int N : keySizes) {
            String randomKey = RandomString(N);

            BigVigenere cipher = new BigVigenere(randomKey);
            System.out.println("Probando con clave de tamaño " + N);
            System.out.println("Clave " + randomKey);

            measureTime(cipher, testMessage);
        }
    }

    private static String RandomString(int length) {
        String ALPHABET = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    public static void measureTime(BigVigenere cipher, String message) {

        long startTime = System.nanoTime();//da el pie de inicio y final al encriptar y desencriptar
        System.out.println(message);
        String a = cipher.encrypt(message);
        System.out.println(a);
        String b = cipher.decrypt(a);
        System.out.println(b);
                                            //1 y 3 chatgpt
        long endTime = System.nanoTime();

        long durationNano = endTime - startTime;    //resta el tiempo final con inicial para sacar el tiempo q se demora
        double durationMs = durationNano / 1000000.0;  //divide por 1000000.0 para pasar de nanosegundos a milisegundos.
        System.out.println("Tiempo total " + durationMs + " ms");

    }
}
