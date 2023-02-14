package com.othamae.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;


public class Juego extends Activity {

    //Variables componentes vista
    ImageButton imb00, imb01, imb02, imb03, imb04, imb05, imb06, imb07, imb08, imb09, imb10, imb11, imb12, imb13, imb14, imb15;
    ImageButton[] tablero = new ImageButton[16];
    Button botonReiniciar, botonSalir;
    TextView textoPuntuacion;
    int puntuacion;
    int aciertos;
    //boolean firstTime = true;

    boolean [] destapado = new boolean[16];
    int fichaActual = -1;


    //Imagenes
    int[] imagenes;
    int fondo;

    //Variables del juego
    ArrayList<Integer> arrayDesordenado;
    ImageButton primero;
    int numPrimero, numSegundo;
    boolean bloqueo = false;
    final Handler handler = new Handler();

    boolean primeraAbierta = false;
    boolean segundaAbierta = false;
    boolean firstTime= true;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);
        init();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Punctuation", puntuacion);
        outState.putIntegerArrayList("arraydes", arrayDesordenado);
        outState.putBooleanArray("estado", destapado);
        outState.putInt("actual", fichaActual);
        outState.putInt("primeraCarta", primeraCarta);
        outState.putInt("segundaCarta", segundaCarta);
        outState.putInt("aciertos", aciertos);
        outState.putBoolean("primeraAbierta", primeraAbierta);
        outState.putBoolean("segundaAbierta", segundaAbierta);
        outState.putBoolean("firstTime", firstTime);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        puntuacion = savedInstanceState.getInt("Punctuation");
        textoPuntuacion.setText("Score: "+ puntuacion);
        arrayDesordenado = savedInstanceState.getIntegerArrayList("arraydes");
        destapado = savedInstanceState.getBooleanArray("estado");
        fichaActual = savedInstanceState.getInt("actual");
        aciertos = savedInstanceState.getInt("aciertos");
        primeraAbierta = savedInstanceState.getBoolean("primeraAbierta");
        segundaAbierta = savedInstanceState.getBoolean("segundaAbierta");
        firstTime = savedInstanceState.getBoolean("firstTime");
        primeraCarta = savedInstanceState.getInt("primeraCarta");
        segundaCarta = savedInstanceState.getInt("segundaCarta");
        if (primeraAbierta && !segundaAbierta){
            primero = tablero[primeraCarta];
        }
        for (int i = 0; i < 16; i++) {
            if (destapado[i]){
                tablero[i].setImageResource(imagenes[arrayDesordenado.get(i)]);
                tablero[i].setEnabled(false);
            }
        }
    }



    //METODOS

    private void cargarTablero(){
        imb00 = findViewById(R.id.boton00);
        imb01 = findViewById(R.id.boton01);
        imb02 = findViewById(R.id.boton02);
        imb03 = findViewById(R.id.boton03);
        imb04 = findViewById(R.id.boton04);
        imb05 = findViewById(R.id.boton05);
        imb06 = findViewById(R.id.boton06);
        imb07 = findViewById(R.id.boton07);
        imb08 = findViewById(R.id.boton08);
        imb09 = findViewById(R.id.boton09);
        imb10 = findViewById(R.id.boton10);
        imb11 = findViewById(R.id.boton11);
        imb12 = findViewById(R.id.boton12);
        imb13 = findViewById(R.id.boton13);
        imb14 = findViewById(R.id.boton14);
        imb15 = findViewById(R.id.boton15);

        tablero[0] = imb00;
        tablero[1] = imb01;
        tablero[2] = imb02;
        tablero[3] = imb03;
        tablero[4] = imb04;
        tablero[5] = imb05;
        tablero[6] = imb06;
        tablero[7] = imb07;
        tablero[8] = imb08;
        tablero[9] = imb09;
        tablero[10] = imb10;
        tablero[11] = imb11;
        tablero[12] = imb12;
        tablero[13] = imb13;
        tablero[14] = imb14;
        tablero[15] = imb15;

    }

    private void cargarBotones(){
        botonReiniciar = findViewById(R.id.boton_juego_reiniciar);
        botonSalir = findViewById(R.id.boton_juego_salir);
        botonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void cargarTexto(){
        textoPuntuacion = findViewById(R.id.texto_puntuacion);
        puntuacion = 0;
        aciertos = 0;
        textoPuntuacion.setText("Score: "+ puntuacion);
    }

    private void cargarImagenes(){
        imagenes = new int[]{
                R.drawable.imagen01,
                R.drawable.imagen02,
                R.drawable.imagen03,
                R.drawable.imagen04,
                R.drawable.imagen05,
                R.drawable.imagen06,
                R.drawable.imagen07,
                R.drawable.imagen08
        };
        fondo = R.drawable.fondocuadrado;
    }

    private ArrayList<Integer> barajar(int longitud){
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i =0; i<longitud*2; i++){
            result.add(i % longitud);
        }
        Collections.shuffle(result);
        //System.out.println(Arrays.toString(result.toArray()));
        return result;
    }

    int primeraCarta ;
    int segundaCarta ;

    private void comprobar(int i, final ImageButton img){
        if (primero == null){
            primero = img;
            primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
            primero.setImageResource(imagenes[arrayDesordenado.get(i)]);
            fichaActual=arrayDesordenado.get(i);
            primero.setEnabled(false);
            primeraAbierta= true;
            numPrimero = arrayDesordenado.get(i);
            primeraCarta = i;
            destapado[primeraCarta]= true;
        }else {
            bloqueo = true;
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setImageResource(imagenes[arrayDesordenado.get(i)]);
            img.setEnabled(false);
            numSegundo = arrayDesordenado.get(i);
            segundaCarta = i;
            segundaAbierta= true;
            destapado[segundaCarta]= true;
            if (numPrimero == numSegundo){
                primero = null;
                bloqueo = false;
                aciertos++;
                puntuacion++;
                textoPuntuacion.setText("Score: "+ puntuacion);
                if (aciertos == imagenes.length){
                    Toast toast = Toast.makeText(getApplicationContext(), "You Win!!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }else{
                destapado[primeraCarta]= false;
                destapado[segundaCarta]= false;
                fichaActual = -1;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        primero.setImageResource(fondo);
                        primero.setEnabled(true);
                        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        img.setImageResource(fondo);
                        img.setEnabled(true);
                        bloqueo = false;
                        primero = null;
                        puntuacion--;
                        textoPuntuacion.setText("Score: "+ puntuacion);
                    }
                },1000);
                primeraAbierta = false;
                segundaAbierta = false;

            }
        }
    }

    private void init(){
        cargarTablero();
        cargarBotones();
        cargarTexto();
        cargarImagenes();
        arrayDesordenado = barajar(imagenes.length);

        for (int i=0; i<tablero.length; i++){
            tablero[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            tablero[i].setImageResource(fondo);
            tablero[i].setTag(imagenes[arrayDesordenado.get(i)]);

        }

        for (int i= 0; i< tablero.length; i++){
            final int j = i;
            tablero[i].setEnabled(true);
            tablero[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bloqueo){
                        comprobar(j, tablero[j]);
                    }
                }
            });
        }
    }



}
