
package com.example.allis.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    MediaPlayer mySong;
    MediaPlayer mySongSensor;

    int horas, minutos, segundos, tempo_sec;

    String nomes_corredores[] = new String[5];
    int vetor_seg_total[] = new int[5];//Tempo total, em segundos, dos 5 corredores.

    //String pt="", st="",tt="", qrt="", qt="";
    //String Pnome = "", Snome = "", Tnome = "", QRnome = "", Qnome = "";

    Calendar Relogio = Calendar.getInstance();
    SimpleDateFormat hr = new SimpleDateFormat("HH");
    SimpleDateFormat mn = new SimpleDateFormat("mm");
    SimpleDateFormat sg = new SimpleDateFormat("ss");
    SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");

    String hora_inicial, minuto_inicial, segundo_inicial;
    List<String> test = new ArrayList<String>();
    int vetor_total[]
            = new int[3];
    int contador = 3;
    String horarios[] = new String[4];
    int posicao = 0;
    int sensor1 = 0, sensor2 = 0, sensor3 = 0;
    int cont = 0;
    TextView textView;

    public void StartTime(View view){

        Calendar Relogio = Calendar.getInstance();
        textView = (TextView) findViewById(R.id.lblHello);

        textView.setText(dt.format(Relogio.getTime()));

        hora_inicial = hr.format(Relogio.getTime());
        minuto_inicial = mn.format(Relogio.getTime());
        segundo_inicial = sg.format(Relogio.getTime());

        horas = Integer.parseInt(hora_inicial);
        minutos = Integer.parseInt(minuto_inicial);
        segundos = Integer.parseInt(segundo_inicial);
        tempo_sec = 3600*horas + 60*minutos + segundos;


        ListView lista = (ListView) findViewById(R.id.lstTempo);
        ArrayList<String> equipes = RemoverDados();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, equipes);
        lista.setAdapter(arrayAdapter);
        contador = 0;

        if(posicao == 5){
            posicao = 0;
            ListView lista1 = (ListView) findViewById(R.id.lstPosicao);
            ArrayList<String> corredores = RemoverDados();
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, corredores);
            lista1.setAdapter(arrayAdapter1);

        }

        mySong.start();
    }//Função corretíssima


    public void name() {
        if (contador == 0) {
            for (int i = 0; i <= posicao; i++) {
                EditText editText = (EditText) findViewById(R.id.NomePiloto);
                Editable newTxt = (Editable) editText.getText();
                nomes_corredores[posicao] = newTxt.toString();
            }
        }
    }
// name();

    public void Ordenar(View view){

            ListView lstPos3 = (ListView) findViewById(R.id.lstPosicao);
            ArrayList<String> posicoes3 = RemoverDados();
            ArrayAdapter<String> arrayAdapterPos3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, posicoes3);
            lstPos3.setAdapter(arrayAdapterPos3);

            posicoes3 = ordenarPosicoes();
            arrayAdapterPos3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, posicoes3);
            lstPos3.setAdapter(arrayAdapterPos3);

    }
    public void click() {
//Parte Sensores Início Modificar #preguiça
        if(contador == 0)
            name();

        if (contador < 3 && posicao < 5) {

            ListView lista = (ListView) findViewById(R.id.lstTempo);
            ArrayList<String> equipes = preencherDados();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, equipes);
            lista.setAdapter(arrayAdapter);

            if (contador == 2) {

                ListView lstPos = (ListView) findViewById(R.id.lstPosicao);
                ArrayList<String> posicoes = preencherPosicoes();
                ArrayAdapter<String> arrayAdapterPos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, posicoes);
                lstPos.setAdapter(arrayAdapterPos);

            }

            contador++;
        }

    }

    private ArrayList<String> ordenarPosicoes(){

        ArrayList<String> dados = new ArrayList<String>();
        int tempo[] = new int[5];
        int tempo_ordenado[] = new int[4];
        int vetor_auxiliar[] = {0,0,0,0,0};
        for(int i = 0; i < 5; i++)
            tempo[i] = vetor_seg_total[i];

        Arrays.sort(tempo);

int io =3;

        for(int i = 0; i < 5; i++) {

            if((tempo[i] == vetor_seg_total[0]) && vetor_auxiliar[0] == 0) {
                tempo_ordenado = convertendo_tempo(tempo[i]);
                dados.add((i+1)+" Lugar :" + nomes_corredores[0]+"|Tempo :"+tempo_ordenado[0]+":"+tempo_ordenado[1]+":"+tempo_ordenado[2]);
                vetor_auxiliar[0] = 1;
            }
            if((tempo[i] == vetor_seg_total[1]) && vetor_auxiliar[1] == 0) {
                tempo_ordenado = convertendo_tempo(tempo[i]);
                dados.add((i+1)+" Lugar :" + nomes_corredores[1]+"|Tempo :"+tempo_ordenado[0]+":"+tempo_ordenado[1]+":"+tempo_ordenado[2]);
                vetor_auxiliar[1] = 1;
            }
            if((tempo[i] == vetor_seg_total[2])  && vetor_auxiliar[2] == 0) {
                tempo_ordenado = convertendo_tempo(tempo[i]);
                dados.add((i+1)+" Lugar :" + nomes_corredores[2]+"|Tempo :"+tempo_ordenado[0]+":"+tempo_ordenado[1]+":"+tempo_ordenado[2]);
                vetor_auxiliar[2] = 1;
            }
            if((tempo[i] == vetor_seg_total[3])  && vetor_auxiliar[3] == 0) {
                tempo_ordenado = convertendo_tempo(tempo[i]);
                dados.add((i+1)+" Lugar :" + nomes_corredores[3]+"|Tempo :"+tempo_ordenado[0]+":"+tempo_ordenado[1]+":"+tempo_ordenado[2]);
                vetor_auxiliar[3] = 1;
            }
            if((tempo[i] == vetor_seg_total[4]) && vetor_auxiliar[4] == 0) {
                tempo_ordenado = convertendo_tempo(tempo[i]);
                dados.add((i+1)+" Lugar :" + nomes_corredores[4]+"|Tempo :"+tempo_ordenado[0]+":"+tempo_ordenado[1]+":"+tempo_ordenado[2]);
                vetor_auxiliar[4] = 1;
            }


        }
        return dados;
    }

    private ArrayList<String> preencherPosicoes(){

        ArrayList<String> dados = new ArrayList<String>();

        int tempo[] =  new int[4];

        for(int i = 0; i < posicao; i++){
            tempo = convertendo_tempo(vetor_seg_total[i]);
            dados.add("Corredor "+(i+1)+": "+nomes_corredores[i]+" | Tempo: "+tempo[0]+" : "+tempo[1]+" : "+tempo[2]);
        }

        return dados;
    }

    private ArrayList<String> RemoverDados(){
        ArrayList<String> dados = new ArrayList<String>();
        dados.add("");
        return dados;
    }

    private void tempo_segundos(int tempos[]){//Não Mexer, função correta
        if(posicao == 0)
            vetor_seg_total[0] = tempos[3];
        if(posicao == 1)
            vetor_seg_total[1] = tempos[3];
        if(posicao == 2)
            vetor_seg_total[2] = tempos[3];
        if(posicao == 3)
            vetor_seg_total[3] = tempos[3];
        if(posicao == 4)
            vetor_seg_total[4] = tempos[3];
    }

    private int[] convertendo_tempo(int tempo){//Não Mexer, função correta

        int tempos[] = new int[4];
        tempos[0] = tempo/3600;
        tempos[1] = (tempo % 3600)/60;
        tempos[2] = (tempo % 3600)%60;
        tempos[3] = 3600*tempos[0]+60*tempos[1]+tempos[2];

        return tempos;
    }

    private ArrayList<String> preencherDados() {//Função corretíssima

        ArrayList<String> dados = new ArrayList<String>();

        int tempos[] = new int[4];
        String hora, minuto, segundo, total;
        int hora_atual, minuto_atual, segundo_atual, tempo_sec_atual, tempo_levado;

        Calendar Relogio1 = Calendar.getInstance();

        total = dt.format(Relogio1.getTime());

        hora = hr.format(Relogio1.getTime());
        minuto = mn.format(Relogio1.getTime());
        segundo = sg.format(Relogio1.getTime());

        hora_atual = Integer.parseInt(hora);
        minuto_atual = Integer.parseInt(minuto);
        segundo_atual = Integer.parseInt(segundo);

        if (hora_atual < horas)
            hora_atual += 24;

        tempo_sec_atual = 3600 * hora_atual + 60 * minuto_atual + segundo_atual;
        tempo_levado = tempo_sec_atual - tempo_sec;

        if (contador == 0) {
            tempos = convertendo_tempo(tempo_levado);
            vetor_total[0] = tempos[3];
        }
        if (contador == 1) {
            tempos = convertendo_tempo(tempo_levado - vetor_total[0]);
            vetor_total[1] = tempos[3];
        }
        if(contador == 2) {
            tempos = convertendo_tempo(tempo_levado - (vetor_total[0] + vetor_total[1]));
            vetor_total[2] = tempos[3];
        }

        horarios[contador] = "Horário :" + total + "--" + tempos[0] + ":" + tempos[1] + ":" + tempos[2];

        for (int i = 0; i <= contador; i++)
            dados.add(horarios[i]);


        if (contador  == 2) {
            tempos = convertendo_tempo(vetor_total[0] + vetor_total[1] + vetor_total[2]);
            tempo_segundos(tempos);
            dados.add("Tempo total:" + tempos[0] + ":" + tempos[1] + ":" + tempos[2]);
            posicao++;
        }
        return dados;
    }

    BluetoothAdapter meuBluetoothAdapter = null;
    BluetoothDevice meuDevice = null;
    BluetoothSocket meuSocket =  null;
    ConnectedThread connectedThread;

    Handler mHandler;
    StringBuilder dadosBluetooth = new StringBuilder();

    private static final int SOLICITA_ATIVACAO = 1;
    private static final int SOLICITA_CONEXAO = 2;
    private static final int MESSAGE_READ = 3;


    boolean conexao = false;
    Button btnConexao;
    private static String END = null;
    UUID MEU_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConexao = (Button)findViewById(R.id.btnConexao);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        meuBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (meuBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Seu dispositivo não possui Bluetooth",Toast.LENGTH_LONG).show();
        } else if (!meuBluetoothAdapter.isEnabled()) {
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);
        }

        btnConexao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao){
                    //desconectar
                    try{
                        meuSocket.close();
                        conexao = false;
                        btnConexao.setText("Conectar");
                        Toast.makeText(getApplicationContext(), "Bluetooth foi desconectado.",Toast.LENGTH_LONG).show();
                    }catch (IOException erro){
                        Toast.makeText(getApplicationContext(), "Erro: "+erro,Toast.LENGTH_LONG).show();
                    }
                }else{
                    //conectar
                    Intent abreLista = new Intent(MainActivity.this, ListaDispositivos.class);
                    //Intent abrePlacar = new Intent(MainActivity.this, Colocacao.class);
                    startActivityForResult(abreLista, SOLICITA_CONEXAO);
                }
            }
        });
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                if(msg.what == MESSAGE_READ){

                   // String recebidos = (String) msg.obj;
                        mySongSensor.start();
                        click();

                }

            }
        };
        mySong = MediaPlayer.create(MainActivity.this, R.raw.app);
       mySongSensor = MediaPlayer.create(MainActivity.this, R.raw.sensorapp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){

            case SOLICITA_ATIVACAO:

                    if(resultCode == Activity.RESULT_OK){
                        Toast.makeText(getApplicationContext(), "O Bluetooth foi ativado.",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "O Bluetooth não foi ativado, o app será encerrado.",Toast.LENGTH_LONG).show();
                        finish();
                    }

                break;

            case SOLICITA_CONEXAO:

                if(resultCode == Activity.RESULT_OK){
                    END = data.getExtras().getString(ListaDispositivos.ENDERECO);

                    meuDevice = meuBluetoothAdapter.getRemoteDevice(END);

                    try{
                        meuSocket = meuDevice.createRfcommSocketToServiceRecord(MEU_UUID);

                        meuSocket.connect();

                        conexao = true;

                        connectedThread = new ConnectedThread(meuSocket);
                        connectedThread.start();

                        btnConexao.setText("Desconectar");
                        Toast.makeText(getApplicationContext(), "Você foi conectado com: "+END,Toast.LENGTH_LONG).show();
                    }catch (IOException erro){
                        conexao = false;
                        Toast.makeText(getApplicationContext(), "Erro: "+erro,Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Falha ao obter o endereço.",Toast.LENGTH_LONG).show();
                }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {

            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()



            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    String dadosBt = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, dadosBt).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }

        }

        public void enviar(String dadosEnviar) {
            byte[] msgBuffer = dadosEnviar.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) { }
        }

    }
}
