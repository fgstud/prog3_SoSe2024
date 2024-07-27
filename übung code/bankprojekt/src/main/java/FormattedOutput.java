import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Scanner;

public class FormattedOutput {
    /**
     Schreiben Sie ein Programm, dass einige Zahlen sinnvoll formatiert in eine Datei beliebigen
     Namens schreibt. Um diese Aufgabe zu erfüllen, stülpen Sie über den FileWriter einen
     PrintWriter. Er bietet die Methode printf(), die in etwa so arbeitet wie die vielleicht
     aus C bekannte Funktion printf(), d.h. in einem ersten String gibt man den auszugebenden
     Text mit Platzhaltern an, an denen dann die nachfolgenden Parameter eingesetzt werden,
     z.B.
     System.out.printf("erste Zahl: %.2f, zweite Zahl %d%n",
     doubleVar, intVar);
     Eine genaue Beschreibung der Formatierungsmöglichkeiten in Java finden Sie unter
     https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/Formatter.html#syntax.
     Benutzen Sie den anzugebenden Formatstring, wandeln Sie die Zahlen und Datumsangaben
     nicht selbst in einen String um und verwenden Sie auch keine weiteren Formatierungsob-
     jekte!
     1. Der Benutzer soll eine ganze Zahl eingeben. Sie soll ohne weitere Formatierung (ge-
     nau gesagt: mit Standardformatierung) in die erste Zeile der Datei geschrieben wer-
     den.
     Programmieren 3 Übung 10
     Dorothea Hubrich
     d_hubrich@web.de 2 / 2
     2. In die zweite Zeile der Datei soll diese Zahl mit insgesamt 12 Stellen geschrieben
     werden. Falls die Zahl zu kurz ist, soll sie vorne mit Nullen aufgefüllt werden, falls sie
     zu lang ist, wird sie nicht verändert.
     3. In die dritte Zeile soll die Zahl mit Vorzeichen (+ oder -) und Tausendertrennzeichen
     geschrieben werden.
     4. In die vierte Zeile soll die Zahl hexadezimal geschrieben werden, wobei die Ziffern A-
     F großgeschrieben werden sollen.
     5. Der Benutzer soll eine Zahl mit Nachkommaanteil eingeben. Sie soll mit der Stan-
     dard-Formatierung in die fünfte Zeile der Datei geschrieben werden.
     6. In die sechste Zeile der Datei soll diese Zahl mit Vorzeichen (+ oder -) und mit 5
     Nachkommastellen geschrieben werden.
     7. In die siebente Zeile soll diese Zahl in wissenschaftlicher Darstellung geschrieben
     werden (mit Angabe des Exponents, wie Sie das vielleicht schon mal im Taschen-
     rechner gesehen haben, wenn die Zahl zu groß für das Display wurde).
     8. In der achten Zeile soll diese Zahl mit 2 Nachkommastellen, aber mit dem in den USA
     üblichen Punkt als Dezimaltrennzeichen geschrieben werden. Benutzen Sie die
     printf()-Methode, die als ersten Parameter ein Locale-Objekt bekommt.
     9. Schreiben Sie in die neunte Zeile das aktuelle Datum. Beginnen Sie mit dem Tag
     (ohne führende 0), dem ausgeschriebenen Monatsnamen und der vierstelligen Jah-
     reszahl. In Klammern soll der abgekürzte Wochentagsname dahinter stehen.
     • Das aktuelle Datum bekommen Sie mit LocalDate heute =
     LocalDate.now();
     10. Schreiben Sie in die zehnte Zeile noch einmal das heutige Datum, diese Mal aber mit
     zweistelliger Tages- und Monats- und Jahreszahl und dem voll ausgeschriebenen
     Wochentagsnamen in italienischer Sprache.
     11. Schreiben Sie in die elfte Zeile die aktuelle Uhrzeit im englischen Format, also
     Stunde:Minute am (bzw. pm) ohne führende Nullen bei der Stundenzahl.
     • Die aktuelle Uhrzeit bekommen Sie mit LocalTime jetzt =
     LocalTime.now();
     Anmerkung: Die Methode String.format() arbeitet genauso. Damit können Sie also sehr
     viele oft benutzte Formatierungen von Zahlen oder Datumsangaben vornehmen, ohne selbst
     Strings „zusammenbasteln“ zu müssen oder verschiedene Formatter-Objekte erstellen zu
     müssen. Es lohnt sich, diese Klasse/Methoden zumindest im Hinterkopf zu haben.
     * @param args
     */
    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);

        String input = "";

        while (!input.equals("0")) {
            System.out.print("Bitte Dateinmane eingeben: (0 zum Schliessen)");
            String dateiname = scanner.next();

            if (dateiname.equals("0")) return;

            Path file = Paths.get(dateiname + ".txt");

            boolean dateiBereitsVorhanden = Files.exists(file);

            try (PrintWriter pw = new PrintWriter(new FileWriter(file.toAbsolutePath().toFile()))) {

                if (dateiBereitsVorhanden) {
                    System.out.println("Ueberschreibe Datei: " + file.toAbsolutePath().toFile());
                } else {
                    System.out.println("Neue Datei erstellt: " + file.toAbsolutePath().toFile());
                }

                Integer integer = null;

                while (integer == null) {
                    try {
                        System.out.print(System.lineSeparator() + "Bitte einen Integer Wert eingeben (0 zum schliessen): ");
                        integer = Integer.parseInt(scanner.next());

                        if (integer == 0) return;
                    } catch (NumberFormatException e) {
                        System.out.println("Dies ist kein Integer, bitte Integer eingeben");
                    }
                }

                pw.printf("%d" + System.lineSeparator(), integer);
                pw.printf("%012d" + System.lineSeparator(), integer);
                pw.printf("%+,d" + System.lineSeparator(), integer);
                pw.printf("%X" + System.lineSeparator(), integer);

                Float floatingPoint = null;

                while (floatingPoint == null) {
                    try {
                        System.out.print(System.lineSeparator() + "Bitte ein float eingeben (0 zum schliessen): ");
                        floatingPoint = Float.parseFloat(scanner.next());

                        if (floatingPoint == 0) return;
                    } catch (NumberFormatException e) {
                        System.out.println("Dies ist kein Float, bitte Float eingeben");
                    }
                }

                pw.printf("%f" + System.lineSeparator(), floatingPoint);
                pw.printf("%+.5f" + System.lineSeparator() , floatingPoint);
                pw.printf("%e" + System.lineSeparator(), floatingPoint); //7
                pw.printf(Locale.US, "%.2f" + System.lineSeparator() , floatingPoint);//8

                pw.printf("%1$tA %1$te %1$tB %1$tY" + System.lineSeparator(), LocalDate.now());//9

                pw.printf(Locale.ITALY, "%1$tA %1$te %1$tB %1$tY" + System.lineSeparator(), LocalDate.now());
                pw.printf(Locale.US, "%1$tI:%1$tM %1$tp" + System.lineSeparator(), LocalTime.now());

                System.out.print(System.lineSeparator() + "Mit beliebigen Char fortfahren (0 zum schliessen): " );
                input = scanner.next();
            } catch (IOException e) {
                System.out.println("Datei existiert nicht, ist in einem anderen Verzeichnis oder kann nicht ge�ffnet " +
                        "werden.");
                e.printStackTrace(System.out);
                continue;
            }
        }
        scanner.close();
    }
}
