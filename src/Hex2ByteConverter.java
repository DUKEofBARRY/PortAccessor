public class Hex2ByteConverter {

    public Hex2ByteConverter(){}

    /**
     * Convert hex in to bin, make port understand the port input
     * @param hexString: dictate need convert into binary
     * @return Binary array input into port
     */
    public byte[] hexConverter(String hexString){
        byte[] order = null;
        if(hexString == null){
            return null;
        }
        else{
            hexString = hexString.replaceAll(" ", "");
            int len = hexString.length();
            int index = 0;
            order = new byte[len/2];
            while (index < len){
                String sub = hexString.substring(index, index + 2);
                order[index/2] = (byte)Integer.parseInt(sub, 16);
                index += 2;
            }
        }
        return order;
    }

    public static String bytesConverter(byte[] order){
        StringBuilder stringBuilder = new StringBuilder("");
        if (order == null || order.length <= 0) {
            return null;
        }
        for (int i = 0; i < order.length; i++) {
            int v = order[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] arg){
        Hex2ByteConverter hbc = new Hex2ByteConverter();
        byte[] order = null;
        System.out.println(order = hbc.hexConverter("FF FF B6 25 00 01 FE FE")); //FF FF B6 25 00 01 FE FE
        System.out.println(Hex2ByteConverter.bytesConverter(order));
    }
}