/**
 * Created by Mateusz on 19.12.2016.
 */
public class charek {
    public static void main(String args[]){
//        char c = 43, a = '\u0043';
//        char d = 2+1, e = 's';
//
//        System.out.println("c="+c+"\na:"+a+"\nd:"+d+"\ne:"+e);
//    int x=41;
//    int y = ~x;
//        System.out.println("x= "+x+"\ny (NIE x) = "+y);
//    int suma = x|y;
//    int roznica = x^y;
//        System.out.println("suma bitowa " + x+" i "+y+ " jest równa "+suma +
//                "\n a roznica symetryczna, to: "+roznica);
//
//        System.out.println("x= "+x+"\n po przesunieciu o 2 wyglada tak: "+ (x>>2)+"\n oryginal: "+ x+"\n po " +
//                "przesunieciu o 2 z wypelnieniem zerami: "+(x>>>2));

//        int z =5;
//        int w =3;
//        boolean a= (z<w);
//        System.out.println(a);
//
//        double h = Math.abs(-90);
//        System.out.println("abs z -90, to: "+h);



//        int jol=1;
//        do{
//            System.out.println(jol);
//            jol++;
//        }while(jol<1);
//
//        int elo=1;
//
//        while(elo<1){
//            System.out.println(elo);
//            elo++;
//        }


//    int iterator = 1;
//         for (int i = 0; i <5; i++){
//           hier: for (int j = 0 ; j<10 ; j++){
////               System.out.print("j = "+j);
//                if(j%2==0) continue hier;
//                else System.out.print((iterator++)+". ooo\n");
//                //                break hier;
//            }
////            System.out.print("\n");
//        }


    int incik = 2;
    Numer(incik);
    }
    public static void Numer(Number number){
        if (number instanceof Integer) System.out.println("TAK!");
        else System.out.println("Nie uta... ");
    }
}