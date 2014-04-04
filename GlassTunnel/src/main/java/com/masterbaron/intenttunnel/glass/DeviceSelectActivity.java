package com.masterbaron.intenttunnel.glass;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.masterbaron.intenttunnel.router.RouterService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Van Etten on 1/4/14.
 */
public class DeviceSelectActivity extends Activity {
    private static String TAG = DeviceSelectActivity.class.getName();
    List<BluetoothDevice> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> deviceNames = new ArrayList<String>();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            devices = new ArrayList(defaultAdapter.getBondedDevices());
            for ( BluetoothDevice device : devices ) {
                deviceNames.add(device.getName());
            }
        }

        if (deviceNames.size() == 0) {
            Toast.makeText(this, "Unable to locate and Bluetooth Devices", Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            openOptionsMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int position = 0;
        for ( BluetoothDevice device : devices ) {
            menu.add(0,position, position, device.getName());
            position ++;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RouterService.setDeviceAddress(this, devices.get(item.getItemId()).getAddress());
        this.finish();
        return true;
    }

    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        this.finish();
    }
}