/**
 * Created by Mateusz on 12.12.2016.
 */
//import org.sqlite.SQLiteConnection;
//import org.sqlite.jdbc3.JDBC3PreparedStatement;

import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Month;
public class Miesiac {

    public static Connection polaczenie;
    public static Statement stmt;

    public static void main(String args[]) throws SQLException {
             /**
             * Connecting to database by connecting function.
             */
            polacz();
            Scanner wczytaj = new Scanner(System.in);
            int wybor;
            /**
             * Choose action in switch function
             */
            do{
                System.out.println("Wybierz jaka operacje chcesz wykonac:\n1. Dodac nowy miesiac\n" +
                        "2. Usunąć istniejący miesiąc \n3. Dodac godziny\n4. Sprawdz istniejace " +
                        "miesiace\n0. Wyjdź");

               wybor = wczytaj.nextInt();
            switch (wybor) {
                case 0:
                    break;
                case 1:
                    dodajMiesiac(wczytaj, stmt);
                    break;
                case 2:
                    usunMiesiac(wczytaj);
                    break;
                case 3:
                    dodajGodziny(wczytaj);
                    break;
                case 4:
                    pokaz(stmt, wczytaj, polaczenie);
                    break;

                default:
                    System.out.println("Podales numer spoza listy");
                    break;

            }}while(wybor!=0);
        polaczenie.close();
    }

    public static void polacz() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:godziny.db");
            stmt = polaczenie.createStatement();
            System.out.println("Polaczono");
        } catch (Exception e) {
            System.err.println("Blad podczas laczenia " + e);
        }
    }
    public static void dodajMiesiac(Scanner wczytaj, Statement stmt) throws SQLException {
        String data;
        start:
        while (true){
            System.out.println("Aby dodać - podaj miesiac w formie MM.YYYY (MM-numer miesiaca, YYYY-rok ROZDZIEL " +
                    "PRZECINKIEM) aby" +
                    " wyjsc wcisnij exit");
            data = wczytaj.next();
            /**
             * Checking correctness of inserted value
             */
            if (data.equals("exit"))
                break;
            else if (data.length() != 7)
                System.out.println("Blednie podana data!");
            else if (data.charAt(2) != '.')
                System.out.println("Zla skladnia!");
            else {
                int miesiac = Integer.parseInt(data.substring(0, 2));
                int rok = Integer.parseInt(data.substring(3, 7));
                String month;
                /**
                 * Equaling month number with it's name
                 */
                switch (miesiac) {
                    case 1:
                        month = "styczen";
                        break;
                    case 2:
                        month = "luty";
                        break;
                    case 3:
                        month = "marzec";
                        break;
                    case 4:
                        month = "kwiecien";
                        break;
                    case 5:
                        month = "maj";
                        break;
                    case 6:
                        month = "czerwiec";
                        break;
                    case 7:
                        month = "lipiec";
                        break;
                    case 8:
                        month = "sierpien";
                        break;
                    case 9:
                        month = "wrzesien";
                        break;
                    case 10:
                        month = "pazdziernik";
                        break;
                    case 11:
                        month = "listopad";
                        break;
                    case 12:
                        month = "grudzien";
                        break;
                    default:
                        System.out.println("Podałeś nieistniejacy miesiac!");
                        continue start;
                }
                System.out.println("miesiac: " + miesiac + " = " + month + "\nrok: " + rok);
                String nowyMiesiac = "CREATE TABLE " + month + rok + " (dzien integer PRIMARY KEY, ilosc integer);";
                System.out.println(nowyMiesiac);
                wykonajZapytanie(nowyMiesiac);
                wypelnij(miesiac, month, rok);
//                pokaz(stmt, wczytaj, polaczenie);
            }

        }
    }
    public static void usunMiesiac(Scanner wczytaj) throws SQLException{
        pokaz(stmt, wczytaj, polaczenie);
        System.out.println("Wpisz nazwe miesiaca do usuniecia.");
        String miesiac = wczytaj.next();
        String sql = "DROP TABLE "+miesiac+";";
        wykonajZapytanie(sql);
        pokaz(stmt, wczytaj, polaczenie);
    }
    public static void wykonajZapytanie(String sql){
        try{
            stmt.executeUpdate(sql);
            System.out.println("Operacja wykonana pomyślnie.");
        }
        catch (Exception e){
            System.out.println("Błąd przy wykonywaniu operacji na bazie danych.\n"+e);
        }
    }
    public static void wypelnij(int intMiesiac, String miesiac, int rok) throws SQLException{
        Month msc = Month.from(LocalDate.of(rok, intMiesiac, 1));
        int dlugosc = msc.maxLength();
        System.out.println("Podany miesiac ma "+dlugosc+" dni");
        String sql = "INSERT INTO "+miesiac+rok+"(dzien) values";
        while(dlugosc>1){
            sql +="("+dlugosc+")";
//                    if(dlugosc==1)
//                        sql+=";";
//                    else
                        sql+=",";
        dlugosc--;
        }
        sql=sql.substring(0, (sql.length()-1))+";";
        wykonajZapytanie(sql);
        String pokaz="SELECT * FROM "+miesiac+rok+";";
        wyswietl(pokaz);
    }

    public static void dodajGodziny(Scanner wczytaj) throws SQLException {
        pobierzMiesiace(stmt, wczytaj, polaczenie);
        System.out.println("Wpisz nazwe miesiaca, w którym chcesz dodać godziny");
        String miesiac = wczytaj.next();
        String sql = "SELECT * FROM "+miesiac+";";
        try{
         wyswietl(sql);
        }
        catch (Exception e){
            System.err.println("Podałeś złą nazwę miesiąca.\n"+e);
        }
        System.out.println("Wpisz numer dnia, w którym chciałbyś dodać wartość.");
        int dzien = wczytaj.nextInt();
        System.out.println("Wpisz ilość godzin jaką chcesz dodać.");
        int godziny = wczytaj.nextInt();
        String dodajSql = "UPDATE "+miesiac+" SET ILOSC = "+godziny+" WHERE DZIEN = "+dzien+";";
        wykonajZapytanie(dodajSql);
        System.out.println("Tak wyglada miesiąc po aktualizacji:");
        wyswietl(sql);
        }
    public static void pokaz(Statement stmt, Scanner wczytaj, Connection polaczenie) throws SQLException {
        pobierzMiesiace(stmt, wczytaj, polaczenie);
        String tabela = "";
        while (!tabela.equals("exit")) {
            System.out.println("tabela: " + tabela + "\nJeżeli chceszz zobaczyć jakiś miesiąc to wpisz jego nazwę a " +
                    "jeśli chcesz cos dodac wpisz dodaj. Wyjscie -" +
                    " wpisz \"exit\"");
            tabela = wczytaj.next();
            if (tabela.equals("exit"))
                break;
            else if(tabela.equals("dodaj"))
                dodajGodziny(wczytaj);
            String sql = "SELECT * FROM " + tabela + ";";
            wyswietl(sql);
        }

        System.out.println("hmmm... no to koniec");
    }
    static void wyswietl(String sql){
        try{
        ResultSet wynik = stmt.executeQuery(sql);
        while (wynik.next()) {
            int dzien = wynik.getInt("dzien");
            int ilosc = wynik.getInt("ilosc");
            System.out.println(dzien + ". " + ilosc);
        }}
        catch (Exception e){
            System.out.println("Nie można wyświetlić zawartości bazy.\n"+e);
        }
            }
    static void pobierzMiesiace(Statement stmt, Scanner wczytaj, Connection polaczenie) throws SQLException {
        try {
            DatabaseMetaData md = polaczenie.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }
        } catch (Exception e) {
            System.err.println("Nie mozna wczytac tabeli z bazy danych.\n" + e);
        }
    }
}