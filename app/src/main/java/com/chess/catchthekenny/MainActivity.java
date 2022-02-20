package com.chess.catchthekenny;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView scoreText;

    ImageView picture1;
    ImageView picture2;
    ImageView picture3;
    ImageView picture4;
    ImageView picture5;
    ImageView picture6;
    ImageView picture7;
    ImageView picture8;
    ImageView picture9;

    ImageView[] imageArray;// Resimlerimizi tutacagimiz bir dizi tanimliyoruz global olarak.

    Runnable runnable;
    Handler handler;

    int score; // Kazanilan puanlari tutan degisken.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(),"Go Catch!",Toast.LENGTH_SHORT).show();

        picture1 = findViewById(R.id.picture1);
        picture2 = findViewById(R.id.picture2);
        picture3 = findViewById(R.id.picture3);
        picture4 = findViewById(R.id.picture4);
        picture5 = findViewById(R.id.picture5);
        picture6 = findViewById(R.id.picture6);
        picture7 = findViewById(R.id.picture7);
        picture8 = findViewById(R.id.picture8);
        picture9 = findViewById(R.id.picture9);

        imageArray = new ImageView[] {picture1,picture2,picture3,picture4,picture5,picture6,picture7,picture8,picture9};// Bu sekilde resimlerimizi bir dizide topladik ki dongu ile random olarak gorunur yapabilelim.
        hideImages();// Program acilir acilmaz yukaridaki diziyi saklasin diye asagida olusturudugumuz methodu onCreate() altinda da sakliyoruz.

        // initialize ediyoruz degiskenlerimizi --> başlatıyoruz, kullanilabilir hale getiriyoruz.

        textView = findViewById(R.id.textView);
        scoreText = findViewById(R.id.textView2);// Skor arttirma text'imizi kullanilabilir hale getirdik.

        score = 0;// Program ilk baslatildiginda puanimiz 0 olur.


        // Sayac kismi:  Sayac calisirken ve bittikten sonra neler olacaginin ayarlanmasi
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textView.setText("Kalan Süre: " + millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {// Sayac bittikten sonraki durumlar icin.

                textView.setText("Zaman Doldu!");// Sayac sonlandiginda bu yazi ciksin.

                handler.removeCallbacks(runnable);// Sayac sonlandiginda runnable'i da "etkisiz hale" getirsin.
                for(ImageView image: imageArray){// Sayac sonlandiginda, "resimlerinin tamaminin gorunmez olmasini" sagladik.

                    image.setVisibility(View.INVISIBLE);// Dizideki tum resimleri dongu sayesinde "gorunmez" yaptik ve setVisibility() methodu ile.
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);// Oyunu tekrar oynamak ister misiniz? temali bir uyari yazisi cikaracagiz sure bitince.

                    alert.setTitle("Tekrar oynamak ister misiniz?");
                    alert.setMessage("Emin misiniz?");
                    alert.setCancelable(false);// Bu ifade, uyari mesajimizin ekranda bos bir yere tiklandiginda kapanmasini engeller. Sona degil basa eklenir.

                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {// Tekrar oynamak isteniyorsa.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {// Evet' e tiklanirsa olacaklari yazariz bu method icerisine.

                        // restart icin (Programi tekrar baslatmak yani onCreate kismina tekrar donmek icin.)

                        // NOT: Programi kapatip acmadan tekrar aktiviteyi nasil baslatabilirim? diye ARAŞTIR!!

                        /*Intent intent = getIntent();
                        finish();// Bu method ile tum aktiviteyi bitiririz. NOT: Cok kullanilmaz ama bu method. BASKA YOLLARINA DA BAK!!
                        startActivity(intent);// Aktiviteyi tekrar baslattik inent nesnesini de yukarida tanimlayarak.
                        */


                    }
                });

                alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {// Hayır' a tiklanirsa olacaklari yazariz bu method icerisine.
                        Toast.makeText(MainActivity.this, "Oyun Bitti!", Toast.LENGTH_SHORT).show();
                        //finish();// Bu method uygulamayi kapatir. Tek ekran ve bolum olmayacagi icin uygulama, aslinda burada yazmaya gerek yoktur.
                    }
                });
                alert.setCancelable(false);// Bu ifade, uyari mesajimizin gozuktugunde ekranda bos bir yere tiklandiginda kapanmasini engeller. Mutlaka Evet/Hayır birini seçmemizi ister bu şekilde.
                alert.show();// En sona da "uyari mesajinin gosterilmesi icin" bu methodu ekleriz.


            }
        }.start();


    }

      // Skor kismi :
      public void increaseScore(View view){// Resimlerimizin onclick isimlerinden method olusturduk. "Resimlere tiklandikca 1 puan kazanilmasini" saglayacagiz.

           scoreText.setText("Skor: " + score);
           score++;// Resimlerden birine her tiklandiginda score degeri 1 artar ve puanimiz 1 artmis olur.
           scoreText.setText("Skor: " + score);
      }

      // Resimlerin random sekilde gorunebilir olmasi :
      public void hideImages(){

        handler = new Handler();// handler nesnemizi olusturduk.

         runnable = new Runnable() {// runnable nesnemizi olusturduk.
             @Override
             public void run() {// Bu kisim cok onemli!! Random sekilde resim goruntulenmesini yaptik!
                 for(ImageView image: imageArray){

                     image.setVisibility(View.INVISIBLE);// Dizideki tum resimleri dongu sayesinde "gorunmez" yaptik ve setVisibility() methodu ile.
                 }

                 Random random = new Random();// Resimlerin rastgele bir bicimde gorunur olmasi icin (random sekilde) bir nesne tanimliyoruz.
                 int i = random.nextInt(9);// random degiskeni ile iliskilendirdigimiz .nextInt methodu ile 0-9 arasi (9 tane rakam) bir rakam atanacak i degiskenine random olarak.
                 imageArray[i].setVisibility(View.VISIBLE);// Dizimizin indislerindeki resimlerimizi, random olarak gelen sayilara gore "rastgele bir tanesinin gorunur olmasini" sagladik.

                 handler.postDelayed(runnable,500);// runnable'a isaret ediyoruz. --> NOT: this yazsaydik da runnable methodunun icerisinde oldugumuz icin (run) ayni gorevi gorurdu.
                                                             // Yarım saniye gecikmeyle resimlerin gosterilmesini sagliyoruz .postDelayed methodu ile.
                                                             // NOT: Yarım saniye yapmamızın sebebi, zor tıklansın ki skor arttırmak da zor olsun oyun icerisinde.
             }
         };

         handler.post(runnable);// Son olarak da .post methodu ile handle sayesinde runnable'imizin calismasini sagliyoruz.



      }

}


