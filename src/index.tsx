import { NativeModules } from 'react-native';

type UnitechBluetoothScannerType = {
  multiply(a: number, b: number): Promise<number>;
};

const { UnitechBluetoothScanner } = NativeModules;

export default UnitechBluetoothScanner as UnitechBluetoothScannerType;
