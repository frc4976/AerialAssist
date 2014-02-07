public class Main {

    public static void main(String [] args) {
        JMiniMap.getMiniMap().init(Double.valueOf(args[0]), Boolean.valueOf(args[1]));
        JMiniMap.getMiniMap().start();
    }

}