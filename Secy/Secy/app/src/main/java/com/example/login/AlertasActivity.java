package com.example.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class AlertasActivity extends AppCompatActivity {

    private static final String MQTT_BROKER = "tcp://mqttservidoriotlun.cloud.shiftr.io:1883";
    private static final String MQTT_USER = "mqttservidoriotlun";
    private static final String MQTT_PASSWORD = "Santotomas2024";
    private static final String MQTT_TOPIC_CONTROL = "sensor/control";

    private MqttClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertas);

        setupMQTT();
    }

    private void setupMQTT() {
        try {
            mqttClient = new MqttClient(MQTT_BROKER, MqttClient.generateClientId(), null);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MQTT_USER);
            options.setPassword(MQTT_PASSWORD.toCharArray());
            mqttClient.connect(options);

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d("MQTT", "Conexión perdida");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    Log.d("MQTT", "Mensaje recibido: " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d("MQTT", "Entrega completa");
                }
            });

            mqttClient.subscribe(MQTT_TOPIC_CONTROL);

        } catch (MqttException e) {
            Log.e("MQTT", "Error al configurar el cliente MQTT", e);
        }
    }

    public void activarLuz(View view) {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.publish(MQTT_TOPIC_CONTROL, "1".getBytes(), 0, false);
                Snackbar.make(view, "Luz activada", Snackbar.LENGTH_SHORT).show();
            }
        } catch (MqttException e) {
            Log.e("MQTT", "Error al enviar mensaje MQTT", e);
        }
    }

    public void apagarLuz(View view) {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.publish(MQTT_TOPIC_CONTROL, "0".getBytes(), 0, false);
                Snackbar.make(view, "Luz apagada", Snackbar.LENGTH_SHORT).show();
            }
        } catch (MqttException e) {
            Log.e("MQTT", "Error al enviar mensaje MQTT", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mqttClient != null) {
                mqttClient.disconnect();
            }
        } catch (MqttException e) {
            Log.e("MQTT", "Error al desconectar el cliente MQTT", e);
        }
    }
}


