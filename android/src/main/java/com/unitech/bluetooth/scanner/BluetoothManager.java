package com.unitech.bluetooth.scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BluetoothManager {

  // Get the status of target scanner
  public static final String API_GET_TARGET_SCANNER_STATUS = "unitech.scanservice.bluetooth.get_target_scanner";

  // Get pairing barcode comtent
  public static final String API_GET_PAIRING_BARCODE = "unitech.scanservice.bluetooth.get_pairing_barcode";

  // Get the list of connected scanners
  public static final String API_GET_CONNECTED_SCANNERS = "unitech.scanservice.bluetooth.get_scanners";

  // Set the target operation scanner if there are multiple scanners connected
  public static final String API_SET_CONNECTED_SCANNER = "unitech.scanservice.bluetooth.set_scanner";

  // Ask scanner to forget the pairing information with current Android device
  public static final String API_UNPAIR_CURRENT_SCANNER = "unitech.scanservice.bluetooth.unpair";

  // Get scanner serial number
  public static final String API_GET_SCANNER_SERIAL_NUMBER = "unitech.scanservice.bluetooth.get_sn";

  // Get scanner BT name
  public static final String API_GET_SCANNER_BLUETOOTH_NAME = "unitech.scanservice.bluetooth.get_name";

  // Get scanner BT MAC address
  public static final String API_GET_SCANNER_BLUETOOTH_MAC = "unitech.scanservice.bluetooth.get_address";

  // Get scanner firmware version
  public static final String API_GET_SCANNER_FIRMWARE_VERSION = "unitech.scanservice.bluetooth.get_fw";

  // Get battery level, 0:charging, 1:very low, 2:low, 3:ok, 4:full
  public static final String API_GET_BATTERY = "unitech.scanservice.bluetooth.get_battery";

  // Get scanner trigger key is enable/disable
  public static final String API_GET_TRIG = "unitech.scanservice.bluetooth.get_trig";                 //1

  // Enable/Disable scanner trigger key. When the trigger is disable, the scanner does not scan when users press trigger key.
  public  static final String API_SET_TRIG = "unitech.scanservice.bluetooth.set_trig";                 //2

  // Ask scanner to start decode which is the same as users press the physical scanner trigger key.
  // Together with Stop Decode can achieve remote trigger capability.
  public static final String API_SCANNER_TRIGGER_START = "unitech.scanservice.bluetooth.start_decode";

  // Ask scanner to stop decode which is the same ase users release the physical scanner trigger key.
  // Together with Start Decode can achieve remote trigger capability.
  public static final String API_SCANNER_TRIGGER_STOP = "unitech.scanservice.bluetooth.stop_decode";

  // Configure Scanner to require ACK or not. When Data ACK/NAK is configured as ON,
  // scanner waits for ACK/NAK while transmitting each data to the hot.
  // If scanner does not receive ACK in three seconds, it will retransmit data at most three times.
  // Note: this configuration is valid only when the scanner is in BT SPP mode and Auto Connection is ON.
  public static final String API_SET_DATA_ACK = "unitech.scanservice.bluetooth.set_ack";

  public static final String API_GET_DATA_ACK = "unitech.scanservice.bluetooth.get_ack";

  // Configure Scanner to automatically connect to the host or not.
  // When Auto Connection is configured as OFF, scanner will not proactively connect to
  // any device but waiting for other devices to connect.
  // Note: this configuration is valid when the scanner is in BT SPP mode.
  public static final String API_SET_AUTO_CONNECTION = "unitech.scanservice.bluetooth.set_auto_conn";

  public static final String API_GET_AUTO_CONNECTION = "unitech.scanservice.bluetooth.get_auto_conn";

  // Configure Scanner Symbology via SSI command.
  public static final String API_GET_CONFIG = "unitech.scanservice.bluetooth.get_config";             //3

  public static final String API_SET_CONFIG = "unitech.scanservice.bluetooth.set_config";             //4

  // When scanner DATA ACK/NAK is enabled, ACK is required to be sent to the scanner.
  // Some indicators can be sent along with the ACK or can be sent to the scanner independently.
  public static final String API_SET_INDICATOR = "unitech.scanservice.bluetooth.set_indicator";

  // Configure Scanner Bluetooth signal checking level
  public static final String API_GET_BLUETOOTH_SIGNAL_CHECKING_LEVEL = "unitech.scanservice.bluetooth.get_bt_signal_checking_level";

  public static final String API_SET_BLUETOOTH_SIGNAL_CHECKING_LEVEL = "unitech.scanservice.bluetooth.set_bt_signal_checking_level";

  public static final String API_GET_TARGET_SCANNER_STATUS_REPLY = "unitech.scanservice.bluetooth.get_target_scanner_reply";

  public static final String API_GET_CONNECTED_SCANNERS_REPLY = "unitech.scanservice.bluetooth.get_scanners_reply";

  public static final String API_GET_PAIRING_BARCODE_REPLY = "unitech.scanservice.bluetooth.get_pairing_barcode_reply";

  public static final String API_GET_SCANNER_SERIAL_NUMBER_REPLY = "unitech.scanservice.bluetooth.get_sn_reply";

  public static final String API_GET_SCANNER_BLUETOOTH_NAME_REPLY = "unitech.scanservice.bluetooth.get_name_reply";

  public static final String API_GET_SCANNER_BLUETOOTH_MAC_REPLY = "unitech.scanservice.bluetooth.get_address_reply";

  public static final String API_GET_SCANNER_FIRMWARE_VERSION_REPLY = "unitech.scanservice.bluetooth.get_fw_reply";

  public static final String API_GET_BATTERY_REPLY = "unitech.scanservice.bluetooth.get_battery_reply"; //8

  public static final String API_GET_TRIG_REPLY = "unitech.scanservice.bluetooth.get_trig_reply";

  public static final String API_GET_DATA_ACK_REPLY = "unitech.scanservice.bluetooth.get_ack_reply";

  public static final String API_GET_AUTO_CONNECTION_REPLY = "unitech.scanservice.bluetooth.get_auto_conn_reply";

  public static final String API_GET_CONFIG_REPLY = "unitech.scanservice.bluetooth.get_config_reply";

  public static final String API_GET_BLUETOOTH_SIGNAL_CHECKING_LEVEL_REPLY = "unitech.scanservice.bluetooth.get_bt_signal_checking_level_reply";

  public static final String API_FINISH_USU ="unitech.scanservice.bluetooth.exit";

  public static final String API_EXPORT_SETTINGS ="unitech.scanservice.bluetooth.export_settings";

  public static final String API_IMPORT_SETTINGS ="unitech.scanservice.bluetooth.import_settings";

  public static final String API_UPLOAD_ALL_SETTINGS ="unitech.scanservice.bluetooth.upload_all_settings";

  public static final String API_DATA_CODETYPE ="com.unitech.bluetooth.dataCodeType";

  public static final String API_CHANGE_TO_SSI_FORMAT="com.unitech.bluetooth.changeToSSI";

  public static final String API_CHANGE_TO_RAW_FORMAT="com.unitech.bluetooth.changeToRAW";

  public static final String API_GET_FORMAT ="com.unitech.bluetooth.getFormat";

  public static final String API_GET_FORMAT_REPLY="com.unitech.bluetooth.getFormat_reply";

  public static final String API_TARGET_SCANNER="unitech.scanservice.bluetooth.target_scanner_callback";

  private Activity mActivity;

  public BluetoothManager(Activity activity) {
    mActivity = activity;
  }

  /**
   * Get Connected Scanner List
   */
  public void GetConnectedScanners() {
    mActivity.sendBroadcast(new Intent().setAction((API_GET_CONNECTED_SCANNERS)));
  }

  public void GetPairingBarcode() {
    mActivity.sendBroadcast(new Intent().setAction((API_GET_PAIRING_BARCODE)));
  }

  /**
   * Get Connected Scanner List
   */
  public void GetTargetScanner() {
    mActivity.sendBroadcast(new Intent().setAction((API_GET_TARGET_SCANNER_STATUS)));
  }

  /**
   * Connect to scanner which serial number is sn
   *
   * @param sn the scanner's serial number
   */
  public void ConnectScanner(String sn) {
    mActivity.sendBroadcast(new Intent().setAction(API_SET_CONNECTED_SCANNER).putExtra("serialNo", sn));
  }

  /**
   * Unpair currect connected scanneer
   */
  public void Unpair() {
    mActivity.sendBroadcast(new Intent().setAction(API_UNPAIR_CURRENT_SCANNER));
  }

  /**
   * Get connected scanner's serial number
   */
  public void GetScannerSerialNumber() {
    mActivity.sendBroadcast(new Intent().setAction(API_GET_SCANNER_SERIAL_NUMBER));
  }

  /**
   * Get connected scanner's bluetooth name
   */
  public void GetScannerBluetoothName() {
    mActivity.sendBroadcast(new Intent().setAction(API_GET_SCANNER_BLUETOOTH_NAME));
  }

  /**
   * Get connected scanner's bluetooth MAC address
   */
  public void GetScannerBluetoothMAC() {
    mActivity.sendBroadcast(new Intent().setAction(API_GET_SCANNER_BLUETOOTH_MAC));
  }

  /**
   * Get connected scanner's firmware version
   */
  public void GetScannerFirmwareVersion() {
    mActivity.sendBroadcast(new Intent().setAction(API_GET_SCANNER_FIRMWARE_VERSION));
  }

  /**
   * Get connected scanner's battery status
   */
  public void GetBattery() {
    mActivity.sendBroadcast(new Intent().setAction(API_GET_BATTERY));
  }

  /**
   * Enable/Disable connected scanner's trigger
   */
  public void SetTrigger(boolean status) {
    mActivity.sendBroadcast(new Intent().setAction(API_SET_TRIG).putExtra("trig", status));
  }

  /**
   * Get connected scanner's trigger status
   */
  public void GetTrigger() {
    mActivity.sendBroadcast(new Intent().setAction(API_GET_TRIG));
  }

  /**
   * Scanner Start Scan
   */
  public void StartDecode() {
    mActivity.sendBroadcast(new Intent().setAction(API_SCANNER_TRIGGER_START));
  }

  /**
   * Scanner Stop Scan
   */
  public void StopDecode() {
    mActivity.sendBroadcast(new Intent().setAction(API_SCANNER_TRIGGER_STOP));
  }

  /**
   * Enable/Disable the ACK
   */
  public void SetACKStatus(boolean ackEnable) {
    mActivity.sendBroadcast(new Intent().setAction(API_SET_DATA_ACK).putExtra("ack", ackEnable));
  }

  /**
   * Get connected scanner's ACK status
   */
  public void GetACKStatus() {
    mActivity.sendBroadcast(new Intent().setAction(API_GET_DATA_ACK));
  }

  /**
   * Get connected scanner's auto connection status
   */
  public void GetAutoConnectionStatus() {
    mActivity.sendBroadcast(new Intent().setAction(API_GET_AUTO_CONNECTION));
  }

  /**
   * Enable/Disable connected scanner's auto connection
   * @param enable  Enable/Disable setting
   */
  public void SetAutoConnectionStatus(boolean enable) {
    mActivity.sendBroadcast(new Intent().setAction(API_SET_AUTO_CONNECTION).putExtra("autoConn", enable));
  }

  /**
   * Get connected scanner's configuration
   */
  public void GetConfiguration() {
    mActivity.sendBroadcast(new Intent().setAction(API_GET_CONFIG));
  }

  /**
   * Set scanner's configuration
   * @param appendix Configuration Name
   * @param value Configuration Value
   */
  public void SetConfiguration(String appendix, int value) {
    mActivity.sendBroadcast(new Intent().setAction(API_SET_CONFIG).putExtra(appendix, value));
  }

  public void SetConfiguration(Bundle bundle) {
    mActivity.sendBroadcast(new Intent().setAction(API_SET_CONFIG).putExtras(bundle));
  }

  /**
   * Set the indicator
   * @param bundle The indicator's bundle
   */
  public void SetIndicator(Bundle bundle) {
    mActivity.sendBroadcast(new Intent().setAction(API_SET_INDICATOR).putExtras(bundle));
  }

  public void SetBTSignalCheckingLevel(int level){
    mActivity.sendBroadcast(new Intent().setAction(API_SET_BLUETOOTH_SIGNAL_CHECKING_LEVEL).putExtra("btSignalCheckingLevel", level));
  }

  public void GetBTSignalCheckingLevel() {
    mActivity.sendBroadcast(new Intent().setAction(API_GET_BLUETOOTH_SIGNAL_CHECKING_LEVEL));
  }
}
